package lk.ijse.computershop.dao.custom.impl;

import lk.ijse.computershop.dao.custom.CustomerDAO;
import lk.ijse.computershop.entity.Customer;
import lk.ijse.computershop.dao.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public boolean save(Customer entity) throws SQLException {
        return CrudUtil.execute("INSERT INTO customer VALUES(?,?,?,?,?,?)",
                entity.getCustomerId(),
                entity.getName(),
                entity.getAddress(),
                entity.getNic(),
                entity.getTelephoneNumber(),
                entity.getEmail()
        );
    }

    @Override
    public boolean update(Customer entity) throws SQLException {
        return CrudUtil.execute("UPDATE customer set name=?, address=?, nic=?, telephoneNumber=?, email=? where customerId=?",
                entity.getName(),
                entity.getAddress(),
                entity.getNic(),
                entity.getTelephoneNumber(),
                entity.getEmail(),
                entity.getCustomerId()
        );
    }

    @Override
    public boolean deleteByPk(String pk) throws SQLException {
        return CrudUtil.execute("DELETE FROM customer WHERE customerId=?",pk);
    }

    @Override
    public ArrayList<Customer> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT *FROM customer");
        ArrayList<Customer>arrayList=new ArrayList<>();
        while (rst.next()){
            arrayList.add(getCustomer(rst));
        }
        return arrayList;
    }

    @Override
    public Optional<Customer> findByPk(String pk) throws SQLException {
        ResultSet rst= CrudUtil.execute("SELECT * FROM customer WHERE customerId=?",pk);
        if (rst.next()){
            return Optional.of(getCustomer(rst));
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> getLastPk() throws SQLException {
        ResultSet rst= CrudUtil.execute("SELECT customerId FROM customer ORDER BY customerId DESC LIMIT 1");
        if(rst.next()){
            return Optional.of(rst.getString(1));
        }
        return Optional.empty();
    }

    @Override
    public long count() throws SQLException {
        ResultSet rst  = CrudUtil.execute("SELECT COUNT(customerId) AS count FROM customer");
        rst.next();
        return rst.getLong(1);
    }

    @Override
    public boolean clearAll() throws SQLException {
        return CrudUtil.execute("delete from customer");
    }

    @Override
    public Optional<Customer> findByName(String name) throws SQLException {
        ResultSet rst= CrudUtil.execute("SELECT * FROM customer WHERE name=?",name);
        if (rst.next()){
            return Optional.of(getCustomer(rst));
        }
        return Optional.empty();
    }

    @Override
    public boolean existsByNic(String nic) throws SQLException {
        ResultSet rst= CrudUtil.execute("SELECT * FROM customer WHERE nic=?",nic);
        return rst.next();
    }

    private Customer getCustomer(ResultSet rst) throws SQLException {
        return new Customer(
                rst.getString(1),
                rst.getString(2),
                rst.getString(3),
                rst.getString(4),
                rst.getString(5),
                rst.getString(6)

        );
    }
}
