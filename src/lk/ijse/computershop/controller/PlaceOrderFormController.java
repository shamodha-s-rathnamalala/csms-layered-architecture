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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import lk.ijse.computershop.dto.ItemDTO;
import lk.ijse.computershop.dto.ItemTransactionDetailsDTO;
import lk.ijse.computershop.dto.RepairTransactionDetailsDTO;
import lk.ijse.computershop.dto.TransactionDTO;
import lk.ijse.computershop.service.ServiceFactory;
import lk.ijse.computershop.service.ServiceTypes;
import lk.ijse.computershop.service.custom.CustomerService;
import lk.ijse.computershop.service.custom.ItemService;
import lk.ijse.computershop.service.custom.RepairService;
import lk.ijse.computershop.service.custom.TransactionService;
import lk.ijse.computershop.util.CustomAlert;
import lk.ijse.computershop.util.Regex;
import lk.ijse.computershop.util.RegexType;
import lk.ijse.computershop.view.tm.ItemCartTM;
import lk.ijse.computershop.view.tm.RepairCartTM;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Locale;
import java.util.stream.Collectors;

public class  PlaceOrderFormController {

    public TextField txtType;
    @FXML
    public JFXButton btnItemUpdate;
    @FXML
    public JFXButton btnRepairUpdate;
    @FXML
    private AnchorPane anc;

    @FXML
    private JFXButton btnItemAdd;

    @FXML
    private JFXButton btnPlaceOrder;

    @FXML
    private JFXButton btnRepairAdd;

    @FXML
    private ComboBox<String> cmbCustomerId;

    @FXML
    private ComboBox<String> cmbItemCode;

    @FXML
    private ComboBox<String> cmbItemDescription;

    @FXML
    private ComboBox<String> cmbRepairId;

    @FXML
    private TableColumn<ItemCartTM, String> colItemAction;

    @FXML
    private TableColumn<RepairCartTM, String> colRepairActon;

    @FXML
    private TableColumn<ItemCartTM, String> colItemCode;

    @FXML
    private TableColumn<ItemCartTM, String> colItemDescription;

    @FXML
    private TableColumn<ItemCartTM, Integer> colItemQty;

    @FXML
    private TableColumn<ItemCartTM, Double> colItemTotal;

    @FXML
    private TableColumn<ItemCartTM, Double> colItemUnitPrice;

    @FXML
    private TableColumn<RepairCartTM, String> colRepairDescription;

    @FXML
    private TableColumn<RepairCartTM, String> colRepairId;

    @FXML
    private TableColumn<RepairCartTM, Double> colRepairPrice;

    @FXML
    private Label lblTotal;

    @FXML
    private TableView<ItemCartTM> tblItem;

    @FXML
    private TableView<RepairCartTM> tblRepair;

    @FXML
    private TextField txtCustomerName;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtQtyOnStock;

    @FXML
    private TextField txtRepairDescription;

    @FXML
    private TextField txtRepairPrice;

    @FXML
    private TextField txtTransactionId;

    @FXML
    private TextField txtUnitPrice;

