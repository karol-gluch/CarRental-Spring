package com.car.rental.project.payment;

import java.util.List;

public class Order {
    private String continueUrl;
    private String extOrderId;
    private String customerIp;
    private String merchantPosId;
    private String description;
    private String currencyCode;
    private String totalAmount;
    private List<Product> products;

    public Order(String continueUrl, String customoerIp, String merchantPosId, String description, String currencyCode, String totalAmount, List<Product> products) {
        this.continueUrl = continueUrl;
        this.customerIp = customoerIp;
        this.merchantPosId = merchantPosId;
        this.description = description;
        this.currencyCode = currencyCode;
        this.totalAmount = totalAmount;
        this.products = products;
    }

    public Order(String extOrderId,String description, String totalAmount, List<Product> products) {
        this.continueUrl = "http://localhost:9090/callback/"+extOrderId;
        this.extOrderId = extOrderId;
        this.customerIp = "127.0.0.1";
        this.merchantPosId = "383389";
        this.currencyCode = "PLN";
        this.description = description;
        this.totalAmount = Integer.toString(Integer.valueOf(totalAmount)*100);
        this.products = products;
    }

    public Order() {
    }

    public String getcontinueUrl() {
        return continueUrl;
    }

    public void setcontinueUrl(String notifyUrl) {
        this.continueUrl = notifyUrl;
    }

    public String getCustomerIp() {
        return customerIp;
    }

    public void setCustomerIp(String customerIp) {
        this.customerIp = customerIp;
    }

    public String getMerchantPosId() {
        return merchantPosId;
    }

    public void setMerchantPosId(String merchantPosId) {
        this.merchantPosId = merchantPosId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}