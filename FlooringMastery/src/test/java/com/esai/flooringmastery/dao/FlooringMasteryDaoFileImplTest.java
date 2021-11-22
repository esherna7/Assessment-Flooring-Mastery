package com.esai.flooringmastery.dao;

import com.esai.flooringmastery.dto.Order;
import com.esai.flooringmastery.dto.Product;
import com.esai.flooringmastery.dto.Taxes;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Esai
 */
// NOTE: Delete all test Order files except Orders_09012013.txt
// before running
public class FlooringMasteryDaoFileImplTest {

    FlooringMasteryDao testDao;

    @Test
    public void testGetOrderList() throws IOException, FlooringMasteryDaoException {
        // Assign testDao to testFile
        File testFile = new File("C:/Users/herna/Documents/EsaiEssentials/Repos/Assessment-Flooring-Mastery/"
                + "FlooringMastery/TestOrders/Orders_09012013.txt");
        new FileWriter(testFile, true);
        testDao = new FlooringMasteryDaoFileImpl(testFile);

        // call getOrderList
        ArrayList<Order> orderListTest = testDao.getOrderList();

        // Assert
        assertTrue(orderListTest.get(0).getCustomerName().equals("Ada Lovelace"));
    }

    @Test
    public void testAddOrder() throws IOException, FlooringMasteryDaoException {
        // Assign testDao to testFile
        File testFile = new File("C:/Users/herna/Documents/EsaiEssentials/Repos/Assessment-Flooring-Mastery/"
                + "FlooringMastery/TestOrders/Orders_09012013.txt");
        new FileWriter(testFile, true);
        testDao = new FlooringMasteryDaoFileImpl(testFile);

        // Create Order test
        Order testOrder = new Order("John Smith", "TX",
                "Carpet", new BigDecimal("150"), new BigDecimal("4.45"),
                new BigDecimal("2.25"), new BigDecimal("2.10"));
        testOrder.calculateMaterialCost();
        testOrder.calculateLaborCost();
        testOrder.calculateTax();
        testOrder.calculateTotal();

        // call addOrder
        boolean testAddOrderBool = testDao.addOrder(testOrder);

        // Assert
        assertTrue(testAddOrderBool, "Order should be successfully added");
    }

    @Test
    public void testEditOrder() throws IOException, FlooringMasteryDaoException {
        // Assign testDao to testFile
        File testFile = new File("C:/Users/herna/Documents/EsaiEssentials/Repos/Assessment-Flooring-Mastery/"
                + "FlooringMastery/TestOrders/Orders_09012013.txt");
        new FileWriter(testFile, true);
        testDao = new FlooringMasteryDaoFileImpl(testFile);

        // Create Order test 2
        Order testOrder2 = new Order("Henry Ford", "TX",
                "Wood", new BigDecimal("300"), new BigDecimal("4.45"),
                new BigDecimal("2.25"), new BigDecimal("2.10"));
        testOrder2.calculateMaterialCost();
        testOrder2.calculateLaborCost();
        testOrder2.calculateTax();
        testOrder2.calculateTotal();
        testOrder2.setOrderNumber(1);

        // call editOrder
        boolean testEditOrderBool = testDao.editOrder(testOrder2);

        // Assert
        assertTrue(testEditOrderBool, "testOrder2 should replace previous added testOrder");

    }

    @Test
    public void testGetOrder() throws IOException, FlooringMasteryDaoException {
        // Assign testDao to testFile
        File testFile = new File("C:/Users/herna/Documents/EsaiEssentials/Repos/Assessment-Flooring-Mastery/"
                + "FlooringMastery/TestOrders/Orders_09012013.txt");
        new FileWriter(testFile, true);
        testDao = new FlooringMasteryDaoFileImpl(testFile);

        // Create Order test 2
        Order testOrder2 = new Order("Henry Ford", "TX",
                "Wood", new BigDecimal("300"), new BigDecimal("4.45"),
                new BigDecimal("2.25"), new BigDecimal("2.10"));
        testOrder2.calculateMaterialCost();
        testOrder2.calculateLaborCost();
        testOrder2.calculateTax();
        testOrder2.calculateTotal();
        testDao.addOrder(testOrder2);

        //get current date and set ordernum to 1
        // add to array of objects
        Object[] dateAndOrderNum = new Object[2];
        LocalDate currentDate = LocalDate.now();
        int orderNum = 2;
        dateAndOrderNum[0] = currentDate;
        dateAndOrderNum[1] = orderNum;

        Order testOrder = testDao.getOrder(dateAndOrderNum);

        //Assert
        //assertNotNull(testOrder, "testOrder should not be null");
    }

