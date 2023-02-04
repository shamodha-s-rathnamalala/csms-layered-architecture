package lk.ijse.computershop.dao.custom;

import lk.ijse.computershop.dao.CrudDAO;
import lk.ijse.computershop.entity.SuppliesDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SuppliesDetailsDAO extends CrudDAO<SuppliesDetails,String> {
    double findTotalByPk(String pk) throws SQLException;

    ArrayList<SuppliesDetails> findAllByPk(String pk) throws SQLException;
}
