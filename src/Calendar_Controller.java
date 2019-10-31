import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import jfxtras.scene.control.CalendarPicker;
import jfxtras.scene.control.agenda.Agenda;

import java.sql.Time;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Calendar_Controller extends Agenda{

	private Main_Calendar calendar = new Main_Calendar();
	private Calendar_View calendar_view = new Calendar_View();
	@FXML private BorderPane mainPane;
	@FXML private AnchorPane calendarPane;
    private Button addButton = new Button();
    private Button removeButton = new Button();
    private Button searchButton = new Button();
    private VBox leftBox;
    private Agenda lAgenda;
    private Map<String, Agenda.AppointmentGroup> lAppointmentGroupMap;

    @FXML
    public void initialize() {
        initializeAgenda();
        getCalendarReservations();
    }

    public void initializeAgenda(){
        // create agenda
        lAgenda = new Agenda();
        lAgenda.setSkin(new jfxtras.internal.scene.control.skin.agenda.AgendaWeekSkin(lAgenda));

        // create calendar picker
        CalendarPicker lCalendarPicker = new CalendarPicker();
        lCalendarPicker.setCalendar(Calendar.getInstance()); // set to today

        // bind picker to agenda
        lAgenda.displayedCalendar().bind(lCalendarPicker.calendarProperty());

        // layout scene
        leftBox = new VBox();
        leftBox.setMinWidth(250);
        leftBox.setAlignment(Pos.TOP_CENTER);
        leftBox.setSpacing(10);
        setButtons();
        leftBox.getChildren().addAll(lCalendarPicker, addButton, removeButton, searchButton);
        mainPane.setLeft(leftBox);
        mainPane.setCenter(lAgenda);
    }

    public void addClicked(){
        Dialog<ArrayList<String>> dialog = new Dialog<>();
        dialog.setTitle("Add reservation");

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        TextField name = new TextField();
        name.setPromptText("Name: ");

        TextField number = new TextField();
        number.setPromptText("Contact number: ");

        TextField table = new TextField();
        table.setPromptText("Table number: ");

        DatePicker date = new DatePicker();
        date.setPromptText("Date: ");

        ComboBox time = new ComboBox();
        time.setItems(FXCollections.observableArrayList("09:00","10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00","17:00"));


        time.setPromptText("Time: ");

        gridPane.add(table, 0, 0);
        gridPane.add(name, 0, 1);
        gridPane.add(number, 0, 2);
        gridPane.add(date, 0, 3);
        gridPane.add(time, 0, 4);

        dialog.getDialogPane().setContent(gridPane);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new ArrayList<>(Arrays.asList(table.getText(), name.getText(), number.getText(), date.getValue().toString(), time.getValue().toString()));
            }
            return null;
        });

        Optional<ArrayList<String>> result = dialog.showAndWait();
        if(result.isPresent()) addCalendarReservation(result);
    }

    public void setButtons(){
        addButton.setText("Add booking");
        addButton.setMinWidth(150);
        addButton.setTextAlignment(TextAlignment.CENTER);
        addButton.setOnAction(e -> addClicked());
        removeButton.setText("Remove booking");

        removeButton.setOnAction(e -> removeCalendarReservation());

        searchButton.setText("Search booking");

    }

    /**
     *
     * Show database
     *
     */
	public void getCalendarReservations() {
        setAppointmentGroups();
        databaseAppointments();

        // update range
        final AtomicBoolean lSkippedFirstRangeChange = new AtomicBoolean(false);
        lAgenda.calendarRangeCallbackProperty().set(arg0 -> {
            // the first change should not be processed, because it is set above
            if (!lSkippedFirstRangeChange.get())
            {
                lSkippedFirstRangeChange.set(true);
                return null;
            }

            return null;
        });
	}

	public void databaseAppointments(){

        ArrayList<Reservation> bookings = calendar.getAllReservations();

        for(Reservation reservation : bookings){
            addAppointment(reservation);
        }
    }

    public void addAppointment(Reservation reservation){
        int year, month, day, hour, minute;

        Calendar cal = Calendar.getInstance();
        cal.setTime(reservation.getDate());

        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DATE);
        hour = reservation.getTime().getHours();
        minute = reservation.getTime().getMinutes();

        lAgenda.appointments().add(new Agenda.AppointmentImpl()
                .withStartTime(new GregorianCalendar(year, month, day, hour, minute))
                .withEndTime(new GregorianCalendar(year, month, day, hour+2,minute))
                .withSummary(reservation.getName() + " : " + reservation.getNumber())
                .withAppointmentGroup(lAppointmentGroupMap.get("group"+hour)));
    }

	public void setAppointmentGroups(){
        // setup appointment groups
        lAppointmentGroupMap = new TreeMap<>();
        lAppointmentGroupMap.put("group0", new Agenda.AppointmentGroupImpl());
        lAppointmentGroupMap.put("group1", new Agenda.AppointmentGroupImpl());
        lAppointmentGroupMap.put("group2", new Agenda.AppointmentGroupImpl());
        lAppointmentGroupMap.put("group3", new Agenda.AppointmentGroupImpl());
        lAppointmentGroupMap.put("group4", new Agenda.AppointmentGroupImpl());
        lAppointmentGroupMap.put("group5", new Agenda.AppointmentGroupImpl());
        lAppointmentGroupMap.put("group6", new Agenda.AppointmentGroupImpl());
        lAppointmentGroupMap.put("group7", new Agenda.AppointmentGroupImpl());
        lAppointmentGroupMap.put("group8", new Agenda.AppointmentGroupImpl());
        lAppointmentGroupMap.put("group9", new Agenda.AppointmentGroupImpl());
        lAppointmentGroupMap.put("group10", new Agenda.AppointmentGroupImpl());
        lAppointmentGroupMap.put("group11", new Agenda.AppointmentGroupImpl());
        lAppointmentGroupMap.put("group12", new Agenda.AppointmentGroupImpl());
        lAppointmentGroupMap.put("group13", new Agenda.AppointmentGroupImpl());
        lAppointmentGroupMap.put("group14", new Agenda.AppointmentGroupImpl());
        lAppointmentGroupMap.put("group15", new Agenda.AppointmentGroupImpl());
        lAppointmentGroupMap.put("group16", new Agenda.AppointmentGroupImpl());
        lAppointmentGroupMap.put("group17", new Agenda.AppointmentGroupImpl());
        lAppointmentGroupMap.put("group18", new Agenda.AppointmentGroupImpl());
        lAppointmentGroupMap.put("group19", new Agenda.AppointmentGroupImpl());
        lAppointmentGroupMap.put("group20", new Agenda.AppointmentGroupImpl());
        lAppointmentGroupMap.put("group21", new Agenda.AppointmentGroupImpl());
        lAppointmentGroupMap.put("group22", new Agenda.AppointmentGroupImpl());
        lAppointmentGroupMap.put("group23", new Agenda.AppointmentGroupImpl());
        for (String lId : lAppointmentGroupMap.keySet())
        {
            AppointmentGroup lAppointmentGroup = lAppointmentGroupMap.get(lId);
            lAppointmentGroup.setDescription(lId);
            lAgenda.appointmentGroups().add(lAppointmentGroup);
        }
    }

	public void addCalendarReservation(Optional<ArrayList<String>> result) {
        result.ifPresent(arrayList -> {
            calendar.resetId();
            Reservation newBooking = new Reservation(Integer.parseInt(arrayList.get(0)),
                    arrayList.get(1),
                    arrayList.get(2),
                    java.sql.Date.valueOf(arrayList.get(3)),
                    Time.valueOf(arrayList.get(4)+":00"));

            // ADD TO DATABASE
            calendar.addReservation(newBooking);
            // CHECK OVERLAPS
            addAppointment(newBooking);
        });
	}

	public void removeCalendarReservation() {

	}

	public void updateCalendarView() {
        getCalendarReservations();
	}


}