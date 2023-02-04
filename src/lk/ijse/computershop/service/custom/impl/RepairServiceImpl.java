package lk.ijse.computershop.service.custom.impl;

import lk.ijse.computershop.dao.DaoFactory;
import lk.ijse.computershop.dao.DaoTypes;
import lk.ijse.computershop.dao.custom.QueryDAO;
import lk.ijse.computershop.dao.custom.RepairDAO;
import lk.ijse.computershop.dto.RepairDTO;
import lk.ijse.computershop.entity.Repair;
import lk.ijse.computershop.service.custom.RepairService;
import lk.ijse.computershop.service.util.Convertor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class RepairServiceImpl implements RepairService {
    private final RepairDAO repairDAO;
    private final QueryDAO queryDAO;
    private final Convertor convertor;

    public RepairServiceImpl() {
        repairDAO = DaoFactory.getInstance().getDAO(DaoTypes.REPAIR);
        queryDAO = DaoFactory.getInstance().getDAO(DaoTypes.QUERY);
        convertor = new Convertor();
    }

    @Override
    public ArrayList<RepairDTO> getNonReturnRepair() throws SQLException {
        return queryDAO.getAllNonReturnedRepair();
    }

    @Override
    public RepairDTO getRepairById(String repairId) throws SQLException {
        Optional<Repair> optional = repairDAO.findByPk(repairId);
        if (optional.isPresent())
            return convertor.fromRepair(optional.get());
        throw new SQLException("repair not found !");
    }

    @Override
    public ArrayList<RepairDTO> getAllReturnedRepair() throws SQLException {
        return queryDAO.getAllReturnedRepair();
    }

    @Override
    public void deleteRepair(String repairId) throws SQLException {
        if(!repairDAO.deleteByPk(repairId))
            throw new SQLException("repair deleting fail !");
    }

    @Override
    public void saveRepair(RepairDTO repairDTO) throws SQLException {
        if (!repairDAO.save(convertor.toRepair(repairDTO)))
            throw new SQLException("Repair saved fail !");
    }

    @Override
    public void updateRepair(RepairDTO repairDTO) throws SQLException {
        if (!repairDAO.update(convertor.toRepair(repairDTO)))
            throw new SQLException("Repair saved fail !");
    }

    @Override
    public String getNextRepairId() throws SQLException {
        Optional<String> lastPk = repairDAO.getLastPk();
        if(lastPk.isPresent()){
            String pk=lastPk.get().substring(1);
            pk=String.format("R%03d", Integer.parseInt(pk)+1);
            return pk;
        }
        return "R001";
    }
}
