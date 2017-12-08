package com.vladi.restaurant.common.beans;

import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order implements Serializable, Comparable<Order> {
    private ObjectId id;
    private Date date;
    private List<Dish> dishes;

    public Order(Date date, ArrayList<Dish> dishes) {
        this.date = date;
        this.dishes = dishes;
    }

    public Order() {
    }

    public USD totalPrice(){
        USD cost = new USD(0);
        for (Dish dish : dishes) {
            cost = cost.add(new USD(Double.valueOf(dish.getPrice())));
        }
        return cost;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", date=" + date +
                ", dishes=" + dishes +
                '}';
    }

    @Override
    public int compareTo(Order o) {
        return -date.compareTo(o.date);
    }
}