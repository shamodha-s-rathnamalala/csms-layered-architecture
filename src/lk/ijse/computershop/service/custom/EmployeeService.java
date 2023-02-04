package lk.ijse.computershop.service.custom;

import lk.ijse.computershop.dto.EmployeeDTO;
import lk.ijse.computershop.service.SuperService;
import lk.ijse.computershop.service.exception.DuplicateException;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeService extends SuperService {
    public ArrayList<EmployeeDTO> getAllEmployee() throws SQLException;

    public void deleteEmployee(String employeeId) throws SQLException;

    public String getNextEmployeeId() throws SQLException;

    public void saveEmployee(EmployeeDTO employee) throws SQLException, DuplicateException;

    public void updateEmployee(EmployeeDTO employee) throws SQLException, DuplicateException;
}
