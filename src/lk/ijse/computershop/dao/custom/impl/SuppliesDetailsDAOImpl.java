package lk.ijse.computershop.dao.custom.impl;

import lk.ijse.computershop.dao.custom.SuppliesDetailsDAO;
import lk.ijse.computershop.entity.SuppliesDetails;
import lk.ijse.computershop.dao.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class SuppliesDetailsDAOImpl implements SuppliesDetailsDAO {

    @Override
    public boolean save(SuppliesDetails entity) throws SQLException {
        return CrudUtil.execute("INSERT INTO suppliesdetails VALUES(?,?,?,?)",
                entity.getSuppliesId(),
                entity.getItemCode(),
                entity.getQuantity(),
                entity.getUnitPrice()
        );
    }

    @Override
    public boolean update(SuppliesDetails entity) throws SQLException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public boolean deleteByPk(String pk) throws SQLException {
        return CrudUtil.execute("DELETE FROM suppliesdetails WHERE suppliesId=?",pk);
    }

    @Override
    public ArrayList<SuppliesDetails> getAll() throws SQLException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public Optional<SuppliesDetails> findByPk(String pk) throws SQLException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public Optional<String> getLastPk() throws SQLException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public long count() throws SQLException {
        ResultSet rst  = CrudUtil.execute("SELECT COUNT(suppliesId) AS count FROM suppliesdetails");
        rst.next();
        return rst.getLong(1);
    }

    @Override
    public boolean clearAll() throws SQLException {
        return CrudUtil.execute("DELETE FROM suppliesdetails");
    }

    @Override
    public double findTotalByPk(String pk) throws SQLException {
        ResultSet rst= CrudUtil.execute("SELECT SUM(quantity*unitPrice) FROM suppliesdetails WHERE suppliesId=?",pk);
        rst.next();
        return rst.getDouble(1);
    }

    @Override
    public ArrayList<SuppliesDetails> findAllByPk(String pk) throws SQLException {
        ArrayList<SuppliesDetails>details=new ArrayList<>();
        ResultSet rst= CrudUtil.execute("SELECT * FROM suppliesdetails WHERE suppliesId=?",pk);
        while (rst.next()){
            details.add(new SuppliesDetails(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4)
            ));
        }
        return details;
    }
}