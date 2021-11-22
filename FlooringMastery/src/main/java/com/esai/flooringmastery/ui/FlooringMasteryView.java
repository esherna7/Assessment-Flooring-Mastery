package com.esai.flooringmastery.ui;

import com.esai.flooringmastery.dto.Order;
import com.esai.flooringmastery.dto.Product;
import com.esai.flooringmastery.dto.Taxes;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 *
 * @author Esai
 */
public class FlooringMasteryView {

    // handles print messages
    UserIO io;

    // constructor assigning UserIO
    public FlooringMasteryView(UserIO io) {
        this.io = io;
    }

    // display menu, return user selection
    public int displayMenuAndGetSelection() {
        //dipslay menu
        io.print("********************************");
        io.print("* <<Flooring Program>>");
        io.print("*1. Display Orders");
        io.print("*2. Add an Order");
        io.print("*3. Edit an Order");
        io.print("*4. Remove an Order");
        io.print("*5. Export All Data");
        io.print("*6. Quit");
        io.print("*");
        io.print("********************************");

        // read user input
        int selection = io.readInt("Please select an option above");

        // verify user input is valid selection
        // if not ask for input again
        while (selection > 6 || selection < 1) {
            io.print("Invalid Selection");
            selection = io.readInt("Please select an option above");
        }
        return selection;
    }

    // display orders
    public void displayOrders(ArrayList<Order> orderList) {
        io.print("All Orders");
        io.print("***************");
        // lambda printing order info
        orderList.forEach((order) -> {
            io.print(order.getOrderInfo());
        });
    }

    // display exit Banner
    public void displayExitBanner() {
        io.print("Thank you! Goodbye!");
    }

    // display Order not approved banner
    public void displayOrderNotApprovedBanner() {
        io.print("Order was not approved");
    }

    // display Order added banner
    public void displayOrderSuccessfullyAddedBanner() {
        io.print("Order was successfully added");
    }

    // display Order does not exist banner
    public void displayOrderDoesNotExistBanner() {
        io.print("Order requested does not exist");
    }

    // display Order not removed banner
    public void displayOrderNotRemovedBanner() {
        io.print("Order was not removed");
    }

    // display Order not removed banner
    public void displayOrderRemovedBanner() {
        io.print("Order was removed");
    }

    // display export success banner
    public void displayExportSuccessBanner() {
        io.print("Orders were successfully exported");
    }

    // display export failure banner
    public void displayExportNotSuccessBanner() {
        io.print("Export was not successful");
    }

    // get Order information from user to add to Orders
    public Order getOrderFromUser(ArrayList<Taxes> taxList, ArrayList<Product> productList) {
        // get date from user
        LocalDate dateStamp = getDateFromUserToAdd();
        // get customer name from user
        String userInputCustomerName = io.readString("Please enter the customer name.");

        // get state name from user and tax rate based on state
        Object[] stateAndTax = getStateFromUser(taxList);
        String userInputState = "" + stateAndTax[0];
        BigDecimal taxRate = new BigDecimal("" + stateAndTax[1]);

        // get product type from user and costs based on product type
        Object[] productTypeAndCost = getProductTypeFromUser(productList);
        String userInputProductType = "" + productTypeAndCost[0];
        BigDecimal costPerSquareFoot = new BigDecimal("" + productTypeAndCost[1]);
        BigDecimal laborCostPerSquareFoot = new BigDecimal("" + productTypeAndCost[2]);

        // get user area
        BigDecimal userInputArea = getAreaFromUser();

        // Create Order based on user input and associated fields
        Order newOrder = new Order(userInputCustomerName, userInputState, userInputProductType, userInputArea,
                taxRate, costPerSquareFoot, laborCostPerSquareFoot);
        return newOrder;
    }

