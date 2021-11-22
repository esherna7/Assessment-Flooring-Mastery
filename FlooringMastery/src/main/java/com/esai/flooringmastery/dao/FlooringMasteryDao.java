package com.esai.flooringmastery.dao;

import com.esai.flooringmastery.dto.Order;
import com.esai.flooringmastery.dto.Product;
import com.esai.flooringmastery.dto.Taxes;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Esai
 */
public interface FlooringMasteryDao {

    ArrayList<Order> getOrderList() throws FlooringMasteryDaoException;

    boolean addOrder(Order userOrder) throws FlooringMasteryDaoException;

    boolean editOrder(Order newEditedOrder) throws FlooringMasteryDaoException;

    Order getOrder(Object[] dateAndOrderNum) throws FlooringMasteryDaoException;
    
    boolean removeOrder(Object[] dateAndOrderNum) throws FlooringMasteryDaoException;

    boolean exportAllData() throws FlooringMasteryDaoException;

    Order unmarshallOrder(String orderFromFile);

    ArrayList<Order> loadOrder() throws FlooringMasteryDaoException;
    
    ArrayList<Order> loadOrderForDate(LocalDate requestedDate) throws FlooringMasteryDaoException;

    Taxes unmarshallTax(String taxesFromFile);

    ArrayList<Taxes> loadTaxes() throws FlooringMasteryDaoException;

    Product unmarshallProduct(String productFromFile);

    ArrayList<Product> loadProduct() throws FlooringMasteryDaoException;

    String marshallOrder(Order orderFromObject);

    void writeOrder() throws FlooringMasteryDaoException;

    File getCurrentDateFile() throws FlooringMasteryDaoException;

    ArrayList<Taxes> getTaxList() throws FlooringMasteryDaoException;

    ArrayList<Product> getProductList() throws FlooringMasteryDaoException;
}
