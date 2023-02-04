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
import lk.ijse.computershop.dto.UserDTO;
import lk.ijse.computershop.service.ServiceFactory;
import lk.ijse.computershop.service.ServiceTypes;
import lk.ijse.computershop.service.custom.UserService;
import lk.ijse.computershop.service.exception.DuplicateException;
import lk.ijse.computershop.util.CustomAlert;
import lk.ijse.computershop.util.Regex;
import lk.ijse.computershop.util.RegexType;
import lk.ijse.computershop.view.tm.UserTM;

import java.sql.SQLException;
import java.util.stream.Collectors;

public class UserFormController {
    @FXML
    public FontAwesomeIconView fxIconEye;
    @FXML
    public PasswordField psPassword;
    @FXML
    private AnchorPane anc;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private ComboBox<String> cmbRank;

    @FXML
    private TableColumn<UserTM, String> colAction;

    @FXML
    private TableColumn<UserTM, String> colEmail;

    @FXML
    private TableColumn<UserTM, String> colId;

    @FXML
    private TableColumn<UserTM, String> colName;

    @FXML
    private TableColumn<UserTM, String> colNic;

    @FXML
    private TableColumn<UserTM, String> colRank;

    @FXML
    private TableColumn<UserTM, String> colTelNumber;

    @FXML
    private TableView<UserTM> tbl;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtNic;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtTelNumber;

    private final ObservableList<UserTM> userTMS = FXCollections.observableArrayList();
    private UserTM userTM;
    private UserDTO userDTO;
    boolean isValidName=false;
    boolean isValidPassword=false;
    boolean isValidNic=false;
    boolean isValidTelephoneNumber=false;
    boolean isValidEmail=false;
    boolean isUpdate=false;
    boolean isPasswordHide = true;
    private final UserService userService = ServiceFactory.getInstance().getService(ServiceTypes.USER);
    public void initialize(){
        loadCmb();
        loadTable();
        reset();
        loadToolTips();
    }

    private void loadToolTips() {
        Tooltip nameTip=new Tooltip("ex: Pronet@20");
        txtName.setTooltip(nameTip);

        Tooltip psTip=new Tooltip("ex: Pronet@20");
        psPassword.setTooltip(psTip);
        txtPassword.setTooltip(psTip);

        Tooltip nicTip=new Tooltip("ex: Pronet@20");
        txtNic.setTooltip(nicTip);

        Tooltip telNumTip=new Tooltip("ex: Pronet@20");
        txtTelNumber.setTooltip(telNumTip);

        Tooltip emailTip=new Tooltip("ex: Pronet@20");
        txtEmail.setTooltip(emailTip);
    }

    private void loadCmb() {
        String[]rank={"Admin","Cashier"};
        cmbRank.getItems().addAll(rank);
    }

    private void reset() {
        try {
            btnUpdate.setDisable(true);
            btnAdd.setDisable(false);
            txtId.setText(userService.getNextUserId());
            txtName.clear();
            txtPassword.clear();
            psPassword.clear();
            txtNic.clear();
            txtTelNumber.clear();
            txtEmail.clear();
            cmbRank.getSelectionModel().clearSelection();
            refreshTable();
        } catch (SQLException e) {
            ButtonType result = CustomAlert.getInstance().showAlert(Alert.AlertType.CONFIRMATION, "Data Not Loading", e + ",Data Not Loading !\nYou Want Exit ? ");
            if (result==ButtonType.OK){
                Platform.exit();
            }
        }
    }

    private void refreshTable() {
        userTMS.clear();
        try {
            userTMS.addAll(userService.getAllUser().stream().map(user ->new UserTM(user.getUserId(), user.getUserName(), user.getUserPassword(), user.getNic(), user.getTelephoneNumber(), user.getEmail(), user.getRank())).collect(Collectors.toList()));
            tbl.setItems(userTMS);
        } catch (SQLException e) {
            ButtonType result = CustomAlert.getInstance().showAlert(Alert.AlertType.CONFIRMATION, "Data Not Loading", e + ",Data Not Loading !\nYou Want Exit ? ");
            if (result==ButtonType.OK){
                Platform.exit();
            }
        }
    }

