package com.test.api.model;

public class Trade {

    private String TradeID;
    private String RestingOrderID;
    private String IncomingOrderID;
    private Double Price;
    private Double Quantity;
    private String Timestamp;

    public Trade(String tradeID, String restingOrderID, String incomingOrderID,
                 Double price, Double quantity, String timestamp) {
        TradeID = tradeID;
        RestingOrderID = restingOrderID;
        IncomingOrderID = incomingOrderID;
        Price = price;
        Quantity = quantity;
        Timestamp = timestamp;
    }

    public String getTradeID() {
        return TradeID;
    }

    public void setTradeID(String tradeID) {
        TradeID = tradeID;
    }

    public String getRestingOrderID() {
        return RestingOrderID;
    }

    public void setRestingOrderID(String restingOrderID) {
        RestingOrderID = restingOrderID;
    }

    public String getIncomingOrderID() {
        return IncomingOrderID;
    }

    public void setIncomingOrderID(String incomingOrderID) {
        IncomingOrderID = incomingOrderID;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public Double getQuantity() {
        return Quantity;
    }

    public void setQuantity(Double quantity) {
        Quantity = quantity;
    }

    public String getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(String timestamp) {
        Timestamp = timestamp;
    }
}
