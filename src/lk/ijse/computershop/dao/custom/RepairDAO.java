package lk.ijse.computershop.dao.custom;

import lk.ijse.computershop.dao.CrudDAO;
import lk.ijse.computershop.entity.Repair;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RepairDAO extends CrudDAO<Repair,String> {
    public ArrayList<Repair> findAllByCustomerId(String customerId) throws SQLException;

    public boolean updateStatus(String repairId, String status) throws SQLException;
}
