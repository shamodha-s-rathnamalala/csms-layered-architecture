package lk.ijse.computershop.dao.custom.impl;

import lk.ijse.computershop.dao.custom.UserDAO;
import lk.ijse.computershop.entity.User;
import lk.ijse.computershop.dao.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {

    @Override
    public boolean save(User entity) throws SQLException {
        return CrudUtil.execute("INSERT INTO user VALUES(?,?,?,?,?,?,?)",
                entity.getUserId(),
                entity.getUserName(),
                entity.getUserPassword(),
                entity.getNic(),
                entity.getTelephoneNumber(),
                entity.getEmail(),
                entity.getRank()
        );
    }

    @Override
    public boolean update(User entity) throws SQLException {
        return CrudUtil.execute("UPDATE user set userName=?, userPassword=?, nic=?, telephoneNumber=?, email=?, `rank` =? where userid=?",
                entity.getUserName(),
                entity.getUserPassword(),
                entity.getNic(),
                entity.getTelephoneNumber(),
                entity.getEmail(),
                entity.getRank(),
                entity.getUserId()
        );
    }

    @Override
    public boolean deleteByPk(String pk) throws SQLException {
        return CrudUtil.execute("DELETE FROM user WHERE userid=?",pk);
    }

    @Override
    public ArrayList<User> getAll() throws SQLException {
        ArrayList<User>userArrayList=new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM user");
        while (rst.next()){
            userArrayList.add(getUser(rst));
        }
        return userArrayList;
    }

    @Override
    public Optional<User> findByPk(String pk) throws SQLException {
        ResultSet rst= CrudUtil.execute("SELECT * FROM user WHERE userid = ?",pk);
        if (rst.next()){
            return Optional.of(getUser(rst));
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> getLastPk() throws SQLException {
        ResultSet rst= CrudUtil.execute("SELECT userid FROM user ORDER BY userid DESC LIMIT 1");
        if(rst.next()){
            return Optional.of(rst.getString(1));
        }
        return Optional.empty();
    }

    @Override
    public long count() throws SQLException {
        ResultSet rst  = CrudUtil.execute("SELECT COUNT(userid) AS count FROM user");
        rst.next();
        return rst.getLong(1);
    }

    @Override
    public boolean clearAll() throws SQLException {
        return CrudUtil.execute("DELETE FROM user");
    }

    @Override
    public Optional<User> findByName(String userName) throws SQLException {
        ResultSet rst= CrudUtil.execute("SELECT * FROM user WHERE userName = ?",userName);
        if (rst.next()){
            return Optional.of(getUser(rst));
        }
        return Optional.empty();
    }

    @Override
    public boolean existsByNic(String nic) throws SQLException {
        ResultSet rst= CrudUtil.execute("SELECT * FROM user WHERE nic=?",nic);
        return rst.next();
    }

    private User getUser(ResultSet rst) throws SQLException {
        return new User(
                rst.getString(1),
                rst.getString(2),
                rst.getString(3),
                rst.getString(4),
                rst.getString(5),
                rst.getString(6),
                rst.getString(7)
        );
    }
}