package lk.ijse.computershop.service.custom.impl;

import lk.ijse.computershop.dao.DaoFactory;
import lk.ijse.computershop.dao.DaoTypes;
import lk.ijse.computershop.dao.custom.ItemDAO;
import lk.ijse.computershop.dao.custom.SuppliesDAO;
import lk.ijse.computershop.dao.custom.SuppliesDetailsDAO;
import lk.ijse.computershop.db.DBConnection;
import lk.ijse.computershop.dto.SuppliesDTO;
import lk.ijse.computershop.dto.SuppliesDetailsDTO;
import lk.ijse.computershop.entity.Supplies;
import lk.ijse.computershop.entity.SuppliesDetails;
import lk.ijse.computershop.service.custom.SuppliesService;
import lk.ijse.computershop.service.util.Convertor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class SuppliesServiceImpl implements SuppliesService {
    private final SuppliesDAO suppliesDAO;
    private final SuppliesDetailsDAO suppliesDetailsDAO;
    private final ItemDAO itemDAO;
    private final Connection connection;
    private final Convertor convertor;

    public SuppliesServiceImpl() {
        suppliesDAO = DaoFactory.getInstance().getDAO(DaoTypes.SUPPLIES);
        suppliesDetailsDAO = DaoFactory.getInstance().getDAO(DaoTypes.SUPPLIESDETAILS);
        itemDAO = DaoFactory.getInstance().getDAO(DaoTypes.ITEM);
        connection = DBConnection.getInstance().getConnection();
        convertor = new Convertor();
    }

    @Override
    public String getNextSuppliesId() throws SQLException {
        Optional<String> lastPk = suppliesDAO.getLastPk();
        if(lastPk.isPresent()){
            String pk=lastPk.get().substring(1);
            pk=String.format("G%03d", Integer.parseInt(pk)+1);
            return pk;
        }
        return "G001";
    }

    @Override
    public void saveSupplies(SuppliesDTO suppliesDTO) throws SQLException {
        try {
            connection.setAutoCommit(false);
            if (suppliesDAO.save(convertor.toSupplies(suppliesDTO))){
                if (saveSuppliesDetails(suppliesDTO.getSuppliesDetailsArrayList())) {
                    connection.commit();
                    return;
                }
            }
            connection.rollback();
            throw new SQLException("The supplies could be placed !");
        }finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public ArrayList<SuppliesDTO> getAllFullSupplies() throws SQLException {
        ArrayList<SuppliesDTO> suppliesDTOS = new ArrayList<>();
        for (Supplies supplies : suppliesDAO.getAll()){
            ArrayList<SuppliesDetailsDTO> suppliesDetails = (ArrayList<SuppliesDetailsDTO>) suppliesDetailsDAO.findAllByPk(supplies.getSuppliesId()).stream().map(sd -> convertor.fromSuppliesDetail(sd)).collect(Collectors.toList());
            suppliesDTOS.add(new SuppliesDTO(
               supplies.getSuppliesId(),
               supplies.getDate(),
               supplies.getTime(),
               supplies.getSupplierId(),
               getItemSuppliesTotalPrice(suppliesDetails),
               suppliesDetails
            ));
        }
        return suppliesDTOS;
    }

    private double getItemSuppliesTotalPrice(ArrayList<SuppliesDetailsDTO> suppliesDetails) {
        double total = 0;
        for (SuppliesDetailsDTO details : suppliesDetails){
            total += (details.getUnitPrice() * details.getQuantity());
        }
        return total;
    }

    private boolean saveSuppliesDetails(ArrayList<SuppliesDetailsDTO> suppliesDetailsArrayList) throws SQLException {
        for (SuppliesDetailsDTO dto : suppliesDetailsArrayList){
            if (suppliesDetailsDAO.save(convertor.toSuppliesDetails(dto)))
                if (itemDAO.suppliesItemQtyByCode(dto.getItemCode(), dto.getQuantity()))
                    continue;
            return false;
        }
        return true;
    }
}
