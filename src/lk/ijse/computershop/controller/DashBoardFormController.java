package lk.ijse.computershop.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lk.ijse.computershop.dto.CustomerTransactionDTO;
import lk.ijse.computershop.service.ServiceFactory;
import lk.ijse.computershop.service.ServiceTypes;
import lk.ijse.computershop.service.custom.CustomerService;
import lk.ijse.computershop.service.custom.RepairService;
import lk.ijse.computershop.service.custom.TransactionService;
import lk.ijse.computershop.util.CustomAlert;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class DashBoardFormController {
    @FXML
    private AnchorPane anc;
    @FXML
    public NumberAxis yAxis;
    @FXML
    public CategoryAxis xAxis;
    @FXML
    private BarChart barChart;
    @FXML
    private Label lblTotalCustomer;

    @FXML
    private Label lblTotalIncomeToDay;

    @FXML
    private Label lblTotalRepairmentDelivered;

    @FXML
    private Label lblTotalTransaction;

    @FXML
    private Label lblTotalTransactionToDay;

    @FXML
    private ScrollPane scPane;
    private CustomerService customerService;
    private TransactionService transactionService;
    private RepairService repairService;

    public void initialize(){
        customerService = ServiceFactory.getInstance().getService(ServiceTypes.CUSTOMER);
        transactionService = ServiceFactory.getInstance().getService(ServiceTypes.TRANSACTION);
        repairService = ServiceFactory.getInstance().getService(ServiceTypes.REPAIR);
        loadLabel();
        loadBarChart();
        loadScrollPane();
    }

    private void loadScrollPane() {
        VBox vBox=new VBox();
        Label nameLabel=new Label("");
        nameLabel.setStyle("-fx-text-fill: #000000;" +
                "-fx-font-size:16px;"+
                "-fx-font-family: Arial Rounded MT Bold;"
        );
        nameLabel.setPrefWidth(200);
        Label priceLabel=new Label("");
        priceLabel.setStyle("-fx-text-fill: #000000;"+
                "-fx-font-size: 16px;"+
                "-fx-font-family: Arial Rounded MT Bold;"
        );
        priceLabel.setPrefWidth(80);
        priceLabel.setAlignment(Pos.CENTER);
        Label typeLabel=new Label("");
        typeLabel.setStyle("-fx-text-fill: #000000;"+
                "-fx-font-size: 16px;"+
                "-fx-font-family: Arial Rounded MT Bold;"
        );
        typeLabel.setPrefWidth(95);
        typeLabel.setAlignment(Pos.CENTER_RIGHT);
        try {
            ArrayList<CustomerTransactionDTO> customerTransactions = customerService.getAllTransactionByCustomer();
            for (CustomerTransactionDTO data : customerTransactions){
                nameLabel.setText("   "+data.getName());
                priceLabel.setText(String.valueOf(data.getTotal()));
                typeLabel.setText(data.getType());
                HBox hBox=new HBox(nameLabel,priceLabel,typeLabel);
                hBox.setStyle("-fx-border-width: 0px 0px 1px 0px;"+
                        "-fx-border-color: #000000");
                vBox.getChildren().add(hBox);
            }
        } catch (SQLException e) {
            ButtonType result = CustomAlert.getInstance().showAlert(Alert.AlertType.CONFIRMATION, "Data Not Loading", e + ",Data Not Loading !\nYou Want Exit ? ");
            if (result==ButtonType.OK){
                Platform.exit();
            }
        }
        scPane.setContent(vBox);
    }
    private void loadBarChart() {
        try {
            XYChart.Series<String, Long> series = new XYChart.Series();
            for (int i=1;i<16;i++) {
                LocalDate date = getDate(-(15-i));
                long count = transactionService.findTransactionCountByDate(date);
                series.getData().add(new XYChart.Data<>(date.toString(), count));
            }
            barChart.getData().addAll(series);
        } catch (SQLException e) {
            ButtonType result = CustomAlert.getInstance().showAlert(Alert.AlertType.CONFIRMATION, "Data Not Loading", e + ",Data Not Loading !\nYou Want Exit ? ");
            if (result==ButtonType.OK){
                Platform.exit();
            }
        }

    }

    private LocalDate getDate(int n) {
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DATE,n);
        String result=dateFormat.format(calendar.getTime());
        return LocalDate.parse(result);
    }

    private void loadLabel() {
        try {
            lblTotalCustomer.setText(String.valueOf(customerService.getCustomerCount()));
            lblTotalRepairmentDelivered.setText(String.valueOf(repairService.getNonReturnRepair().size()));
            lblTotalTransaction.setText(String.valueOf(transactionService.transactionCount()));
            lblTotalIncomeToDay.setText(NumberFormat.getCurrencyInstance(new Locale("en","in")).format(transactionService.getIncomeByDate(LocalDate.now())));
            lblTotalTransactionToDay.setText(String.valueOf(transactionService.findTransactionCountByDate(LocalDate.now())));
        } catch (SQLException e) {
            ButtonType result = CustomAlert.getInstance().showAlert(Alert.AlertType.CONFIRMATION, "Data Not Loading", e + ",Data Not Loading !\nYou Want Exit ? ");
            if (result==ButtonType.OK){
                Platform.exit();
            }
        }
    }
}