    private final ObservableList<ItemCartTM> itemCartTMS = FXCollections.observableArrayList();
    private final ObservableList<RepairCartTM> repairCartTms = FXCollections.observableArrayList();
    private ItemCartTM itemCartTM = null;
    private RepairCartTM repairCartTm = null;
    private double netTotal;
    private boolean isValidQuantity;
    private boolean isValidPrice;
    private boolean isItemUpdate;
    private boolean isRepairUpdate;
    private final TransactionService transactionService = ServiceFactory.getInstance().getService(ServiceTypes.TRANSACTION);
    private final CustomerService customerService = ServiceFactory.getInstance().getService(ServiceTypes.CUSTOMER);
    private final ItemService itemService = ServiceFactory.getInstance().getService(ServiceTypes.ITEM);
    private final RepairService repairService = ServiceFactory.getInstance().getService(ServiceTypes.REPAIR);
    public void initialize(){
        btnItemAdd.setDisable(true);
        btnItemUpdate.setDisable(true);
        isItemUpdate =false;
        btnItemUpdate.setVisible(false);

        btnRepairAdd.setDisable(true);
        btnRepairUpdate.setDisable(true);
        isRepairUpdate=false;
        btnRepairUpdate.setVisible(false);

        txtQty.setDisable(true);
        txtRepairPrice.setDisable(true);
        try {
            loadTable();
            loadCmb();
            reset();
        } catch (SQLException e) {
            ButtonType result = CustomAlert.getInstance().showAlert(Alert.AlertType.CONFIRMATION, "Data Not Loading", e + ",Data Not Loading !\nYou Want Exit ? ");
            if (result==ButtonType.OK){
                Platform.exit();
            }
        }
    }

