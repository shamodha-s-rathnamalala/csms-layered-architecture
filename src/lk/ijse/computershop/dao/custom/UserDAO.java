package lk.ijse.computershop.dao.custom;

import lk.ijse.computershop.dao.CrudDAO;
import lk.ijse.computershop.entity.User;

import java.sql.SQLException;
import java.util.Optional;

public interface UserDAO extends CrudDAO<User,String> {
    Optional<User> findByName(String userName) throws SQLException;

    boolean existsByNic(String nic) throws SQLException;
}
