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
import lk.ijse.computershop.dto.EmployeeDTO;
import lk.ijse.computershop.service.ServiceFactory;
import lk.ijse.computershop.service.ServiceTypes;
import lk.ijse.computershop.service.custom.EmployeeService;
import lk.ijse.computershop.service.exception.DuplicateException;
import lk.ijse.computershop.util.CustomAlert;
import lk.ijse.computershop.util.Regex;
import lk.ijse.computershop.util.RegexType;
import lk.ijse.computershop.view.tm.EmployeeTM;

import java.sql.SQLException;
import java.util.stream.Collectors;

public class EmployeeFormController {

    @FXML
    private AnchorPane anc;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private ComboBox<String> cmbRank;

    @FXML
    private TableColumn<EmployeeTM, String> colAction;

    @FXML
    private TableColumn<EmployeeTM, String> colAddress;

    @FXML
    private TableColumn<EmployeeTM, String> colEmployeeId;

    @FXML
    private TableColumn<EmployeeTM, String> colName;

    @FXML
    private TableColumn<EmployeeTM, String> colNic;

    @FXML
    private TableColumn<EmployeeTM, String> colRank;

    @FXML
    private TableColumn<EmployeeTM, String> colTelNumber;

    @FXML
    private TableView<EmployeeTM> tblEmployee;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmployeeId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtNic;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtTelNumber;

    private final ObservableList<EmployeeTM> employeeObservableList = FXCollections.observableArrayList();
    private EmployeeTM employeeTM = null;
    private EmployeeDTO employeeDTO=null;
    private boolean isValidName=false;
    private boolean isValidAddress=false;
    private boolean isValidNic=false;
    private boolean isValidTelephoneNumber=false;
    private boolean isUpdate=false;
    private final EmployeeService employeeService = ServiceFactory.getInstance().getService(ServiceTypes.EMPLOYEE);

    public void initialize(){
        btnUpdate.setDisable(true);
        loadCmb();
        loadTable();
        resetOnAction(new ActionEvent());
    }

    private void loadCmb() {
        String[] ranks={"Manager","Repairer","Technical Supporters","Cashier","Minor Employees"};
        cmbRank.getItems().addAll(ranks);
    }

    private void refreshTable() {
        employeeObservableList.clear();
        try {
            employeeObservableList.addAll(employeeService.getAllEmployee().stream().map(e -> new EmployeeTM(e.getEmployeeId(), e.getName(), e.getAddress(), e.getNic(), employeeTM.getTelephoneNumber(), employeeTM.getRank())).collect(Collectors.toList()));
            tblEmployee.setItems(employeeObservableList);
        } catch (SQLException e) {
            ButtonType result = CustomAlert.getInstance().showAlert(Alert.AlertType.CONFIRMATION, "Data Not Loading", e + ",Data Not Loading !\nYou Want Exit ? ");
            if (result==ButtonType.OK){
                Platform.exit();
            }
        }
    }

