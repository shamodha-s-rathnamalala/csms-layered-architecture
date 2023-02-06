package lk.ijse.computershop.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
public class SideBarController{
    @FXML
    private Button btnLoginRecord;
    @FXML
    private Button btnUser;
    @FXML
    private Button btnEmployee;
    @FXML
    private Button btnSupplier;
    @FXML
    private Button btnSupplies;
    @FXML
    private Button btnViewSupplies;
    @FXML
    private Button btnItem;
    @FXML
    private Button btnCustomer;
    @FXML
    private Button btnPlaceorder;
    @FXML
    private Button btnRepair;
    @FXML
    private Button btnViewPurchases;
    @FXML
    private Button btnStock;
    @FXML
    private Button btnReport;
    @FXML
    private Pane rightPane;
    @FXML
    private FontAwesomeIconView btnLoginRecordIcon;
    @FXML
    private FontAwesomeIconView btnUserIcon;
    @FXML
    private FontAwesomeIconView btnEmployeeIcon;
    @FXML
    private FontAwesomeIconView btnSupplierIcon;
    @FXML
    private FontAwesomeIconView btnSuppliesIcon;
    @FXML
    private FontAwesomeIconView btnViewSuppliesIcon;
    @FXML
    private FontAwesomeIconView btnItemIcon;
    @FXML
    private FontAwesomeIconView btnDashBoardIcon;
    @FXML
    private FontAwesomeIconView btnCustomerIcon;
    @FXML
    private FontAwesomeIconView btnPlaceorderIcon;
    @FXML
    private FontAwesomeIconView btnRepairIcon;
    @FXML
    private FontAwesomeIconView btnViewPurchasesIcon;
    @FXML
    private FontAwesomeIconView btnStockIcon;
    @FXML
    private FontAwesomeIconView btnReportIcon;
    @FXML
    private AnchorPane anc;
    @FXML
    private BorderPane mainPane;
    @FXML
    private Button btnAdmin;
    @FXML
    private Button btnDashBoard;
    @FXML
    private Label date;
    @FXML
    private Label lblUserName;
    @FXML
    private Label lblUserRank;
    @FXML
    private VBox vbAdmin;
    @FXML
    private VBox vbCashier;
    private ArrayList<Button> buttonArrayList=new ArrayList<>();
    private ArrayList<FontAwesomeIconView>fxView=new ArrayList<>();
    private static boolean isAdmin;
    private static String userName;
    public static void setUserProfile(boolean isAdmin, String userName){
        SideBarController.isAdmin = isAdmin;
        SideBarController.userName = userName;
    }
    public void initialize(){
        setProfileDetails();
        setButtonsArray();
        ActionEvent actionEvent=new ActionEvent();
        if (isAdmin)
            btnAdmin.setVisible(true);
        else
            btnAdmin.setVisible(false);
        goDashBoardOnAction(actionEvent);
    }
    private void setProfileDetails() {
        lblUserName.setText(userName);
        lblUserRank.setText(isAdmin?"Admin":"Cashier");
        date.setText(LocalDate.now().toString());
    }
    private void setButtonsArray() {
        buttonArrayList.add(btnLoginRecord);
        fxView.add(btnLoginRecordIcon);
        buttonArrayList.add(btnUser);
        fxView.add(btnUserIcon);
        buttonArrayList.add(btnEmployee);
        fxView.add(btnEmployeeIcon);
        buttonArrayList.add(btnSupplier);
        fxView.add(btnSupplierIcon);
        buttonArrayList.add(btnSupplies);
        fxView.add(btnSuppliesIcon);
        buttonArrayList.add(btnViewSupplies);
        fxView.add(btnViewSuppliesIcon);
        buttonArrayList.add(btnItem);
        fxView.add(btnItemIcon);
        buttonArrayList.add(btnDashBoard);
        fxView.add(btnDashBoardIcon);
        buttonArrayList.add(btnCustomer);
        fxView.add(btnCustomerIcon);
        buttonArrayList.add(btnPlaceorder);
        fxView.add(btnPlaceorderIcon);
        buttonArrayList.add(btnRepair);
        fxView.add(btnRepairIcon);
        buttonArrayList.add(btnViewPurchases);
        fxView.add(btnViewPurchasesIcon);
        buttonArrayList.add(btnStock);
        fxView.add(btnStockIcon);
        buttonArrayList.add(btnReport);
        fxView.add(btnReportIcon);
    }
    @FXML
    void adminOnAction(ActionEvent event) {
        vbCashier.setVisible(false);
        vbAdmin.setVisible(true);
        goLoginRecordAction(event);
//        Navigation.navigate(Routes.ADMIN, anc);
        initUI("LoginRecordForm.fxml");
    }
    @FXML
    void goCustomerOnAction(ActionEvent event) {
        changeSelectedButton(btnCustomer);
//        Navigation.navigate(Routes.CUSTOMER, anc);
        initUI("CustomerForm.fxml");
    }
    @FXML
    void goDashBoardOnAction(ActionEvent event) {
        vbCashier.setVisible(true);
        vbAdmin.setVisible(false);
        changeSelectedButton(btnDashBoard);
//        Navigation.navigate(Routes.DASHBOARD, anc);
        initUI("DashBoardForm.fxml");
    }
    @FXML
    void goPlaceOrderOnAction(ActionEvent event) {
        changeSelectedButton(btnPlaceorder);
//        Navigation.navigate(Routes.PLACE_ORDER, anc);
        initUI("PlaceOrderForm.fxml");
    }
    @FXML
    void goRepairOnAction(ActionEvent event) {
        changeSelectedButton(btnRepair);
//        Navigation.navigate(Routes.REPAIR, anc);
        initUI("RepairForm.fxml");
    }
    @FXML
    void goReportsOnAction(ActionEvent event) {
        changeSelectedButton(btnReport);
//        Navigation.navigate(Routes.REPORTS, anc);
        initUI("ReportsForm.fxml");
    }
    @FXML
    void goStockOnAction(ActionEvent event) {
        changeSelectedButton(btnStock);
//        Navigation.navigate(Routes.STOCK, anc);
        initUI("StockForm.fxml");
    }
    @FXML
    public void goViewPurchasesOnAction(ActionEvent event) {
        changeSelectedButton(btnViewPurchases);
//        Navigation.navigate(Routes.VIEW_PURCHASES, anc);
        initUI("ViewPurchasesForm.fxml");
    }
    @FXML
    void goEmployerOnAction(ActionEvent event) {
        changeSelectedButton(btnEmployee);
//        Navigation.navigate(Routes.EMPLOYEE, anc);
        initUI("EmployeeForm.fxml");
    }
    @FXML
    void goItemOnAction(ActionEvent event) {
        changeSelectedButton(btnItem);
//        Navigation.navigate(Routes.ITEM, anc);
        initUI("ItemForm.fxml");
    }
    @FXML
    void goLoginRecordAction(ActionEvent event) {
        changeSelectedButton(btnLoginRecord);
//        Navigation.navigate(Routes.ADMIN, anc);
        initUI("LoginRecordForm.fxml");
    }
    @FXML
    void goSupplierOnAction(ActionEvent event) {
        changeSelectedButton(btnSupplier);
//        Navigation.navigate(Routes.SUPPLIER, anc);
        initUI("SupplierForm.fxml");
    }
    @FXML
    void goSuppliesOnAction(ActionEvent event) {
        changeSelectedButton(btnSupplies);
//        Navigation.navigate(Routes.SUPPLIES, anc);
        initUI("SuppliesForm.fxml");
    }
    @FXML
    void goUserOnAction(ActionEvent event) {
        changeSelectedButton(btnUser);
//        Navigation.navigate(Routes.USER, anc);
        initUI("UserForm.fxml");
    }
    @FXML
    public void goViewSuppliesOnAction(ActionEvent actionEvent) {
        changeSelectedButton(btnViewSupplies);
//        Navigation.navigate(Routes.VIEW_SUPPLIES, anc);
        initUI("ViewSuppliesForm.fxml");
    }
    @FXML
    void logOutOnAction(ActionEvent event) {
//        Navigation.navigate(Routes.LOGIN,mainPane);
        initUI("LoginForm.fxml");
    }

