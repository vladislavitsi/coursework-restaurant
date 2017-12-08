package com.vladi.restaurant.common.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class History implements Serializable{
    private List<Order> orders;

    public History(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public History() {
    }

    public List<Order> getOrders() {
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
