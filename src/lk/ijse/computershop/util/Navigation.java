package lk.ijse.computershop.util;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;

import java.io.IOException;
public class Navigation {
    private static Pane pane;
    public static void navigate(Routes route, Pane pane) {
        Navigation.pane = pane;
        Navigation.pane.getChildren().clear();
        switch (route) {
            case ADMIN:
                initUI("LoginRecordForm.fxml");
                break;
            case CUSTOMER:
                initUI("CustomerForm.fxml");
                break;
            case DASHBOARD:
                initUI("DashBoardForm.fxml");
                break;
            case ITEM:
                initUI("ItemForm.fxml");
                break;
            case PLACE_ORDER:
                initUI("PlaceOrderForm.fxml");
                break;
            case REPAIR:
                initUI("RepairForm.fxml");
                break;
            case REPORTS:
                initUI("ReportsForm.fxml");
                break;
            case STOCK:
                initUI("StockForm.fxml");
                break;
            case SUPPLIES:
                initUI("SuppliesForm.fxml");
                break;
            case VIEW_PURCHASES:
                initUI("ViewPurchasesForm.fxml");
                break;
            case EMPLOYEE:
                initUI("EmployeeForm.fxml");
                break;
            case SUPPLIER:
                initUI("SupplierForm.fxml");
                break;
            case USER:
                initUI("UserForm.fxml");
                break;
            case VIEW_SUPPLIES:
                initUI("ViewSuppliesForm.fxml");
                break;
            case LOGIN:
                initUI("LoginForm.fxml");
                break;
            case MAIN:
                initUI("SideBar.fxml");
                break;
            default:
                new Alert(Alert.AlertType.ERROR, "UI Not Found!").show();
        }
    }
    private static void initUI(String location) {
        try {
            Navigation.pane.getChildren().add(FXMLLoader.load(Navigation.class.getResource("../view/" + location)));
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Forms Error !\n"+e.getMessage()).show();
        }
    }
}
