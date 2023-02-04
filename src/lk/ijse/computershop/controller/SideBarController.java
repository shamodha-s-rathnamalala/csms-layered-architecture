package lk.ijse.computershop.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lk.ijse.computershop.util.Navigation;
import lk.ijse.computershop.util.Routes;

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
        //Project.setPane(mainPane);
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
        Navigation.navigate(Routes.ADMIN, anc);
    }
    @FXML
    void goCustomerOnAction(ActionEvent event) {
        changeSelectedButton(btnCustomer);
        Navigation.navigate(Routes.CUSTOMER, anc);
    }
    @FXML
    void goDashBoardOnAction(ActionEvent event) {
        vbCashier.setVisible(true);
        vbAdmin.setVisible(false);
        changeSelectedButton(btnDashBoard);
        Navigation.navigate(Routes.DASHBOARD, anc);
    }
    @FXML
    void goPlaceOrderOnAction(ActionEvent event) {
        changeSelectedButton(btnPlaceorder);
        Navigation.navigate(Routes.PLACE_ORDER, anc);
    }
    @FXML
    void goRepairOnAction(ActionEvent event) {
        changeSelectedButton(btnRepair);
        Navigation.navigate(Routes.REPAIR, anc);
    }
    @FXML
    void goReportsOnAction(ActionEvent event) {
        changeSelectedButton(btnReport);
        Navigation.navigate(Routes.REPORTS, anc);
    }
    @FXML
    void goStockOnAction(ActionEvent event) {
        changeSelectedButton(btnStock);
        Navigation.navigate(Routes.STOCK, anc);
    }
    @FXML
    public void goViewPurchasesOnAction(ActionEvent event) {
        changeSelectedButton(btnViewPurchases);
        Navigation.navigate(Routes.VIEW_PURCHASES, anc);
    }
    @FXML
    void goEmployerOnAction(ActionEvent event) {
        changeSelectedButton(btnEmployee);
        Navigation.navigate(Routes.EMPLOYEE, anc);
    }
    @FXML
    void goItemOnAction(ActionEvent event) {
        changeSelectedButton(btnItem);
        Navigation.navigate(Routes.ITEM, anc);
    }
    @FXML
    void goLoginRecordAction(ActionEvent event) {
        changeSelectedButton(btnLoginRecord);
        Navigation.navigate(Routes.ADMIN, anc);
    }
    @FXML
    void goSupplierOnAction(ActionEvent event) {
        changeSelectedButton(btnSupplier);
        Navigation.navigate(Routes.SUPPLIER, anc);
    }
    @FXML
    void goSuppliesOnAction(ActionEvent event) {
        changeSelectedButton(btnSupplies);
        Navigation.navigate(Routes.SUPPLIES, anc);
    }
    @FXML
    void goUserOnAction(ActionEvent event) {
        changeSelectedButton(btnUser);
        Navigation.navigate(Routes.USER, anc);
    }
    @FXML
    public void goViewSuppliesOnAction(ActionEvent actionEvent) {
        changeSelectedButton(btnViewSupplies);
        Navigation.navigate(Routes.VIEW_SUPPLIES, anc);
    }
    @FXML
    void logOutOnAction(ActionEvent event) throws IOException {
        Navigation.navigate(Routes.LOGIN,mainPane);
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
