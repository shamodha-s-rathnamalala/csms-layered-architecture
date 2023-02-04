package lk.ijse.computershop.dao.custom;

import lk.ijse.computershop.dao.CrudDAO;
import lk.ijse.computershop.entity.Transaction;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public interface TransactionDAO extends CrudDAO<Transaction,String> {
    ArrayList<Transaction> findAllByDate(Date date) throws SQLException;

    ArrayList<Transaction> findAllByCustomerId(String customerId) throws SQLException;
}
