package lk.ijse.computershop.dao.custom.impl;

import lk.ijse.computershop.dao.custom.RepairDAO;
import lk.ijse.computershop.entity.Repair;
import lk.ijse.computershop.dao.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class RepairDAOImpl implements RepairDAO {

    @Override
    public boolean save(Repair entity) throws SQLException {
        return CrudUtil.execute("INSERT INTO repair VALUES(?,?,?,?,?,?)",
                entity.getRepairId(),
                entity.getReceiveDate(),
                entity.getReturnDate(),
                entity.getStatus(),
                entity.getDescription(),
                entity.getCustomerId()
        );
    }

    @Override
    public boolean update(Repair entity) throws SQLException {
        return CrudUtil.execute("UPDATE repair set receiveDate=?, returnDate=?, status=?, description=?, customerId=? where repairId=?",
                entity.getReceiveDate(),
                entity.getReturnDate(),
                entity.getStatus(),
                entity.getDescription(),
                entity.getCustomerId(),
                entity.getRepairId()
        );
    }

    @Override
    public boolean deleteByPk(String pk) throws SQLException {
        return CrudUtil.execute("DELETE FROM repair WHERE repairId=?",pk);
    }

    @Override
    public ArrayList<Repair> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT *FROM repair");
        ArrayList<Repair>arrayList=new ArrayList<>();
        while (rst.next()){
            arrayList.add(getRepair(rst));
        }
        return arrayList;
    }

    @Override
    public Optional<Repair> findByPk(String pk) throws SQLException {
        ResultSet rst= CrudUtil.execute("SELECT * FROM repair WHERE repairId=?",pk);
        if (rst.next()){
            return Optional.of(getRepair(rst));
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> getLastPk() throws SQLException {
        ResultSet rst= CrudUtil.execute("SELECT repairId FROM repair ORDER BY repairId DESC LIMIT 1");
        if(rst.next()){
            return Optional.of(rst.getString(1));
        }
        return Optional.empty();
    }

    @Override
    public long count() throws SQLException {
        ResultSet rst  = CrudUtil.execute("SELECT COUNT(repairId) AS count FROM repair");
        rst.next();
        return rst.getLong(1);
    }

    @Override
    public boolean clearAll() throws SQLException {
        return CrudUtil.execute("DELETE FROM repair");
    }

    private Repair getRepair(ResultSet rst) throws SQLException {
        return new Repair(
                rst.getString(1),
                rst.getDate(2),
                rst.getDate(3),
                rst.getString(4),
                rst.getString(5),
                rst.getString(6)
        );
    }

    @Override
    public ArrayList<Repair> findAllByCustomerId(String customerId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT *FROM repair WHERE customerId = ?",customerId);
        ArrayList<Repair>arrayList=new ArrayList<>();
        while (rst.next()){
            arrayList.add(getRepair(rst));
        }
        return arrayList;
    }

    @Override
    public boolean updateStatus(String repairId, String status) throws SQLException {
        return CrudUtil.execute("UPDATE repair set state=? where repairId=?",status,repairId);
    }
}
