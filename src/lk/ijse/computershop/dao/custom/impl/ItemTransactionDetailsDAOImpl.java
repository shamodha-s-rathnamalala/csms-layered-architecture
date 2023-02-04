package lk.ijse.computershop.dao.custom.impl;

import lk.ijse.computershop.dao.custom.ItemTransactionDetailsDAO;
import lk.ijse.computershop.dao.util.CrudUtil;
import lk.ijse.computershop.entity.ItemTransactionDetails;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class ItemTransactionDetailsDAOImpl implements ItemTransactionDetailsDAO {

    @Override
    public boolean save(ItemTransactionDetails entity) throws SQLException {
        return CrudUtil.execute("INSERT INTO itemtransactiondetails VALUES(?,?,?,?)",
                entity.getTransactionId(),
                entity.getItemCode(),
                entity.getQuantity(),
                entity.getUnitPrice()
        );
    }

    @Override
    public boolean update(ItemTransactionDetails entity) throws SQLException{
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public boolean deleteByPk(String pk) throws SQLException {
        return CrudUtil.execute("DELETE FROM itemtransactiondetails WHERE transactionId=?",pk);
    }

    @Override
    public ArrayList<ItemTransactionDetails> getAll() throws SQLException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public Optional<ItemTransactionDetails> findByPk(String pk) throws SQLException{
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public Optional<String> getLastPk() throws SQLException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public long count() throws SQLException {
        ResultSet rst  = CrudUtil.execute("SELECT COUNT(transactionId) AS count FROM itemtransactiondetails");
        rst.next();
        return rst.getLong(1);
    }

    @Override
    public boolean clearAll() throws SQLException {
        return CrudUtil.execute("DELETE FROM itemtransactiondetails");
    }

    @Override
    public ArrayList<ItemTransactionDetails> findAllById(String id) throws SQLException {
        ArrayList<ItemTransactionDetails> details=new ArrayList<>();
        ResultSet rst= CrudUtil.execute("SELECT * FROM itemtransactiondetails WHERE transactionId=?",id);
        while (rst.next()){
            details.add(new ItemTransactionDetails(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4)
            ));
        }
        return details;
    }
}
