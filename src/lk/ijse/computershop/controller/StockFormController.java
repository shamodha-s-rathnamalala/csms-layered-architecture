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
import lk.ijse.computershop.dto.ItemDTO;
import lk.ijse.computershop.service.ServiceFactory;
import lk.ijse.computershop.service.ServiceTypes;
import lk.ijse.computershop.service.custom.ItemService;
import lk.ijse.computershop.util.CustomAlert;
import lk.ijse.computershop.view.tm.ItemTM;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class StockFormController {
    @FXML
    public JFXButton btnM;
    @FXML
    public JFXButton btnP;
    @FXML
    private AnchorPane ancStock;

    @FXML
    private ComboBox<String> cmbDescription;

    @FXML
    private ComboBox<String> cmbItem;

    @FXML
    private TableColumn<ItemTM, String> colAction;

    @FXML
    private TableColumn<ItemTM, String> colCode;

    @FXML
    private TableColumn<ItemTM, String> colDescription;

    @FXML
    private TableColumn<ItemTM, Integer> colQtyOnStock;

    @FXML
    private TableColumn<ItemTM, String> colType;

    @FXML
    private TableColumn<ItemTM, Double> colUnitPrice;

    @FXML
    private TableView<ItemTM> tblItem;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtSearch;
    private final ObservableList<ItemTM> itemObservableList = FXCollections.observableArrayList();
    private ItemTM itemTM = null;
    private ItemDTO itemDTO = null;
    private final ItemService itemService = ServiceFactory.getInstance().getService(ServiceTypes.ITEM);
    public void initialize(){
        loadCmb();
        loadTable();
        reset();
    }

    private void loadTable() {
        refreshTable();
        colCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colType.setCellValueFactory(new PropertyValueFactory<>("itemType"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnStock.setCellValueFactory(new PropertyValueFactory<>("qtyOnStock"));
        Callback<TableColumn<ItemTM, String>, TableCell<ItemTM, String>> cellFactory = (TableColumn<ItemTM, String> param) -> {
            // make cell containing buttons
            final TableCell<ItemTM, String> cell = new TableCell<ItemTM, String>() {
                @Override
                public void updateItem(String item2, boolean empty) {
                    super.updateItem(item2, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);
                        editIcon.setStyle(" -fx-cursor: hand ;" + "-glyph-size:28px;" + "-fx-fill:#00E676;");
                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            itemTM=tblItem.getSelectionModel().getSelectedItem();
                            cmbItem.getSelectionModel().select(itemTM.getItemCode());
                            cmbDescription.getSelectionModel().select(itemTM.getDescription());
                            txtQty.setStyle("-fx-border-color: #76ff03");
                            btnM.setDisable(false);
                            btnP.setDisable(false);
                        });
                        HBox hBox = new HBox(editIcon);
                        hBox.setStyle("-fx-alignment:center");
                        HBox.setMargin(editIcon, new Insets(2, 0, 0, 0));
                        setGraphic(hBox);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        colAction.setCellFactory(cellFactory);
        tblItem.setItems(itemObservableList);
    }

    private void refreshTable() {
        try {
            itemObservableList.clear();
            itemObservableList.addAll(itemService.getAllItem().stream().map(item -> new ItemTM(item.getItemCode(), item.getItemType(), item.getDescription(), item.getUnitPrice(), item.getQtyOnStock())).collect(Collectors.toList()));
            tblItem.setItems(itemObservableList);
        } catch (SQLException e) {
            CustomAlert.getInstance().showAlert(Alert.AlertType.ERROR, "Data Not Loading", e.getMessage());
        }
    }

    private void loadCmb() {
        try {
            ArrayList<ItemDTO>allItem = itemService.getAllItem();
            for (ItemDTO item:allItem) {
                cmbItem.getItems().add(item.getItemCode());
                cmbDescription.getItems().add(item.getDescription());
            }
        } catch (SQLException e) {
            CustomAlert.getInstance().showAlert(Alert.AlertType.ERROR, "Data Not Loading", e.getMessage());
        }
    }

    @FXML
    void addOnAction(ActionEvent event) {
        if (cmbItem.getSelectionModel().getSelectedItem()==null){
            cmbItem.setStyle("-fx-border-color: #ff0000");
            cmbDescription.setStyle("-fx-border-color: #ff0000");
        } else if (txtQty.getText().isEmpty()) {
            txtQty.setStyle("-fx-border-color: #ff0000");
        }else {
            String itemCode=cmbItem.getSelectionModel().getSelectedItem();
            int addingQty = Integer.parseInt(txtQty.getText());
            itemTM = getItemTm(itemCode);
            if (itemTM != null) {
                itemTM.setQtyOnStock(itemTM.getQtyOnStock() + addingQty);
                itemDTO = new ItemDTO(itemTM.getItemCode(), itemTM.getItemType(), itemTM.getDescription(), itemTM.getUnitPrice(), itemTM.getQtyOnStock());
                ButtonType result = CustomAlert.getInstance().showAlert(Alert.AlertType.CONFIRMATION, "Adding Quantity ","\nAdd quantity for "+itemCode+" ? ");
                if (result==ButtonType.OK) {
                    try {
                        itemService.updateItem(itemDTO);
                        CustomAlert.getInstance().showAlert(Alert.AlertType.INFORMATION, "Adding Successfully", "Adding quantity for " + itemCode + "!\nnew quantity is " + itemTM.getQtyOnStock());
                        refreshTable();
                        reset();
                    } catch (SQLException e) {
                        CustomAlert.getInstance().showAlert(Alert.AlertType.WARNING, "Adding Unsuccessfully", e.getMessage());
                    }
                }
            }
        }
    }

    @FXML
    void minusOnAction(ActionEvent event) {
        if (cmbItem.getSelectionModel().getSelectedItem()==null){
            cmbItem.setStyle("-fx-border-color: #ff0000");
            cmbDescription.setStyle("-fx-border-color: #ff0000");
        } else if (txtQty.getText().isEmpty()) {
            txtQty.setStyle("-fx-border-color: #ff0000");
        }else {
            String itemCode=cmbItem.getSelectionModel().getSelectedItem();
            int reductionQty=Integer.parseInt(txtQty.getText());
            ButtonType result = CustomAlert.getInstance().showAlert(Alert.AlertType.CONFIRMATION, "Reduction Quantity ","\nReduction quantity for "+itemCode+" ? ");
            itemTM = getItemTm(itemCode);
            if (itemTM != null) {
                itemTM.setQtyOnStock(itemTM.getQtyOnStock() - reductionQty);
                if (itemTM.getQtyOnStock() >= 0) {
                    itemDTO = new ItemDTO(itemTM.getItemCode(), itemTM.getItemType(), itemTM.getDescription(), itemTM.getUnitPrice(), itemTM.getQtyOnStock());
                    if (result == ButtonType.OK) {
                        try {
                            itemService.updateItem(itemDTO);
                            CustomAlert.getInstance().showAlert(Alert.AlertType.INFORMATION, "Reduction Successfully", "Reduced quantity for " + itemCode + "!\nnew quantity is " + itemTM.getQtyOnStock());
                            refreshTable();
                            reset();
                        } catch (SQLException e) {
                            CustomAlert.getInstance().showAlert(Alert.AlertType.WARNING, "Reduction Unsuccessfully", e.getMessage());
                        }
                    }
                }
            }
        }
    }

    private ItemTM getItemTm(String itemCode) {
        for (int i = 0; i < tblItem.getItems().size(); i++){
            if (itemObservableList.get(i).getItemCode().equals(itemCode)){
                return itemObservableList.get(i);
            }
        }
        return null;
    }

    @FXML
    void cmbDescriptionOnAction(ActionEvent event) {
        try {
            cmbItem.setStyle("-fx-border-color: #76ff03");
            cmbDescription.setStyle("-fx-border-color: #76ff03");
            btnP.setDisable(false);
            btnM.setDisable(false);
            String description = cmbDescription.getSelectionModel().getSelectedItem();
            if (description != null)
                cmbItem.getSelectionModel().select(itemService.getItemByDescription(description).getItemCode());
        } catch (SQLException e) {
            ButtonType result = CustomAlert.getInstance().showAlert(Alert.AlertType.CONFIRMATION, "Data Not Loading", e + ",Data Not Loading !\nYou Want Exit ? ");
            if (result==ButtonType.OK){
                Platform.exit();
            }
        }
    }

    @FXML
    void cmbItemOnAction(ActionEvent event) {
        try {
            cmbItem.setStyle("-fx-border-color: #76ff03");
            cmbDescription.setStyle("-fx-border-color: #76ff03");
            btnP.setDisable(false);
            btnM.setDisable(false);
            String itemCode = cmbItem.getSelectionModel().getSelectedItem();
            if (itemCode != null)
                cmbDescription.getSelectionModel().select(itemService.getItemByCode(itemCode).getDescription());
        } catch (SQLException e) {
            ButtonType result = CustomAlert.getInstance().showAlert(Alert.AlertType.CONFIRMATION, "Data Not Loading", e + ",Data Not Loading !\nYou Want Exit ? ");
            if (result==ButtonType.OK){
                Platform.exit();
            }
        }
    }

    private void reset() {
        cmbItem.getSelectionModel().clearSelection();
        cmbDescription.getSelectionModel().clearSelection();
        txtQty.clear();
        btnM.setDisable(true);
        btnP.setDisable(true);
    }

    @FXML
    void txtQtyOnClick(MouseEvent event) {
        if (cmbItem.getSelectionModel().getSelectedItem()==null){
            cmbItem.setStyle("-fx-border-color: #ff0000");
            cmbDescription.setStyle("-fx-border-color: #ff0000");
        }
        txtQty.setStyle("-fx-border-color: #76ff03");
    }

    @FXML
    void txtSearchMouseOnClick(MouseEvent event) {
        txtSearch.clear();
        FilteredList<ItemTM> filteredList=new FilteredList<>(itemObservableList, b -> true);
        txtSearch.textProperty().addListener((observable,oldValue,newValue)->{
            filteredList.setPredicate(Item ->{
                if (newValue.isEmpty() || newValue==null ){
                    return true;
                }
                String searchKeyword=newValue.toLowerCase();
                if (Item.getItemCode().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if (Item.getItemType().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if (Item.getDescription().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if (String.valueOf(Item.getUnitPrice()).indexOf(searchKeyword) > -1){
                    return true;
                }else if (String.valueOf(Item.getQtyOnStock()).indexOf(searchKeyword) > -1){
                    return true;
                }else {
                    return false;
                }
            });
        });
        SortedList<ItemTM> sortedList=new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tblItem.comparatorProperty());
        tblItem.setItems(sortedList);
    }
    @FXML
    public void resetOnAction(ActionEvent actionEvent) {
        reset();
    }
    @FXML
    public void txtQtyOnStockKeyPressedOnAction(KeyEvent keyEvent) {
        if (!keyEvent.getCharacter().matches("\\d"))
            keyEvent.consume();
    }
}
