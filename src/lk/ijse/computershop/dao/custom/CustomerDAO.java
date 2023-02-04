package lk.ijse.computershop.dao.custom;

import lk.ijse.computershop.dao.CrudDAO;
import lk.ijse.computershop.entity.Customer;

import java.sql.SQLException;
import java.util.Optional;

public interface CustomerDAO extends CrudDAO<Customer,String> {
    public Optional<Customer> findByName(String name) throws SQLException;

    public boolean existsByNic(String nic) throws SQLException;
}
