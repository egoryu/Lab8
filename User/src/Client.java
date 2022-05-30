
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.*;

public class Client extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        CommonAnimation.changeScene(new File("Auth.fxml"), true);
    }
}

