package com.example.official;

public class EventListItem {

    private String name, orderID;

    public String getName() {
        return name;
    }
    public String getOrderID() {
        return orderID;
    }

    public EventListItem(String name, String orderID) {
        this.name = name;
        this.orderID = orderID;
    }

}
