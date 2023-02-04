package lk.ijse.computershop.controller;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import lk.ijse.computershop.dto.CustomerDTO;
import lk.ijse.computershop.dto.RepairDTO;
import lk.ijse.computershop.dto.RepairTransactionDetailsDTO;
import lk.ijse.computershop.dto.TransactionDTO;
import lk.ijse.computershop.service.ServiceFactory;
import lk.ijse.computershop.service.ServiceTypes;
import lk.ijse.computershop.service.custom.CustomerService;
import lk.ijse.computershop.service.custom.RepairService;
import lk.ijse.computershop.service.custom.TransactionService;
import lk.ijse.computershop.util.CustomAlert;
import lk.ijse.computershop.view.tm.RepairTM;
import lk.ijse.computershop.view.tm.ReturnedRepairTM;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class RepairFormController {

    @FXML
    public HBox hbStatus;
    @FXML
    private AnchorPane ancRepair;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private ComboBox<String> cmbCustomerId;

    @FXML
    private ComboBox<String> cmbCustomerName;

    @FXML
    private ComboBox<String> cmbStatus;

    @FXML
    private TableColumn<RepairTM, String> colAction;

    @FXML
    private TableColumn<RepairTM, String> colCustomerId;

    @FXML
    private TableColumn<RepairTM, String> colDescription;

    @FXML
    private TableColumn<RepairTM, LocalDate> colReceiveDate;

    @FXML
    private TableColumn<RepairTM, String> colRepairId;

    @FXML
    private TableColumn<RepairTM, LocalDate> colReturnDate;

    @FXML
    private TableColumn<RepairTM, String> colState;
    @FXML
    private TableColumn<ReturnedRepairTM, String> colReturnedCustomerId;

    @FXML
    private TableColumn<ReturnedRepairTM, String> colReturnedDescription;

    @FXML
    private TableColumn<ReturnedRepairTM, Double> colReturnedPrice;

    @FXML
    public TableColumn<ReturnedRepairTM,String> colReceiveCustomerId;
    @FXML
    private TableColumn<ReturnedRepairTM, LocalDate> colReturnedReceiveDate;

    @FXML
    private TableColumn<ReturnedRepairTM, String> colReturnedRepairId;

    @FXML
    private TableColumn<ReturnedRepairTM, String> colReturnedTransactionId;


    @FXML
    private TableColumn<ReturnedRepairTM, LocalDate> colTrueReturnDate;

    @FXML
    private DatePicker dpReceiveDate;

    @FXML
    private DatePicker dpReturnDate;

    @FXML
    private TableView<RepairTM> tblRepair;

    @FXML
    private TableView<ReturnedRepairTM> tblReturnedRepair;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtRepairId;
    private final ObservableList<RepairTM> repairTMS = FXCollections.observableArrayList();
    private final ObservableList<ReturnedRepairTM> returnRepairTms = FXCollections.observableArrayList();
    private RepairTM repairTM = null;
    private RepairDTO repairDTO = null;
    private final RepairService repairService = ServiceFactory.getInstance().getService(ServiceTypes.REPAIR);
    private final TransactionService transactionService = ServiceFactory.getInstance().getService(ServiceTypes.TRANSACTION);
    private final CustomerService customerService = ServiceFactory.getInstance().getService(ServiceTypes.CUSTOMER);
    public void initialize() {
        loadTable();
        loadCmb();
        reset();
    }

    private void refreshTable() {
        repairTMS.clear();
        try {
            repairTMS.addAll(repairService.getNonReturnRepair().stream().map(r -> new RepairTM(r.getRepairId(), r.getReceiveDate().toLocalDate(), r.getReturnDate().toLocalDate(), r.getStatus(), r.getDescription(), r.getCustomerId())).collect(Collectors.toList()));
            tblRepair.setItems(repairTMS);
        } catch (SQLException e) {
            ButtonType result = CustomAlert.getInstance().showAlert(Alert.AlertType.CONFIRMATION, "Data Not Loading", e.getMessage() + ",Data Not Loading !\nYou Want Exit ? ");
            if (result==ButtonType.OK){
                Platform.exit();
            }
        }
    }

    private void loadTable() {
        refreshTable();
        colRepairId.setCellValueFactory(new PropertyValueFactory<>("repairId"));
        colReceiveDate.setCellValueFactory(new PropertyValueFactory<>("receiveDate"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        colState.setCellValueFactory(new PropertyValueFactory<>("state"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        //repair returned Table
        colReturnedRepairId.setCellValueFactory(new PropertyValueFactory<>("repairId"));
        colReturnedTransactionId.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        colReturnedDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colReceiveCustomerId.setCellValueFactory(new PropertyValueFactory<>("receiveCustomerId"));
        colReturnedReceiveDate.setCellValueFactory(new PropertyValueFactory<>("receiveDate"));
        colReturnedCustomerId.setCellValueFactory(new PropertyValueFactory<>("returnedCustomerId"));
        colTrueReturnDate.setCellValueFactory(new PropertyValueFactory<>("trueReturnDate"));
        colReturnedPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        try {
            for (RepairDTO repair:repairService.getAllReturnedRepair()){
                RepairTransactionDetailsDTO rtDTO= transactionService.getRepairTransactionDetails(repair.getRepairId());
                TransactionDTO transactionDTO = transactionService.getTransaction(rtDTO.getTransactionId());
                returnRepairTms.add(new ReturnedRepairTM(
                        repair.getRepairId(),
                        transactionDTO.getTransactionId(),
                        repair.getDescription(),
                        repair.getCustomerId(),
                        repair.getReceiveDate().toLocalDate(),
                        transactionDTO.getCustomerId(),
                        transactionDTO.getDate().toLocalDate(),
                        rtDTO.getRepairPrice()
                ));
            }
        } catch (SQLException e) {
            CustomAlert.getInstance().showAlert(Alert.AlertType.ERROR,"Database Error",e.getMessage());
        }

        tblReturnedRepair.setItems(returnRepairTms);
        //action column
        Callback<TableColumn<RepairTM, String>, TableCell<RepairTM, String>> cellFactory = (TableColumn<RepairTM, String> param) -> {
            // make cell containing buttons
            final TableCell<RepairTM, String> cell = new TableCell<RepairTM, String>() {
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
                            repairTM =tblRepair.getSelectionModel().getSelectedItem();
                            ButtonType result = CustomAlert.getInstance().showAlert(Alert.AlertType.CONFIRMATION, "Deleting confirmation","Are You Sure ? ");
                            if (result == ButtonType.OK){
                                try {
                                    repairService.deleteRepair(repairTM.getRepairId());
                                    reset();
                                    refreshTable();
                                    CustomAlert.getInstance().showAlert(Alert.AlertType.INFORMATION, "Deleting Successfully", repairTM.getRepairId()+" was deleted");
                                } catch (SQLException e) {
                                    CustomAlert.getInstance().showAlert(Alert.AlertType.WARNING, "Deleting error",e.getMessage());
                                }
                            }
                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            repairTM =tblRepair.getSelectionModel().getSelectedItem();
                            txtRepairId.setText(repairTM.getRepairId());
                            cmbCustomerId.getSelectionModel().select(repairTM.getCustomerId());
                            cmbCustomerIdOnAction(new ActionEvent());
                            dpReceiveDate.setValue(repairTM.getReceiveDate());
                            dpReturnDate.setValue(repairTM.getReturnDate());
                            txtDescription.setText(repairTM.getDescription());
                            cmbStatus.getSelectionModel().select(repairTM.getStatus());
                            btnUpdate.setVisible(true);
                            btnAdd.setVisible(false);
                            hbStatus.setVisible(true);
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
        tblRepair.setItems(repairTMS);
    }

    private void loadCmb() {
        try {
            String[] status={"repairing","repaired"};
            cmbStatus.getItems().addAll(status);
            ArrayList<CustomerDTO> customers = customerService.getAllCustomer();
            cmbCustomerId.getItems().addAll(customers.stream().map(customer -> customer.getCustomerId()).collect(Collectors.toList()));
            cmbCustomerName.getItems().addAll(customers.stream().map(customer -> customer.getName()).collect(Collectors.toList()));
        } catch (SQLException e) {
            CustomAlert.getInstance().showAlert(Alert.AlertType.ERROR,"Database error",e.getMessage());
        }
    }

    @FXML
    void addOnAction(ActionEvent event) {
        if (cmbCustomerId.getSelectionModel().isEmpty()) {
            cmbCustomerId.setStyle("-fx-border-color: #ff0000");
            cmbCustomerName.setStyle("-fx-border-color: #ff0000");
        } else if (dpReceiveDate.getValue()==null){
            dpReceiveDate.setStyle("-fx-border-color: #ff0000");
        } else if (dpReturnDate.getValue()==null) {
            dpReturnDate.setStyle("-fx-border-color: #ff0000");
        } else if (txtDescription.getText()==null) {
            txtDescription.setText("-fx-border-color: #ff0000");
        } else {
            String repairId=txtRepairId.getText();
            LocalDate receiveDate=dpReceiveDate.getValue();
            LocalDate returnDate=dpReturnDate.getValue();
            String status="repairing";
            String description=txtDescription.getText();
            String customerId=cmbCustomerId.getSelectionModel().getSelectedItem();
            try {
                repairDTO = new RepairDTO(repairId, Date.valueOf(receiveDate), Date.valueOf(returnDate), status, description, customerId);
                repairService.saveRepair(repairDTO);
                reset();
                refreshTable();
                CustomAlert.getInstance().showAlert(Alert.AlertType.INFORMATION, "successfully","Repair data added.");
            } catch (SQLException e) {
                CustomAlert.getInstance().showAlert(Alert.AlertType.ERROR, "unsuccessfully",e.getMessage());
            }
        }
    }

    @FXML
    void cmbCustomerIdOnAction(ActionEvent event) {
        try {
            String customerId = cmbCustomerId.getSelectionModel().getSelectedItem();
            if (customerId != null) {
                cmbCustomerName.getSelectionModel().select(customerService.getCustomer(customerId).getName());
                cmbCustomerId.setStyle("-fx-border-color: #76ff03");
                cmbCustomerName.setStyle("-fx-border-color: #76ff03");
            }
        } catch (SQLException e) {
            CustomAlert.getInstance().showAlert(Alert.AlertType.ERROR, "Error",e.getMessage());
        }
    }

    @FXML
    void cmbCustomerNameOnAction(ActionEvent event) {
        try {
            String name = cmbCustomerName.getSelectionModel().getSelectedItem();
            if (name != null) {
                cmbCustomerId.getSelectionModel().select(customerService.getCustomerByName(name).getCustomerId());
                cmbCustomerId.setStyle("-fx-border-color: #76ff03");
                cmbCustomerName.setStyle("-fx-border-color: #76ff03");
            }
        } catch (SQLException e) {
            CustomAlert.getInstance().showAlert(Alert.AlertType.ERROR, "Error",e.getMessage());
        }
    }

    @FXML
    public void checkCustomerIsSelect(MouseEvent mouseEvent) {
        if (cmbCustomerId.getSelectionModel().isEmpty()){
            cmbCustomerId.setStyle("-fx-border-color: #ff0000");
            cmbCustomerName.setStyle("-fx-border-color: #ff0000");
        }
        if (dpReceiveDate.getValue() != null){
            dpReceiveDate.setStyle("-fx-border-color: #76ff03");
        }
        if (dpReturnDate.getValue() != null){
            dpReturnDate.setStyle("-fx-border-color: #76ff03");
        }
        if (txtDescription.getText() != null){
            txtDescription.setStyle("-fx-border-color: #76ff03");
        }
        if (!cmbStatus.getSelectionModel().isEmpty()){
            cmbStatus.setStyle("-fx-border-color: #76ff03");
        }
    }

    @FXML
    void resetOnAction(ActionEvent event) {
        reset();
    }

    @FXML
    void updateOnAction(ActionEvent event) {
        if (cmbCustomerId.getSelectionModel().isEmpty()) {
            cmbCustomerId.setStyle("-fx-border-color: #ff0000");
            cmbCustomerName.setStyle("-fx-border-color: #ff0000");
        } else if (dpReceiveDate.getValue()==null){
            dpReceiveDate.setStyle("-fx-border-color: #ff0000");
        } else if (dpReturnDate.getValue()==null) {
            dpReturnDate.setStyle("-fx-border-color: #ff0000");
        } else if (txtDescription.getText()==null) {
            txtDescription.setText("-fx-border-color: #ff0000");
        } else if (cmbStatus.getSelectionModel().isEmpty()) {
            cmbStatus.setStyle("-fx-border-color: #ff0000");
        }else {
            String repairId=txtRepairId.getText();
            LocalDate receiveDate=dpReceiveDate.getValue();
            LocalDate returnDate=dpReturnDate.getValue();
            String state= cmbStatus.getSelectionModel().getSelectedItem();
            String description=txtDescription.getText();
            String customerId=cmbCustomerId.getSelectionModel().getSelectedItem();
            try {
                repairDTO = new RepairDTO(repairId,Date.valueOf(receiveDate),Date.valueOf(returnDate),state,description,customerId);
                repairService.updateRepair(repairDTO);
                reset();
                refreshTable();
                CustomAlert.getInstance().showAlert(Alert.AlertType.INFORMATION, "successfully","Repair data updated.");
            } catch (SQLException e) {
                CustomAlert.getInstance().showAlert(Alert.AlertType.ERROR, "unsuccessfully",e.getMessage());
            }
        }
    }
    private void reset(){
        try {
            String repairId = repairService.getNextRepairId();
            txtRepairId.setText(repairId);
            btnUpdate.setVisible(false);
            btnAdd.setVisible(true);
            hbStatus.setVisible(false);
            cmbCustomerId.getSelectionModel().clearSelection();
            cmbCustomerName.getSelectionModel().clearSelection();
            dpReceiveDate.setValue(null);
            dpReturnDate.setValue(null);
            txtDescription.clear();
            cmbStatus.getSelectionModel().clearSelection();
        } catch (SQLException e) {
            CustomAlert.getInstance().showAlert(Alert.AlertType.ERROR, "error",e.getMessage());
        }
    }

    @FXML
    public void dpReceiveDateOnAction(ActionEvent actionEvent) {
        dpReturnDate.setDayCellFactory(d -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (dpReceiveDate.getValue() != null) {
                    setDisable(item.isBefore(dpReceiveDate.getValue()));
                }
            }
        });
    }
    @FXML
    public void dpReturnDateOnAction(ActionEvent actionEvent) {
        dpReceiveDate.setDayCellFactory(d -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (dpReturnDate.getValue() != null) {
                    setDisable(item.isAfter(dpReturnDate.getValue()));
                }
            }
        });
    }
}
