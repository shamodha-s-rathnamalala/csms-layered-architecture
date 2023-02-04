package lk.ijse.computershop.dao;

import lk.ijse.computershop.entity.SuperEntity;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public interface CrudDAO<T extends SuperEntity ,ID extends Serializable> extends SuperDAO{
    public boolean save(T entity) throws SQLException;
    public boolean update(T entity) throws SQLException;
    public boolean deleteByPk(ID pk) throws SQLException;
    public ArrayList<T> getAll() throws SQLException;
    public Optional<T> findByPk(ID pk) throws SQLException;
    public Optional<String> getLastPk() throws SQLException;
    public long count() throws SQLException;
    public boolean clearAll() throws SQLException;
}
