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
import lk.ijse.computershop.dto.SuppliesDTO;
import lk.ijse.computershop.dto.SuppliesDetailsDTO;
import lk.ijse.computershop.service.ServiceFactory;
import lk.ijse.computershop.service.ServiceTypes;
import lk.ijse.computershop.service.custom.ItemService;
import lk.ijse.computershop.service.custom.SuppliesService;
import lk.ijse.computershop.util.CustomAlert;
import lk.ijse.computershop.view.tm.ItemCartTM;
import lk.ijse.computershop.view.tm.SuppliesTM;

import java.sql.SQLException;
import java.util.ArrayList;

public class ViewSuppliesFormController {
    @FXML
    private Label lblSuppliesId;
    @FXML
    private AnchorPane ancViewPurchase;

    @FXML
    private TableColumn<SuppliesTM, String> colDateTime;

    @FXML
    private TableColumn<ItemCartTM, String> colItemCode;

    @FXML
    private TableColumn<ItemCartTM, String> colItemDiscription;

    @FXML
    private TableColumn<ItemCartTM, Integer> colItemQty;

    @FXML
    private TableColumn<ItemCartTM, Double> colItemTotal;

    @FXML
    private TableColumn<SuppliesTM, String> colSupplierId;

    @FXML
    private TableColumn<SuppliesTM, String> colSuppliesId;

    @FXML
    private TableColumn<SuppliesTM, Double> colTotal;

    @FXML
    private TableColumn<ItemCartTM, Double> colItemUnitPrice;

    @FXML
    private TableColumn<SuppliesTM, String> colAction;

    @FXML
    private TableView<ItemCartTM> tblItem;

    @FXML
    private TableView<SuppliesTM> tblSupplies;
    private final ObservableList<SuppliesTM> suppliesTMS = FXCollections.observableArrayList();
    private final ObservableList<ItemCartTM> itemCartTMS = FXCollections.observableArrayList();
    private final SuppliesService suppliesService = ServiceFactory.getInstance().getService(ServiceTypes.SUPPLIES);
    private final ItemService itemService = ServiceFactory.getInstance().getService(ServiceTypes.ITEM);
    private ArrayList<SuppliesDTO> suppliesDTOS = new ArrayList<>();
    public void initialize(){
        loadArrayList();
        setCellTableFactory();
        loadSuppliesTable();
    }

    private void loadArrayList() {
        try {
            suppliesDTOS = suppliesService.getAllFullSupplies();
        } catch (SQLException e) {
            ButtonType result = CustomAlert.getInstance().showAlert(Alert.AlertType.CONFIRMATION, "Data Not Loading", e + ",Data Not Loading !\nYou Want Exit ? ");
            if (result==ButtonType.OK){
                Platform.exit();
            }
        }
    }

    private void loadSuppliesTable() {
        for (SuppliesDTO supplies : suppliesDTOS){
            double total= supplies.getTotal();
            suppliesTMS.add(new SuppliesTM(supplies.getSuppliesId(), supplies.getDate()+" "+supplies.getDate(), supplies.getSupplierId(), total));
        }
        tblSupplies.setItems(suppliesTMS);
    }

    private void setCellTableFactory() {
        colSuppliesId.setCellValueFactory(new PropertyValueFactory<>("suppliesId"));
        colDateTime.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colItemDiscription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colItemQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colItemUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colItemTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        Callback<TableColumn<SuppliesTM, String>, TableCell<SuppliesTM, String>> cellFactory = (TableColumn<SuppliesTM, String> param) -> {
            // make cell containing buttons
            final TableCell<SuppliesTM, String> cell = new TableCell<SuppliesTM, String>() {
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
                            SuppliesTM suppliesTm = tblSupplies.getSelectionModel().getSelectedItem();
                            lblSuppliesId.setText(suppliesTm.getSuppliesId());
                            refreshItemTable(suppliesTm.getSuppliesId());
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

    private void refreshItemTable(String suppliesId) {
        itemCartTMS.clear();
        try {
            for (int i = 0; i < suppliesDTOS.size(); i++){
                if (suppliesDTOS.get(i).getSuppliesId().equals(suppliesId)){
                    for (SuppliesDetailsDTO dto : suppliesDTOS.get(i).getSuppliesDetailsArrayList()){
                        String description = itemService.getItemByCode(dto.getItemCode()).getDescription();
                        double total = dto.getQuantity() * dto.getUnitPrice();
                        itemCartTMS.add(new ItemCartTM(dto.getItemCode(), description, dto.getQuantity(), dto.getUnitPrice(), total));
                    }
                }
            }
        }catch (SQLException e){
            ButtonType result = CustomAlert.getInstance().showAlert(Alert.AlertType.CONFIRMATION, "Data Not Loading", e + ",Data Not Loading !\nYou Want Exit ? ");
            if (result==ButtonType.OK)
                Platform.exit();
        }
        tblItem.setItems(itemCartTMS);
    }
}