import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class CommonAnimation {
    public static Stage stage;
    public static Stage subStage;
    public static Table element;

    public static void Shake(Node node) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(100), node);
        translateTransition.setFromX(0);
        translateTransition.setByX(10);
        translateTransition.setCycleCount(5);
        translateTransition.setAutoReverse(true);
        translateTransition.playFromStart();
    }

    public static void changeScene(File file, boolean resize) {
        if (stage != null && stage.isShowing())
            stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(CommonAnimation.class.getResource(file.getPath()));

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = fxmlLoader.getRoot();
        stage = new Stage();
        if (resize)
            stage.setResizable(false);
        stage.setScene(new Scene(root));

        InputStream iconStream = CommonAnimation.class.getResourceAsStream("icon.jpg");
        Image image = new Image(iconStream);
        stage.getIcons().add(image);

        stage.setTitle("Created by Nikitin Egor P3117");

        stage.show();
    }

    public static void changeSubScene(File file, boolean resize) {
        if (subStage != null && subStage.isShowing())
            subStage.close();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(CommonAnimation.class.getResource(file.getPath()));

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = fxmlLoader.getRoot();
        subStage = new Stage();
        if (resize)
            subStage.setResizable(false);
        subStage.setScene(new Scene(root));

        InputStream iconStream = CommonAnimation.class.getResourceAsStream("icon.jpg");
        Image image = new Image(iconStream);
        subStage.getIcons().add(image);

        subStage.setTitle("Created by Nikitin Egor P3117");

        subStage.show();
    }

    public static void closeSubStage() {
        if (subStage != null && subStage.isShowing())
            subStage.close();
    }
}
