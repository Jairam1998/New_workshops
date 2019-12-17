package com.example.official;

public class WorkshopListItem {

    private String name, orderID, status;

    public String getName() {
        return name;
    }

    public String getOrderID() {
        return orderID;
    }

    public String getStatus() {
        return status;
    }

    public static final String STATUS_SUCCESS = "SUCCESS";
    public static final String STATUS_INITIALIZED = "INITIALIZED";
    public static final String STATUS_FAILURE = "FAILURE";

    public WorkshopListItem(String name, String orderID, String status) {
        this.name = name;
        this.orderID = orderID;
        this.status = status;
    }

}
