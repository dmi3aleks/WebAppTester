package com.test.api.model;

public class Order {

    private String OrderID;
    private String InstrCode;
    private String Side;
    private Double Quantity;
    private Double Price;
    private String Notes;
    private String Status;

    public Order(String OrderID, String InstrCode, String Side,
                 Double Quantity, Double Price, String Notes, String Status) {
        this.OrderID = OrderID;
        this.InstrCode = InstrCode;
        this.Side = Side;
        this.Quantity = Quantity;
        this.Price = Price;
        this.Notes = Notes;
        this.Status = Status;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
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

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        this.Status = status;
    }
}
