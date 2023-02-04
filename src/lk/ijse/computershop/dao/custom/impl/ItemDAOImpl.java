package lk.ijse.computershop.dao.custom.impl;

import lk.ijse.computershop.dao.custom.ItemDAO;
import lk.ijse.computershop.entity.Item;
import lk.ijse.computershop.dao.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class ItemDAOImpl implements ItemDAO {

    @Override
    public boolean save(Item entity) throws SQLException {
        return CrudUtil.execute("INSERT INTO item VALUES(?,?,?,?,?)",
                entity.getItemCode(),
                entity.getItemType(),
                entity.getDescription(),
                entity.getUnitPrice(),
                entity.getQtyOnStock()
        );
    }

    @Override
    public boolean update(Item entity) throws SQLException {
        return CrudUtil.execute("UPDATE item set itemType=?, description=?, unitPrice=?, qtyOnStock=? WHERE itemCode=?",
                entity.getItemType(),
                entity.getDescription(),
                entity.getUnitPrice(),
                entity.getQtyOnStock(),
                entity.getItemCode()
        );
    }

    @Override
    public boolean deleteByPk(String pk) throws SQLException {
        return CrudUtil.execute("DELETE FROM item WHERE itemCode=?",pk);
    }

    @Override
    public ArrayList<Item> getAll() throws SQLException {
        ArrayList<Item>itemArrayList=new ArrayList<>();
        ResultSet rst= CrudUtil.execute("SELECT *FROM item");
        while (rst.next()){
            itemArrayList.add(getItem(rst));
        }
        return itemArrayList;
    }

    @Override
    public Optional<Item> findByPk(String pk) throws SQLException {
        ResultSet rst= CrudUtil.execute("SELECT * FROM item WHERE itemCode=?",pk);
        if (rst.next()){
            return Optional.of(getItem(rst));
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> getLastPk() throws SQLException {
        ResultSet rst= CrudUtil.execute("SELECT itemCode FROM item ORDER BY itemCode DESC LIMIT 1");
        if(rst.next()){
            return Optional.of(rst.getString(1));
        }
        return Optional.empty();
    }

    @Override
    public long count() throws SQLException {
        ResultSet rst  = CrudUtil.execute("SELECT COUNT(itemCode) AS count FROM item");
        rst.next();
        return rst.getLong(1);
    }

    @Override
    public boolean clearAll() throws SQLException {
        return CrudUtil.execute("delete from item");
    }

    @Override
    public Optional<Item> findByDescription(String description) throws SQLException {
        ResultSet rst= CrudUtil.execute("SELECT * FROM item WHERE description=?",description);
        if (rst.next()){
            return Optional.of(getItem(rst));
        }
        return Optional.empty();
    }

    @Override
    public boolean suppliesItemQtyByCode(String itemCode, int addingQty) throws SQLException {
        return updateQty(itemCode,-(addingQty));
    }

    @Override
    public boolean sellItemQtyByCode(String itemCode, int sellingQty) throws SQLException {
        return updateQty(itemCode, sellingQty);
    }

    private boolean updateQty(String itemCode, int qty) throws SQLException {
        return CrudUtil.execute("UPDATE Item SET qtyOnStock = qtyOnStock - ? WHERE itemCode = ?",qty,itemCode);
    }

    private Item getItem(ResultSet rst) throws SQLException {
        return new Item(
                rst.getString(1),
                rst.getString(2),
                rst.getString(3),
                rst.getDouble(4),
                rst.getInt(5)
        );
    }
}