    @Test
    public void testRemoveOrder() throws IOException, FlooringMasteryDaoException {
        // Assign testDao to testFile
        File testFile = new File("C:/Users/herna/Documents/EsaiEssentials/Repos/Assessment-Flooring-Mastery/"
                + "FlooringMastery/TestOrders/Orders_09012013.txt");
        new FileWriter(testFile, true);
        testDao = new FlooringMasteryDaoFileImpl(testFile);

        // Create Order test 2
        Order testOrder2 = new Order("Alex Joe", "TX",
                "Wood", new BigDecimal("300"), new BigDecimal("4.45"),
                new BigDecimal("2.25"), new BigDecimal("2.10"));
        testOrder2.calculateMaterialCost();
        testOrder2.calculateLaborCost();
        testOrder2.calculateTax();
        testOrder2.calculateTotal();
        testDao.addOrder(testOrder2);

        //get current date and set ordernum to 1
        // add to array of objects
        Object[] dateAndOrderNum = new Object[2];
        LocalDate currentDate = LocalDate.now();
        int orderNum = 2;
        dateAndOrderNum[0] = currentDate;
        dateAndOrderNum[1] = orderNum;

        boolean testRemoveSuccess = testDao.removeOrder(dateAndOrderNum);

        // Assert
        assertTrue(testRemoveSuccess, "Order 2 should have been removed");
    }

    @Test
    public void testExportAllData() throws IOException, FlooringMasteryDaoException {
        // Assign testDao to testFile
        File testFile = new File("C:/Users/herna/Documents/EsaiEssentials/Repos/Assessment-Flooring-Mastery/"
                + "FlooringMastery/TestOrders/Orders_09012013.txt");
        new FileWriter(testFile, true);
        testDao = new FlooringMasteryDaoFileImpl(testFile);

        boolean exportSuccessTest = testDao.exportAllData();

        // Assert
        assertTrue(exportSuccessTest, "All Orders should be exported to test file");
    }

    @Test
    public void testUnmarshallOrder() throws IOException, FlooringMasteryDaoException {
        // Assign testDao to testFile
        File testFile = new File("C:/Users/herna/Documents/EsaiEssentials/Repos/Assessment-Flooring-Mastery/"
                + "FlooringMastery/TestOrders/Orders_09012013.txt");
        new FileWriter(testFile, true);
        testDao = new FlooringMasteryDaoFileImpl(testFile);

        String testString = "1,Ada Lovelace,CA,25.00,Tile,249.00,3.50,4.15,871.50,1033.35,476.21,2381.06";

        Order testOrder = testDao.unmarshallOrder(testString);

        // Assert
        assertNotNull(testOrder, "testOrder should not be null with unmarshalled string");
    }

    @Test
    public void loadOrder() throws IOException, FlooringMasteryDaoException {
        // Assign testDao to testFile
        File testFile = new File("C:/Users/herna/Documents/EsaiEssentials/Repos/Assessment-Flooring-Mastery/"
                + "FlooringMastery/TestOrders/Orders_09012013.txt");
        new FileWriter(testFile, true);
        testDao = new FlooringMasteryDaoFileImpl(testFile);

        ArrayList<Order> testOrderList = testDao.loadOrder();

        // Assert
        assertNotNull(testOrderList, "Order List should have orders");
    }

    @Test
    public void loadOrderForDate() throws IOException, FlooringMasteryDaoException {
        // Assign testDao to testFile
        File testFile = new File("C:/Users/herna/Documents/EsaiEssentials/Repos/Assessment-Flooring-Mastery/"
                + "FlooringMastery/TestOrders/Orders_09012013.txt");
        new FileWriter(testFile, true);
        testDao = new FlooringMasteryDaoFileImpl(testFile);

        LocalDate currentDate = LocalDate.now();

        ArrayList<Order> testOrderList = testDao.loadOrderForDate(currentDate);

        // Assert
        assertNotNull(testOrderList, "Order List should have orders");
    }

    @Test
    public void testUnmarshallTax() throws IOException, FlooringMasteryDaoException {
        // Assign testDao to testFile
        File testFile = new File("C:/Users/herna/Documents/EsaiEssentials/Repos/Assessment-Flooring-Mastery/"
                + "FlooringMastery/TestOrders/Orders_09012013.txt");
        new FileWriter(testFile, true);
        testDao = new FlooringMasteryDaoFileImpl(testFile);

        String testString = "TX,Texas,4.45";

        Taxes testTax = testDao.unmarshallTax(testString);

        // Assert
        assertNotNull(testTax, "testTax should not be null with unmarshalled string");
    }

