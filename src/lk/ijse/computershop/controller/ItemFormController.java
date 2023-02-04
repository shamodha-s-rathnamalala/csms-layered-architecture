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
import lk.ijse.computershop.service.exception.DuplicateException;
import lk.ijse.computershop.util.CustomAlert;
import lk.ijse.computershop.util.Regex;
import lk.ijse.computershop.util.RegexType;
import lk.ijse.computershop.view.tm.ItemTM;

import java.sql.SQLException;
import java.util.stream.Collectors;

public class ItemFormController {

    @FXML
    private AnchorPane anc;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private ComboBox<String> cmbType;

    @FXML
    private TableColumn<ItemTM, String> colAction;

    @FXML
    private TableColumn<ItemTM, String> colDescription;

    @FXML
    private TableColumn<ItemTM, String> colItemCode;

    @FXML
    private TableColumn<ItemTM, Integer> colQtyOnStock;

    @FXML
    private TableColumn<ItemTM, String> colType;

    @FXML
    private TableColumn<ItemTM, Double> colUnitPrice;

    @FXML
    private TableView<ItemTM> tbl;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtItemCode;

    @FXML
    private TextField txtQtyOnStock;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtUnitPrice;

    private final ObservableList<ItemTM>itemObservableList= FXCollections.observableArrayList();
    private ItemTM itemTM = null;
    private ItemDTO itemDTO=null;
    boolean isValidPrice=false;
    boolean isUpdate=false;
    private final ItemService itemService = ServiceFactory.getInstance().getService(ServiceTypes.ITEM);

    public void initialize(){
        btnUpdate.setDisable(true);
        loadCmb();
        loadTable();
        resetOnAction(new ActionEvent());
    }

    private void loadCmb() {
        String[] type={"Laptop","Desktop","Accessories","Parts"};
        cmbType.getItems().addAll(type);
    }

