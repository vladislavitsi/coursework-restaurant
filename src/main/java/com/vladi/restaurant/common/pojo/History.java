package com.vladi.restaurant.common.pojo;

import java.io.Serializable;
import java.util.ArrayList;

public class History implements Serializable{
    private ArrayList<Order> orders;

    public History(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public History() {
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "History{" +
                "orders=" + orders +
                '}';
    }
}
