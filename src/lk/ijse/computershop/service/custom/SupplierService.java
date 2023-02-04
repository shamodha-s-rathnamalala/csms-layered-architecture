package lk.ijse.computershop.service.custom;

import lk.ijse.computershop.dto.SupplierDTO;
import lk.ijse.computershop.service.SuperService;
import lk.ijse.computershop.service.exception.DuplicateException;
import lk.ijse.computershop.view.tm.SupplierTM;

import java.sql.SQLException;
import java.util.List;

public interface SupplierService extends SuperService {
    public void deleteSupplier(String supplierId) throws SQLException;

    public List<SupplierDTO> getAllSupplier() throws SQLException;

    public String getNextSupplierId() throws SQLException;

    public void saveSupplier(SupplierDTO supplierDTO) throws SQLException, DuplicateException;

    public void updateSupplier(SupplierDTO supplierDTO) throws SQLException, DuplicateException;

    public SupplierDTO getSupplierById(String supplierId) throws SQLException;

    public SupplierDTO getSupplierByName(String name) throws SQLException;
}
