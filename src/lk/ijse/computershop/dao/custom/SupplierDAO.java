package lk.ijse.computershop.dao.custom;

import lk.ijse.computershop.dao.CrudDAO;
import lk.ijse.computershop.entity.Supplier;

import java.sql.SQLException;
import java.util.Optional;

public interface SupplierDAO extends CrudDAO<Supplier,String> {
    Optional<Supplier> findByName(String name) throws SQLException;
}
