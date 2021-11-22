package com.esai.flooringmastery.dto;

import java.math.BigDecimal;

/**
 *
 * @author Esai
 */
public class Product {

    // properties
    private String productType;
    private BigDecimal costPerSquareFoot;
    private BigDecimal laborCostPerSquareFoot;

    // return product type
    public String getProductType() {
        return productType;
    }

    // set productType
    public void setProductType(String productType) {
        this.productType = productType;
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

    // return string with all info
    public String getProductInfo() {
        String info = productType + ", costPerSquareFoot: " + costPerSquareFoot
                + ", laborCostPerSquareFoot: " + laborCostPerSquareFoot;
        return info;
    }

}
