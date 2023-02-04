package lk.ijse.computershop.service.custom;

import lk.ijse.computershop.dto.CustomerDTO;
import lk.ijse.computershop.dto.CustomerTransactionDTO;
import lk.ijse.computershop.service.SuperService;
import lk.ijse.computershop.service.exception.DuplicateException;
import lk.ijse.computershop.service.exception.InUseException;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerService extends SuperService {
    public void saveCustomer(CustomerDTO customerDTO) throws SQLException,DuplicateException;

    public void updateCustomer(CustomerDTO customerDTO) throws SQLException, DuplicateException;

    public ArrayList<CustomerDTO> getAllCustomer() throws SQLException;

    public void deleteCustomer(String customerId) throws SQLException, InUseException;

    public String getNextCustomerId() throws SQLException;

    public ArrayList<CustomerTransactionDTO> getAllTransactionByCustomer() throws SQLException;

    public long getCustomerCount() throws SQLException;

    public CustomerDTO getCustomer(String cusId) throws SQLException;

    public CustomerDTO getCustomerByName(String name) throws SQLException;
}
