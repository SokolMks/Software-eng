import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Main_Controller {
    private Calendar_Controller bookingsPageController;
    private Ordering_System tableOrdersController;
    private Order makeOrderController;
    @FXML private VBox mainContent;

    @FXML
    public void initialize() {
        //System.out.println("MAIN CONTROLLER");
    }

    public void logoutClicked()throws IOException{
        Pane loader = FXMLLoader.load(getClass().getResource("/Interfaces/Login/Login_GUI.fxml"));

        Stage stage = (Stage) mainContent.getScene().getWindow();
        Scene scene = new Scene(loader);
        stage.setScene(scene);
    }
}
