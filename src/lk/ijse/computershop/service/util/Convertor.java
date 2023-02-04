package lk.ijse.computershop.service.util;

import lk.ijse.computershop.dto.*;
import lk.ijse.computershop.entity.*;

public class Convertor {
    public Customer toCustomer(CustomerDTO customerDTO) {
        return new Customer(customerDTO.getCustomerId(), customerDTO.getName(), customerDTO.getAddress(), customerDTO.getNic(), customerDTO.getTelephoneNumber(), customerDTO.getEmail());
    }

    public CustomerDTO fromCustomer(Customer customer) {
        return new CustomerDTO(customer.getCustomerId(), customer.getName(), customer.getAddress(), customer.getNic(), customer.getTelephoneNumber(), customer.getEmail());
    }

    public EmployeeDTO fromEmployee(Employee employee) {
        return new EmployeeDTO(employee.getEmployeeId(), employee.getName(), employee.getAddress(), employee.getNic(), employee.getTelephoneNumber(), employee.getRank());
    }

    public Employee toEmployee(EmployeeDTO employeeDTO) {
        return new Employee(employeeDTO.getEmployeeId(), employeeDTO.getName(), employeeDTO.getAddress(), employeeDTO.getNic(), employeeDTO.getTelephoneNumber(), employeeDTO.getRank());
    }

    public ItemDTO fromItem(Item item) {
        return new ItemDTO(item.getItemCode(), item.getItemType(), item.getDescription(), item.getUnitPrice(), item.getQtyOnStock());
    }

    public Item toItem(ItemDTO itemDTO) {
        return new Item(itemDTO.getItemCode(), itemDTO.getItemType(), itemDTO.getDescription(), itemDTO.getUnitPrice(), itemDTO.getQtyOnStock());
    }

    public User toUser(UserDTO user) {
        return new User(user.getUserId(), user.getUserName(), user.getUserPassword(), user.getNic(), user.getTelephoneNumber(), user.getEmail(), user.getEmail());
    }

    public UserDTO fromUser(User user) {
        return new UserDTO(user.getUserId(), user.getUserName(), user.getUserPassword(), user.getNic(), user.getTelephoneNumber(), user.getEmail(), user.getEmail());
    }

    public RepairDTO fromRepair(Repair repair) {
        return new RepairDTO(repair.getRepairId(), repair.getReceiveDate(), repair.getReturnDate(), repair.getStatus(), repair.getDescription(), repair.getCustomerId());
    }

    public Transaction toTransaction(TransactionDTO tDTO) {
        return new Transaction(tDTO.getTransactionId(), tDTO.getDate(), tDTO.getTime(), tDTO.getCustomerId(), tDTO.getType());
    }

    public RepairTransactionDetails toRepairTransactionDetails(RepairTransactionDetailsDTO rtDTO) {
        return new RepairTransactionDetails(rtDTO.getTransactionId(), rtDTO.getRepairId(), rtDTO.getRepairPrice());
    }

    public RepairTransactionDetailsDTO fromRepairTransactionDetails(RepairTransactionDetails rt) {
        return new RepairTransactionDetailsDTO(rt.getTransactionId(), rt.getRepairId(), rt.getRepairPrice());
    }

    public ItemTransactionDetails toItemTransactionDetails(ItemTransactionDetailsDTO itDTO) {
        return new ItemTransactionDetails(itDTO.getTransactionId(), itDTO.getItemCode(), itDTO.getQuantity(), itDTO.getUnitPrice());
    }

    public ItemTransactionDetailsDTO fromItemTransactionDetails(ItemTransactionDetails itd) {
        return new ItemTransactionDetailsDTO(itd.getTransactionId(), itd.getItemCode(), itd.getQuantity(), itd.getUnitPrice());
    }

    public TransactionDTO fromTransaction(Transaction t) {
        return new TransactionDTO(t.getTransactionId(), t.getDate(), t.getTime(), t.getCustomerId(), t.getType());
    }

    public Repair toRepair(RepairDTO repairDTO) {
        return new Repair(repairDTO.getRepairId(), repairDTO.getReceiveDate(), repairDTO.getReturnDate(), repairDTO.getStatus(), repairDTO.getDescription(), repairDTO.getCustomerId());
    }

    public SupplierDTO fromSupplier(Supplier supplier) {
        return new SupplierDTO(supplier.getSupplierId(), supplier.getName(), supplier.getAddress(), supplier.getTelephoneNumber(), supplier.getEmail());
    }

    public Supplier toSupplier(SupplierDTO supplierDTO) {
        return new Supplier(supplierDTO.getSupplierId(), supplierDTO.getName(), supplierDTO.getAddress(), supplierDTO.getTelephoneNumber(), supplierDTO.getEmail());
    }

    public Supplies toSupplies(SuppliesDTO suppliesDTO) {
       return new Supplies(suppliesDTO.getSuppliesId(), suppliesDTO.getDate(), suppliesDTO.getTime(), suppliesDTO.getSupplierId());
    }

    public SuppliesDetails toSuppliesDetails(SuppliesDetailsDTO dto) {
        return new SuppliesDetails(dto.getSuppliesId(), dto.getItemCode(), dto.getQuantity(), dto.getUnitPrice());
    }

    public SuppliesDetailsDTO fromSuppliesDetail(SuppliesDetails sd) {
        return new SuppliesDetailsDTO(sd.getSuppliesId(), sd.getItemCode(), sd.getQuantity(), sd.getUnitPrice());
    }
}
