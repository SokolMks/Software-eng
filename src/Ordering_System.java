import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;

public class Ordering_System {

    private DecimalFormat twoPlaces;
    @FXML private BorderPane main;
    @FXML private javafx.scene.control.ListView detailPane;
    @FXML private ScrollPane scrollPane;
    @FXML private AnchorPane tablePane;
    @FXML private BorderPane leftBorderPane;
    @FXML private Button completeBtn;
    private ObservableList<String> listOfDetails;

    public Ordering_System() {
    }

    @FXML
    public void initialize() {
        detailPane = new jfxtras.scene.control.ListView(); // Initialize
        listOfDetails = FXCollections.observableArrayList("\t\t\tTABLE INFORMATION","","","","",""); // Set first row

        detailPane.setItems(listOfDetails);
        detailPane.setMinWidth(300);
        leftBorderPane.setRight(detailPane);
        setupTables();
        setupAddBtn();
    }

    public void setupAddBtn(){
        completeBtn.setOnMouseClicked(e -> removeOrder());
    }

    public void setupTables(){
        AnchorPane temp;
        HBox row;

        // i = the number of tables in the restaurant
        for(int i = 0, y = 20 , x = 60; i < 12 ; i++, x = x + 220){

            if(i != 0 && i % 4 == 0 ){
                x = 60;
                y = y + 300;
            }

            row = new HBox();
            row.setLayoutX(x);
            row.setLayoutY(y);

            temp = new AnchorPane();

            temp.getChildren().add(setupTableButton(i));
            row.getChildren().add(temp);
            tablePane.getChildren().add(row);
        }
        //
    }

    public Button setupTableButton(int count){
        Button table;

        table = new Button("Table "+(count+1));
        table.setStyle("-fx-border-radius: 30px");
        table.setStyle("-fx-background-radius: 30px");
        table.setMinWidth(200);
        table.setMinHeight(225);
        table.setOnMouseClicked(e -> getTableDetails(count+1));

        return table;
    }

    public void getTableDetails(int tableNum){
        listOfDetails.set(1,"Table "+tableNum);
        listOfDetails.set(2,"Reservations: ");

        // REFRESH DATABASE


        // CHECK RESERVATIONS
        setupReservations(tableNum);

        //DISPLAY ALL ITEMS ORDERED
        setupOrder(tableNum);

        detailPane.setItems(listOfDetails);
    }

    public void setupReservations(int tableNum){
        Table myTable = new Table();
        ArrayList<Reservation> reservations = myTable.getReservations(tableNum);
        String allReservations = "";

        for(Reservation res : reservations){
            //Check date today
            if((res.getDate().toString()).compareTo(LocalDate.now().toString()) == 0)
                allReservations += "\tName: "+res.getName()+
                        "\n\tMobile: "+res.getNumber()+
                        "\n\tTime: "+res.getTime() + "\n\n";
        }
        listOfDetails.set(3, allReservations);
    }

    public void setupOrder(int tableNum){
        ArrayList<Item> temp = new Table().getOrder(tableNum);
        String allItems = "";
        for(Item item: temp){
            allItems+= "\t"+item.getName()+ ": £" +item.getPrice()+ "\n";
        }
        listOfDetails.set(4, "Order: ");
        listOfDetails.set(5,allItems);
    }

	public void removeOrder() {
        if(listOfDetails.get(5) != "") {
            listOfDetails.set(5, "");

            // Remove order entry from database
            new Table().removeOrder(new Table().getTableNum(listOfDetails.get(1)));
        }
	}

}