import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AuthController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button signUpButton;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private TextField logInTextField;

    @FXML
    private Button logInButton;

    @FXML
    void initialize() {
        signUpButton.setOnAction(event -> {
            signUpButton.getScene().getWindow().hide();

            CommonAnimation.changeScene(new File("Register.fxml"), true);
        });

        logInButton.setOnAction(event -> {
            if (Transfer.getTransfer().authorization("login", logInTextField.getText().trim(), passwordTextField.getText().trim())) {
                logInButton.getScene().getWindow().hide();
                CommonAnimation.changeScene(new File("App.fxml"), false);
            }
            else {
                CommonAnimation.Shake(passwordTextField);
                CommonAnimation.Shake(logInTextField);
            }
        });
    }
}
