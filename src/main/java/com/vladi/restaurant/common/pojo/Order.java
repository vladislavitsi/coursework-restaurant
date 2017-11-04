package com.vladi.restaurant.common.pojo;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;

public class Order {
    private ObjectId id;
    private Date date;
    private ArrayList<Dish> dishes;


}