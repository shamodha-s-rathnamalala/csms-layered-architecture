package lk.ijse.computershop.dao.custom.impl;

import lk.ijse.computershop.dao.custom.QueryDAO;
import lk.ijse.computershop.dao.util.CrudUtil;
import lk.ijse.computershop.dto.LoginDTO;
import lk.ijse.computershop.dto.RepairDTO;
import lk.ijse.computershop.dto.TransactionDTO;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class QueryDAOImpl implements QueryDAO {

    @Override
    public boolean returnedRepairByPk(String repairId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT r.repairId is not null as returned FROM repair r INNER JOIN repairtransactiondetails rd ON r.repairId = rd.repairId WHERE r.repairId=?",repairId);
        return rst.next();
    }

    @Override
    public ArrayList<RepairDTO> getAllReturnedRepair() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT r.repairId,r.receiveDate,r.returnDate,r.status,r.description,r.customerId FROM repairtransactiondetails rd LEFT JOIN repair r ON rd.repairId = r.repairId");
        return getRepairList(rst);
    }

    @Override
    public ArrayList<RepairDTO> getAllNonReturnedRepair() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT r.repairId,r.receiveDate,r.returnDate,r.status,r.description,r.customerId FROM repair r LEFT JOIN repairtransactiondetails rd ON r.repairId = rd.repairId WHERE rd.repairId is null");
        return getRepairList(rst);
    }

    @Override
    public ArrayList<RepairDTO> findNonReturnRepairByCustomerId(String customerId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT r.repairId,r.receiveDate,r.returnDate,r.status,r.description,r.customerId FROM repair r LEFT JOIN repairtransactiondetails rd ON r.repairId = rd.repairId WHERE rd.repairId is null AND r.customerId=?",customerId);
        return getRepairList(rst);
    }

    @Override
    public double repairIncomeByDate(Date date) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT SUM(r.repairPrice) FROM transaction t LEFT JOIN repairtransactiondetails r on t.transactionId = r.transactionId WHERE t.date = ?",date);
        rst.next();
        return rst.getDouble(1);
    }

    @Override
    public double sellingIncomeByDate(Date date) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT SUM((i.quantity*i.unitPrice)) FROM transaction t LEFT JOIN itemtransactiondetails i On t.transactionId=i.transactionId WHERE t.date = ?",date);
        rst.next();
        return rst.getDouble(1);
    }

    @Override
    public double sellingIncomeByCustomer(String customerId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT SUM((i.quantity*i.unitPrice)) FROM transaction t LEFT JOIN itemtransactiondetails i On t.transactionId=i.transactionId WHERE t.customerId = ?",customerId);
        rst.next();
        return rst.getDouble(1);
    }

    @Override
    public double repairIncomeByCustomer(String customerId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT SUM(r.repairPrice) FROM transaction t LEFT JOIN repairtransactiondetails r on t.transactionId = r.transactionId WHERE t.customerId = ?",customerId);
        rst.next();
        return rst.getDouble(1);
    }

    @Override
    public ArrayList<LoginDTO> findAllLoginRecord() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT l.loginId,u.userId,u.userName,l.date,l.time,u.telephoneNumber,u.`rank` FROM loginrecord l LEFT JOIN user u ON l.userId = u.userId;");
        ArrayList<LoginDTO> arrayList = new ArrayList<>();
        while (rst.next()){
            arrayList.add(new LoginDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    LocalDateTime.parse(rst.getDate(4)+"T"+rst.getTime(5)),
                    rst.getString(6),
                    rst.getString(7)
            ));
        }
        return arrayList;
    }

    private ArrayList<RepairDTO> getRepairList(ResultSet rst) throws SQLException {
        ArrayList<RepairDTO>arrayList=new ArrayList<>();
        while (rst.next()){
            arrayList.add(new RepairDTO(
                    rst.getString(1),
                    rst.getDate(2),
                    rst.getDate(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            ));
        }
        return arrayList;
    }
}
