package lk.ijse.computershop.dao.custom;

import lk.ijse.computershop.dao.SuperDAO;
import lk.ijse.computershop.dto.LoginDTO;
import lk.ijse.computershop.dto.RepairDTO;
import lk.ijse.computershop.dto.TransactionDTO;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public interface QueryDAO extends SuperDAO {
    public boolean returnedRepairByPk(String repairId) throws SQLException;

    public ArrayList<RepairDTO> getAllReturnedRepair() throws SQLException;

    public ArrayList<RepairDTO> getAllNonReturnedRepair() throws SQLException;

    public ArrayList<RepairDTO> findNonReturnRepairByCustomerId(String customerId) throws SQLException;

    public double repairIncomeByDate(Date date) throws SQLException;

    public double sellingIncomeByDate(Date date) throws SQLException;

    public double sellingIncomeByCustomer(String customerId) throws SQLException;

    public double repairIncomeByCustomer(String customerId) throws SQLException;

    public ArrayList<LoginDTO> findAllLoginRecord() throws SQLException;
}
