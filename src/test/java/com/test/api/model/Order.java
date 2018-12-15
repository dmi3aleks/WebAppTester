package com.test.api.model;

public class Order {

    private String InstrCode;
    private String Side;
    private Double Quantity;
    private Double Price;
    private String Notes;

    public Order(String InstrCode, String Side, Double Quantity, Double Price, String Notes) {
        this.InstrCode = InstrCode;
        this.Side = Side;
        this.Quantity = Quantity;
        this.Price = Price;
        this.Notes = Notes;
    }

    public String getInstCode() {
        return InstrCode;
    }

    public void setInstCode(String InstrCode) {
        this.InstrCode = InstrCode;
    }

    public String getSide() {
        return Side;
    }

    public void setSide(String Side) {
        this.Side = Side;
    }

    public Double getQuantity() {
        return Quantity;
    }

    public void setQuantity(Double Quantity) {
        this.Quantity = Quantity;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double Price) {
        this.Price = Price;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String Notes) {
        this.Notes = Notes;
    }
}