    // ask user whether to approve the new order or not
    public boolean approveOrder(Order newUserOrder) {
        boolean validSelection = false;
        boolean userApproval = false;
        io.print(newUserOrder.getOrderInfo());

        // check if user entered valid selection
        while (!validSelection) {
            String userInput = io.readString("Do you want to approve your order above? Yes/No");
            if (userInput.equals("Yes")) {
                userApproval = true;
                validSelection = true;
            } else if (userInput.equals("No")) {
                userApproval = false;
                validSelection = true;
            } else {
                io.print("Not a valid Selection");
            }
        }
        return userApproval;
    }

    // get user inputted date and order number to see if exists in orders
    // return array containing user input date and order number
    public Object[] getOrderToEdit() {
        // get date from user
        LocalDate dateStamp = getDateFromUserToEdit();
        // get order number from user
        int orderNumber = getOrderNumberFromUser();
        // create array to hold user date and order number input
        Object[] dateAndOrderNum = new Object[2];
        dateAndOrderNum[0] = dateStamp;
        dateAndOrderNum[1] = orderNumber;

        return dateAndOrderNum;
    }

    // edit order user requested to edit
    public Order editOrder(Order orderToEdit, ArrayList<Taxes> taxList, ArrayList<Product> productList) {
        // edit name
        String newName = io.readString("Enter Customer Name (" + orderToEdit.getCustomerName() + ")");
        if (!newName.equals("")) {
            orderToEdit.setCustomerName(newName);
        }
        // edit state and possibly new tax
        Object[] newStateAndTax = editState(orderToEdit, taxList);

        // edit productType and possible costs
        Object[] newProductTypeAndCost = editProductType(productList, orderToEdit);

        BigDecimal newArea = editArea(orderToEdit);

        // display new edited data
        io.print("New Edited Order:");
        io.print(orderToEdit.getCustomerName() + "," + orderToEdit.getState() + "," + orderToEdit.getProductType() + "," + orderToEdit.getArea());

        boolean validSelection = false;
        boolean userApproval = false;
        // check if user entered valid selection
        while (!validSelection) {
            String userInput = io.readString("Do you want to approve your order above? Yes/No");
            switch (userInput) {
                case "Yes":
                    orderToEdit.setState(newStateAndTax[0].toString());
                    orderToEdit.setTax(new BigDecimal(newStateAndTax[1].toString()));
                    orderToEdit.setProductType(newProductTypeAndCost[0].toString());
                    orderToEdit.setCostPerSquareFoot(new BigDecimal(newProductTypeAndCost[1].toString()));
                    orderToEdit.setLaborCostPerSquareFoot(new BigDecimal(newProductTypeAndCost[2].toString()));
                    orderToEdit.setArea(newArea);
                    return orderToEdit;
                case "No":
                    return null;
                default:
                    io.print("Not a valid Selection");
                    break;
            }
        }

        return null;
    }

    // ask user if they want to approve removal of order
    public boolean approveRemoveOrder(Order orderToRemove) {
        io.print(orderToRemove.getOrderInfo());
        boolean validSelection = false;

        // check if user entered valid selection
        while (!validSelection) {
            String userInput = io.readString("Do you want to approve your order above? Yes/No");
            switch (userInput) {
                case "Yes":
                    return true;
                case "No":
                    return false;
                default:
                    io.print("Not a valid Selection");
                    break;
            }
        }
        return false;
    }

    // edit area and return new area from user
    private BigDecimal editArea(Order orderToEdit) {
        BigDecimal userInputArea = new BigDecimal("0");
        boolean validArea = false;

        while (!validArea) {
            try {
                userInputArea = new BigDecimal(io.readString("Please enter the area you require. "
                        + "Minimum value of 100 sq ft (" + orderToEdit.getArea() + ")"));
                userInputArea.setScale(0, RoundingMode.HALF_UP);
                // check if input area is valid
                if (userInputArea.compareTo(new BigDecimal("100")) >= 0) {
                    validArea = true;
                } else {
                    io.print("Not a valid area input");
                }
            } catch (NumberFormatException e) {
                io.print("Not a valid area input");
            }
        }

        return userInputArea;
    }

