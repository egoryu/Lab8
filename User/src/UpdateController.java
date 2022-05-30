import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class UpdateController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField nameField;

    @FXML
    private Button updateButton;

    @FXML
    private TextField xField;

    @FXML
    private TextField yField;

    @FXML
    private TextField difficultyField;

    @FXML
    private TextField minpointField;

    @FXML
    private TextField discriptionField;

    @FXML
    private TextField personNameField;

    @FXML
    private TextField heightField;

    @FXML
    private TextField weightField;

    @FXML
    private TextField birthdayField;

    @FXML
    private TextField keyField;

    @FXML
    private TextField idField;

    @FXML
    private TextField creationField;

    @FXML
    private Button deleteButton;

    @FXML
    void initialize() {
        Table element = CommonAnimation.element;
        if (element != null) {
            keyField.setText(element.getKey());
            nameField.setText(element.getName());
            idField.setText(String.valueOf(element.getId()));
            discriptionField.setText(element.getDescription());
            creationField.setText(String.valueOf(element.getCreationDate().toLocalDate()));
            minpointField.setText(String.valueOf(element.getMinimalPoint()));
            xField.setText(String.valueOf(element.getX()));
            yField.setText(String.valueOf(element.getY()));
            personNameField.setText(element.getPersonName());
            heightField.setText(String.valueOf(element.getHeight()));
            weightField.setText(String.valueOf(element.getWeight()));
            birthdayField.setText(String.valueOf(element.getBirthday().toLocalDate()));
        }
    }
}
