package com.esai.flooringmastery.service;

import com.esai.flooringmastery.dao.FlooringMasteryDao;
import com.esai.flooringmastery.dao.FlooringMasteryDaoException;
import com.esai.flooringmastery.dao.FlooringMasteryDaoFileImpl;
import com.esai.flooringmastery.dto.Order;
import com.esai.flooringmastery.dto.Product;
import com.esai.flooringmastery.dto.Taxes;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Esai
 */
public class FlooringMasteryServiceLayerFileImplTest {

    FlooringMasteryServiceLayer testService;
    FlooringMasteryDao testDao;

    public FlooringMasteryServiceLayerFileImplTest() {
    }

    @Test
    public void testGetOrderList() throws IOException, FlooringMasteryDaoException {
        // Assign testDao to testFile
        File testFile = new File("C:/Users/herna/Documents/EsaiEssentials/Repos/Assessment-Flooring-Mastery/"
                + "FlooringMastery/TestOrders/Orders_09012013.txt");
        new FileWriter(testFile, true);
        testDao = new FlooringMasteryDaoFileImpl(testFile);
        testService = new FlooringMasteryServiceLayerFileImpl(testDao);

        ArrayList<Order> orderList = testService.getOrderList();

        // Assert
        assertNotNull(orderList, "Order list should not be empty");
    }

    @Test
    public void testAddOrder() throws FlooringMasteryDaoException, IOException {
        // Assign testDao to testFile
        File testFile = new File("C:/Users/herna/Documents/EsaiEssentials/Repos/Assessment-Flooring-Mastery/"
                + "FlooringMastery/TestOrders/Orders_09012013.txt");
        new FileWriter(testFile, true);
        testDao = new FlooringMasteryDaoFileImpl(testFile);
        testService = new FlooringMasteryServiceLayerFileImpl(testDao);

        // Create Order test
        Order testOrder = new Order("John Smith", "TX",
                "Carpet", new BigDecimal("150"), new BigDecimal("4.45"),
                new BigDecimal("2.25"), new BigDecimal("2.10"));
        testOrder.calculateMaterialCost();
        testOrder.calculateLaborCost();
        testOrder.calculateTax();
        testOrder.calculateTotal();

        boolean testAddOrderSuccess = testService.addOrder(testOrder);

        // Assert
        assertTrue(testAddOrderSuccess, "Test order should be added");
    }

    @Test
    public void testGetOrder() throws IOException, FlooringMasteryDaoException {
        // Assign testDao to testFile
        File testFile = new File("C:/Users/herna/Documents/EsaiEssentials/Repos/Assessment-Flooring-Mastery/"
                + "FlooringMastery/TestOrders/Orders_09012013.txt");
        new FileWriter(testFile, true);
        testDao = new FlooringMasteryDaoFileImpl(testFile);
        testService = new FlooringMasteryServiceLayerFileImpl(testDao);

        //get current date and set ordernum to 1
        // add to array of objects
        Object[] dateAndOrderNum = new Object[2];
        LocalDate currentDate = LocalDate.parse("2013-09-01");
        int orderNum = 1;
        dateAndOrderNum[0] = currentDate;
        dateAndOrderNum[1] = orderNum;

        Order retrievedOrder = testService.getOrder(dateAndOrderNum);

        // Assert
        assertNotNull(retrievedOrder, "Order retrieved should not be null");
    }

    @Test
    public void testGetTaxList() throws IOException, FlooringMasteryDaoException {
        // Assign testDao to testFile
        File testFile = new File("C:/Users/herna/Documents/EsaiEssentials/Repos/Assessment-Flooring-Mastery/"
                + "FlooringMastery/TestOrders/Orders_09012013.txt");
        new FileWriter(testFile, true);
        testDao = new FlooringMasteryDaoFileImpl(testFile);
        testService = new FlooringMasteryServiceLayerFileImpl(testDao);

        ArrayList<Taxes> testTaxList = testService.getTaxList();

        // Assert
        assertNotNull(testTaxList, "The list of taxes should not be null");
    }

    @Test
    public void testGetProductList() throws IOException, FlooringMasteryDaoException {
        // Assign testDao to testFile
        File testFile = new File("C:/Users/herna/Documents/EsaiEssentials/Repos/Assessment-Flooring-Mastery/"
                + "FlooringMastery/TestOrders/Orders_09012013.txt");
        new FileWriter(testFile, true);
        testDao = new FlooringMasteryDaoFileImpl(testFile);
        testService = new FlooringMasteryServiceLayerFileImpl(testDao);

        ArrayList<Product> testProductList = testService.getProductList();

        // Assert
        assertNotNull(testProductList, "The list of products should not be null");
    }

}
