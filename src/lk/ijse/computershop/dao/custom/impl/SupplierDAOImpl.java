package lk.ijse.computershop.dao.custom.impl;

import lk.ijse.computershop.dao.custom.SupplierDAO;
import lk.ijse.computershop.entity.Supplier;
import lk.ijse.computershop.dao.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class SupplierDAOImpl implements SupplierDAO {

    @Override
    public boolean save(Supplier entity) throws SQLException {
        return CrudUtil.execute("INSERT INTO supplier VALUES(?,?,?,?,?)",
                entity.getSupplierId(),
                entity.getName(),
                entity.getAddress(),
                entity.getTelephoneNumber(),
                entity.getEmail()
        );
    }

    @Override
    public boolean update(Supplier entity) throws SQLException {
        return CrudUtil.execute("UPDATE supplier set name=?, address=?, telephoneNumber=?, email=? where supplierId=?",
                entity.getName(),
                entity.getAddress(),
                entity.getTelephoneNumber(),
                entity.getEmail(),
                entity.getSupplierId()
        );
    }

    @Override
    public boolean deleteByPk(String pk) throws SQLException {
        return CrudUtil.execute("DELETE FROM supplier WHERE supplierId=?",pk);
    }

    @Override
    public ArrayList<Supplier> getAll() throws SQLException {
        ArrayList<Supplier>allSupplier=new ArrayList<>();
        ResultSet rst= CrudUtil.execute("SELECT * FROM supplier");
        while (rst.next()){
            allSupplier.add(new Supplier(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            ));
        }
        return allSupplier;
    }

    @Override
    public Optional<Supplier> findByPk(String pk) throws SQLException {
        ResultSet rst= CrudUtil.execute("SELECT * FROM supplier WHERE supplierId=?",pk);
        if (rst.next()){
            return Optional.of(new Supplier(
                            rst.getString(1),
                            rst.getString(2),
                            rst.getString(3),
                            rst.getString(4),
                            rst.getString(5)
            ));
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> getLastPk() throws SQLException {
        ResultSet rst= CrudUtil.execute("SELECT supplierId FROM supplier ORDER BY supplierId DESC LIMIT 1");
        if(rst.next()){
            return Optional.of(rst.getString(1));
        }
        return Optional.empty();
    }

    @Override
    public long count() throws SQLException {
        ResultSet rst  = CrudUtil.execute("SELECT COUNT(supplierId) AS count FROM supplier");
        rst.next();
        return rst.getLong(1);
    }

    @Override
    public boolean clearAll() throws SQLException {
        return CrudUtil.execute("DELETE FROM supplier");
    }

    @Override
    public Optional<Supplier> findByName(String name) throws SQLException {
        ResultSet rst= CrudUtil.execute("SELECT * FROM supplier WHERE name=?",name);
        if (rst.next()){
            return Optional.of(new Supplier(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            ));
        }
        return Optional.empty();
    }
}