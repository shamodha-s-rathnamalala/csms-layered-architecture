package lk.ijse.computershop.dao.custom.impl;

import lk.ijse.computershop.dao.custom.LoginRecordDAO;
import lk.ijse.computershop.entity.LoginRecord;
import lk.ijse.computershop.dao.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class LoginRecordDAOImpl implements LoginRecordDAO {

    @Override
    public boolean save(LoginRecord entity) throws SQLException {
        return CrudUtil.execute("INSERT INTO loginrecord VALUES(?, ?, ?, ?)",
                entity.getLoginId(),
                entity.getDate(),
                entity.getTime(),
                entity.getUserId()
        );
    }

    @Override
    public boolean update(LoginRecord entity) throws SQLException{
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public boolean deleteByPk(String pk) throws SQLException {
        return CrudUtil.execute("DELETE FROM loginrecord WHERE loginId=?",pk);
    }

    @Override
    public ArrayList<LoginRecord> getAll() throws SQLException {
        ResultSet rst= CrudUtil.execute("SELECT * FROM loginrecord");
        ArrayList<LoginRecord>loginRecordArrayList=new ArrayList<>();
        while (rst.next()){
            loginRecordArrayList.add(new LoginRecord(
                    rst.getString(1),
                    rst.getDate(2),
                    rst.getTime(3),
                    rst.getString(4)
            ));
        }
        return loginRecordArrayList;
    }

    @Override
    public Optional<LoginRecord> findByPk(String pk) throws SQLException{
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public Optional<String> getLastPk() throws SQLException {
        ResultSet rst= CrudUtil.execute("SELECT loginId FROM loginrecord ORDER BY loginId DESC LIMIT 1");
        if(rst.next()){
            return Optional.of(rst.getString(1));
        }
        return Optional.empty();
    }

    @Override
    public long count() throws SQLException {
        ResultSet rst  = CrudUtil.execute("SELECT COUNT(loginId) AS count FROM loginrecord");
        rst.next();
        return rst.getLong(1);
    }

    @Override
    public boolean clearAll() throws SQLException {
        return CrudUtil.execute("delete from loginrecord");
    }
}