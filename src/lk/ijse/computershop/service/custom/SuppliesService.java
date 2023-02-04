package lk.ijse.computershop.service.custom;

import lk.ijse.computershop.dto.SuppliesDTO;
import lk.ijse.computershop.service.SuperService;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SuppliesService extends SuperService {
    public String getNextSuppliesId() throws SQLException;

    public void saveSupplies(SuppliesDTO suppliesDTO) throws SQLException;

    public ArrayList<SuppliesDTO> getAllFullSupplies() throws SQLException;
}
