package lk.ijse.computershop.dao.custom.impl;

import lk.ijse.computershop.dao.custom.TransactionDAO;
import lk.ijse.computershop.entity.Transaction;
import lk.ijse.computershop.dao.util.CrudUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class TransactionDAOImpl implements TransactionDAO {

    @Override
    public boolean save(Transaction entity) throws SQLException {
        return CrudUtil.execute("INSERT INTO transaction VALUES(?,?,?,?,?)",
                entity.getTransactionId(),
                entity.getDate(),
                entity.getTime(),
                entity.getCustomerId(),
                entity.getType()
        );
    }

    @Override
    public boolean update(Transaction entity) throws SQLException {
        return CrudUtil.execute("UPDATE transaction set date=?, time=?, customerId=?, type=? where transactionId=?",
                entity.getDate(),
                entity.getTime(),
                entity.getCustomerId(),
                entity.getType(),
                entity.getTransactionId()
        );
    }

    @Override
    public boolean deleteByPk(String pk) throws SQLException {
        return CrudUtil.execute("DELETE FROM transaction WHERE transactionId=?",pk);
    }

    @Override
    public ArrayList<Transaction> getAll() throws SQLException {
        ResultSet rst= CrudUtil.execute("SELECT *FROM transaction");
        return getTransactionList(rst);

    }

    @Override
    public Optional<Transaction> findByPk(String pk) throws SQLException {
        ResultSet rst= CrudUtil.execute("SELECT * FROM transaction WHERE transactionId=?",pk);
        if (rst.next()){
            return Optional.of(getTransaction(rst));
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> getLastPk() throws SQLException {
        ResultSet rst= CrudUtil.execute("SELECT transactionId FROM transaction ORDER BY transactionId DESC LIMIT 1");
        if(rst.next()){
            return Optional.of(rst.getString(1));
        }
        return Optional.empty();
    }

    @Override
    public long count() throws SQLException {
        ResultSet rst  = CrudUtil.execute("SELECT COUNT(transactionId) AS count FROM transaction");
        rst.next();
        return rst.getLong(1);
    }

    @Override
    public boolean clearAll() throws SQLException {
        return CrudUtil.execute("DELETE FROM transaction");
    }

    @Override
    public ArrayList<Transaction> findAllByDate(Date date) throws SQLException {
        ArrayList<Transaction>toDayAllTransaction=new ArrayList<>();
        ResultSet rst= CrudUtil.execute("SELECT * FROM transaction WHERE date LIKE ?",date);
        return getTransactionList(rst);
    }

    @Override
    public ArrayList<Transaction> findAllByCustomerId(String customerId) throws SQLException {
        ResultSet rst= CrudUtil.execute("SELECT * FROM transaction WHERE customerId=?",customerId);
        return getTransactionList(rst);
    }

    private ArrayList<Transaction> getTransactionList(ResultSet rst) throws SQLException {
        ArrayList<Transaction>arrayList=new ArrayList<>();
        while (rst.next()){
            arrayList.add(getTransaction(rst));
        }
        return arrayList;
    }

    private Transaction getTransaction(ResultSet rst) throws SQLException {
        return new Transaction(
                rst.getString(1),
                rst.getDate(2),
                rst.getTime(3),
                rst.getString(4),
                rst.getString(5)
        );
    }
}