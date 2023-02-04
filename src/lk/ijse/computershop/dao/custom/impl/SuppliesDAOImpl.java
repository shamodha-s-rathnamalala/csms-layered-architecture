package lk.ijse.computershop.dao.custom.impl;

import lk.ijse.computershop.dao.custom.SuppliesDAO;
import lk.ijse.computershop.entity.Supplies;
import lk.ijse.computershop.dao.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class SuppliesDAOImpl implements SuppliesDAO {

    @Override
    public boolean save(Supplies entity) throws SQLException {
        return CrudUtil.execute("INSERT INTO supplies VALUES(?,?,?,?)",
                entity.getSuppliesId(),
                entity.getDate(),
                entity.getTime(),
                entity.getSupplierId()
        );
    }

    @Override
    public boolean update(Supplies entity) throws SQLException {
        return CrudUtil.execute("UPDATE supplies set date=?, time=?, supplierId=? where suppliesId=?",
                entity.getSuppliesId(),
                entity.getDate(),
                entity.getTime(),
                entity.getSupplierId()
        );
    }

    @Override
    public boolean deleteByPk(String pk) throws SQLException {
        return CrudUtil.execute("DELETE FROM supplies WHERE suppliesId=?",pk);
    }

    @Override
    public ArrayList<Supplies> getAll() throws SQLException {
        ArrayList<Supplies>suppliesArrayList=new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT *FROM supplies");
        while (rst.next()){
            suppliesArrayList.add(new Supplies(
                    rst.getString(1),
                    rst.getDate(2),
                    rst.getTime(3),
                    rst.getString(4)
            ));
        }
        return suppliesArrayList;
    }

    @Override
    public Optional<Supplies> findByPk(String pk) throws SQLException {
        ResultSet rst= CrudUtil.execute("SELECT * FROM supplies WHERE suppliesId=?",pk);
        if (rst.next()){
            return Optional.of(new Supplies(
                    rst.getString(1),
                    rst.getDate(2),
                    rst.getTime(3),
                    rst.getString(4)
            ));
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> getLastPk() throws SQLException {
        ResultSet rst= CrudUtil.execute("SELECT suppliesId FROM supplies ORDER BY suppliesId DESC LIMIT 1");
        if(rst.next()){
            return Optional.of(rst.getString(1));
        }
        return Optional.empty();
    }

    @Override
    public long count() throws SQLException {
        ResultSet rst  = CrudUtil.execute("SELECT COUNT(suppliesId) AS count FROM supplies");
        rst.next();
        return rst.getLong(1);
    }

    @Override
    public boolean clearAll() throws SQLException {
        return CrudUtil.execute("DELETE FROM supplies");
    }
}