    private void initUI(String location) {
        Pane pane = location.equals("LoginForm.fxml") ? mainPane : anc;
        try {
            pane.getChildren().clear();
            pane.getChildren().add(FXMLLoader.load(getClass().getResource("../view/" + location)));
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Forms Error !\n"+e.getMessage()).show();
        }
    }

    public void exitOnAction(ActionEvent actionEvent) {
        Platform.exit();
    }
    void changeSelectedButton(Button selectedButton){
        for (Button button:buttonArrayList) {
            if (button.getId().equals(selectedButton.getId())){
                selectedButton.getStyleClass().removeAll("navigation-button");
                selectedButton.getStyleClass().addAll("selected-navigation-button");
            }else {
                button.getStyleClass().removeAll("selected-navigation-button");
                button.getStyleClass().addAll("navigation-button");
            }
        }
        for ( int i=0; i<buttonArrayList.size(); i++) {
            Button button=buttonArrayList.get(i);
            FontAwesomeIconView fxIcon=fxView.get(i);
            if (button.getId().equals(selectedButton.getId())){
                selectedButton.getStyleClass().removeAll("navigation-button");
                selectedButton.getStyleClass().addAll("selected-navigation-button");
                fxIcon.getStyleClass().removeAll("navigation-button");
                fxIcon.getStyleClass().addAll("selected-navigation-button");

            }else {
                button.getStyleClass().removeAll("selected-navigation-button");
                button.getStyleClass().addAll("navigation-button");
                fxIcon.getStyleClass().removeAll("selected-navigation-button");
                fxIcon.getStyleClass().addAll("navigation-button");
            }
        }

    }
}
