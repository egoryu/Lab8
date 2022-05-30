import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class InsertController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField nameField;

    @FXML
    private Button insertButton;

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
    void initialize() {
        signUpButton.setOnAction(event -> {
            signUpButton.getScene().getWindow().hide();

            CommonAnimation.changeScene(new File("App.fxml"), false);
            CommonAnimation.closeSubStage();
        });

        insertButton.setOnAction(event -> {
            LabWork labWork = new LabWork(nameField.getText(),
                    new Coordinates(Float.parseFloat(xField.getText()), Integer.parseInt(yField.getText())),
                    Integer.parseInt(minpointField.getText()), discriptionField.getText(), Difficulty.valueOf(difficultyField.getText()),
                    new Person(personNameField.getText(), null, Integer.parseInt(heightField.getText()),
                            Integer.parseInt(weightField.getText())));
            try {
                Transfer.getTransfer().Start("insert " + keyField.getText().trim(), labWork);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            CommonAnimation.closeSubStage();
        });

        AppController.setFloatFilter(xField);
        AppController.setIntegerFilter(weightField);
        AppController.setIntegerFilter(heightField);
        AppController.setIntegerFilter(yField);
        AppController.setIntegerFilter(minpointField);
        AppController.setDateFilter(birthdayField);
    }
}
