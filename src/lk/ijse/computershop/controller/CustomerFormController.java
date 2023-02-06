package lk.ijse.computershop.controller;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import lk.ijse.computershop.dto.CustomerDTO;
import lk.ijse.computershop.service.ServiceFactory;
import lk.ijse.computershop.service.ServiceTypes;
import lk.ijse.computershop.service.custom.CustomerService;
import lk.ijse.computershop.service.exception.DuplicateException;
import lk.ijse.computershop.service.exception.InUseException;
import lk.ijse.computershop.util.CustomAlert;
import lk.ijse.computershop.util.Regex;
import lk.ijse.computershop.util.RegexType;
import lk.ijse.computershop.view.tm.CustomerTM;

import java.sql.SQLException;
import java.util.stream.Collectors;

public class CustomerFormController {

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<CustomerTM, String> colAction;

    @FXML
    private TableColumn<CustomerTM, String> colAddress;

    @FXML
    private TableColumn<CustomerTM, String> colCustomerId;

    @FXML
    private TableColumn<CustomerTM, String> colEmail;

    @FXML
    private TableColumn<CustomerTM, String> colName;

    @FXML
    private TableColumn<CustomerTM, String> colNic;

    @FXML
    private TableColumn<CustomerTM, String> colTelNumber;

    @FXML
    private TableView<CustomerTM> tblCustomer;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtCusId;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtNic;

    @FXML
    private TextField txtTelNumber;
    private final ObservableList<CustomerTM> customerObservableList = FXCollections.observableArrayList();
    private CustomerTM customerTM = null;
    private CustomerDTO customerDTO = null;
    private boolean isValidName=false;
    private boolean isValidAddress=false;
    private boolean isValidNic=false;
    private boolean isValidTelephoneNumber=false;
    private boolean isValidEmail=false;
    private boolean isUpdate=false;
    private final CustomerService customerService = ServiceFactory.getInstance().getService(ServiceTypes.CUSTOMER);

