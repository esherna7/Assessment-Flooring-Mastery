package com.esai.flooringmastery.dao;

import com.esai.flooringmastery.dto.Order;
import com.esai.flooringmastery.dto.Product;
import com.esai.flooringmastery.dto.Taxes;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Esai
 */
public class FlooringMasteryDaoFileImpl implements FlooringMasteryDao {

    // list to be written into holding specificed objects from file
    ArrayList<Order> orderList = new ArrayList<Order>();
    ArrayList<Taxes> taxList = new ArrayList<Taxes>();
    ArrayList<Product> productList = new ArrayList<Product>();

    // holds folder where orders located
    public File ORDER_FOLDER;
    // holds text file to read products
    public final String FLOORING_MASTERY_PRODUCT;
    // holds text file to read taxes
    public final String FLOORING_MASTERY_TAXES;
    // export file
    public final String FLOORING_MASTERY_EXPORT;
    // Delimiter
    public static final String DELIMITER = ",";
    // header to add to new files
    public final String ORDER_FILE_HEADER;
    // Order number to assign orders an orderNumber
    public static int orderNumber;

    // holds text file to write to Orders
    public File flooringMasteryOrderTextFile;

    // test constructor
    public FlooringMasteryDaoFileImpl(File testFile) throws FlooringMasteryDaoException {
        flooringMasteryOrderTextFile = testFile;
        ORDER_FOLDER = new File("C:/Users/herna/Documents/EsaiEssentials/Repos/Assessment-Flooring-Mastery/FlooringMastery/TestOrders");
        FLOORING_MASTERY_PRODUCT = "Products.txt";
        FLOORING_MASTERY_TAXES = "Taxes.txt";
        ORDER_FILE_HEADER = "OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total";
        FLOORING_MASTERY_EXPORT = "ExportedDataTest.txt";
        loadOrder();
        orderNumber = orderList.size() + 1;
    }

    // constructor assign FLOORING_MASTERY text file
    public FlooringMasteryDaoFileImpl() throws FlooringMasteryDaoException {
        ORDER_FOLDER = new File("C:/Users/herna/Documents/EsaiEssentials/Repos/Assessment-Flooring-Mastery/FlooringMastery/Orders");
        FLOORING_MASTERY_PRODUCT = "Products.txt";
        FLOORING_MASTERY_TAXES = "Taxes.txt";
        ORDER_FILE_HEADER = "OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total";
        FLOORING_MASTERY_EXPORT = "ExportedData.txt";
        // assign orderNumber based on current total orders
        loadOrder();
        orderNumber = orderList.size() + 1;
    }

    // return arraylist holding all orders
    @Override
    public ArrayList<Order> getOrderList() throws FlooringMasteryDaoException {
        loadOrder();
        return orderList;
    }

    // recieve an order user wants to add
    // add order to currentDate text file
    @Override
    public boolean addOrder(Order userOrder) throws FlooringMasteryDaoException {
        LocalDate currentDate = LocalDate.now();
        loadOrderForDate(currentDate);
        userOrder.setOrderNumber(orderNumber++);
        orderList.add(userOrder);
        writeOrder();
        return true;
    }

    // recieve user inputted order to replace old order based on orderNumber
    @Override
    public boolean editOrder(Order newEditedOrder) throws FlooringMasteryDaoException {
        // go through orderList to replaced original order with edited order
        for (int currentOrder = 0; currentOrder < orderList.size(); currentOrder++) {
            if (orderList.get(currentOrder).getOrderNumber() == newEditedOrder.getOrderNumber()) {
                orderList.set(currentOrder, newEditedOrder);
                return true;
            }
        }
        writeOrder();
        return false;
    }

    // recieve date and order num to verify order in requested date exists
    // return that order if it does exist
    @Override
    public Order getOrder(Object[] dateAndOrderNum) throws FlooringMasteryDaoException {
        // set date and order number to appropriate value types
        LocalDate requestedDate = LocalDate.parse(dateAndOrderNum[0].toString());
        int orderNum = Integer.parseInt(dateAndOrderNum[1].toString());

        // set orderList to contain orders from requested date
        loadOrderForDate(requestedDate);
        // if no orders in orderList, then requested date does not exist
        if (orderList.size() == 0) {
            return null;
        } else {
            // go through orderList to see if requested Order exists 
            // based on order number input from user
            for (Order currentOrder : orderList) {
                if (currentOrder.getOrderNumber() == orderNum) {
                    return currentOrder;
                }
            }

        }
        return null;
    }

    // recieve date and order num, call getOrder to verify order exists
    // remove order if it exists
    @Override
    public boolean removeOrder(Object[] dateAndOrderNum) throws FlooringMasteryDaoException {
        Order orderToRemove = getOrder(dateAndOrderNum);
        // go through orderList to remove order
        for (int x = 0; x < orderList.size(); x++) {
            if (orderList.get(x).getOrderNumber() == orderToRemove.getOrderNumber()) {
                orderList.remove(x);
                break;
            }
        }
        writeOrder();
        return true;
    }

