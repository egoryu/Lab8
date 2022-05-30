import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class AnimController {

    private double maxX = 0f;
    private double maxY = 0;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Button outButton;

    @FXML
    private MenuBar menuBar;

    @FXML
    private MenuItem ChangeMode;

    @FXML
    private AnchorPane animSpace;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    void initialize() throws IOException, ClassNotFoundException {
        outButton.setOnAction(event -> {
            outButton.getScene().getWindow().hide();

            CommonAnimation.changeScene(new File("Auth.fxml"), true);
        });

        ChangeMode.setOnAction(event -> {
            outButton.getScene().getWindow().hide();

            CommonAnimation.changeScene(new File("App.fxml"), false);
        });

        maxX = animSpace.getPrefWidth();
        maxY = animSpace.getPrefHeight();
        Request request = Transfer.getTransfer().Start("get", null);
        if (request != null) {
            request.getCollection().forEach((key, value) -> {
                maxX = Math.max(maxX, value.getCoordinates().getX());
                maxY = Math.max(maxY, value.getCoordinates().getY());
            });

            animSpace.setPrefSize(maxX, maxY);
            scrollPane.setPannable(true);
            request.getCollection().forEach((key, value) -> {
                drawMonitor(value.getCoordinates().getX(), value.getCoordinates().getY());
            });
            CommonAnimation.stage.setScene(scrollPane.getScene());
        }
    }

    public void drawMonitor(Float X, Integer Y) {
        Rectangle rectangle = new Rectangle();
        rectangle.setX(X);
        rectangle.setY(Y);
        rectangle.setWidth(maxX / 5.0);
        rectangle.setHeight(maxY / 10.0);
        rectangle.setFill(Color.BLACK);

        Rectangle rectangle1 = new Rectangle();
        rectangle1.setX(X + maxX / 100.0);
        rectangle1.setY(Y + maxY / 200.0);
        rectangle1.setWidth(0.9 * maxX / 5.0);
        rectangle1.setHeight(0.9 * maxY / 10.0);
        rectangle1.setFill(Color.BLUE);

        Rectangle rectangle2 = new Rectangle();
        rectangle2.setX(X + maxX / 10.0 - 0.1 * maxX / 10.0);
        rectangle2.setY(Y + maxY / 10.0);
        rectangle2.setWidth(0.1 * maxX / 5.0);
        rectangle2.setHeight(0.5 * maxY / 10.0);
        rectangle2.setFill(Color.BLACK);

        Line line = new Line(X + 0.25 * maxX / 5.0, Y + maxY / 10.0 + 0.5 * maxY / 10.0,
                X + 0.75 * maxX / 5.0, Y + maxY / 10.0 + 0.5 * maxY / 10.0);
        line.setStrokeWidth(2);
        line.setStroke(Color.BLACK);

        animSpace.getChildren().add(rectangle);
        animSpace.getChildren().add(rectangle1);
        animSpace.getChildren().add(rectangle2);
        animSpace.getChildren().add(line);
    }
}