    @Test
    public void loadTaxes() throws IOException, FlooringMasteryDaoException {
        // Assign testDao to testFile
        File testFile = new File("C:/Users/herna/Documents/EsaiEssentials/Repos/Assessment-Flooring-Mastery/"
                + "FlooringMastery/TestOrders/Orders_09012013.txt");
        new FileWriter(testFile, true);
        testDao = new FlooringMasteryDaoFileImpl(testFile);

        ArrayList<Taxes> testTaxList = testDao.loadTaxes();

        // Assert
        assertNotNull(testTaxList, "Tax List should have taxes");
    }

    @Test
    public void testUnmarshallProduct() throws IOException, FlooringMasteryDaoException {
        // Assign testDao to testFile
        File testFile = new File("C:/Users/herna/Documents/EsaiEssentials/Repos/Assessment-Flooring-Mastery/"
                + "FlooringMastery/TestOrders/Orders_09012013.txt");
        new FileWriter(testFile, true);
        testDao = new FlooringMasteryDaoFileImpl(testFile);

        String testString = "Carpet,2.25,2.10";

        Product testProduct = testDao.unmarshallProduct(testString);

        // Assert
        assertNotNull(testProduct, "testProduct should not be null with unmarshalled string");
    }

    @Test
    public void testLoadProduct() throws IOException, FlooringMasteryDaoException {
        // Assign testDao to testFile
        File testFile = new File("C:/Users/herna/Documents/EsaiEssentials/Repos/Assessment-Flooring-Mastery/"
                + "FlooringMastery/TestOrders/Orders_09012013.txt");
        new FileWriter(testFile, true);
        testDao = new FlooringMasteryDaoFileImpl(testFile);

        ArrayList<Product> testProductList = testDao.loadProduct();

        // Assert
        assertNotNull(testProductList, "Tax List should have taxes");
    }

    @Test
    public void testMarshallOrder() throws IOException, FlooringMasteryDaoException {
        // Assign testDao to testFile
        File testFile = new File("C:/Users/herna/Documents/EsaiEssentials/Repos/Assessment-Flooring-Mastery/"
                + "FlooringMastery/TestOrders/Orders_09012013.txt");
        new FileWriter(testFile, true);
        testDao = new FlooringMasteryDaoFileImpl(testFile);

        // Create Order test
        Order testOrder = new Order("John Smith", "TX",
                "Carpet", new BigDecimal("150"), new BigDecimal("4.45"),
                new BigDecimal("2.25"), new BigDecimal("2.10"));
        testOrder.calculateMaterialCost();
        testOrder.calculateLaborCost();
        testOrder.calculateTax();
        testOrder.calculateTotal();
        testOrder.setOrderNumber(7);

        String marshallOrderStringTest = testDao.marshallOrder(testOrder);
        String expectedResult = "7,John Smith,TX,4.45,Carpet,150,2.25,2.10,337.50,315.00,29.036250,681.536250";

        // Assert
        assertEquals(marshallOrderStringTest, expectedResult, "Marshall Order doesn't match");
    }

    @Test
    public void testGetTaxList() throws IOException, FlooringMasteryDaoException {
        // Assign testDao to testFile
        File testFile = new File("C:/Users/herna/Documents/EsaiEssentials/Repos/Assessment-Flooring-Mastery/"
                + "FlooringMastery/TestOrders/Orders_09012013.txt");
        new FileWriter(testFile, true);
        testDao = new FlooringMasteryDaoFileImpl(testFile);

        ArrayList<Taxes> taxList = testDao.getTaxList();

        // Assert
        assertNotNull(taxList, "Tax List should not be empty");
    }

    @Test
    public void testGetProductList() throws IOException, FlooringMasteryDaoException {
        // Assign testDao to testFile
        File testFile = new File("C:/Users/herna/Documents/EsaiEssentials/Repos/Assessment-Flooring-Mastery/"
                + "FlooringMastery/TestOrders/Orders_09012013.txt");
        new FileWriter(testFile, true);
        testDao = new FlooringMasteryDaoFileImpl(testFile);

        ArrayList<Product> productList = testDao.getProductList();

        // Assert
        assertNotNull(productList, "Product List should not be empty");
    }

}