    private void loadTable() {
        refreshTable();
        colId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colTelNumber.setCellValueFactory(new PropertyValueFactory<>("telephoneNumber"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colRank.setCellValueFactory(new PropertyValueFactory<>("rank"));
        Callback<TableColumn<UserTM, String>, TableCell<UserTM, String>> cellFactory = (TableColumn<UserTM, String> param) -> {
            // make cell containing buttons
            final TableCell<UserTM, String> cell = new TableCell<UserTM, String>() {
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
                        FontAwesomeIconView lock = new FontAwesomeIconView(FontAwesomeIcon.LOCK);
                        deleteIcon.setStyle(" -fx-cursor: hand ;" + "-glyph-size:28px;" + "-fx-fill:#ff1744;");
                        editIcon.setStyle(" -fx-cursor: hand ;" + "-glyph-size:28px;" + "-fx-fill:#00E676;");
                        lock.setStyle(" -fx-cursor: hand ;" + "-glyph-size:30px;" + "-fx-fill:#f1c40f;");
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            userTM = tbl.getSelectionModel().getSelectedItem();
                            ButtonType result = CustomAlert.getInstance().showAlert(Alert.AlertType.CONFIRMATION, "Deleting a user named "+ userTM.getUserName(),"Are You Sure ? ");
                            if (result == ButtonType.OK){
                                try {
                                    userService.deleteUser(userTM.getUserId());
                                    reset();
                                    refreshTable();
                                    CustomAlert.getInstance().showAlert(Alert.AlertType.INFORMATION, "Deleting Successfully", userTM.getUserName()+" was deleted");
                                } catch (SQLException e) {
                                    CustomAlert.getInstance().showAlert(Alert.AlertType.INFORMATION, "Deleting Unsuccessfully", e.getMessage());
                                }
                            }
                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            userTM = tbl.getSelectionModel().getSelectedItem();
                            txtId.setText(userTM.getUserId());
                            txtName.setText(userTM.getUserName());
                            psPassword.setText(userTM.getUserPassword());
                            txtPassword.setText(userTM.getUserPassword());
                            txtNic.setText(userTM.getNic());
                            txtTelNumber.setText(userTM.getTelephoneNumber());
                            txtEmail.setText(userTM.getEmail());
                            cmbRank.getSelectionModel().select(userTM.getRank());
                            btnUpdate.setDisable(false);
                            btnAdd.setDisable(true);
                            isUpdate=true;
                        });
                        lock.setOnMouseClicked((MouseEvent event)->{
                            userTM =tbl.getSelectionModel().getSelectedItem();
                            txtId.setText(userTM.getUserId());
                            txtName.setText(userTM.getUserName());
                            psPassword.setText(userTM.getUserPassword());
                            txtPassword.setText(userTM.getUserPassword());
                            txtNic.setText(userTM.getNic());
                            txtTelNumber.setText(userTM.getTelephoneNumber());
                            txtEmail.setText(userTM.getEmail());
                            cmbRank.getSelectionModel().select(userTM.getRank());
                            btnUpdate.setDisable(true);
                            btnAdd.setDisable(true);
                        });
                        HBox hBox = new HBox(lock,editIcon, deleteIcon);
                        hBox.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 2, 0, 2));
                        HBox.setMargin(lock, new Insets(2, 3, 0, 2));
                        setGraphic(hBox);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        colAction.setCellFactory(cellFactory);
        tbl.setItems(userTMS);
    }

    @FXML
    void addOnAction(ActionEvent event) {
        validateAll();
        if (!isValidName)
            txtName.requestFocus();
        else if (!isValidPassword)
            (isPasswordHide?psPassword:txtPassword).requestFocus();
        else if (!isValidNic)
            txtNic.requestFocus();
        else if (!isValidTelephoneNumber)
            txtTelNumber.requestFocus();
        else if (!isValidEmail)
            txtEmail.requestFocus();
        else if (cmbRank.getSelectionModel().isEmpty()) {
            cmbRank.setStyle("-fx-border-color: #ff0000");
            cmbRank.requestFocus();
        } else {
            String userId=txtId.getText();
            String name=txtName.getText();
            String password=(isPasswordHide?psPassword:txtPassword).getText();
            String nic=txtNic.getText();
            String telNumber=txtTelNumber.getText();
            String email=txtEmail.getText();
            String rank=cmbRank.getValue();
            try {
                userDTO = new UserDTO(userId, name, password, nic, telNumber, email, rank);
                userService.saveUser(userDTO);
                reset();
                txtName.requestFocus();
                CustomAlert.getInstance().showAlert(Alert.AlertType.INFORMATION, "successfully", "user data added.");
            }catch (DuplicateException e){
                CustomAlert.getInstance().showAlert(Alert.AlertType.WARNING, "exists",e.getMessage());
                if (e.getMessage().matches("name")) {
                    txtName.setStyle("-fx-border-color: #ff0000");
                    txtName.selectAll();
                    txtName.requestFocus();
                }
                if (e.getMessage().matches("nic")){
                    txtNic.setStyle("-fx-border-color: #ff0000");
                    txtNic.selectAll();
                    txtNic.requestFocus();
                }
            } catch (SQLException e) {
                CustomAlert.getInstance().showAlert(Alert.AlertType.ERROR, "Unsuccessfully",e.getMessage());
            }
        }
    }

