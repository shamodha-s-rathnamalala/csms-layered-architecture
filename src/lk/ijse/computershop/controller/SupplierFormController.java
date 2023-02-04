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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import lk.ijse.computershop.dto.SupplierDTO;
import lk.ijse.computershop.service.ServiceFactory;
import lk.ijse.computershop.service.ServiceTypes;
import lk.ijse.computershop.service.custom.SupplierService;
import lk.ijse.computershop.service.exception.DuplicateException;
import lk.ijse.computershop.util.CustomAlert;
import lk.ijse.computershop.util.Regex;
import lk.ijse.computershop.util.RegexType;
import lk.ijse.computershop.view.tm.SupplierTM;

import java.sql.SQLException;
import java.util.stream.Collectors;

public class SupplierFormController {

    @FXML
    private AnchorPane anc;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<SupplierTM, String> colAction;

    @FXML
    private TableColumn<SupplierTM, String> colAddress;

    @FXML
    private TableColumn<SupplierTM, String> colEmail;

    @FXML
    private TableColumn<SupplierTM, String> colId;

    @FXML
    private TableColumn<SupplierTM, String> colName;

    @FXML
    private TableColumn<SupplierTM, String> colTelNumber;

    @FXML
    private TableView<SupplierTM> tbl;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtTelNumber;
    private final ObservableList<SupplierTM> supplierTMS = FXCollections.observableArrayList();
    private SupplierTM supplierTM = null;
    private SupplierDTO supplierDTO = null;
    boolean isValidName=false;
    boolean isValidAddress=false;
    boolean isValidTelephoneNumber=false;
    boolean isValidEmail=false;
    boolean isUpdate=false;
    private final SupplierService supplierService = ServiceFactory.getInstance().getService(ServiceTypes.SUPPLIER);
    public void initialize(){
        loadTable();
        reset();
    }

