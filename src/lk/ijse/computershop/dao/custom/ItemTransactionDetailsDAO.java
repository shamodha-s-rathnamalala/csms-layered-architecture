package lk.ijse.computershop.dao.custom;

import lk.ijse.computershop.dao.CrudDAO;
import lk.ijse.computershop.entity.ItemTransactionDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemTransactionDetailsDAO extends CrudDAO<ItemTransactionDetails,String> {
    ArrayList<ItemTransactionDetails> findAllById(String id) throws SQLException;
}
