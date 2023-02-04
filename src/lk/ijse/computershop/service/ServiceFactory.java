package lk.ijse.computershop.service;

import lk.ijse.computershop.dao.custom.impl.TransactionDAOImpl;
import lk.ijse.computershop.service.custom.impl.*;

public class ServiceFactory {
    private static ServiceFactory serviceFactory;

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance(){
        return serviceFactory==null?(serviceFactory=new ServiceFactory()):serviceFactory;
    }

    public <T extends SuperService>T getService(ServiceTypes serviceTypes){
        switch (serviceTypes){
            case CUSTOMER:
                return (T) new CustomerServiceImpl();
            case TRANSACTION:
                return (T) new TransactionServiceImpl();
            case REPAIR:
                return (T) new RepairServiceImpl();
            case EMPLOYEE:
                return (T) new EmployeeServiceImpl();
            case ITEM:
                return (T) new ItemServiceImpl();
            case USER:
                return (T) new UserServiceImpl();
            case SUPPLIER:
                return (T) new SupplierServiceImpl();
            case SUPPLIES:
                return (T) new SuppliesServiceImpl();
            default:
                return null;
        }
    }
}
