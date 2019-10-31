import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Catherine on 16/03/2018.
 */
public class Login_Controller extends Application{
    @FXML private ResourceBundle resources;

    @FXML private Button loginButton;
    @FXML private TextField username;
    @FXML private TextField password;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {}

    public void start(Stage stage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("/Interfaces/Login/Login_GUI.fxml"));
        stage.setTitle("Restaurant Management System");
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    public void button_clicked() throws IOException {

        if (!username.getText().equals("catherine")) {
            System.err.println("Incorrect username");
        }
        else if (!password.getText().equals("password")) {
            System.err.println("Incorrect password");
        }
        else {
            Pane loader = FXMLLoader.load(getClass().getResource("/Interfaces/Main/Main_GUI.fxml"));

            Stage stage = (Stage) loginButton.getScene().getWindow();
            Scene scene = new Scene(loader);
            stage.setScene(scene);
        }
    }

}