    // return productType, costPerSquareFoot and laborCostPerSquareFoot
    // based on productType inputted to edit
    private Object[] editProductType(ArrayList<Product> productList, Order orderToEdit) {
        boolean validProduct = false;
        String userInputProductType = "";
        BigDecimal costPerSquareFoot = new BigDecimal("0");
        BigDecimal laborCostPerSquareFoot = new BigDecimal("0");
        Object[] productTypeAndCost = new Object[3];

        // display ProductList products for user to select
        io.print("Current Product Options:");
        for (Product currentProduct : productList) {
            io.print(currentProduct.getProductInfo());
        }

        // verify user inputted product is valid product in Products list
        while (!validProduct) {
            userInputProductType = io.readString("Please enter the product type "
                    + "(" + orderToEdit.getProductType() + ")");

            // if not input keep producttype and costs the same
            if (userInputProductType.equals("")) {
                userInputProductType = orderToEdit.getProductType();
            }

            // check through taxList to see if state exists
            for (Product currentProduct : productList) {
                if (currentProduct.getProductType().equals(userInputProductType)) {
                    costPerSquareFoot = currentProduct.getCostPerSquareFoot();
                    laborCostPerSquareFoot = currentProduct.getLaborCostPerSquareFoot();
                    validProduct = true;
                    break;
                }
            }
            // print product type not available
            if (!validProduct) {
                io.print("Inputted product type not available");
            }
        }

        // push userInputProductType and costs to array
        productTypeAndCost[0] = userInputProductType;
        productTypeAndCost[1] = costPerSquareFoot;
        productTypeAndCost[2] = laborCostPerSquareFoot;

        return productTypeAndCost;
    }

    // return string of newly edited State
    private Object[] editState(Order orderToEdit, ArrayList<Taxes> taxList) {
        boolean validState = false;
        String userInputState = "";
        BigDecimal taxRate = new BigDecimal("0");
        // create array of object to hold state name and tax rate for that state
        Object[] stateAndTax = new Object[2];

        // verify user inputted state is in tax files
        while (!validState) {
            userInputState = io.readString("Please enter the state postal abbreviation ("
                    + orderToEdit.getState() + ")");

            // if no input keep state the same
            if (userInputState.equals("")) {
                userInputState = orderToEdit.getState();
            }

            // check through taxList to see if state exists
            for (Taxes currentTax : taxList) {
                if (currentTax.getState().equals(userInputState)) {
                    taxRate = currentTax.getTaxRate();
                    validState = true;
                    break;
                }
            }
            // print state not in tax file if not found
            if (!validState) {
                io.print("Inputted State abbreviation does not exist in tax file");
            }
        }

        // push user input state and tax for that state into array
        stateAndTax[0] = userInputState;
        stateAndTax[1] = taxRate;

        return stateAndTax;
    }

    // returns user inputted order number
    private int getOrderNumberFromUser() {
        boolean validOrderNum = false;
        int userInputOrderNumber = 0;

        // verify inputted order number is valid
        while (!validOrderNum) {
            try {
                userInputOrderNumber = io.readInt("Please enter the Order Number");
                validOrderNum = true;
            } catch (NumberFormatException e) {
                io.print("Not a valid input");
            }
        }
        return userInputOrderNumber;
    }

    // verifies if date inputted from user is valid
    // returns LocalDate
    private LocalDate getDateFromUserToAdd() {
        boolean dateValid = false;
        LocalDate dateStamp = LocalDate.now();

        // verify inputted date is valid date
        while (!dateValid) {
            // get date input from user
            String userInputDate = io.readString("Please enter a date for the order in the form YYYY-MM-DD");
            try {
                dateStamp = LocalDate.parse(userInputDate);
                dateValid = true;
            } catch (DateTimeParseException e) {
                io.print("Invalid date entered");
            }

            if (LocalDate.now().isAfter(dateStamp)) {
                io.print("Invalid date entered. Date must be after today");
                dateValid = false;
            }
        }
        return dateStamp;
    }

