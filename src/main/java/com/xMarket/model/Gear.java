package com.xMarket.model;

import java.io.Serializable;

public class Gear implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Double price;
    private int quantity;
    
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
       this.price = price;
    }

    public int getQuantity() {
       return quantity;
    }

    public void setQuantity(int quantity) {
       this.quantity = quantity;
    }

}