    public void initialize(){
        btnUpdate.setDisable(true);
        loadTable();
        resetOnAction(new ActionEvent());
    }
    @FXML
    public void txtSearchMouseOnClick(MouseEvent mouseEvent) {
        txtSearch.clear();
        FilteredList<CustomerTM>filteredList=new FilteredList<>(customerObservableList,b -> true);
        txtSearch.textProperty().addListener((observable,oldValue,newValue)->{
            filteredList.setPredicate(Customer ->{
                if (newValue.isEmpty() || newValue==null ){
                    return true;
                }
                String searchKeyword=newValue.toLowerCase();
                if (Customer.getCustomerId().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if (Customer.getName().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if (Customer.getAddress().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if (Customer.getNic().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if (Customer.getTelephoneNumber().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if (Customer.getEmail().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else {
                    return false;
                }
            });
        });
        SortedList<CustomerTM>sortedList=new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tblCustomer.comparatorProperty());
        tblCustomer.setItems(sortedList);
    }

    @FXML
    void resetOnAction(ActionEvent event) {
        btnAdd.setDisable(false);
        btnUpdate.setDisable(true);
        clearAllTextField();
    }
    @FXML
    public void addOnAction(ActionEvent actionEvent) {
        validateAll();
        if (!isValidName)
            txtName.requestFocus();
        else if (!isValidAddress)
            txtAddress.requestFocus();
        else if (!isValidNic)
            txtNic.requestFocus();
        else if (!isValidTelephoneNumber)
            txtTelNumber.requestFocus();
        else if (!isValidEmail)
            txtEmail.requestFocus();
        else {
            String cusId=txtCusId.getText();
            String name=txtName.getText();
            String address=txtAddress.getText();
            String nic=txtNic.getText();
            String telNumber=txtTelNumber.getText();
            String email=txtEmail.getText();
            try {
                customerDTO=new CustomerDTO(cusId,name,address,nic,telNumber,email);
                customerService.saveCustomer(customerDTO);
                CustomAlert.getInstance().showAlert(Alert.AlertType.INFORMATION,"Success","Successfully Registered customer !");
                clearAllTextField();
                txtName.requestFocus();
            } catch (DuplicateException e) {
                CustomAlert.getInstance().showAlert(Alert.AlertType.WARNING,"Failed",e.getMessage());
                txtNic.selectAll();
                txtNic.requestFocus();
            } catch (SQLException e){
                CustomAlert.getInstance().showAlert(Alert.AlertType.ERROR,"Failed",e.getMessage());
            }
        }
    }

    @FXML
    void updateOnAction(ActionEvent event) {
        validateAll();
        if (!isValidName)
            txtName.requestFocus();
        else if (!isValidAddress)
            txtAddress.requestFocus();
        else if (!isValidNic)
            txtNic.requestFocus();
        else if (!isValidTelephoneNumber)
            txtTelNumber.requestFocus();
        else if (!isValidEmail)
            txtEmail.requestFocus();
        else {
            String cusId=txtCusId.getText();
            String name=txtName.getText();
            String address=txtAddress.getText();
            String nic=txtNic.getText();
            String telNumber=txtTelNumber.getText();
            String email=txtEmail.getText();
            try {
                customerDTO=new CustomerDTO(cusId,name,address,nic,telNumber,email);
                customerService.updateCustomer(customerDTO);
                btnUpdate.setDisable(true);
                btnAdd.setDisable(false);
                clearAllTextField();
                txtName.requestFocus();
                isUpdate=false;
                CustomAlert.getInstance().showAlert(Alert.AlertType.INFORMATION,"Success","Successfully updated customer !");
            } catch (DuplicateException e) {
                CustomAlert.getInstance().showAlert(Alert.AlertType.WARNING,"Failed",e.getMessage());
                txtNic.selectAll();
                txtNic.requestFocus();
            } catch (SQLException e){
                CustomAlert.getInstance().showAlert(Alert.AlertType.ERROR,"Failed",e.getMessage());
            }
        }
    }

    @FXML
    void telNumberOnAction(ActionEvent event) {
        if (isValidTelephoneNumber)
            txtEmail.requestFocus();
    }

    @FXML
    public void nameOnAction(ActionEvent actionEvent) {
        if (isValidName)
            txtAddress.requestFocus();
    }

    @FXML
    public void addressOnAction(ActionEvent actionEvent) {
        if (isValidAddress)
            txtNic.requestFocus();
    }

    @FXML
    public void nicOnAction(ActionEvent actionEvent) {
        if (isValidNic)
            txtTelNumber.requestFocus();
    }
    @FXML
    public void emailOnAction(ActionEvent actionEvent) {
        if (isValidEmail) {
            if (isUpdate){
                updateOnAction(actionEvent);
            }else {
                addOnAction(actionEvent);
            }
        }
    }
    @FXML
    void telNumKeyPressedOnAction(KeyEvent event) {
        telephoneNumberValidation();
    }
    @FXML
    public void nameKeyPressedOnAction(KeyEvent keyEvent) {
        nameValidation();
    }
    @FXML
    public void addressKeyPressedOnAction(KeyEvent keyEvent) {
        addressValidation();
    }
    @FXML
    public void nicKeyPressedOnAction(KeyEvent keyEvent) {
        nicValidation();
    }
    @FXML
    public void emailKeyPressedOnAction(KeyEvent keyEvent) {
        emailValidation();
    }

    private void refreshTable() {
        customerObservableList.clear();
        try {
            customerObservableList.addAll(customerService.getAllCustomer().stream().map(customer -> new CustomerTM(customer.getCustomerId(), customer.getName(), customer.getAddress(), customer.getNic(), customer.getTelephoneNumber(), customer.getEmail())).collect(Collectors.toList()));
            tblCustomer.setItems(customerObservableList);
        } catch (SQLException e) {
            ButtonType result = CustomAlert.getInstance().showAlert(Alert.AlertType.CONFIRMATION, "Data Not Loading", e.getMessage() + ",Data Not Loading !\nYou Want Exit ? ");
            if (result==ButtonType.OK){
                Platform.exit();
            }
        }
    }

    private void loadTable() {
        refreshTable();
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colTelNumber.setCellValueFactory(new PropertyValueFactory<>("telephoneNumber"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        Callback<TableColumn<CustomerTM, String>, TableCell<CustomerTM, String>> cellFactory = (TableColumn<CustomerTM, String> param) -> {
            // make cell containing buttons
            final TableCell<CustomerTM, String> cell = new TableCell<CustomerTM, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);
                        deleteIcon.setStyle(" -fx-cursor: hand ;" + "-glyph-size:28px;" + "-fx-fill:#ff1744;");
                        editIcon.setStyle(" -fx-cursor: hand ;" + "-glyph-size:28px;" + "-fx-fill:#00E676;");
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            customerTM=tblCustomer.getSelectionModel().getSelectedItem();
                            ButtonType result = CustomAlert.getInstance().showAlert(Alert.AlertType.CONFIRMATION, "Deleting a customer ","Are You Sure ? ");
                            if (result==ButtonType.OK){
                                try {
                                    customerService.deleteCustomer(customerTM.getCustomerId());
                                    clearAllTextField();
                                    refreshTable();
                                    CustomAlert.getInstance().showAlert(Alert.AlertType.INFORMATION, "Deleting Successfully",customerTM.getName()+" was deleted");
                                }catch (InUseException e) {
                                    CustomAlert.getInstance().showAlert(Alert.AlertType.WARNING, "Deleting unSuccessfully",e.getMessage());
                                }catch (SQLException e) {
                                    ButtonType result2 = CustomAlert.getInstance().showAlert(Alert.AlertType.CONFIRMATION, "Deleting error",e.getMessage()+"\nyou want exit program ? ");
                                    if (result2==ButtonType.OK){
                                        Platform.exit();
                                    }
                                }
                            }
                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            customerTM=tblCustomer.getSelectionModel().getSelectedItem();
                            txtCusId.setText(customerTM.getCustomerId());
                            txtName.setText(customerTM.getName());
                            txtAddress.setText(customerTM.getAddress());
                            txtNic.setText(customerTM.getNic());
                            txtTelNumber.setText(customerTM.getTelephoneNumber());
                            txtEmail.setText(customerTM.getEmail());
                            btnUpdate.setDisable(false);
                            btnAdd.setDisable(true);
                            isUpdate=true;
                        });
                        HBox hBox = new HBox(editIcon, deleteIcon);
                        hBox.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));
                        setGraphic(hBox);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        colAction.setCellFactory(cellFactory);
        tblCustomer.setItems(customerObservableList);
    }
    private void clearAllTextField() {
        try {
            txtCusId.setText(customerService.getNextCustomerId());
            txtName.setText("");
            txtAddress.setText("");
            txtNic.setText("");
            txtTelNumber.setText("");
            txtEmail.setText("");
            refreshTable();
        } catch (SQLException e) {
            CustomAlert.getInstance().showAlert(Alert.AlertType.ERROR, "DataBase Error",e.getMessage());
        }
    }
    private void nameValidation(){
        if (Regex.getInstance().isValid(RegexType.NAME,txtName.getText())){
            txtName.setStyle("-fx-border-color: #76ff03");
            isValidName=true;
        }else {
            txtName.setStyle("-fx-border-color: #ff0000");
            isValidName=false;
        }
    }
    private void addressValidation(){
        if (!txtAddress.equals("")){
            txtAddress.setStyle("-fx-border-color: #76ff03");
            isValidAddress=true;
        }else {
            txtAddress.setStyle("-fx-border-color: #ff0000");
            isValidAddress=false;
        }
    }
    private void nicValidation(){
        if (Regex.getInstance().isValid(RegexType.NIC,txtNic.getText())){
            txtNic.setStyle("-fx-border-color: #76ff03");
            isValidNic=true;
        }else {
            txtNic.setStyle("-fx-border-color: #ff0000");
            isValidNic=false;
        }
    }
    private void telephoneNumberValidation() {
        if (Regex.getInstance().isValid(RegexType.TELEPHONE_NUMBER,txtTelNumber.getText())){
            txtTelNumber.setStyle("-fx-border-color: #76ff03");
            isValidTelephoneNumber=true;
        }else {
            txtTelNumber.setStyle("-fx-border-color: #ff0000");
            isValidTelephoneNumber=false;
        }
    }

    private void emailValidation(){
        if (Regex.getInstance().isValid(RegexType.EMAIL,txtEmail.getText())){
            txtEmail.setStyle("-fx-border-color: #76ff03");
            isValidEmail=true;
        }else {
            txtEmail.setStyle("-fx-border-color: #ff0000");
            isValidEmail=false;
        }
    }

    private void validateAll() {
        nameValidation();
        addressValidation();
        nicValidation();
        telephoneNumberValidation();
        emailValidation();
    }
}
