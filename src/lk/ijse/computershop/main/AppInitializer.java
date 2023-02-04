package lk.ijse.computershop.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.computershop.util.Navigation;


public class AppInitializer extends Application {
    private static double xOffset = 0;
    private static double yOffset = 0;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Pronet Computers");
        stage.getIcons().add(new Image("/lk/ijse/computershop/asset/logo.png"));
        stage.initStyle(StageStyle.TRANSPARENT);
        Parent root = FXMLLoader.load(Navigation.class.getResource("../view/LoginForm.fxml"));
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
