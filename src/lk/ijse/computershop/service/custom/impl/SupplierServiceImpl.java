package lk.ijse.computershop.service.custom.impl;

import lk.ijse.computershop.dao.DaoFactory;
import lk.ijse.computershop.dao.DaoTypes;
import lk.ijse.computershop.dao.custom.SupplierDAO;
import lk.ijse.computershop.dto.SupplierDTO;
import lk.ijse.computershop.entity.Supplier;
import lk.ijse.computershop.service.custom.SupplierService;
import lk.ijse.computershop.service.exception.DuplicateException;
import lk.ijse.computershop.service.util.Convertor;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SupplierServiceImpl implements SupplierService {
    private final SupplierDAO supplierDAO;
    private final Convertor convertor;

    public SupplierServiceImpl() {
        supplierDAO = DaoFactory.getInstance().getDAO(DaoTypes.SUPPLIER);
        convertor = new Convertor();
    }

    @Override
    public void deleteSupplier(String supplierId) throws SQLException {
        if (!supplierDAO.deleteByPk(supplierId))
            throw new SQLException("supplier delete fail !");
    }

    @Override
    public List<SupplierDTO> getAllSupplier() throws SQLException {
        return supplierDAO.getAll().stream().map(supplier -> convertor.fromSupplier(supplier)).collect(Collectors.toList());
    }

    @Override
    public String getNextSupplierId() throws SQLException {
        Optional<String> lastPk = supplierDAO.getLastPk();
        if(lastPk.isPresent()){
            String pk=lastPk.get().substring(1);
            pk=String.format("S%03d", Integer.parseInt(pk)+1);
            return pk;
        }
        return "S001";
    }

    @Override
    public void saveSupplier(SupplierDTO supplierDTO) throws SQLException, DuplicateException {
        Optional<Supplier> optional = supplierDAO.findByName(supplierDTO.getName());
        if (optional.isPresent())
            throw new DuplicateException("Duplicate name\nSupplier already saved !");
        if (!supplierDAO.save(convertor.toSupplier(supplierDTO)))
            throw new SQLException("Fail to save the supplier !");
    }

    @Override
    public void updateSupplier(SupplierDTO supplierDTO) throws SQLException, DuplicateException {
        Optional<Supplier> optional = supplierDAO.findByName(supplierDTO.getName());
        if (optional.isPresent() && (!optional.get().getSupplierId().equals(supplierDTO.getSupplierId())))
            throw new DuplicateException("Another supplier name is duplicated !");
        if (!supplierDAO.update(convertor.toSupplier(supplierDTO)))
            throw new SQLException("Fail to update the supplier !");
    }

    @Override
    public SupplierDTO getSupplierById(String supplierId) throws SQLException {
        Optional<Supplier> optional = supplierDAO.findByPk(supplierId);
        if (optional.isPresent())
            return convertor.fromSupplier(optional.get());
        throw new SQLException("not found !");
    }

    @Override
    public SupplierDTO getSupplierByName(String name) throws SQLException {
        Optional<Supplier> optional = supplierDAO.findByName(name);
        if (optional.isPresent())
            return convertor.fromSupplier(optional.get());
        throw new SQLException("not found !");
    }
}