    // verifies if date inputted from user is valid
    // returns LocalDate
    private LocalDate getDateFromUserToEdit() {
        boolean dateValid = false;
        LocalDate dateStamp = LocalDate.now();

        // verify inputted date is valid date
        while (!dateValid) {
            // get date input from user
            String userInputDate = io.readString("Please enter a date for the order in the form YYYY-MM-DD");
            try {
                dateStamp = LocalDate.parse(userInputDate);
                dateValid = true;
            } catch (DateTimeParseException e) {
                io.print("Invalid date entered");
            }
        }
        return dateStamp;
    }

    // verifies if state inputted from user is in tax files
    // return user inputted state
    private Object[] getStateFromUser(ArrayList<Taxes> taxList) {
        boolean validState = false;
        String userInputState = "";
        BigDecimal taxRate = new BigDecimal("0");
        // create array of object to hold state name and tax rate for that state
        Object[] stateAndTax = new Object[2];

        // verify user inputted state is in tax files
        while (!validState) {
            userInputState = io.readString("Please enter the state postal abbreviation");

            // check through taxList to see if state exists
            for (Taxes currentTax : taxList) {
                if (currentTax.getState().equals(userInputState)) {
                    taxRate = currentTax.getTaxRate();
                    validState = true;
                    break;
                }
            }
            // print state not in tax file if not found
            if (!validState) {
                io.print("Inputted State abbreviation does not exist in tax file");
            }
        }

        // push user input state and tax for that state into array
        stateAndTax[0] = userInputState;
        stateAndTax[1] = taxRate;

        return stateAndTax;
    }

    // return productType, costPerSquareFoot and laborCostPerSquareFoot
    // based on productType inputted
    private Object[] getProductTypeFromUser(ArrayList<Product> productList) {
        boolean validProduct = false;
        String userInputProductType = "";
        BigDecimal costPerSquareFoot = new BigDecimal("0");
        BigDecimal laborCostPerSquareFoot = new BigDecimal("0");
        Object[] productTypeAndCost = new Object[3];

        // display ProductList products for user to select
        io.print("Current Product Options:");
        for (Product currentProduct : productList) {
            io.print(currentProduct.getProductInfo());
        }

        // verify user inputted product is valid product in Products list
        while (!validProduct) {
            userInputProductType = io.readString("Please enter the product type");
            // check through taxList to see if state exists
            for (Product currentProduct : productList) {
                if (currentProduct.getProductType().equals(userInputProductType)) {
                    costPerSquareFoot = currentProduct.getCostPerSquareFoot();
                    laborCostPerSquareFoot = currentProduct.getLaborCostPerSquareFoot();
                    validProduct = true;
                    break;
                }
            }
            // print product type not available
            if (!validProduct) {
                io.print("Inputted product type not available");
            }
        }

        // push userInputProductType and costs to array
        productTypeAndCost[0] = userInputProductType;
        productTypeAndCost[1] = costPerSquareFoot;
        productTypeAndCost[2] = laborCostPerSquareFoot;

        return productTypeAndCost;
    }

    // return inputted user area
    private BigDecimal getAreaFromUser() {
        BigDecimal userInputArea = new BigDecimal("0");
        boolean validArea = false;

        // verify inputted area is valid 
        while (!validArea) {
            try {
                userInputArea = new BigDecimal(io.readString("Please enter the area you require. Minimum value of 100 sq ft"));
                userInputArea.setScale(0, RoundingMode.HALF_UP);
                // check if input area is valid
                if (userInputArea.compareTo(new BigDecimal("100")) >= 0) {
                    validArea = true;
                } else {
                    io.print("Not a valid area input");
                }
            } catch (NumberFormatException e) {
                io.print("Not a valid area input");
            }
        }

        return userInputArea;
    }

}
