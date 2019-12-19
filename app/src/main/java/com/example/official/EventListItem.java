package com.example.official;

public class EventListItem {

    private String name, orderID, date;

    public String getName() {
        return name;
    }
    public String getOrderID() {
        return orderID;
    }
    public String getDate() { return date; }

    public EventListItem(String name, String orderID, String date) {
        this.name = name;
        this.orderID = orderID;
        this.date = date;
    }

}
