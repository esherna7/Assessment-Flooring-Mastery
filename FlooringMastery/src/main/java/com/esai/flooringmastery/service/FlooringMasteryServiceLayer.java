package com.esai.flooringmastery.service;

import com.esai.flooringmastery.dao.FlooringMasteryDaoException;
import com.esai.flooringmastery.dto.Order;
import com.esai.flooringmastery.dto.Product;
import com.esai.flooringmastery.dto.Taxes;
import java.util.ArrayList;

/**
 *
 * @author Esai
 */
public interface FlooringMasteryServiceLayer {

    ArrayList<Order> getOrderList() throws FlooringMasteryDaoException;

    boolean addOrder(Order userOrder) throws FlooringMasteryDaoException;

    boolean editOrder(Order newEditedOrder) throws FlooringMasteryDaoException;

    Order getOrder(Object[] dateAndOrderNum) throws FlooringMasteryDaoException;
    
    boolean removeOrder(Object[] dateAndOrderNum) throws FlooringMasteryDaoException;

    boolean exportAllData() throws FlooringMasteryDaoException;

    ArrayList<Taxes> getTaxList() throws FlooringMasteryDaoException;

    ArrayList<Product> getProductList() throws FlooringMasteryDaoException;

}