    private void loadTable() {
        refreshTable();
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colType.setCellValueFactory(new PropertyValueFactory<>("itemType"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnStock.setCellValueFactory(new PropertyValueFactory<>("qtyOnStock"));
        Callback<TableColumn<ItemTM, String>, TableCell<ItemTM, String>> cellFactory = (TableColumn<ItemTM, String> param) -> {
            // make cell containing buttons
            final TableCell<ItemTM, String> cell = new TableCell<ItemTM, String>() {
                @Override
                public void updateItem(String uItem, boolean empty) {
                    super.updateItem(uItem, empty);
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
                            itemTM=tbl.getSelectionModel().getSelectedItem();
                            ButtonType result = CustomAlert.getInstance().showAlert(Alert.AlertType.CONFIRMATION, "Deleting a Item code : "+itemTM.getItemCode(),"When the item is deleted,The order is also deleted\nAre You Sure ? ");
                            if (result==ButtonType.OK){
                                try {
                                    itemService.deleteItem(itemTM.getItemCode());
                                    clearAll();
                                    refreshTable();
                                    CustomAlert.getInstance().showAlert(Alert.AlertType.INFORMATION, "Deleting Successfully",itemTM.getDescription()+" was deleted");
                                } catch (SQLException e) {
                                    CustomAlert.getInstance().showAlert(Alert.AlertType.ERROR, "Deleting error",e.getMessage());
                                }
                            }
                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            itemTM=tbl.getSelectionModel().getSelectedItem();
                            txtItemCode.setText(itemTM.getItemCode());
                            cmbType.getSelectionModel().select(itemTM.getItemType());
                            txtDescription.setText(itemTM.getDescription());
                            txtUnitPrice.setText(String.format("%.2f",itemTM.getUnitPrice()));
                            txtQtyOnStock.setText(String.valueOf(itemTM.getQtyOnStock()));
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
        tbl.setItems(itemObservableList);
    }

    private void refreshTable() {
        itemObservableList.clear();
        try {
            itemObservableList.addAll(itemService.getAllItem().stream().map(i -> new ItemTM(i.getItemCode(), i.getItemType(), i.getDescription(), i.getUnitPrice(), i.getQtyOnStock())).collect(Collectors.toList()));
            tbl.setItems(itemObservableList);
        } catch (SQLException e) {
            ButtonType result = CustomAlert.getInstance().showAlert(Alert.AlertType.CONFIRMATION, "Data Not Loading", e.getMessage() + ",Data Not Loading !\nYou Want Exit ? ");
            if (result==ButtonType.OK){
                Platform.exit();
            }
        }
    }

    @FXML
    void addOnAction(ActionEvent event) {
        priceValidation();
        if (cmbType.getSelectionModel().isEmpty())
            cmbType.setStyle("-fx-border-color: #ff0000");
        else if (txtDescription.getText().isEmpty())
            txtDescription.requestFocus();
        else if ((!isValidPrice) || txtUnitPrice.getText().isEmpty())
            txtUnitPrice.requestFocus();
        else if (txtQtyOnStock.getText().isEmpty())
            txtQtyOnStock.requestFocus();
        else {
            String itemCode=txtItemCode.getText();
            String type=cmbType.getSelectionModel().getSelectedItem();
            String description=txtDescription.getText();
            double unitPrice=Double.parseDouble(txtUnitPrice.getText());
            int qtyOnStock=Integer.parseInt(txtQtyOnStock.getText());
            try {
                itemDTO = new ItemDTO(itemCode,type,description,unitPrice,qtyOnStock);
                itemService.saveItem(itemDTO);
                clearAll();
                refreshTable();
                CustomAlert.getInstance().showAlert(Alert.AlertType.INFORMATION, "successfully","item data added.");
            } catch (SQLException e) {
                CustomAlert.getInstance().showAlert(Alert.AlertType.ERROR, "unsuccessful",e.getMessage());
            } catch (DuplicateException e){
                CustomAlert.getInstance().showAlert(Alert.AlertType.INFORMATION, "unsuccessful",e.getMessage());
                txtDescription.selectAll();
                txtDescription.requestFocus();
            }
        }
    }

    @FXML
    void updateOnAction(ActionEvent event) {
        priceValidation();
        if (cmbType.getSelectionModel().isEmpty())
            cmbType.setStyle("-fx-border-color: #ff0000");
        else if (txtDescription.getText().isEmpty())
            txtDescription.requestFocus();
        else if ((!isValidPrice) || txtUnitPrice.getText().isEmpty())
            txtUnitPrice.requestFocus();
        else if (txtQtyOnStock.getText().isEmpty())
            txtQtyOnStock.requestFocus();
        else {
            String itemCode=txtItemCode.getText();
            String type=cmbType.getSelectionModel().getSelectedItem();
            String description=txtDescription.getText();
            double unitPrice=Double.parseDouble(txtUnitPrice.getText());
            int qtyOnStock=Integer.parseInt(txtQtyOnStock.getText());
            try {
                itemDTO = new ItemDTO(itemCode,type,description,unitPrice,qtyOnStock);
                itemService.updateItem(itemDTO);
                btnUpdate.setDisable(true);
                btnAdd.setDisable(false);
                clearAll();
                refreshTable();
                isUpdate=false;
                CustomAlert.getInstance().showAlert(Alert.AlertType.INFORMATION, "successfully","item data updated !");
            } catch (SQLException e) {
                CustomAlert.getInstance().showAlert(Alert.AlertType.ERROR, "unsuccessful",e.getMessage());
            } catch (DuplicateException e){
                CustomAlert.getInstance().showAlert(Alert.AlertType.INFORMATION, "unsuccessful",e.getMessage());
                txtDescription.selectAll();
                txtDescription.requestFocus();
            }
        }
    }

    @FXML
    void cmbTypeOnAction(ActionEvent event) {
        cmbType.setStyle("-fx-border-color: #76ff03");
        txtDescription.requestFocus();
    }

    @FXML
    void descriptionOnAction(ActionEvent event) {
        if (!txtDescription.getText().isEmpty())
            txtUnitPrice.requestFocus();
    }

    @FXML
    void qtyOnStockOnAction(ActionEvent event) {
        if (isUpdate){
            updateOnAction(event);
        }else {
            addOnAction(event);
        }

    }

    @FXML
    void resetOnAction(ActionEvent event) {
        btnAdd.setDisable(false);
        btnUpdate.setDisable(true);
        clearAll();
    }

    private void clearAll() {
        try {
            txtItemCode.setText(itemService.getNextItemCode());
            cmbType.getSelectionModel().clearSelection();
            txtDescription.clear();
            txtUnitPrice.clear();
            txtQtyOnStock.clear();
        } catch (SQLException e) {
            CustomAlert.getInstance().showAlert(Alert.AlertType.ERROR, "Error",e.getMessage());
        }
    }

    @FXML
    void txtSearchMouseOnClick(MouseEvent event) {
        txtSearch.clear();
        FilteredList<ItemTM> filteredList=new FilteredList<>(itemObservableList, b -> true);
        txtSearch.textProperty().addListener((observable,oldValue,newValue)->{
            filteredList.setPredicate(itemSearchModel ->{
                if (newValue.isEmpty() || newValue==null ){
                    return true;
                }
                String searchKeyword=newValue.toLowerCase();
                if (itemSearchModel.getItemCode().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if (itemSearchModel.getItemType().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if (itemSearchModel.getDescription().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if (String.valueOf(itemSearchModel.getUnitPrice()).indexOf(searchKeyword) > -1){
                    return true;
                }else if (String.valueOf(itemSearchModel.getQtyOnStock()).indexOf(searchKeyword) > -1){
                    return true;
                }else {
                    return false;
                }
            });
        });
        SortedList<ItemTM> sortedList=new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tbl.comparatorProperty());
        tbl.setItems(sortedList);
    }

    @FXML
    void unitPriceOnAction(ActionEvent event) {
        if (isValidPrice)
            txtQtyOnStock.requestFocus();
    }
    @FXML
    public void txtUnitPriceKeyPressedOnAction(KeyEvent keyEvent) {
        if (!keyEvent.getCharacter().matches("[0-9\\.]"))
            keyEvent.consume();
    }
    @FXML
    public void txtQtyOnStockKeyPressedOnAction(KeyEvent keyEvent) {
        if (!keyEvent.getCharacter().matches("[0-9]"))
            keyEvent.consume();
    }
    private void priceValidation(){
        if (Regex.getInstance().isValid(RegexType.PRICE,txtUnitPrice.getText())){
            txtUnitPrice.setStyle("-fx-border-color: #76ff03");
            isValidPrice=true;
        }else {
            txtUnitPrice.setStyle("-fx-border-color: #ff0000");
            isValidPrice=false;
        }
    }
}
