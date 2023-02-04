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
import lk.ijse.computershop.dto.SupplierDTO;
import lk.ijse.computershop.dto.SuppliesDTO;
import lk.ijse.computershop.dto.SuppliesDetailsDTO;
import lk.ijse.computershop.service.ServiceFactory;
import lk.ijse.computershop.service.ServiceTypes;
import lk.ijse.computershop.service.custom.ItemService;
import lk.ijse.computershop.service.custom.SupplierService;
import lk.ijse.computershop.service.custom.SuppliesService;
import lk.ijse.computershop.util.CustomAlert;
import lk.ijse.computershop.view.tm.SuppliesCartTM;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Locale;

public class SuppliesFormController {
    @FXML
    private Label lblTotal;

    @FXML
    private AnchorPane anc;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnPlaceOrder;

    @FXML
    private ComboBox<String> cmbCode;

    @FXML
    private ComboBox<String> cmbDescription;

    @FXML
    private ComboBox<String> cmbSupplierId;

    @FXML
    private ComboBox<String> cmbSupplierName;

    @FXML
    private TableColumn<SuppliesCartTM, String> colType;

    @FXML
    private TableColumn<SuppliesCartTM, String> colDescription;
    @FXML
    private TableColumn<SuppliesCartTM, String> colAction;

    @FXML
    private TableColumn<SuppliesCartTM, String> colCode;

    @FXML
    private TableColumn<SuppliesCartTM, Integer> colNewStockQty;

    @FXML
    private TableColumn<SuppliesCartTM, Integer> colOldSctockQty;

    @FXML
    private TableColumn<SuppliesCartTM, Integer> colQty;
    @FXML
    private TableColumn<SuppliesCartTM, Double> colTotal;

    @FXML
    private TableColumn<SuppliesCartTM, Double> colUnitPrice;

    @FXML
    private TableView<SuppliesCartTM> tblSupplies;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtType;

    @FXML
    private TextField txtQtyOnStock;

    @FXML
    private TextField txtSuppliesId;

    @FXML
    private TextField txtUnitPrice;
    private final ObservableList<SuppliesCartTM> suppliesCartTMS = FXCollections.observableArrayList();
    private SuppliesCartTM suppliesCartTM;
    private SuppliesDTO suppliesDTO;
    private double netTotal;
    private boolean isValidQuantity;
    private final SuppliesService suppliesService = ServiceFactory.getInstance().getService(ServiceTypes.SUPPLIES);
    private final SupplierService supplierService = ServiceFactory.getInstance().getService(ServiceTypes.SUPPLIER);
    private final ItemService itemService = ServiceFactory.getInstance().getService(ServiceTypes.ITEM);
    public void initialize(){
        btnAdd.setDisable(true);
        txtQty.setDisable(true);
        loadTable();
        loadCmb();
        reset();
    }

    private void loadCmb() {
        try {
            for (SupplierDTO supplier:supplierService.getAllSupplier()){
                cmbSupplierId.getItems().add(supplier.getSupplierId());
                cmbSupplierName.getItems().add(supplier.getName());
            }
            for (ItemDTO item:itemService.getAllItem()){
                cmbCode.getItems().add(item.getItemCode());
                cmbDescription.getItems().add(item.getDescription());
            }
        } catch (SQLException e) {
            ButtonType result = CustomAlert.getInstance().showAlert(Alert.AlertType.CONFIRMATION, "Data Not Loading", e + ",Data Not Loading !\nYou Want Exit ? ");
            if (result==ButtonType.OK)
                Platform.exit();
        }
    }

    private void reset() {
        try {
            txtSuppliesId.setText(suppliesService.getNextSuppliesId());
            cmbSupplierId.setValue(null);
            cmbSupplierName.setValue(null);
            btnPlaceOrder.setDisable(true);
        } catch (SQLException e) {
            ButtonType result = CustomAlert.getInstance().showAlert(Alert.AlertType.CONFIRMATION, "Data Not Loading", e + ",Data Not Loading !\nYou Want Exit ? ");
            if (result==ButtonType.OK)
                Platform.exit();
        }
    }