    @FXML
    void updateOnAction(ActionEvent event) {
        validateAll();
        if (!isValidName)
            txtName.requestFocus();
        else if (!isValidPassword)
            (isPasswordHide?psPassword:txtPassword).requestFocus();
        else if (!isValidNic)
            txtNic.requestFocus();
        else if (!isValidTelephoneNumber)
            txtTelNumber.requestFocus();
        else if (!isValidEmail)
            txtEmail.requestFocus();
        else if (cmbRank.getSelectionModel().isEmpty()) {
            cmbRank.setStyle("-fx-border-color: #ff0000");
            cmbRank.requestFocus();
        }else{
            String userId=txtId.getText();
            String name=txtName.getText();
            String password=(isPasswordHide?psPassword:txtPassword).getText();
            String nic=txtNic.getText();
            String telNumber=txtTelNumber.getText();
            String email=txtEmail.getText();
            String rank=cmbRank.getValue();
            try {
                userDTO = new UserDTO(userId,name,password,nic,telNumber,email,rank);
                userService.updateUser(userDTO);
                reset();
                txtName.requestFocus();
                isUpdate=false;
                CustomAlert.getInstance().showAlert(Alert.AlertType.INFORMATION, "successfully","user data updated.");
            } catch (DuplicateException e){
                CustomAlert.getInstance().showAlert(Alert.AlertType.WARNING, "exists",e.getMessage());
                if (e.getMessage().matches("name")) {
                    txtName.setStyle("-fx-border-color: #ff0000");
                    txtName.selectAll();
                    txtName.requestFocus();
                }
                if (e.getMessage().matches("nic")){
                    txtNic.setStyle("-fx-border-color: #ff0000");
                    txtNic.selectAll();
                    txtNic.requestFocus();
                }
            } catch (SQLException e) {
                CustomAlert.getInstance().showAlert(Alert.AlertType.ERROR, "Unsuccessfully",e.getMessage());
            }
        }
    }

    @FXML
    void resetOnAction(ActionEvent event) {
        reset();
    }

    @FXML
    void nameOnAction(ActionEvent event) {
        if (isValidName)
            (isPasswordHide?psPassword:txtPassword).requestFocus();
    }

    @FXML
    void passwordOnAction(ActionEvent event) {
        if (isValidPassword)
            txtNic.requestFocus();
    }

    @FXML
    void nicOnAction(ActionEvent event) {
        if (isValidNic)
            txtTelNumber.requestFocus();
    }

    @FXML
    void telNumberOnAction(ActionEvent event) {
        if (isValidTelephoneNumber)
            txtEmail.requestFocus();
    }

    @FXML
    void emailOnAction(ActionEvent event) {
        if (isValidEmail)
            cmbRank.requestFocus();
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
    void passwordKeyPressedOnAction(KeyEvent event) {
        passwordValidation();
    }

    @FXML
    void nicKeyPressedOnAction(KeyEvent event) {
        nicValidation();
    }

    @FXML
    void telNumKeyPressedOnAction(KeyEvent event) {
        telephoneNumberValidation();
    }

    @FXML
    void emailKeyPressedOnAction(KeyEvent event) {
        emailValidation();
    }

    @FXML
    public void showHidePasswordOnAction(ActionEvent actionEvent) {
        if (isPasswordHide){
            isPasswordHide=false;
            txtPassword.setText(psPassword.getText());
            fxIconEye.setStyle("-glyph-name:EYE;");
            psPassword.setVisible(false);
            txtPassword.setVisible(true);
        }else {
            isPasswordHide=true;
            psPassword.setText(txtPassword.getText());
            fxIconEye.setStyle("-glyph-name:EYE_SLASH;");
            txtPassword.setVisible(false);
            psPassword.setVisible(true);
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
    private void passwordValidation(){
        String password=(isPasswordHide?psPassword:txtPassword).getText();
        if (password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")){
            (isPasswordHide?psPassword:txtPassword).setStyle("-fx-border-color: #76ff03");
            isValidPassword=true;
        }else {
            (isPasswordHide?psPassword:txtPassword).setStyle("-fx-border-color: #ff0000");
            isValidPassword=false;
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
        passwordValidation();
        nicValidation();
        telephoneNumberValidation();
        emailValidation();
    }
}
