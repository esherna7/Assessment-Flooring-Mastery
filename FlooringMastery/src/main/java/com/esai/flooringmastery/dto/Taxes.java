package com.esai.flooringmastery.dto;

import java.math.BigDecimal;

/**
 *
 * @author Esai
 */
public class Taxes {

    // properties
    private String state;
    private String stateName;
    private BigDecimal taxRate;
    
    // return state
    public String getState(){
        return state;
    }
    
    // set state
    public void setState(String state){
        this.state = state;
    }
    
    // return stateName
    public String getStateName(){
        return stateName;
    }
    
    // set stateName
    public void setStateName(String stateName){
        this.stateName = stateName;
    }
    
    // return taxRate
    public BigDecimal getTaxRate(){
        return taxRate;
    }
    
    // set taxRate
    public void setTaxRate(BigDecimal taxRate){
        this.taxRate = taxRate;
    }
}