    private void loadTable() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colOldSctockQty.setCellValueFactory(new PropertyValueFactory<>("oldQuantity"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colNewStockQty.setCellValueFactory(new PropertyValueFactory<>("newQuantity"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        Callback<TableColumn<SuppliesCartTM, String>, TableCell<SuppliesCartTM, String>> cellFactory = (TableColumn<SuppliesCartTM, String> param) -> {
            // make cell containing buttons
            final TableCell<SuppliesCartTM, String> cell = new TableCell<SuppliesCartTM, String>() {
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
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            ButtonType result = CustomAlert.getInstance().showAlert(Alert.AlertType.CONFIRMATION, "Removing item from cart", "Are You Sure ?");
                            if (result==ButtonType.OK){
                                SuppliesCartTM suppliesCartTm =tblSupplies.getSelectionModel().getSelectedItem();
                                double total= suppliesCartTm.getTotal();
                                netTotal -= total;
                                lblTotal.setText(NumberFormat.getCurrencyInstance(new Locale("en","in")).format(netTotal));
                                tblSupplies.getItems().removeAll(tblSupplies.getSelectionModel().getSelectedItem());
                                if (suppliesCartTMS.isEmpty()){
                                    btnPlaceOrder.setDisable(true);
                                }
                            }
                        });
                        HBox hBox = new HBox(deleteIcon);
                        hBox.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 0, 2, 0));
                        setGraphic(hBox);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        colAction.setCellFactory(cellFactory);
        tblSupplies.setItems(suppliesCartTMS);
    }

    @FXML
    void PlaceOrderOnAction(ActionEvent event) {
        ButtonType result= CustomAlert.getInstance().showAlert(Alert.AlertType.CONFIRMATION, "Place Supplies","Total cost : "+NumberFormat.getCurrencyInstance(new Locale("en","in")).format(netTotal)+"\nAre you confirm ? ");
        if (result==ButtonType.OK) {
            String suppliesId = txtSuppliesId.getText();
            String supplierId = cmbSupplierId.getSelectionModel().getSelectedItem();
            ArrayList<SuppliesDetailsDTO> suppliesDetails = new ArrayList<>();
            for (int i = 0; i < tblSupplies.getItems().size(); i++) {
                SuppliesCartTM tm = suppliesCartTMS.get(i);
                suppliesDetails.add(new SuppliesDetailsDTO(suppliesId, tm.getItemCode(), tm.getQuantity(), tm.getUnitPrice()));
            }
            suppliesDTO = new SuppliesDTO(suppliesId, Date.valueOf(LocalDate.now()), Time.valueOf(LocalTime.now()), supplierId, suppliesDetails);
            try {
                suppliesService.saveSupplies(suppliesDTO);
                reset();
                resetTable();
                cmbCode.setValue(null);
                cmbDescription.setValue(null);
                txtType.clear();
                txtUnitPrice.clear();
                txtQtyOnStock.clear();
                txtQty.clear();
                txtQty.setDisable(true);
                btnAdd.setDisable(true);
                lblTotal.setText("Rs.0.00");
                netTotal = 0.00;
                CustomAlert.getInstance().showAlert(Alert.AlertType.INFORMATION, "Successfully", "supplies added!");
            } catch (SQLException e) {
                CustomAlert.getInstance().showAlert(Alert.AlertType.ERROR, "unsuccessfully", e.getMessage());
            }
        }
    }

    @FXML
    void addOnAction(ActionEvent event) {
        String code = String.valueOf(cmbCode.getValue());
        String description = String.valueOf(cmbDescription.getValue());
        String type=txtType.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int oldQty = Integer.parseInt(txtQty.getText());
        int qty = Integer.parseInt(txtQty.getText());
        int newQty = qty + Integer.parseInt(txtQty.getText());
        double total = unitPrice * qty;
        netTotal +=total;
        txtQty.clear();
        lblTotal.setText(NumberFormat.getCurrencyInstance(new Locale("en","in")).format(netTotal));
        btnAdd.setDisable(true);
        if (!cmbSupplierId.getSelectionModel().isEmpty())
            btnPlaceOrder.setDisable(false);
        if (!suppliesCartTMS.isEmpty()){
            for ( int i=0; i<tblSupplies.getItems().size(); i++){
                if (colCode.getCellData(i).equals(code)){
                    qty += colQty.getCellData(i);
                    newQty += colNewStockQty.getCellData(i);
                    total = unitPrice * qty;

                    suppliesCartTMS.get(i).setQuantity(qty);
                    suppliesCartTMS.get(i).setNewQuantity(newQty);
                    suppliesCartTMS.get(i).setTotal(total);
                    tblSupplies.refresh();
                    return;
                }
            }
        }
        suppliesCartTMS.add(new SuppliesCartTM(code,description,type,unitPrice,oldQty,qty,newQty,total));
        tblSupplies.setItems(suppliesCartTMS);
    }

    @FXML
    void cmbCodeOnAction(ActionEvent event) {
        try {
            String itemCode = cmbCode.getSelectionModel().getSelectedItem();
            if (itemCode != null) {
                ItemDTO item = itemService.getItemByCode(itemCode);
                itemPane(item);
                cmbDescription.setValue(item.getDescription());
            }
        } catch (SQLException e) {
            ButtonType result = CustomAlert.getInstance().showAlert(Alert.AlertType.CONFIRMATION, "Data Not Loading", e + ",Data Not Loading !\nYou Want Exit ? ");
            if (result==ButtonType.OK){
                Platform.exit();
            }
        }
    }

    private void itemPane(ItemDTO item) {
        txtType.setText(item.getItemType());
        txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
        txtQtyOnStock.setText(String.valueOf(item.getQtyOnStock()));
        btnAdd.setDisable(true);
        txtQty.setDisable(false);
        txtQty.requestFocus();
    }

    @FXML
    void cmbDescriptionOnAction(ActionEvent event) {
        try {
            String desc = cmbDescription.getSelectionModel().getSelectedItem();
            if (desc != null) {
                ItemDTO item = itemService.getItemByDescription(desc);
                cmbCode.setValue(item.getItemCode());
            }
        } catch (SQLException e) {
            ButtonType result = CustomAlert.getInstance().showAlert(Alert.AlertType.CONFIRMATION, "Data Not Loading", e + ",Data Not Loading !\nYou Want Exit ? ");
            if (result==ButtonType.OK){
                Platform.exit();
            }
        }
    }

    @FXML
    void cmbSupplierIdOnAction(ActionEvent event) {
        try {
            String supplierId = cmbSupplierId.getSelectionModel().getSelectedItem();
            if (supplierId != null) {
                cmbSupplierName.getSelectionModel().select(supplierService.getSupplierById(supplierId).getName());
                if (!suppliesCartTMS.isEmpty())
                    btnPlaceOrder.setDisable(false);
            }
        } catch (SQLException e) {
            CustomAlert.getInstance().showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML
    void cmbSupplierNameOnAction(ActionEvent event) {
        try {
            String name = cmbSupplierName.getSelectionModel().getSelectedItem();
            if (name != null){
                cmbSupplierId.getSelectionModel().select(supplierService.getSupplierByName(name).getSupplierId());
                if (!suppliesCartTMS.isEmpty())
                    btnPlaceOrder.setDisable(false);
            }
        } catch (SQLException e) {
            CustomAlert.getInstance().showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML
    void resetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        reset();

    }
    @FXML
    public void resetTableOnAction(ActionEvent actionEvent) {
        resetTable();
    }

    private void resetTable() {
        suppliesCartTMS.clear();
        tblSupplies.refresh();
    }

    @FXML
    public void qtyKeyReleasedOnAction(KeyEvent keyEvent) {
        if (txtQty.getText().isEmpty()){
            btnAdd.setDisable(true);
            txtQty.setStyle("-fx-border-color: #ff0000");
            isValidQuantity =false;
        }else {
            btnAdd.setDisable(false);
            txtQty.setStyle("-fx-border-color: #76ff03");
            isValidQuantity =true;
        }
    }
    @FXML
    public void qtyKeyTypedOnAction(KeyEvent keyEvent) {
        if (!keyEvent.getCharacter().matches("\\d")) {
            keyEvent.consume();
        }
    }

    @FXML
    public void qtyOnAction(ActionEvent actionEvent) {
        if (isValidQuantity)
            addOnAction(actionEvent);
    }
}