    // write our all orders to Export file
    @Override
    public boolean exportAllData() throws FlooringMasteryDaoException {
        loadOrder();
        PrintWriter out;

        // try catch block throws error if file isnt found
        try {
            out = new PrintWriter(new FileWriter(FLOORING_MASTERY_EXPORT));
        } catch (IOException e) {
            throw new FlooringMasteryDaoException("Could not export Order data.", e);
        }

        String orderText;
        // transform Order info to text
        for (Order currentOrder : orderList) {
            orderText = marshallOrder(currentOrder);
            // write text to file
            out.println(orderText);
            out.flush();
        }
        // close PrintWriter
        out.close();
        return true;
    }

    // translate object in file to a Order item object
    // splits on delimiter ,
    @Override
    public Order unmarshallOrder(String orderFromFile) {
        // split order info from file to arraylist via , delimiter
        String[] orderItemTokens = orderFromFile.split(DELIMITER);

        // create new order item with info from orderItemTokens
        Order newOrderFromFile = new Order();
        newOrderFromFile.setOrderNumber(Integer.parseInt(orderItemTokens[0]));
        newOrderFromFile.setCustomerName(orderItemTokens[1]);
        newOrderFromFile.setState(orderItemTokens[2]);
        newOrderFromFile.setTaxRate(new BigDecimal(orderItemTokens[3]));
        newOrderFromFile.setProductType(orderItemTokens[4]);
        newOrderFromFile.setArea(new BigDecimal(orderItemTokens[5]));
        newOrderFromFile.setCostPerSquareFoot(new BigDecimal(orderItemTokens[6]));
        newOrderFromFile.setLaborCostPerSquareFoot(new BigDecimal(orderItemTokens[7]));
        newOrderFromFile.setMaterialCost(new BigDecimal(orderItemTokens[8]));
        newOrderFromFile.setLaborCost(new BigDecimal(orderItemTokens[9]));
        newOrderFromFile.setTax(new BigDecimal(orderItemTokens[10]));
        newOrderFromFile.setTotal(new BigDecimal(orderItemTokens[11]));

        // return created Order
        return newOrderFromFile;
    }

    // transform all orders from file to memory
    @Override
    public ArrayList<Order> loadOrder() throws FlooringMasteryDaoException {
        orderList = new ArrayList<Order>();
        Scanner scanner = null;

        // go through Order Folder and read files one by one
        for (File currentFile : ORDER_FOLDER.listFiles()) {
            flooringMasteryOrderTextFile = currentFile;

            try {
                scanner = new Scanner(new BufferedReader(new FileReader(flooringMasteryOrderTextFile)));
            } catch (FileNotFoundException e) {
                throw new FlooringMasteryDaoException("Could not load Orders into memory", e);
            }
            String currentLine;
            Order currentOrder;

            while (scanner.hasNextLine()) {
                currentLine = scanner.nextLine();
                currentOrder = unmarshallOrder(currentLine);
                orderList.add(currentOrder);
            }
        }
        //close Scanner
        scanner.close();
        return orderList;
    }

    // transform orders from specified file to memory
    @Override
    public ArrayList<Order> loadOrderForDate(LocalDate requestedDate) throws FlooringMasteryDaoException {
        orderList = new ArrayList<Order>();
        Scanner scanner = null;
        // make sure month and day values are double digits
        String monthValue = "" + requestedDate.getMonthValue();
        if (monthValue.length() == 1) {
            monthValue = "0" + monthValue;
        }
        String dayValue = "" + requestedDate.getDayOfMonth();
        if (dayValue.length() == 1) {
            dayValue = "0" + dayValue;
        }

        String requestedDateFormatted = "" + monthValue + dayValue + requestedDate.getYear();

        // search for file based on requested date and load orders into arraylist orderList
        File requestedDateTextFile = new File(ORDER_FOLDER + "/Orders_" + requestedDateFormatted + ".txt");
        if (requestedDateTextFile.exists()) {
            try {
                scanner = new Scanner(new BufferedReader(new FileReader(requestedDateTextFile)));
            } catch (FileNotFoundException e) {
                throw new FlooringMasteryDaoException("Could not load Orders into memory", e);
            }
            String currentLine;
            Order currentOrder;

            while (scanner.hasNextLine()) {
                currentLine = scanner.nextLine();
                currentOrder = unmarshallOrder(currentLine);
                orderList.add(currentOrder);
            }
        }
        //close Scanner
        //scanner.close();
        return orderList;
    }

    // translate object in file to a Taxes item object
    // splits on delimiter ,
    @Override
    public Taxes unmarshallTax(String taxesFromFile) {
        // split tax info from file to arraylist via , delimiter
        String[] taxesItemTokens = taxesFromFile.split(DELIMITER);

        // create new taxes item with info from taxesItemTokens
        Taxes newTaxFromFile = new Taxes();
        newTaxFromFile.setState(taxesItemTokens[0]);
        newTaxFromFile.setStateName(taxesItemTokens[1]);
        newTaxFromFile.setTaxRate(new BigDecimal(taxesItemTokens[2]));

        // return created Taxes
        return newTaxFromFile;
    }

