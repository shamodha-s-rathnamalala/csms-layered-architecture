package lk.ijse.computershop.dao.custom.impl;

import lk.ijse.computershop.dao.custom.EmployeeDAO;
import lk.ijse.computershop.entity.Employee;
import lk.ijse.computershop.dao.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class EmployeeDAOImpl implements EmployeeDAO {

    @Override
    public boolean save(Employee entity) throws SQLException {
        return CrudUtil.execute("INSERT INTO employee VALUES(?,?,?,?,?,?)",
                entity.getEmployeeId(),
                entity.getName(),
                entity.getAddress(),
                entity.getNic(),
                entity.getTelephoneNumber(),
                entity.getRank()
        );
    }

    @Override
    public boolean update(Employee entity) throws SQLException {
        return CrudUtil.execute("UPDATE employee set name=?, address=?, nic=?, telephoneNumber=?, `rank`=? where employeeId=?",
                entity.getName(),
                entity.getAddress(),
                entity.getNic(),
                entity.getTelephoneNumber(),
                entity.getRank(),
                entity.getEmployeeId()
        );
    }

    @Override
    public boolean deleteByPk(String pk) throws SQLException {
        return CrudUtil.execute("DELETE FROM employee WHERE employeeId=?",pk);
    }

    @Override
    public ArrayList<Employee> getAll() throws SQLException {
        ArrayList<Employee>allEmployeeList=new ArrayList<>();
        ResultSet rst= CrudUtil.execute("SELECT *FROM employee");
        while (rst.next()){
            allEmployeeList.add(getEmployee(rst));
        }
        return allEmployeeList;
    }

    private Employee getEmployee(ResultSet rst) throws SQLException {
        return new Employee(
                rst.getString(1),
                rst.getString(2),
                rst.getString(3),
                rst.getString(4),
                rst.getString(5),
                rst.getString(6)
        );
    }

    @Override
    public Optional<Employee> findByPk(String pk) throws SQLException {
        ResultSet rst= CrudUtil.execute("SELECT * FROM employee WHERE employeeId=?",pk);
        if (rst.next()){
            Optional.of(getEmployee(rst));
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> getLastPk() throws SQLException {
        ResultSet rst= CrudUtil.execute("SELECT employeeId FROM employee ORDER BY employeeId DESC LIMIT 1");
        if(rst.next()){
            return Optional.of(rst.getString(1));
        }
        return Optional.empty();
    }

    @Override
    public long count() throws SQLException {
        ResultSet rst  = CrudUtil.execute("SELECT COUNT(employeeId) AS count FROM employee");
        rst.next();
        return rst.getLong(1);
    }

    @Override
    public boolean clearAll() throws SQLException {
        return CrudUtil.execute("DELETE FROM employee");
    }

    @Override
    public boolean existsByNic(String nic) throws SQLException {
        ResultSet rst= CrudUtil.execute("SELECT * FROM employee WHERE nic=?",nic);
        return rst.next();
    }
}
