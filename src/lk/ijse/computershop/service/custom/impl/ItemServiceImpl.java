package lk.ijse.computershop.service.custom.impl;

import lk.ijse.computershop.dao.DaoFactory;
import lk.ijse.computershop.dao.DaoTypes;
import lk.ijse.computershop.dao.custom.ItemDAO;
import lk.ijse.computershop.dto.CustomerDTO;
import lk.ijse.computershop.dto.ItemDTO;
import lk.ijse.computershop.entity.Item;
import lk.ijse.computershop.service.custom.ItemService;
import lk.ijse.computershop.service.exception.DuplicateException;
import lk.ijse.computershop.service.util.Convertor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class ItemServiceImpl implements ItemService {
    private final ItemDAO itemDAO;
    private final Convertor convertor;
    public ItemServiceImpl() {
        itemDAO = DaoFactory.getInstance().getDAO(DaoTypes.ITEM);
        convertor = new Convertor();
    }

    @Override
    public void deleteItem(String itemCode) throws SQLException {
        if (!itemDAO.deleteByPk(itemCode))
            throw new SQLException("Fail to delete the item !");
    }

    @Override
    public ArrayList<ItemDTO> getAllItem() throws SQLException {
        return (ArrayList<ItemDTO>) itemDAO.getAll().stream().map(item -> convertor.fromItem(item)).collect(Collectors.toList());
    }

    @Override
    public void saveItem(ItemDTO item) throws SQLException, DuplicateException {
        if (itemDAO.findByDescription(item.getDescription()).isPresent())
            throw new DuplicateException("Duplicate description\nItem already saved !");
        if (!itemDAO.save(convertor.toItem(item)))
            throw new SQLException("Fail to save the item !");
    }

    @Override
    public String getNextItemCode() throws SQLException {
        Optional<String> lastPk = itemDAO.getLastPk();
        if(lastPk.isPresent()){
            String pk=lastPk.get().substring(1);
            pk=String.format("I%03d", Integer.parseInt(pk)+1);
            return pk;
        }
        return "I001";
    }

    @Override
    public void updateItem(ItemDTO item) throws SQLException, DuplicateException {
        if (!itemDAO.findByPk(item.getItemCode()).get().getDescription().equals(item.getDescription())){
            if (itemDAO.findByDescription(item.getDescription()).isPresent())
                throw new DuplicateException("Another item description is duplicated !");
        }
        if (!itemDAO.update(convertor.toItem(item)))
            throw new SQLException("Fail to update the item !");
    }

    @Override
    public ItemDTO getItemByCode(String itemCode) throws SQLException {
        Optional<Item> optional = itemDAO.findByPk(itemCode);
        if (optional.isPresent())
            return convertor.fromItem(optional.get());
        throw new SQLException("Item not found !");
    }

    @Override
    public ItemDTO getItemByDescription(String description) throws SQLException {
        Optional<Item> optional = itemDAO.findByDescription(description);
        if (optional.isPresent())
            return convertor.fromItem(optional.get());
        throw new SQLException("Item not found !");
    }
}
