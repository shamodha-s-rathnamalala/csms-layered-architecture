package lk.ijse.computershop.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;

public class SplashScreenController {

    @FXML
    public ImageView imgSlash;

    @FXML
    public AnchorPane mainPane;

    @FXML
    public Label lblLoading;

    @FXML
    public Rectangle rctContainer;

    @FXML
    public Rectangle rctLoading;

    public void initialize(){
        Image image1 = new Image("lk/ijse/computershop/asset/slash/si1.png");
        Image image2 = new Image("lk/ijse/computershop/asset/slash/si2.jpg");
        Image image3 = new Image("lk/ijse/computershop/asset/slash/si3.jpg");
        Image image4 = new Image("lk/ijse/computershop/asset/slash/si4.jpg");
        Image image5 = new Image("lk/ijse/computershop/asset/slash/si5.jpg");
        Image image6 = new Image("lk/ijse/computershop/asset/slash/si6.jpg");
        Image image7 = new Image("lk/ijse/computershop/asset/slash/si7.png");


        KeyFrame keyFrame1= new KeyFrame(Duration.millis(0), actionEvent ->{
            imgSlash.setImage(image1);
            lblLoading.setText("Initializing Application....");
            lblLoading.setStyle("-fx-text-fill:  #FEFEFE;");
            rctLoading.setWidth(rctContainer.getWidth()*0.15);
        });
        KeyFrame keyFrame2= new KeyFrame(Duration.millis(1500), actionEvent ->{
            imgSlash.setImage(image2);
            lblLoading.setText("Loading Internal Resources....");
            lblLoading.setStyle("-fx-text-fill:  #FEFEFE;");
            rctLoading.setWidth(rctContainer.getWidth()*0.30);
        });
        KeyFrame keyFrame3= new KeyFrame(Duration.millis(2500), actionEvent ->{
            imgSlash.setImage(image3);
            lblLoading.setText("Loading Images....");
            lblLoading.setStyle("-fx-text-fill: #000000;");
            rctLoading.setWidth(rctContainer.getWidth()*0.45);
        });
        KeyFrame keyFrame4= new KeyFrame(Duration.millis(3500), actionEvent ->{
            imgSlash.setImage(image4);
            lblLoading.setText("Loading UIs....");
            lblLoading.setStyle("-fx-text-fill:  #FEFEFE;");
            rctLoading.setWidth(rctContainer.getWidth()*0.6);
        });
        KeyFrame keyFrame5= new KeyFrame(Duration.millis(4500), actionEvent ->{
            imgSlash.setImage(image5);
            lblLoading.setText("Welcome to CSMS v1.0.0");
            lblLoading.setStyle("-fx-text-fill: #000000;");
            rctLoading.setWidth(rctContainer.getWidth()*0.75);

        });
        KeyFrame keyFrame6= new KeyFrame(Duration.millis(5500), actionEvent ->{
            imgSlash.setImage(image6);
            lblLoading.setText("Welcome to CSMS v1.0.0");
            lblLoading.setStyle("-fx-text-fill: #000000;");
            rctLoading.setWidth(rctContainer.getWidth()*0.9);
        });
        KeyFrame keyFrame7= new KeyFrame(Duration.millis(6500), actionEvent ->{
            imgSlash.setImage(image7);
            lblLoading.setText("Welcome to CSMS v1.0.0");
            lblLoading.setStyle("-fx-text-fill:  #FEFEFE;");
            rctLoading.setWidth(rctContainer.getWidth());
        });
        KeyFrame keyFrame8 = new KeyFrame(Duration.millis(7500),actionEvent -> {
            try {
                mainPane.getChildren().clear();
                mainPane.getChildren().add(FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Timeline timeline = new Timeline(keyFrame1,keyFrame2,keyFrame3,keyFrame4,keyFrame5,keyFrame6,keyFrame7,keyFrame8);
        timeline.playFromStart();




    }
}
