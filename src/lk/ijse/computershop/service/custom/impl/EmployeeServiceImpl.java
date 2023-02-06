package lk.ijse.computershop.service.custom.impl;

import lk.ijse.computershop.dao.DaoFactory;
import lk.ijse.computershop.dao.DaoTypes;
import lk.ijse.computershop.dao.custom.EmployeeDAO;
import lk.ijse.computershop.dto.CustomerDTO;
import lk.ijse.computershop.dto.EmployeeDTO;
import lk.ijse.computershop.service.custom.EmployeeService;
import lk.ijse.computershop.service.exception.DuplicateException;
import lk.ijse.computershop.service.util.Convertor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeDAO employeeDAO;
    private final Convertor convertor;
    public EmployeeServiceImpl() {
        employeeDAO = DaoFactory.getInstance().getDAO(DaoTypes.EMPLOYEE);
        convertor = new Convertor();
    }

    @Override
    public ArrayList<EmployeeDTO> getAllEmployee() throws SQLException {
        return (ArrayList<EmployeeDTO>) employeeDAO.getAll().stream().map(employee -> convertor.fromEmployee(employee)).collect(Collectors.toList());
    }

    @Override
    public void deleteEmployee(String employeeId) throws SQLException {
        if(!employeeDAO.deleteByPk(employeeId))
            throw new SQLException("Fail to delete the employee !");
    }

    @Override
    public String getNextEmployeeId() throws SQLException {
        Optional<String> lastPk = employeeDAO.getLastPk();
        if(lastPk.isPresent()){
            String pk=lastPk.get().substring(1);
            pk=String.format("E%03d", Integer.parseInt(pk)+1);
            return pk;
        }
        return "E001";
    }

    @Override
    public void saveEmployee(EmployeeDTO employee) throws SQLException, DuplicateException {
        if (employeeDAO.existsByNic(employee.getNic()))
            throw new DuplicateException("Duplicate nic\nemployee already saved !");
        if (!employeeDAO.save(convertor.toEmployee(employee)))
            throw new SQLException("Fail to save the employee !");
    }

    @Override
    public void updateEmployee(EmployeeDTO employee) throws SQLException, DuplicateException {
        if (!employeeDAO.findByPk(employee.getEmployeeId()).get().getNic().equals(employee.getNic())){
            if (employeeDAO.existsByNic(employee.getNic()))
                throw new DuplicateException("Another employee nic is duplicated !");
        }
        if (!employeeDAO.update(convertor.toEmployee(employee)))
            throw new SQLException("Fail to update the employee !");
    }
}
