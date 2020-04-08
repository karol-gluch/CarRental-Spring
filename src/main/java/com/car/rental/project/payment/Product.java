package com.car.rental.project.payment;

public class Product {
    private String name;
    private String unitPrice;
    private String quantity;

    public Product(String name, String unitPrice) {
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = "1";
    }

    public Product() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
