package lk.ijse.computershop.service.custom.impl;

import lk.ijse.computershop.dao.DaoFactory;
import lk.ijse.computershop.dao.DaoTypes;
import lk.ijse.computershop.dao.custom.*;
import lk.ijse.computershop.db.DBConnection;
import lk.ijse.computershop.dto.*;
import lk.ijse.computershop.entity.Customer;
import lk.ijse.computershop.entity.ItemTransactionDetails;
import lk.ijse.computershop.entity.RepairTransactionDetails;
import lk.ijse.computershop.entity.Transaction;
import lk.ijse.computershop.service.custom.TransactionService;
import lk.ijse.computershop.service.util.Convertor;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class TransactionServiceImpl implements TransactionService {
    private final TransactionDAO transactionDAO;
    private final QueryDAO queryDAO;
    private final RepairTransactionDetailsDAO repairTransactionDetailsDAO;
    private final RepairDAO repairDAO;
    private final ItemTransactionDetailsDAO itemTransactionDetailsDAO;
    private final ItemDAO itemDAO;
    private final CustomerDAO customerDAO;
    private final Connection connection;
    private final Convertor convertor;

    public TransactionServiceImpl() {
        transactionDAO = DaoFactory.getInstance().getDAO(DaoTypes.TRANSACTION);
        queryDAO = DaoFactory.getInstance().getDAO(DaoTypes.QUERY);
        repairTransactionDetailsDAO = DaoFactory.getInstance().getDAO(DaoTypes.REPAIRTRANSACTIONDETAILS);
        repairDAO = DaoFactory.getInstance().getDAO(DaoTypes.REPAIR);
        itemTransactionDetailsDAO = DaoFactory.getInstance().getDAO(DaoTypes.ITEMTRANSATCIONDETAILS);
        itemDAO = DaoFactory.getInstance().getDAO(DaoTypes.ITEM);
        customerDAO = DaoFactory.getInstance().getDAO(DaoTypes.CUSTOMER);
        connection = DBConnection.getInstance().getConnection();
        convertor = new Convertor();
    }

    @Override
    public long findTransactionCountByDate(LocalDate date) throws SQLException {
        return transactionDAO.findAllByDate(Date.valueOf(date)).size();
    }

    @Override
    public long transactionCount() throws SQLException {
        return transactionDAO.count();
    }

    @Override
    public double getIncomeByDate(LocalDate date) throws SQLException {
        return queryDAO.getIncomeByDate(Date.valueOf(date));
    }

    @Override
    public String getNextTransactionId() throws SQLException {
        Optional<String> lastPk = transactionDAO.getLastPk();
        if(lastPk.isPresent()){
            String pk=lastPk.get().substring(1);
            pk=String.format("T%03d", Integer.parseInt(pk)+1);
            return pk;
        }
        return "T001";
    }

    @Override
    public void saveTransaction(TransactionDTO transactionDTO) throws SQLException {
        //database transaction
        try {
            connection.setAutoCommit(false);
            System.out.println("Auto commit on");
            if (transactionDAO.save(convertor.toTransaction(transactionDTO))) {
                if (saveRepairDetails(transactionDTO.getRepairTransactionDetailsArrayList())) {
                    if (saveItemDetails(transactionDTO.getItemTransactionDetailsArrayList())) {
                        connection.commit();
                        System.out.println("commit");
                        return;
                    }
                }
            }
            throw new SQLException("The order could not be placed !");
        }catch (SQLException e){
            connection.rollback();
            System.out.println("roll");
            throw new SQLException(e);
        }finally {
            connection.setAutoCommit(true);
            System.out.println("Auto commit on");
        }
    }

    @Override
    public RepairTransactionDetailsDTO getRepairTransactionDetails(String repairId) throws SQLException {
        Optional<RepairTransactionDetails> optional = repairTransactionDetailsDAO.findByRepairId(repairId);
        if (optional.isPresent())
            return convertor.fromRepairTransactionDetails(optional.get());
        throw new SQLException("not found repair details !");
    }

    @Override
    public TransactionDTO getTransaction(String transactionId) throws SQLException {
        Optional<Transaction> optional = transactionDAO.findByPk(transactionId);
        if (optional.isPresent())
            return convertor.fromTransaction(optional.get());
        throw new SQLException("not found !");
    }

    @Override
    public ArrayList<TransactionDTO> getAllFullTransaction() throws SQLException {
        ArrayList<TransactionDTO> transactionDTOS =new ArrayList<>();
        for (Transaction transaction : transactionDAO.getAll()){
            Customer customer = customerDAO.findByPk(transaction.getCustomerId()).get();
            ArrayList<ItemTransactionDetails> itemTransactionDetails = itemTransactionDetailsDAO.findAllById(transaction.getTransactionId());
            ArrayList<RepairTransactionDetails> repairTransactionDetails = repairTransactionDetailsDAO.findAllByTransactionId(transaction.getTransactionId());
            double total = getItemTotal(itemTransactionDetails) + getRepairTotal(repairTransactionDetails);
            transactionDTOS.add(new TransactionDTO(
                    transaction.getTransactionId(),
                    transaction.getDate(),
                    transaction.getTime(),
                    transaction.getCustomerId(),
                    customer.getName(),
                    customer.getTelephoneNumber(),
                    transaction.getType(),
                    total,
                    (ArrayList<ItemTransactionDetailsDTO>) itemTransactionDetails.stream().map(itd -> convertor.fromItemTransactionDetails(itd)).collect(Collectors.toList()),
                    (ArrayList<RepairTransactionDetailsDTO>) repairTransactionDetails.stream().map(rtd -> convertor.fromRepairTransactionDetails(rtd)).collect(Collectors.toList())
            ));
        }
        return transactionDTOS;
    }

    @Override
    public ArrayList<CustomerTransactionDTO> getAllCustomerTransaction() throws SQLException {
        ArrayList<Transaction> transactions = transactionDAO.getAll();
        ArrayList<CustomerTransactionDTO> customerTransactionDTOS = new ArrayList<>();
        for (Transaction transaction : transactions){
            Optional<CustomerTransactionDTO> optional = queryDAO.getAllCustomerTransaction(transaction.getTransactionId());
            if (optional.isPresent()) {
                customerTransactionDTOS.add(optional.get());
            }
        }
        return customerTransactionDTOS;
    }

    private double getRepairTotal(ArrayList<RepairTransactionDetails> repairTransactionDetails) {
        double total = 0;
        for (RepairTransactionDetails repairTransactionDetail : repairTransactionDetails){
            total += repairTransactionDetail.getRepairPrice();
        }
        return total;
    }

    private double getItemTotal(ArrayList<ItemTransactionDetails> itemTransactionDetails) {
        double total = 0;
        for (ItemTransactionDetails itemTransactionDetail : itemTransactionDetails){
            total += (itemTransactionDetail.getQuantity() * itemTransactionDetail.getUnitPrice());
        }
        return total;
    }

    private boolean saveItemDetails(ArrayList<ItemTransactionDetailsDTO> items) throws SQLException {
        for (ItemTransactionDetailsDTO item : items){
            if (itemTransactionDetailsDAO.save(convertor.toItemTransactionDetails(item)))
                if (itemDAO.sellItemQtyByCode(item.getItemCode(), item.getQuantity()))
                    continue;
            return false;
        }
        return true;
    }

    private boolean saveRepairDetails(ArrayList<RepairTransactionDetailsDTO> repairs) throws SQLException {
        for (RepairTransactionDetailsDTO repair : repairs){
            if (repairTransactionDetailsDAO.save(convertor.toRepairTransactionDetails(repair)))
                if(repairDAO.updateStatus(repair.getRepairId(),"returned"))
                    continue;
            return false;
        }
        return true;
    }
}
