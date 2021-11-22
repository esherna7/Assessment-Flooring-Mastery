package com.esai.flooringmastery.dto;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *
 * @author Esai
 */
public class Order {

    // properties
    private int orderNumber;
    private String customerName;
    private String state;
    private BigDecimal taxRate;
    private String productType;
    private BigDecimal area;
    private BigDecimal costPerSquareFoot;
    private BigDecimal laborCostPerSquareFoot;
    private BigDecimal materialCost;
    private BigDecimal laborCost;
    private BigDecimal tax;
    private BigDecimal total;

    // default constructor
    public Order() {
    }

    // constructor with user based input
    public Order(String customerName, String state, String productType, BigDecimal area,
            BigDecimal taxRate, BigDecimal costPerSquareFoot, BigDecimal laborCostPerSquareFoot) {
        this.customerName = customerName;
        this.state = state;
        this.productType = productType;
        this.area = area;
        this.taxRate = taxRate;
        this.costPerSquareFoot = costPerSquareFoot;
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }

    // return orderNumber
    public int getOrderNumber() {
        return orderNumber;
    }

    // set orderNumber
    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    // return customer name
    public String getCustomerName() {
        return customerName;
    }

    // set customer name
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    // return state
    public String getState() {
        return state;
    }

    // set state
    public void setState(String state) {
        this.state = state;
    }

    // return taxRate
    public BigDecimal getTaxRate() {
        return taxRate;
    }

    // set taxRate
    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    // return product type
    public String getProductType() {
        return productType;
    }

    // set productType
    public void setProductType(String productType) {
        this.productType = productType;
    }

    // return area
    public BigDecimal getArea() {
        return area;
    }

    // set area
    public void setArea(BigDecimal area) {
        this.area = area;
    }

    // return costPerSquareFoot
    public BigDecimal getCostPerSquareFoot() {
        return costPerSquareFoot;
    }

    // set costPerSquareFoot
    public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
        this.costPerSquareFoot = costPerSquareFoot;
    }

    // return laborCostPerSquareFoot
    public BigDecimal getLaborCostPerSquareFoot() {
        return laborCostPerSquareFoot;
    }

    // set laborCostPerSquareFoot
    public void setLaborCostPerSquareFoot(BigDecimal laborCostPerSquareFoot) {
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }

    // return materialCost
    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    // set materialCost
    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost;
    }

    // return laborCost
    public BigDecimal getLaborCost() {
        return laborCost;
    }

    // set laborCost
    public void setLaborCost(BigDecimal laborCost) {
        this.laborCost = laborCost;
    }

    // return tax
    public BigDecimal getTax() {
        return tax;
    }

    // set tax
    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    // return total
    public BigDecimal getTotal() {
        return total;
    }

    // set total
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    // return string with all info
    public String getOrderInfo() {
        String info = orderNumber + "," + customerName + "," + state + ","
                + taxRate + "," + productType + "," + area + ","
                + costPerSquareFoot + "," + laborCostPerSquareFoot + "," + materialCost
                + "," + laborCost + "," + tax + "," + total;
        return info;
    }

    // calculate material cost and set it
    // area * costPerSquareFoot
    public void calculateMaterialCost() {
        this.setMaterialCost(area.multiply(costPerSquareFoot));
    }

    // calculate laborCost and set it
    // are * laborCostPerSquareFoot
    public void calculateLaborCost() {
        this.setLaborCost(area.multiply(laborCostPerSquareFoot));
    }

    // calculate tax and set it
    // (materialCost + laborCost) * (taxRate/100)
    public void calculateTax() {
        this.setTax((materialCost.add(laborCost)).multiply(taxRate.divide(new BigDecimal("100"))));
    }

    // calculate total and set it
    // materialCost + laborCost + tax
    public void calculateTotal() {
        this.setTotal(materialCost.add(laborCost).add(tax));
    }

}
