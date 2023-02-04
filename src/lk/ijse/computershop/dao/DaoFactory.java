package lk.ijse.computershop.dao;

import lk.ijse.computershop.dao.custom.impl.*;

public class DaoFactory {
    private static DaoFactory daoFactory;

    private DaoFactory() {
    }
    public static DaoFactory getInstance(){
        return daoFactory==null?(daoFactory=new DaoFactory()):daoFactory;
    }
    public <T extends SuperDAO>T getDAO(DaoTypes daoTypes){
        switch (daoTypes){
            case CUSTOMER:
                return (T) new CustomerDAOImpl();
            case EMPLOYEE:
                return (T) new EmployeeDAOImpl();
            case ITEM:
                return (T) new ItemDAOImpl();
            case ITEMTRANSATCIONDETAILS:
                return (T) new ItemTransactionDetailsDAOImpl();
            case LOGINRECORD:
                return (T) new LoginRecordDAOImpl();
            case REPAIR:
                return (T) new RepairDAOImpl();
            case REPAIRTRANSACTIONDETAILS:
                return (T) new RepairTransactionDetailsDAOImpl();
            case SUPPLIER:
                return (T) new SupplierDAOImpl();
            case SUPPLIES:
                return (T) new SuppliesDAOImpl();
            case SUPPLIESDETAILS:
                return (T) new SuppliesDetailsDAOImpl();
            case TRANSACTION:
                return (T) new TransactionDAOImpl();
            case USER:
                return (T) new UserDAOImpl();
            case QUERY:
                return (T) new QueryDAOImpl();
            default:
                return null;
        }
    }
}