    private void loadTable() {
        refreshTable();
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colTelNumber.setCellValueFactory(new PropertyValueFactory<>("telephoneNumber"));
        colRank.setCellValueFactory(new PropertyValueFactory<>("rank"));
        Callback<TableColumn<EmployeeTM, String>, TableCell<EmployeeTM, String>> cellFactory = (TableColumn<EmployeeTM, String> param) -> {
            // make cell containing buttons
            final TableCell<EmployeeTM, String> cell = new TableCell<EmployeeTM, String>() {
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
                            employeeTM=tblEmployee.getSelectionModel().getSelectedItem();
                            ButtonType result = CustomAlert.getInstance().showAlert(Alert.AlertType.CONFIRMATION, "Deleting a employee named "+employeeTM.getName(),"Are You Sure ? ");
                            if (result==ButtonType.OK){
                                try {
                                    employeeService.deleteEmployee(employeeTM.getEmployeeId());
                                    clearAll();
                                    refreshTable();
                                    CustomAlert.getInstance().showAlert(Alert.AlertType.INFORMATION, "Deleting Successfully",employeeTM.getName()+" was deleted");
                                } catch (SQLException  e) {
                                    ButtonType result2= CustomAlert.getInstance().showAlert(Alert.AlertType.CONFIRMATION, "Deleting error",e+"\nyou want exit program ? ");
                                    if (result2==ButtonType.OK){
                                        Platform.exit();
                                    }
                                }
                            }
                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            employeeTM=tblEmployee.getSelectionModel().getSelectedItem();
                            txtEmployeeId.setText(employeeTM.getEmployeeId());
                            txtName.setText(employeeTM.getName());
                            txtAddress.setText(employeeTM.getAddress());
                            txtNic.setText(employeeTM.getNic());
                            txtTelNumber.setText(employeeTM.getTelephoneNumber());
                            cmbRank.getSelectionModel().select(employeeTM.getRank());
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
        tblEmployee.setItems(employeeObservableList);
    }

    private void clearAll() {
        try {
            txtEmployeeId.setText(employeeService.getNextEmployeeId());
            txtName.setText("");
            txtAddress.setText("");
            txtNic.setText("");
            txtTelNumber.setText("");
            cmbRank.getSelectionModel().clearSelection();
        } catch (SQLException e) {
            CustomAlert.getInstance().showAlert(Alert.AlertType.ERROR, "DataBase Error",e+"");
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


    @FXML
    void addOnAction(ActionEvent event) {
        validateAll();
        if (!isValidName)
            txtName.requestFocus();
        else if (!isValidAddress)
            txtAddress.requestFocus();
        else if (!isValidNic)
            txtNic.requestFocus();
        else if (!isValidTelephoneNumber)
            txtTelNumber.requestFocus();
        else if (cmbRank.getSelectionModel().isEmpty())
            cmbRank.setStyle("-fx-border-color: #ff0000");
        else {
            String employeeId=txtEmployeeId.getText();
            String name=txtName.getText();
            String address=txtAddress.getText();
            String nic=txtNic.getText();
            String telNumber=txtTelNumber.getText();
            String rank=cmbRank.getSelectionModel().getSelectedItem();
            try {
                employeeDTO=new EmployeeDTO(employeeId, name, address, nic, telNumber, rank);
                employeeService.saveEmployee(employeeDTO);
                clearAll();
                refreshTable();
                txtName.requestFocus();
                CustomAlert.getInstance().showAlert(Alert.AlertType.INFORMATION,"Success","Successfully Registered Employee !");
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
        else if (cmbRank.getSelectionModel().isEmpty())
            cmbRank.setStyle("-fx-border-color: #ff0000");
        else {
            String employeeId=txtEmployeeId.getText();
            String name=txtName.getText();
            String address=txtAddress.getText();
            String nic=txtNic.getText();
            String telNumber=txtTelNumber.getText();
            String rank=cmbRank.getSelectionModel().getSelectedItem();
            try {
                employeeDTO=new EmployeeDTO(employeeId, name, address, nic, telNumber, rank);
                employeeService.updateEmployee(employeeDTO);
                btnUpdate.setDisable(true);
                btnAdd.setDisable(false);
                clearAll();
                refreshTable();
                txtName.requestFocus();
                isUpdate=false;
                CustomAlert.getInstance().showAlert(Alert.AlertType.INFORMATION, "successfully","Employee data updated !");
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
    void addressKeyPressedOnAction(KeyEvent event) {
        addressValidation();
    }

    @FXML
    void addressOnAction(ActionEvent event) {
        if (isValidAddress)
            txtNic.requestFocus();
    }

    @FXML
    void cmbRankOnAction(ActionEvent event) {
        cmbRank.setStyle("-fx-border-color: #76ff03");
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
    void nicKeyPressedOnAction(KeyEvent event) {
        nicValidation();
    }

    @FXML
    void nicOnAction(ActionEvent event) {
        if (isValidNic)
            txtTelNumber.requestFocus();
    }

    @FXML
    void resetOnAction(ActionEvent event) {
        btnAdd.setDisable(false);
        btnUpdate.setDisable(true);
        clearAll();
    }

    @FXML
    void telNumKeyPressedOnAction(KeyEvent event) {
        telephoneNumberValidation();
    }

    @FXML
    void txtSearchMouseOnClick(MouseEvent event) {
        txtSearch.clear();
        FilteredList<EmployeeTM> filteredList=new FilteredList<>(employeeObservableList, b -> true);
        txtSearch.textProperty().addListener((observable,oldValue,newValue)->{
            filteredList.setPredicate(Employee ->{
                if (newValue.isEmpty() || newValue==null ){
                    return true;
                }
                String searchKeyword=newValue.toLowerCase();
                if (Employee.getEmployeeId().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if (Employee.getName().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if (Employee.getAddress().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if (Employee.getNic().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if (Employee.getTelephoneNumber().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if (Employee.getRank().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else {
                    return false;
                }
            });
        });
        SortedList<EmployeeTM> sortedList=new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tblEmployee.comparatorProperty());
        tblEmployee.setItems(sortedList);
    }

    private void validateAll() {
        nameValidation();
        addressValidation();
        nicValidation();
        telephoneNumberValidation();
    }

}