    private void loadTable() {
        //item table
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colItemDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colItemQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colItemUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colItemTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        Callback<TableColumn<ItemCartTM, String>, TableCell<ItemCartTM, String>> itemCellFactory = (TableColumn<ItemCartTM, String> param) -> {
            // make cell containing buttons
            final TableCell<ItemCartTM, String> cell = new TableCell<ItemCartTM, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        deleteIcon.setStyle(" -fx-cursor: hand ;" + "-glyph-size:28px;" + "-fx-fill:#ff1744;");
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);
                        editIcon.setStyle(" -fx-cursor: hand ;" + "-glyph-size:28px;" + "-fx-fill:#00E676;");
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            ButtonType result = CustomAlert.getInstance().showAlert(Alert.AlertType.CONFIRMATION, "Removing item from cart", "Are You Sure ?");
                            if (result == ButtonType.OK){
                                itemCartTM = tblItem.getSelectionModel().getSelectedItem();
                                double total = itemCartTM.getTotal();
                                netTotal -= total;
                                lblTotal.setText(NumberFormat.getCurrencyInstance(new Locale("en","in")).format(netTotal));
                                tblItem.getItems().removeAll(tblItem.getSelectionModel().getSelectedItem());
                                cmbItemCode.setValue(null);
                                if (repairCartTms.isEmpty() && itemCartTMS.isEmpty()){
                                    btnPlaceOrder.setDisable(true);
                                }
                            }
                        });
                        editIcon.setOnMouseClicked((MouseEvent)->{
                            itemCartTM = tblItem.getSelectionModel().getSelectedItem();
                            cmbItemCode.setValue(null);
                            isItemUpdate = true;
                            cmbItemCode.setValue(itemCartTM.getItemCode());
                            txtQty.setText(String.valueOf(itemCartTM.getQuantity()));
                            btnItemAdd.setVisible(false);
                            btnItemUpdate.setVisible(true);
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
        colItemAction.setCellFactory(itemCellFactory);
        tblItem.setItems(itemCartTMS);

        //repair table
        colRepairId.setCellValueFactory(new PropertyValueFactory<>("repairId"));
        colRepairDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colRepairPrice.setCellValueFactory(new PropertyValueFactory<>("repairPrice"));
        Callback<TableColumn<RepairCartTM, String>, TableCell<RepairCartTM, String>> repairCellFactory = (TableColumn<RepairCartTM, String> param) -> {
            // make cell containing buttons
            final TableCell<RepairCartTM, String> cell = new TableCell<RepairCartTM, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        deleteIcon.setStyle(" -fx-cursor: hand ;" + "-glyph-size:28px;" + "-fx-fill:#ff1744;");
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);
                        editIcon.setStyle(" -fx-cursor: hand ;" + "-glyph-size:28px;" + "-fx-fill:#00E676;");
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            ButtonType result = CustomAlert.getInstance().showAlert(Alert.AlertType.CONFIRMATION, "Removing item from cart", "Are You Sure ?");
                            if (result == ButtonType.OK){
                                repairCartTm =tblRepair.getSelectionModel().getSelectedItem();
                                double total = repairCartTm.getRepairPrice();
                                netTotal -= total;
                                lblTotal.setText(NumberFormat.getCurrencyInstance(new Locale("en","in")).format(netTotal));
                                tblRepair.getItems().removeAll(tblRepair.getSelectionModel().getSelectedItem());
                                cmbRepairId.getItems().add(repairCartTm.getRepairId());
                                if (repairCartTms.isEmpty() && itemCartTMS.isEmpty()){
                                    btnPlaceOrder.setDisable(true);
                                }
                            }
                        });
                        editIcon.setOnMouseClicked((MouseEvent)->{
                            isRepairUpdate = true;
                            repairCartTm = tblRepair.getSelectionModel().getSelectedItem();
                            cmbRepairId.setValue(repairCartTm.getRepairId());
                            txtRepairPrice.setText(String.valueOf(repairCartTm.getRepairPrice())+"0");
                            btnRepairAdd.setVisible(false);
                            btnRepairUpdate.setVisible(true);
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
        colRepairActon.setCellFactory(repairCellFactory);
        tblRepair.setItems(repairCartTms);
    }

    private void reset() throws SQLException {
        txtTransactionId.setText(transactionService.getNextTransactionId());
        cmbCustomerId.setValue(null);
        txtCustomerName.clear();
        btnPlaceOrder.setDisable(true);
    }

    private void loadCmb() throws SQLException{
        cmbCustomerId.getItems().clear();
        cmbCustomerId.getItems().addAll(customerService.getAllCustomer().stream().map(c -> c.getCustomerId()).collect(Collectors.toList()));

        cmbItemCode.getItems().clear();
        cmbItemDescription.getItems().clear();
        ArrayList<ItemDTO> items = itemService.getAllItem();
        for (ItemDTO item : items){
            cmbItemCode.getItems().add(item.getItemCode());
            cmbItemDescription.getItems().add(item.getDescription());
        }
        cmbRepairId.getItems().clear();
        cmbRepairId.getItems().addAll(repairService.getNonReturnRepair().stream().map(repairDTO -> repairDTO.getRepairId()).collect(Collectors.toList()));
    }

    @FXML
    void PlaceOrderOnAction(ActionEvent event) {
        ButtonType btnResult= CustomAlert.getInstance().showAlert(Alert.AlertType.CONFIRMATION, "Place Payment","Total cost : "+NumberFormat.getCurrencyInstance(new Locale("en","in")).format(netTotal)+"\nAre you confirm ? ");
        if (btnResult==ButtonType.OK) {
            String transactionId = txtTransactionId.getText();
            String customerId = String.valueOf(cmbCustomerId.getValue());
            ArrayList<ItemTransactionDetailsDTO> itemDetails = new ArrayList<>();
            ArrayList<RepairTransactionDetailsDTO> repairDetails = new ArrayList<>();
            for (int i = 0; i < tblItem.getItems().size(); i++) {
                itemCartTM = itemCartTMS.get(i);
                itemDetails.add(new ItemTransactionDetailsDTO(transactionId, itemCartTM.getItemCode(), itemCartTM.getQuantity(), itemCartTM.getUnitPrice()));
            }
            for (int i = 0; i < tblRepair.getItems().size(); i++) {
                repairCartTm = repairCartTms.get(i);
                repairDetails.add(new RepairTransactionDetailsDTO(transactionId, repairCartTm.getRepairId(), repairCartTm.getRepairPrice()));
            }
            String type=itemDetails.size()>0 && repairDetails.size()>0?"items/repair":itemDetails.size()>0?"items":repairDetails.size()>0?"repair":"";
            TransactionDTO transaction = new TransactionDTO(transactionId, Date.valueOf(LocalDate.now()), Time.valueOf(LocalTime.now()), customerId, type, netTotal,itemDetails,repairDetails);
            try {
                transactionService.saveTransaction(transaction);
                loadCmb();
                reset();
                resetTableOnAction(event);
                cmbItemCode.setValue(null);
                cmbItemDescription.setValue(null);
                cmbRepairId.setValue(null);
                txtType.clear();
                txtUnitPrice.clear();
                txtQtyOnStock.clear();
                txtQty.clear();
                txtRepairDescription.clear();
                txtRepairPrice.clear();
                txtQty.setDisable(true);
                txtRepairPrice.setDisable(true);
                btnItemAdd.setDisable(true);
                btnRepairAdd.setDisable(true);
                lblTotal.setText("Rs.0.00");
                netTotal = 0.00;
                CustomAlert.getInstance().showAlert(Alert.AlertType.INFORMATION, "Successfully", "order added!");
            } catch (SQLException e) {
                CustomAlert.getInstance().showAlert(Alert.AlertType.ERROR, "unsuccessfully", e.getMessage());
            }
        }
    }

    @FXML
    void repairQtyKeyReleasedOnAction(KeyEvent event) {
        if ((!txtRepairPrice.getText().isEmpty()) && Regex.getInstance().isValid(RegexType.PRICE,txtRepairPrice.getText())){
            txtRepairPrice.setStyle("-fx-border-color: #76ff03");
            isValidPrice =true;
            btnRepairAdd.setDisable(false);
            btnRepairUpdate.setDisable(false);
        }else {
            txtRepairPrice.setStyle("-fx-border-color: #ff0000");
            isValidPrice =false;
            btnRepairAdd.setDisable(true);
            btnRepairUpdate.setDisable(true);
        }
    }

    @FXML
    void cmbCustomerIdOnAction(ActionEvent event) {
        try {
            String cusId = cmbCustomerId.getValue();
            if (cusId != null) {
                txtCustomerName.setText(customerService.getCustomer(cusId).getName());
                if ((!itemCartTMS.isEmpty()) || (!repairCartTms.isEmpty()))
                    btnPlaceOrder.setDisable(false);
            }
        } catch (SQLException e) {
            ButtonType result = CustomAlert.getInstance().showAlert(Alert.AlertType.CONFIRMATION, "Data Loading Error", e.getMessage() + ",Data Not Loading !\nYou Want Exit ? ");
            if (result == ButtonType.OK){
                Platform.exit();
            }
        }
    }

    @FXML
    void cmbItemCodeOnAction(ActionEvent event) {
        try {
            String itemCode = cmbItemCode.getSelectionModel().getSelectedItem();
            if (itemCode != null) {
                ItemDTO item = itemService.getItemByCode(itemCode);
                itemPane(item);
                cmbItemDescription.setValue(item.getDescription());
            }
        } catch (SQLException e) {
            ButtonType result = CustomAlert.getInstance().showAlert(Alert.AlertType.CONFIRMATION, "Data Loading Error", e.getMessage() + ",Data Not Loading !\nYou Want Exit ? ");
            if (result == ButtonType.OK){
                Platform.exit();
            }
        }
    }
    private void itemPane(ItemDTO item) {
        int qtyOnStock = item.getQtyOnStock();
        if (!isItemUpdate){
            if (!tblItem.getItems().isEmpty()) {
                for (int i = 0; i < tblItem.getItems().size(); i++) {
                    if (colItemCode.getCellData(i).equals(item.getItemCode())) {
                        qtyOnStock -= colItemQty.getCellData(i);
                        break;
                    }

                }
            }
        }
        isItemUpdate =false;
        txtQtyOnStock.setText(String.valueOf(qtyOnStock));
        txtType.setText(item.getItemType());
        txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
        btnItemAdd.setDisable(true);
        btnItemUpdate.setDisable(true);
        txtQty.setDisable(false);
        btnItemAdd.setVisible(true);
        btnItemUpdate.setVisible(false);
        txtQty.clear();
        txtQty.requestFocus();
    }
    @FXML
    void cmbItemDescriptionOnAction(ActionEvent event) {
        try {
            String description = cmbItemDescription.getSelectionModel().getSelectedItem();
            if (description != null) {
                ItemDTO item = itemService.getItemByDescription(description);
                cmbItemCode.setValue(item.getItemCode());
            }
        } catch (SQLException e) {
            ButtonType result = CustomAlert.getInstance().showAlert(Alert.AlertType.CONFIRMATION, "Data Loading Error", e.getMessage() + ",Data Not Loading !\nYou Want Exit ? ");
            if (result == ButtonType.OK){
                Platform.exit();
            }
        }
    }

    @FXML
    void cmbRepairIdOnActon(ActionEvent event) {
        try {
            String repairId=cmbRepairId.getValue();
            if (repairId != null) {
                String description = repairService.getRepairById(repairId).getDescription();
                txtRepairDescription.setText(description);
                btnRepairAdd.setDisable(true);
                btnRepairAdd.setVisible(true);
                btnRepairUpdate.setDisable(true);
                btnRepairUpdate.setVisible(false);
                txtRepairPrice.setDisable(false);
                txtRepairPrice.clear();
                txtRepairPrice.requestFocus();
            }
        } catch (SQLException e) {
            ButtonType result = CustomAlert.getInstance().showAlert(Alert.AlertType.CONFIRMATION, "Data Not Loading", e.getMessage() + ",Data Not Loading !\nYou Want Exit ? ");
            if (result==ButtonType.OK){
                Platform.exit();
            }
        }
    }
    @FXML
    void resetOnAction(ActionEvent event) {
        try {
            reset();
        } catch (SQLException e) {
            ButtonType result = CustomAlert.getInstance().showAlert(Alert.AlertType.CONFIRMATION, "Data Not Loading", e + ",Data Not Loading !\nYou Want Exit ? ");
            if (result==ButtonType.OK){
                Platform.exit();
            }
        }
    }
    @FXML
    void itemAddOnAction(ActionEvent event) {
        String code = String.valueOf(cmbItemCode.getValue());
        String description = String.valueOf(cmbItemDescription.getValue());
        String type=txtType.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int qty = Integer.parseInt(txtQty.getText());
        int qtyOnStock = Integer.parseInt(txtQtyOnStock.getText())-qty;
        txtQtyOnStock.setText(String.valueOf(qtyOnStock));
        double total = unitPrice * qty;
        netTotal +=total;
        txtQty.clear();
        lblTotal.setText(NumberFormat.getCurrencyInstance(new Locale("en","in")).format(netTotal));
        btnItemAdd.setDisable(true);
        if (!cmbCustomerId.getSelectionModel().isEmpty())
            btnPlaceOrder.setDisable(false);
        else
            btnPlaceOrder.setDisable(true);
        if (!itemCartTMS.isEmpty()){
            for ( int i = 0; i < tblItem.getItems().size(); i++){
                if (colItemCode.getCellData(i).equals(code)){
                    qty += colItemQty.getCellData(i);
                    total = unitPrice * qty;
                    itemCartTMS.get(i).setQuantity(qty);
                    itemCartTMS.get(i).setTotal(total);
                    tblItem.refresh();
                    return;
                }
            }
        }
        itemCartTMS.add(new ItemCartTM(code,description,qty,unitPrice,total));
        tblItem.setItems(itemCartTMS);
    }

    @FXML
    void repairAddOnAction(ActionEvent event) {
        String id = String.valueOf(cmbRepairId.getValue());
        String description = String.valueOf(txtRepairDescription.getText());
        double repairPrice = Double.parseDouble(txtRepairPrice.getText());
        netTotal += repairPrice;
        txtRepairPrice.clear();
        lblTotal.setText(NumberFormat.getCurrencyInstance(new Locale("en","in")).format(netTotal));
        btnRepairAdd.setDisable(true);
        if (cmbCustomerId.getSelectionModel().isEmpty())
            btnPlaceOrder.setDisable(true);
        else
            btnPlaceOrder.setDisable(false);
        repairCartTms.add(new RepairCartTM(id,description,repairPrice));
        tblRepair.setItems(repairCartTms);
        cmbRepairId.getItems().remove(id);
        cmbRepairId.setValue(null);
        txtRepairPrice.setDisable(true);
    }

    @FXML
    void itemQtyKeyReleasedOnAction(KeyEvent event) {
        int qtyOnStock = Integer.parseInt(txtQtyOnStock.getText());
        int qty = txtQty.getText().isEmpty()?0:Integer.parseInt(txtQty.getText());
        if (txtQty.getText().isEmpty() || 0 > qtyOnStock-qty){
            btnItemAdd.setDisable(true);
            btnItemUpdate.setDisable(true);
            txtQty.setStyle("-fx-border-color: #ff0000");
            isValidQuantity =false;
        }else {
            btnItemAdd.setDisable(false);
            btnItemUpdate.setDisable(false);
            txtQty.setStyle("-fx-border-color: #76ff03");
            isValidQuantity =true;
        }
    }

    @FXML
    void qtyKeyTypedOnAction(KeyEvent event) {
        if (!event.getCharacter().matches("[\\d]")) {
            event.consume();
        }
    }

    public void priceKeyTypedOnAction(KeyEvent keyEvent) {
        if (!keyEvent.getCharacter().matches("[\\d\\.]")) {
            keyEvent.consume();
        }
    }



    @FXML
    void resetTableOnAction(ActionEvent event) {
        itemCartTMS.clear();
        tblItem.refresh();
        repairCartTms.clear();
        tblRepair.refresh();
        netTotal = 0;
        lblTotal.setText(NumberFormat.getCurrencyInstance(new Locale("en","in")).format(netTotal));
    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {
        if (isValidQuantity) {
            if (isItemUpdate)
                itemUpdateOnAction(event);
            else
                itemAddOnAction(event);
        }
    }

    @FXML
    void txtRepairPriceOnAction(ActionEvent event) {
        if (isValidPrice) {
            if (isRepairUpdate)
                repairUpdateOnAction(event);
            else
                repairAddOnAction(event);
        }
    }

    public void itemUpdateOnAction(ActionEvent actionEvent) {
        isItemUpdate = false;
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        itemCartTM = tblItem.getSelectionModel().getSelectedItem();
        int qty = itemCartTM.getQuantity();
        netTotal -= qty*unitPrice;
        qty = Integer.parseInt(txtQty.getText());
        itemCartTM.setQuantity(qty);
        itemCartTM.setTotal(qty*unitPrice);
        netTotal += qty*unitPrice;
        lblTotal.setText(NumberFormat.getCurrencyInstance(new Locale("en","in")).format(netTotal));
        tblItem.refresh();
        cmbItemCode.setValue(null);
    }

    public void repairUpdateOnAction(ActionEvent actionEvent) {
        isRepairUpdate = false;
        RepairCartTM tm = tblRepair.getSelectionModel().getSelectedItem();
        double price = tm.getRepairPrice();
        netTotal -= price;
        price = Double.parseDouble(txtRepairPrice.getText());
        netTotal += price;
        tm.setRepairPrice(price);
        tblRepair.refresh();
        lblTotal.setText(NumberFormat.getCurrencyInstance(new Locale("en","in")).format(netTotal));
        cmbRepairId.setValue(null);
        txtRepairPrice.setDisable(true);
    }

    public void resetItem(ActionEvent actionEvent) {
        cmbItemCode.setValue(null);
        cmbItemDescription.setValue(null);
        txtQty.setDisable(true);
    }

    public void resetRepair(ActionEvent actionEvent) {
        cmbRepairId.setValue(null);
        txtRepairPrice.setDisable(true);
    }
}
