package lk.ijse.computershop.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

public class CustomAlert {
    private static CustomAlert customAlert;

    private CustomAlert() {
    }
    public static CustomAlert getInstance(){
        return customAlert ==null?(customAlert =new CustomAlert()): customAlert;
    }
    public ButtonType showAlert(Alert.AlertType type, String title, String contend){
        javafx.scene.control.Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(contend);
        Optional<ButtonType> buttonType = alert.showAndWait();
        return buttonType.get();
    }
}
