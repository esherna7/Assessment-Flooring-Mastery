package com.esai.flooringmastery.controller;

import com.esai.flooringmastery.dao.FlooringMasteryDaoException;
import com.esai.flooringmastery.dto.Order;
import com.esai.flooringmastery.dto.Product;
import com.esai.flooringmastery.dto.Taxes;
import com.esai.flooringmastery.service.FlooringMasteryServiceLayer;
import com.esai.flooringmastery.ui.FlooringMasteryView;
import java.util.ArrayList;

/**
 *
 * @author Esai
 */
public class FlooringMasteryController {

    // handles ui display
    FlooringMasteryView view;
    // handles business logic
    FlooringMasteryServiceLayer service;

    // constructor assigning view and service
    public FlooringMasteryController(FlooringMasteryView view, FlooringMasteryServiceLayer service) {
        this.view = view;
        this.service = service;
    }

    public void run() throws FlooringMasteryDaoException {
        boolean exited = false;
        int menuSelection;

        while (!exited) {
            // get User selection
            menuSelection = getMenuSelection();
            switch (menuSelection) {
                // display orders
                case 1:
                    displayOrderList();
                    break;
                // add order
                case 2:
                    addOrder();
                    break;
                // edit order
                case 3:
                    editOrder();
                    break;
                // remove order
                case 4:
                    removeOrder();
                    break;
                // export all data
                case 5:
                    exportAllData();
                    break;
                // quit
                case 6:
                    exited = true;
                    break;
            }
        }

        view.displayExitBanner();
    }

    // calls view to print menu and return user selected option
    private int getMenuSelection() {
        return view.displayMenuAndGetSelection();
    }

    // calls service to return arraylist of order
    // send list to view to display
    private void displayOrderList() throws FlooringMasteryDaoException {
        ArrayList<Order> orderList = service.getOrderList();
        view.displayOrders(orderList);
    }

    // get taxesList and product list to send to view for validation of user input data
    // calls view asking for order info from user
    // calculate remaining Order prices and add to Orders List
    private void addOrder() throws FlooringMasteryDaoException {
        ArrayList<Taxes> taxList = service.getTaxList();
        ArrayList<Product> productList = service.getProductList();
        Order newUserOrder = view.getOrderFromUser(taxList, productList);
        // calculate remaining cost Fields
        newUserOrder.calculateMaterialCost();
        newUserOrder.calculateLaborCost();
        newUserOrder.calculateTax();
        newUserOrder.calculateTotal();
        boolean approveOrder = view.approveOrder(newUserOrder);
        if (approveOrder) {
            service.addOrder(newUserOrder);
            view.displayOrderSuccessfullyAddedBanner();
        } else {
            view.displayOrderNotApprovedBanner();
        }
    }

    // call view to get user edit selection option
    // call service to verify user input data order exists
    // verify if user wants to proceed with edit or not
    // replace order if approved
    private void editOrder() throws FlooringMasteryDaoException {
        ArrayList<Taxes> taxList = service.getTaxList();
        ArrayList<Product> productList = service.getProductList();
        Object[] userDateAndOrderNum = view.getOrderToEdit();
        Order requestedOrderToEdit = service.getOrder(userDateAndOrderNum);
        if (requestedOrderToEdit == null) {
            view.displayOrderDoesNotExistBanner();
        } else {
            Order newEditedOrder = view.editOrder(requestedOrderToEdit, taxList, productList);
            if (newEditedOrder == null) {
                view.displayOrderNotApprovedBanner();
            } else {
                service.editOrder(newEditedOrder);
            }
        }
    }

    // get date and order number from user
    // check if order exists and then remove order
    private void removeOrder() throws FlooringMasteryDaoException {
        ArrayList<Taxes> taxList = service.getTaxList();
        ArrayList<Product> productList = service.getProductList();
        Object[] userDateAndOrderNum = view.getOrderToEdit();
        Order requestedOrderToRemove = service.getOrder(userDateAndOrderNum);
        if (requestedOrderToRemove == null) {
            view.displayOrderDoesNotExistBanner();
        } else {
            boolean removalApproved = view.approveRemoveOrder(requestedOrderToRemove);
            if (removalApproved) {
                service.removeOrder(userDateAndOrderNum);
                view.displayOrderRemovedBanner();
            }
            else {
                view.displayOrderNotRemovedBanner();
            }
        }
    }

    // write out all data to files
    private void exportAllData() throws FlooringMasteryDaoException{
        boolean exportSuccess = service.exportAllData();
        if (exportSuccess){
            view.displayExportSuccessBanner();
        }
        else{
            view.displayExportNotSuccessBanner();
        }
    }

}