    private void loadTable(){
        refreshTable();
        colId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colTelNumber.setCellValueFactory(new PropertyValueFactory<>("telephoneNumber"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        Callback<TableColumn<SupplierTM, String>, TableCell<SupplierTM, String>> cellFactory = (TableColumn<SupplierTM, String> param) -> {
            // make cell containing buttons
            final TableCell<SupplierTM, String> cell = new TableCell<SupplierTM, String>() {
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
                            supplierTM=tbl.getSelectionModel().getSelectedItem();
                            ButtonType result = CustomAlert.getInstance().showAlert(Alert.AlertType.CONFIRMATION, "Deleting a supplier named "+supplierTM.getName(),"When the supplier is deleted,The supplies is also deleted\nAre You Sure ? ");
                            if (result==ButtonType.OK){
                                try {
                                    supplierService.deleteSupplier(supplierTM.getSupplierId());
                                    reset();
                                    refreshTable();
                                    CustomAlert.getInstance().showAlert(Alert.AlertType.INFORMATION, "Deleting Successfully",supplierTM.getName()+" was deleted");
                                } catch (SQLException e) {
                                    CustomAlert.getInstance().showAlert(Alert.AlertType.WARNING, "Deleting Unsuccessfully",e.getMessage());
                                }
                            }
                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            supplierTM=tbl.getSelectionModel().getSelectedItem();
                            txtId.setText(supplierTM.getSupplierId());
                            txtName.setText(supplierTM.getName());
                            txtAddress.setText(supplierTM.getAddress());
                            txtTelNumber.setText(supplierTM.getTelephoneNumber());
                            txtEmail.setText(supplierTM.getEmail());
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
        tbl.setItems(supplierTMS);
    }

    private void refreshTable() {
        supplierTMS.clear();
        try {
            supplierTMS.addAll(supplierService.getAllSupplier().stream().map(supplier -> new SupplierTM(supplier.getSupplierId(), supplier.getName(), supplier.getAddress(), supplier.getTelephoneNumber(), supplier.getEmail())).collect(Collectors.toList()));
            tbl.setItems(supplierTMS);
        } catch (SQLException e) {
            ButtonType result = CustomAlert.getInstance().showAlert(Alert.AlertType.CONFIRMATION, "Data Not Loading", e + ",Data Not Loading !\nYou Want Exit ? ");
            if (result==ButtonType.OK){
                Platform.exit();
            }
        }

    }

    private void reset() {
        try {
            txtId.setText(supplierService.getNextSupplierId());
            txtName.clear();
            txtAddress.clear();
            txtTelNumber.clear();
            txtEmail.clear();
            btnUpdate.setDisable(true);
            btnAdd.setDisable(false);
        } catch (SQLException e) {
            ButtonType result = CustomAlert.getInstance().showAlert(Alert.AlertType.CONFIRMATION, "Data Not Loading", e + ",Data Not Loading !\nYou Want Exit ? ");
            if (result==ButtonType.OK){
                Platform.exit();
            }
        }
    }

    @FXML
    void addOnAction(ActionEvent event) {
        validateAll();
        if (!isValidName)
            txtName.requestFocus();
        else if (!isValidAddress)
            txtAddress.requestFocus();
        else if (!isValidTelephoneNumber)
            txtTelNumber.requestFocus();
        else if (!isValidEmail)
            txtEmail.requestFocus();
        else {
            String supplierId=txtId.getText();
            String name=txtName.getText();
            String address=txtAddress.getText();
            String telNumber=txtTelNumber.getText();
            String email=txtEmail.getText();
            try {
                supplierDTO = new SupplierDTO(supplierId, name, address, telNumber, email);
                supplierService.saveSupplier(supplierDTO);
                reset();
                refreshTable();
                CustomAlert.getInstance().showAlert(Alert.AlertType.INFORMATION, "successfully", "supplier data added.");
            }catch (DuplicateException e){
                CustomAlert.getInstance().showAlert(Alert.AlertType.WARNING, "Duplicate",e.getMessage());
            } catch (SQLException e) {
                CustomAlert.getInstance().showAlert(Alert.AlertType.ERROR, "unsuccessfully",e.getMessage());
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
        else if (!isValidTelephoneNumber)
            txtTelNumber.requestFocus();
        else if (!isValidEmail)
            txtEmail.requestFocus();
        else {
            String supplierId=txtId.getText();
            String name=txtName.getText();
            String address=txtAddress.getText();
            String telNumber=txtTelNumber.getText();
            String email=txtEmail.getText();
            try {
                supplierDTO = new SupplierDTO(supplierId, name, address, telNumber, email);
                supplierService.updateSupplier(supplierDTO);
                btnUpdate.setDisable(true);
                btnAdd.setDisable(false);
                reset();
                refreshTable();
                isUpdate=false;
                CustomAlert.getInstance().showAlert(Alert.AlertType.INFORMATION, "successfully","supplier data updated !");
            }catch (DuplicateException e){
                CustomAlert.getInstance().showAlert(Alert.AlertType.WARNING, "Duplicate",e.getMessage());
            } catch (SQLException e) {
                CustomAlert.getInstance().showAlert(Alert.AlertType.ERROR, "unsuccessfully",e.getMessage());
            }
        }
    }

    private void validateAll() {
        nameValidation();
        addressValidation();
        telephoneNumberValidation();
        emailValidation();
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

    @FXML
    void addressKeyPressedOnAction(KeyEvent event) {
        addressValidation();
    }

    @FXML
    void addressOnAction(ActionEvent event) {
        if (isValidAddress)
            txtTelNumber.requestFocus();
    }

    @FXML
    void emailKeyPressedOnAction(KeyEvent event) {
        emailValidation();
    }

    @FXML
    void emailOnAction(ActionEvent event) {
        if (isValidEmail) {
            if (isUpdate){
                updateOnAction(event);
            }else {
                addOnAction(event);
            }
        }
    }

    @FXML
    void nameKeyPressedOnAction(KeyEvent event) {
        nameValidation();
    }

    @FXML
    void nameOnAction(ActionEvent event) {
        if (isValidName)
            txtAddress.requestFocus();
    }

    @FXML
    void resetOnAction(ActionEvent event) {
        reset();
    }

    @FXML
    void telNumKeyPressedOnAction(KeyEvent event) {
        telephoneNumberValidation();
    }

    @FXML
    void telNumberOnAction(ActionEvent event) {
        if (isValidTelephoneNumber)
            txtEmail.requestFocus();
    }

    @FXML
    void txtSearchMouseOnClick(MouseEvent event) {
        txtSearch.clear();
        FilteredList<SupplierTM> filteredList=new FilteredList<>(supplierTMS, b -> true);
        txtSearch.textProperty().addListener((observable,oldValue,newValue)->{
            filteredList.setPredicate(supplierSearchModel ->{
                if (newValue.isEmpty() || newValue==null ){
                    return true;
                }
                String searchKeyword=newValue.toLowerCase();
                if (supplierSearchModel.getSupplierId().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if (supplierSearchModel.getName().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if (supplierSearchModel.getAddress().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if (supplierSearchModel.getTelephoneNumber().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if (supplierSearchModel.getEmail().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else {
                    return false;
                }
            });
        });
        SortedList<SupplierTM> sortedList=new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tbl.comparatorProperty());
        tbl.setItems(sortedList);
    }
}
