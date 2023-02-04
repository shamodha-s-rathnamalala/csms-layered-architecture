package lk.ijse.computershop.dao.custom.impl;

import lk.ijse.computershop.dao.custom.RepairTransactionDetailsDAO;
import lk.ijse.computershop.entity.RepairTransactionDetails;
import lk.ijse.computershop.dao.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class RepairTransactionDetailsDAOImpl implements RepairTransactionDetailsDAO {

    @Override
    public boolean save(RepairTransactionDetails entity) throws SQLException {
        return CrudUtil.execute("INSERT INTO repairtransactiondetails VALUES(?,?,?)",
                entity.getTransactionId(),
                entity.getRepairId(),
                entity.getRepairPrice()
        );
    }

    @Override
    public boolean update(RepairTransactionDetails entity) throws SQLException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public boolean deleteByPk(String pk) throws SQLException {
        return CrudUtil.execute("DELETE FROM repairtransactiondetails WHERE repairId=?",pk);
    }

    @Override
    public ArrayList<RepairTransactionDetails> getAll() throws SQLException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public Optional<RepairTransactionDetails> findByPk(String pk) throws SQLException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public Optional<String> getLastPk() throws SQLException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public long count() throws SQLException {
        ResultSet rst  = CrudUtil.execute("SELECT COUNT(repairId) AS count FROM repairtransactiondetails");
        rst.next();
        return rst.getLong(1);
    }

    @Override
    public boolean clearAll() throws SQLException {
        return CrudUtil.execute("DELETE FROM repairtransactiondetails");
    }

    @Override
    public ArrayList<RepairTransactionDetails> findAllByTransactionId(String transactionId) throws SQLException {
        ArrayList<RepairTransactionDetails>details=new ArrayList<>();
        ResultSet rst= CrudUtil.execute("SELECT * FROM repairtransactiondetails WHERE transactionId=?",transactionId);
        while (rst.next()){
            details.add(new RepairTransactionDetails(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3)
            ));
        }
        return details;
    }

    @Override
    public Optional<RepairTransactionDetails> findByRepairId(String repairId) throws SQLException{
        ResultSet rst= CrudUtil.execute("SELECT * FROM repairtransactiondetails WHERE repairId=?",repairId);
        while (rst.next()){
            return Optional.of(new RepairTransactionDetails(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3)
            ));
        }
        return Optional.empty();
    }
}