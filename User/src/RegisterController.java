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

public class RegisterController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button logInButton;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private TextField logInTextField;

    @FXML
    private Button signUpButton;

    @FXML
    void initialize() {
        logInButton.setOnAction(event -> {
            logInButton.getScene().getWindow().hide();

            CommonAnimation.changeScene(new File("Auth.fxml"), true);
        });
        signUpButton.setOnAction(event -> {
            if (!logInTextField.getText().trim().isEmpty() && !passwordTextField.getText().trim().isEmpty() &&
                    Transfer.getTransfer().authorization("signup", logInTextField.getText().trim(), passwordTextField.getText().trim())) {
                signUpButton.getScene().getWindow().hide();
                CommonAnimation.changeScene(new File("App.fxml"), false);
            }
            else {
                CommonAnimation.Shake(passwordTextField);
                CommonAnimation.Shake(logInTextField);
            }
        });
    }
}
