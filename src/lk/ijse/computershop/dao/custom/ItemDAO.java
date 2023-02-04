package lk.ijse.computershop.dao.custom;

import lk.ijse.computershop.dao.CrudDAO;
import lk.ijse.computershop.entity.Item;

import java.sql.SQLException;
import java.util.Optional;

public interface ItemDAO extends CrudDAO<Item,String> {

    Optional<Item> findByDescription(String description) throws SQLException;

    boolean suppliesItemQtyByCode(String itemCode, int addingQty) throws SQLException;

    boolean sellItemQtyByCode(String itemCode, int sellingQty) throws SQLException;
}