    // transform taxes from file to memory
    @Override
    public ArrayList<Taxes> loadTaxes() throws FlooringMasteryDaoException {
        taxList = new ArrayList<Taxes>();
        Scanner scanner;

        try {
            scanner = new Scanner(new BufferedReader(new FileReader(FLOORING_MASTERY_TAXES)));
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryDaoException("Could not load Taxes into memory", e);
        }
        String currentLine;
        Taxes currentTax;

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTax = unmarshallTax(currentLine);
            taxList.add(currentTax);
        }
        //close Scanner
        scanner.close();
        return taxList;
    }

    // translate object in file to a Products item object
    // splits on delimiter ,
    @Override
    public Product unmarshallProduct(String productFromFile) {
        // split product info from file to arraylist via , delimiter
        String[] productItemTokens = productFromFile.split(DELIMITER);

        // create new product item with info from productItemTokens
        Product newProductFromFile = new Product();
        newProductFromFile.setProductType(productItemTokens[0]);
        newProductFromFile.setCostPerSquareFoot(new BigDecimal(productItemTokens[1]));
        newProductFromFile.setLaborCostPerSquareFoot(new BigDecimal(productItemTokens[2]));

        // return created Product
        return newProductFromFile;
    }

    // transform product from file to memory
    @Override
    public ArrayList<Product> loadProduct() throws FlooringMasteryDaoException {
        productList = new ArrayList<Product>();
        Scanner scanner;

        try {
            scanner = new Scanner(new BufferedReader(new FileReader(FLOORING_MASTERY_PRODUCT)));
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryDaoException("Could not load Product into memory", e);
        }
        String currentLine;
        Product currentProduct;

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentProduct = unmarshallProduct(currentLine);
            productList.add(currentProduct);
        }
        //close Scanner
        scanner.close();
        return productList;
    }

    // transform order information in memory to line of text to be written
    @Override
    public String marshallOrder(Order orderFromObject) {
        String orderText = orderFromObject.getOrderNumber() + DELIMITER;
        orderText += orderFromObject.getCustomerName() + DELIMITER;
        orderText += orderFromObject.getState() + DELIMITER;
        orderText += orderFromObject.getTaxRate() + DELIMITER;
        orderText += orderFromObject.getProductType() + DELIMITER;
        orderText += orderFromObject.getArea() + DELIMITER;
        orderText += orderFromObject.getCostPerSquareFoot() + DELIMITER;
        orderText += orderFromObject.getLaborCostPerSquareFoot() + DELIMITER;
        orderText += orderFromObject.getMaterialCost() + DELIMITER;
        orderText += orderFromObject.getLaborCost() + DELIMITER;
        orderText += orderFromObject.getTax() + DELIMITER;
        orderText += orderFromObject.getTotal();

        return orderText;
    }

// write current arraylist orderList FLOORING_MASTERY_ORDER file
    @Override
    public void writeOrder() throws FlooringMasteryDaoException {
        PrintWriter out;
        flooringMasteryOrderTextFile = getCurrentDateFile();
        // try catch block throws error if file isnt found
        try {
            out = new PrintWriter(new FileWriter(flooringMasteryOrderTextFile));
        } catch (IOException e) {
            throw new FlooringMasteryDaoException("Could not save Order data.", e);
        }

        String orderText;
        // transform Order info to text
        for (Order currentOrder : orderList) {
            orderText = marshallOrder(currentOrder);
            // write text to file
            out.println(orderText);
            out.flush();
        }
        // close PrintWriter
        out.close();
    }

    // checks to see if current date file exists already to add orders to and return it to writeOrder()
    // if not create new file and return file to writeOrder()
    @Override
    public File getCurrentDateFile() throws FlooringMasteryDaoException {
        // get current and reformat to MMDDYYYY
        LocalDate currentDate = LocalDate.now();
        // make sure month and day values are double digits
        String monthValue = "" + currentDate.getMonthValue();
        if (monthValue.length() == 1) {
            monthValue = "0" + monthValue;
        }
        String dayValue = "" + currentDate.getDayOfMonth();
        if (dayValue.length() == 1) {
            dayValue = "0" + dayValue;
        }

        String currentDateFormatted = "" + monthValue + dayValue + currentDate.getYear();
        // create file based on currentDate
        File newOrdersTextFile = new File(ORDER_FOLDER + "/Orders_" + currentDateFormatted + ".txt");

        // if file doesn't exist create a new file and return it
        if (!newOrdersTextFile.exists()) {
            try {
                newOrdersTextFile.createNewFile();
                return newOrdersTextFile;
            } catch (IOException ex) {

            }
        }

        return newOrdersTextFile;
    }

    // return arraylist containing all Tax file info
    @Override
    public ArrayList<Taxes> getTaxList() throws FlooringMasteryDaoException {
        loadTaxes();
        return taxList;
    }

    // return arraylist containing all product file info
    @Override
    public ArrayList<Product> getProductList() throws FlooringMasteryDaoException {
        loadProduct();
        return productList;
    }

}
