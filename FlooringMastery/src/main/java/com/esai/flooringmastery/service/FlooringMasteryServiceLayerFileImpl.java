package com.esai.flooringmastery.service;

import com.esai.flooringmastery.dao.FlooringMasteryDao;
import com.esai.flooringmastery.dao.FlooringMasteryDaoException;
import com.esai.flooringmastery.dto.Order;
import com.esai.flooringmastery.dto.Product;
import com.esai.flooringmastery.dto.Taxes;
import java.util.ArrayList;

/**
 *
 * @author Esai
 */
public class FlooringMasteryServiceLayerFileImpl implements FlooringMasteryServiceLayer {

    // dao to access files
    FlooringMasteryDao dao;

    // constructor assigning dao
    public FlooringMasteryServiceLayerFileImpl(FlooringMasteryDao dao) {
        this.dao = dao;
    }

    // returns arrayList containing orders from dao
    @Override
    public ArrayList<Order> getOrderList() throws FlooringMasteryDaoException {
        return dao.getOrderList();
    }

    // return true or false that order was successfully added
    @Override
    public boolean addOrder(Order userOrder) throws FlooringMasteryDaoException{
        return dao.addOrder(userOrder);
    }

    // return true or false that order was successfully edited
    @Override
    public boolean editOrder(Order newEditedOrder) throws FlooringMasteryDaoException{
        return dao.editOrder(newEditedOrder);
    }
    
    // return true or false that order exists
    @Override
    public Order getOrder(Object[] dateAndOrderNum) throws FlooringMasteryDaoException{
        return dao.getOrder(dateAndOrderNum);
    }

    // return true or false that order was successfully removed
    @Override
    public boolean removeOrder(Object[] dateAndOrderNum) throws FlooringMasteryDaoException{
        return dao.removeOrder(dateAndOrderNum);
    }

    // return true or false that all orders were exported
    @Override
    public boolean exportAllData() throws FlooringMasteryDaoException{
        return dao.exportAllData();
    }

    // return arraylist containing taxes info from taxes file
    @Override
    public ArrayList<Taxes> getTaxList() throws FlooringMasteryDaoException {
        return dao.getTaxList();
    }

    // return arraylist containing product info from products file
    @Override
    public ArrayList<Product> getProductList() throws FlooringMasteryDaoException {
        return dao.getProductList();
    }
    
}
