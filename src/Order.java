import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Order {
	private double bill;
	private DecimalFormat twoPlaces;

	@FXML private BorderPane main;
    @FXML private AnchorPane menuPane; // Menu display
    @FXML private javafx.scene.control.ListView orderSummary;
    private ObservableList<String> listOfOrder;
    private Menu myMenu;
    private ArrayList<Item> menuItems;
    private ArrayList<Item> orderedItems;

	@FXML
    public void initialize(){
	    bill = 0.00;
        setupOrderList();
        setUpMenu();
    }

    public void setupOrderList(){
        orderedItems = new ArrayList<>();
        orderSummary = new jfxtras.scene.control.ListView(); // Initialize
        orderSummary.setMinWidth(300);
        setupRight();
        //CREATE ON DELETE
        orderSummary.setOnKeyPressed(event -> removeItems(event));
    }

    public void setupList(){
        listOfOrder = FXCollections.observableArrayList("\t\t\tORDER SUMMARY"); // Set first row
        // 2 decimal places formatter
        twoPlaces = new DecimalFormat("0.00");
        listOfOrder.add("Bill: £" + twoPlaces.format(bill)); // Set last row

        orderSummary.setItems(listOfOrder);
    }

    public void setupRight(){
        BorderPane borders = new BorderPane();
        AnchorPane bottom = new AnchorPane();
        HBox box = new HBox();
        box.setMinWidth(300);

        Button add = new Button("Add order");
        Button clear = new Button("Clear order");
        add.setMinWidth(150);
        add.setOnMouseClicked(e -> addOrder());

        clear.setMinWidth(150);
        clear.setOnMouseClicked(e -> clearList());

        box.getChildren().add(add);
        box.getChildren().add(clear);
        bottom.getChildren().add(box);
        borders.setBottom(bottom);
        borders.setCenter(orderSummary);

        setupList();
        main.setRight(borders);
    }

    public void clearList(){
        bill = 0;
        setupList();
        orderedItems = new ArrayList<>();
    }

    public void addOrder(){
        new Table().addOrder(orderedItems , bill);
    }

    public void setUpMenu(){
        myMenu = new Menu();
        menuItems = myMenu.getMenuItems();

        for(int  count = 0, i = 4, x = 60 , y = 20; count < menuItems.size(); count++, i++, x = x + 220) {
            if(i != 4 && i % 4 == 0 ){
                x = 60;
                y = y + 230;
            }

            menuPane.getChildren().add(setEachItem(count , x, y));
        }
    }

    public BorderPane setEachItem(int i, int x, int y){
        // TEMPORARY
        javafx.scene.image.ImageView image;
        Text description;

        //ANCHOR
        BorderPane eachItem = new BorderPane();
        eachItem.setLayoutX(x);
        eachItem.setLayoutY(y);
        eachItem.setMinWidth(200);
        eachItem.setMaxHeight(200);
        eachItem.setMinHeight(200);
        eachItem.setStyle("-fx-border-color: black");

        //IMAGE
        image = new javafx.scene.image.ImageView(new Image(menuItems.get(i).getImagePath()));
        image.setFitHeight(100);
        image.setFitWidth(100);

        //DESCRIPTION
        description = new Text(menuItems.get(i).getDescription());
        description.setWrappingWidth(200);

        //CREATE ONCLICK
        eachItem.setOnMouseClicked(e -> addItems(menuItems.get(i)));

        // ADD TO BORDER PANE
        eachItem.setBottom(description);
        eachItem.setCenter(image);

        return eachItem;
    }

	public void addItems(Item item) {
        listOfOrder.add("");
        int count = listOfOrder.size();

        //LIST OF ITEMS
        setBill(item.getPrice(), true);
        listOfOrder.set(count-2,item.getName()+ ": £"+twoPlaces.format(item.getPrice()));
        listOfOrder.set(count-1, "Bill: £" + twoPlaces.format(getBill()));

        //ADD TO LIST VIEW
        orderSummary.setItems(listOfOrder);
        //Add to array of ordered items
        orderedItems.add(item);
    }

//    TODO FIX THE BILL REMOVAL

	public void removeItems(KeyEvent e) {
        if(e.getCode() == KeyCode.BACK_SPACE){
            // CHECK NOT TITLE ROW OR BILL ROW
            if(orderSummary.getSelectionModel().getSelectedIndex() != 0 && orderSummary.getSelectionModel().getSelectedIndex() != listOfOrder.size()-1) {
                String temp = (String) orderSummary.getSelectionModel().getSelectedItem();
                double price = Double.parseDouble(temp.substring(temp.length() - 4, temp.length()));

                orderedItems.remove(findItemObj(temp));
                //Item item =
                //REMOVE FROM LIST
                setBill(price, false);
            }
        }
	}

	public Item findItemObj(String name){
        String[] parts = name.split(":");
        String itemName = parts[0];

        return new Menu().getItembyName(itemName);
    }

	public void setBill(double price, boolean add) {
        if(add)
            bill += price;
        else {
            bill -= price;
            listOfOrder.remove(listOfOrder.size()-2);
            listOfOrder.set(listOfOrder.size()-1, "Bill: £" + twoPlaces.format(getBill()));
            orderSummary.setItems(listOfOrder);
        }
    }


	public double getBill() {
		return this.bill;
	}


}