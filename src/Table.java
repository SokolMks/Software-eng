import DatabaseConfiguration.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.layout.GridPane;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

public class Table {
    private ArrayList<Item> tableOrder;

	public Table(){
	}

	public ArrayList<Item> getOrder(int tableNum){
        Statement query;
        Statement query2;
        tableOrder = new ArrayList<>();

        try {
            query = DatabaseConnection.getConnection().createStatement();
            String sql = ("SELECT * FROM Orders WHERE TableNum = " + tableNum);
            ResultSet response = query.executeQuery(sql);

            while (response.next()) {
                query2 = DatabaseConnection.getConnection().createStatement();
                String sql2 = ("SELECT * FROM Order_Items WHERE OrderItemsID = " + response.getInt("OrderID"));
                ResultSet response2 = query2.executeQuery(sql2);

                while (response2.next()) {
                    System.out.println(response2.getInt("ItemID"));
                    tableOrder.add(new Menu().getItem(response2.getInt("ItemID")));
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return tableOrder;
    }

    // TODO - FIX
    public boolean getAvailability(int tableNum){
        Statement query;
        Statement query2;

        try {
            query = DatabaseConnection.getConnection().createStatement();
            String sql = ("SELECT * FROM Reservations WHERE TableNum = " + tableNum);
            ResultSet response = query.executeQuery(sql);

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    public boolean getPaidStatus(int tableNum){
        Statement query;

        try {
            query = DatabaseConnection.getConnection().createStatement();
            String sql = ("SELECT Paid FROM Orders WHERE TableNum = " + tableNum);
            ResultSet response = query.executeQuery(sql);

            if(response.next()) return response.getBoolean("Paid");
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean removeOrder(int tableNum){

        try {
            PreparedStatement update = DatabaseConnection.getConnection().prepareStatement("DELETE FROM Orders WHERE TableNum = '" + tableNum + "';");
            System.out.println(update.executeUpdate());

            return true;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Reservation> getReservations(int tableNum){
        Statement query;
        ArrayList<Reservation> allReservations = new ArrayList<>();

        try {
            query = DatabaseConnection.getConnection().createStatement();
            String sql = ("SELECT * FROM Reservations WHERE TableNum = " + tableNum);
            ResultSet response = query.executeQuery(sql);

            while (response.next()){
                allReservations.add(new Reservation(response.getInt("TableNum"),
                        response.getString("customerName"),
                        response.getString("mobile"),
                        response.getDate("bookingDate"),
                        response.getTime("bookingTime")));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return allReservations;
    }

    public int getTableNum(String name){
        if(name.length()>6)
            return Integer.parseInt(name.substring(6, name.length()));
        return 0;
    }

    public boolean addOrder(ArrayList<Item> list, double bill){
        int table = createAddDialog();
        Statement query;

        try {
            query = DatabaseConnection.getConnection().createStatement();
            query.execute("INSERT INTO `Orders`" +
                    "(Bill,Paid,Timestamp,TableNum) " +
                    "VALUE ('"+bill+"','"+0+"', '"+LocalDate.now()+"', '"+table+"') ");

            //addItems(list);
            return true;

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public int getLastID(){
        Statement query;

        try {
            query = DatabaseConnection.getConnection().createStatement();
            return query.executeUpdate("SELECT MAX(id) FROM Orders", Statement.RETURN_GENERATED_KEYS);

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public boolean addItems(ArrayList<Item> list){
        int table = createAddDialog();
        int id = getLastID();
        Statement query;

        try {
            query = DatabaseConnection.getConnection().createStatement();

            for(int i = 0 ; i < list.size() ;i++) {
                query.execute("INSERT INTO `Order_Items`" +
                        "(OrderItemsID, ItemID, Quantity) " +
                        "VALUE ('"+id+"','"+list.get(i).getItemID() + 1 + "') ");
            }
            return true;

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public int createAddDialog(){
        Dialog<ArrayList<String>> dialog = new Dialog<>();
        dialog.setTitle("Add order");

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        ComboBox table = new ComboBox();
        table.setPromptText("Table number: ");
        table.setItems(FXCollections.observableArrayList("1", "2","3","4","5","6","7","8","9","10","11","12"));
        gridPane.add(table, 0, 0);
        dialog.getDialogPane().setContent(gridPane);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new ArrayList<>(Collections.singletonList(table.getValue().toString()));
            }
            return null;
        });

        Optional<ArrayList<String>> result = dialog.showAndWait();
        if(result.isPresent()) return Integer.parseInt(result.get().get(0));
        return 0;
    }

}