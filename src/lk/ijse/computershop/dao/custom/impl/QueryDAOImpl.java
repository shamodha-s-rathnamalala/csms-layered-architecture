package lk.ijse.computershop.dao.custom.impl;

import lk.ijse.computershop.dao.custom.QueryDAO;
import lk.ijse.computershop.dao.util.CrudUtil;
import lk.ijse.computershop.dto.CustomerTransactionDTO;
import lk.ijse.computershop.dto.LoginDTO;
import lk.ijse.computershop.dto.RepairDTO;
import org.apache.hadoop.hdfs.web.resources.GetOpParam;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

public class QueryDAOImpl implements QueryDAO {
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

    @Override
    public double getIncomeByDate(Date date) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT SUM(COALESCE(i.unitPrice*i.quantity,0) + COALESCE(rd.repairPrice,0)) AS total FROM transaction t LEFT JOIN itemtransactiondetails i on t.transactionId = i.transactionId  LEFT JOIN repairtransactiondetails rd on t.transactionId = rd.transactionId WHERE t.date = ? ",date);
        rst.next();
        return rst.getDouble(1);
    }

    @Override
    public Optional<CustomerTransactionDTO> getAllCustomerTransaction(String transactionId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT c.name,SUM(COALESCE(i.unitPrice*i.quantity,0) + COALESCE(rd.repairPrice,0)) AS total,t.type FROM transaction t LEFT JOIN itemtransactiondetails i on t.transactionId = i.transactionId  LEFT JOIN repairtransactiondetails rd on t.transactionId = rd.transactionId LEFT JOIN customer c on t.customerId = c.customerId WHERE t.transactionId = ? Group By t.customerId",transactionId);
        if (rst.next()){
            return Optional.of(new CustomerTransactionDTO(rst.getString(1),rst.getDouble(2),rst.getString(3)));
        }
        return Optional.empty();
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
