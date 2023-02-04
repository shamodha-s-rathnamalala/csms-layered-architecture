package lk.ijse.computershop.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import lk.ijse.computershop.dto.ItemTransactionDetailsDTO;
import lk.ijse.computershop.dto.RepairTransactionDetailsDTO;
import lk.ijse.computershop.dto.TransactionDTO;
import lk.ijse.computershop.service.ServiceFactory;
import lk.ijse.computershop.service.ServiceTypes;
import lk.ijse.computershop.service.custom.ItemService;
import lk.ijse.computershop.service.custom.RepairService;
import lk.ijse.computershop.service.custom.TransactionService;
import lk.ijse.computershop.util.CustomAlert;
import lk.ijse.computershop.view.tm.ItemCartTM;
import lk.ijse.computershop.view.tm.RepairCartTM;
import lk.ijse.computershop.view.tm.TransactionTM;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ViewPurchasesFormController {

    @FXML
    private AnchorPane ancViewPurchase;

    @FXML
    private TableColumn<TransactionTM, String> colAction;

    @FXML
    private TableColumn<TransactionTM, String> colCusId;

    @FXML
    private TableColumn<TransactionTM, String> colCusName;

    @FXML
    private TableColumn<TransactionTM, String> colDateTime;

    @FXML
    private TableColumn<ItemCartTM, String> colItemCode;

    @FXML
    private TableColumn<ItemCartTM, String> colItemDiscription;

    @FXML
    private TableColumn<ItemCartTM, Integer> colItemQty;

    @FXML
    private TableColumn<ItemCartTM, Double> colItemTotal;

    @FXML
    private TableColumn<RepairCartTM, String> colRepairDescription;

    @FXML
    private TableColumn<RepairCartTM, String> colRepairId;

    @FXML
    private TableColumn<RepairCartTM, Double> colRepairTotal;

    @FXML
    private TableColumn<TransactionTM, String> colTelNumber;

    @FXML
    private TableColumn<TransactionTM, String> colTransactionId;

    @FXML
    private TableColumn<TransactionTM, Double> colTransactionTotal;

    @FXML
    private TableColumn<TransactionTM, String> colType;

    @FXML
    private TableColumn<ItemCartTM, Double> colUnitPrice;

    @FXML
    private Label lblTransactionId;

    @FXML
    private TableView<ItemCartTM> tblItem;
    private final ObservableList<ItemCartTM> itemCartTMS = FXCollections.observableArrayList();

    @FXML
    private TableView<RepairCartTM> tblRepair;
    private final ObservableList<RepairCartTM> repairCartTMS = FXCollections.observableArrayList();
    @FXML
    private TableView<TransactionTM> tblTransaction;
    private final ObservableList<TransactionTM> transactionTMS = FXCollections.observableArrayList();
    private final TransactionService transactionService = ServiceFactory.getInstance().getService(ServiceTypes.TRANSACTION);
    private final ItemService itemService = ServiceFactory.getInstance().getService(ServiceTypes.ITEM);
    private final RepairService repairService = ServiceFactory.getInstance().getService(ServiceTypes.REPAIR);
    private ArrayList<TransactionDTO> transactionArrayList = new ArrayList<>();
    public void initialize(){
        setCellTableFactory();
        loadTransactionArrayList();
        loadTransactionTable();
    }

    private void loadTransactionArrayList() {
        try {
            transactionArrayList = transactionService.getAllFullTransaction();
        } catch (SQLException e) {
            ButtonType result = CustomAlert.getInstance().showAlert(Alert.AlertType.CONFIRMATION, "Data Not Loading", e + ",Data Not Loading !\nYou Want Exit ? ");
            if (result==ButtonType.OK){
                Platform.exit();
            }
        }
    }

    private void loadTransactionTable() {
        transactionTMS.addAll(transactionArrayList.stream().map(t -> new TransactionTM(t.getCustomerId(), t.getTransactionId(), t.getDate().toString()+t.getTime().toString(), t.getType(), t.getCustomerName(), t.getTelNumber(), t.getTotal())).collect(Collectors.toList()));
        tblTransaction.setItems(transactionTMS);
    }

    private void setCellTableFactory() {
        //item table
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colItemDiscription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colItemQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colItemTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        //repair table
        colRepairId.setCellValueFactory(new PropertyValueFactory<>("repairId"));
        colRepairDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colRepairTotal.setCellValueFactory(new PropertyValueFactory<>("repairPrice"));

        //transaction table
        colCusId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colTransactionId.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        colDateTime.setCellValueFactory(new PropertyValueFactory<>("dataTime"));
        colType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        colCusName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colTelNumber.setCellValueFactory(new PropertyValueFactory<>("telNumber"));
        colTransactionTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        Callback<TableColumn<TransactionTM, String>, TableCell<TransactionTM, String>> cellFactory = (TableColumn<TransactionTM, String> param) -> {
            // make cell containing buttons
            final TableCell<TransactionTM, String> cell = new TableCell<TransactionTM, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        FontAwesomeIconView show = new FontAwesomeIconView(FontAwesomeIcon.EYE);
                        show.setStyle(" -fx-cursor: hand ;" + "-glyph-size:28px;" + "-fx-fill:#f1c40f;");
                        show.setOnMouseClicked((MouseEvent event) -> {
                            TransactionTM transactionTm = tblTransaction.getSelectionModel().getSelectedItem();
                            lblTransactionId.setText(transactionTm.getTransactionId());
                            refreshTable(transactionTm.getTransactionId());
                        });
                        HBox hBox = new HBox(show);
                        hBox.setStyle("-fx-alignment:center");
                        HBox.setMargin(show, new Insets(2, 0, 2, 0));
                        setGraphic(hBox);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        colAction.setCellFactory(cellFactory);
    }

    private void refreshTable(String transactionId) {
        itemCartTMS.clear();
        repairCartTMS.clear();
        try {
            for (int i = 0; i < transactionArrayList.size(); i++) {
                if (transactionArrayList.get(i).getTransactionId().equals(transactionId)) {
                    for (ItemTransactionDetailsDTO detailsDTO : transactionArrayList.get(i).getItemTransactionDetailsArrayList()){
                        String description = itemService.getItemByCode(detailsDTO.getItemCode()).getDescription();
                        double total = detailsDTO.getQuantity() * detailsDTO.getUnitPrice();
                        itemCartTMS.add(new ItemCartTM(detailsDTO.getItemCode(), description, detailsDTO.getQuantity(), detailsDTO.getUnitPrice(), total));
                    }
                    for (RepairTransactionDetailsDTO detailsDTO : transactionArrayList.get(i).getRepairTransactionDetailsArrayList()){
                        String description = repairService.getRepairById(detailsDTO.getRepairId()).getDescription();
                        repairCartTMS.add(new RepairCartTM(detailsDTO.getRepairId(), description, detailsDTO.getRepairPrice()));
                    }
                }
            }
        }catch (SQLException e){
            ButtonType result = CustomAlert.getInstance().showAlert(Alert.AlertType.CONFIRMATION, "Data Not Loading", e + ",Data Not Loading !\nYou Want Exit ? ");
            if (result==ButtonType.OK){
                Platform.exit();
            }
        }
        tblItem.setItems(itemCartTMS);
        tblRepair.setItems(repairCartTMS);
    }
}
