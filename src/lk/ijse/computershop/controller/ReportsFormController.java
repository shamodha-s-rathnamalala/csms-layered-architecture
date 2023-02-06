package lk.ijse.computershop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import lk.ijse.computershop.db.DBConnection;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;

public class ReportsFormController {
    @FXML
    void incomeOnAction(ActionEvent event) {
        InputStream inputStream = this.getClass().getResourceAsStream("/lk/ijse/computershop/report/income.jrxml");
        runJasperReport(inputStream);
    }

    private void runJasperReport(InputStream inputStream) {
        try {
            JasperReport compileReport = JasperCompileManager.compileReport(inputStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport,null, DBConnection.getInstance().getConnection());
            //JasperPrintManager.printReport(jasperPrint,true);
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void customerReport(ActionEvent actionEvent) {
        InputStream inputStream = this.getClass().getResourceAsStream("/lk/ijse/computershop/report/Customer.customer.jrxmljrxml");
        runJasperReport(inputStream);
    }
}
