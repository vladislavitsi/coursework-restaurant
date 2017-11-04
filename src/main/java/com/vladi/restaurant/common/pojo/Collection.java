package com.vladi.restaurant.common.pojo;

import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.ArrayList;

public class Collection implements Serializable{
    private ObjectId id;
    private String name;
    private String desc;
    private ArrayList<Dish> dishes;

    public Collection() {

    }

    public Collection(String name, String desc) {
        this.name = name;
        this.desc = desc;
        this.dishes = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public ArrayList<Dish> getDishes() {
        return dishes;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setDishes(ArrayList<Dish> dishes) {
        this.dishes = dishes;
    }

    @Override
    public String toString() {
        return "Collection{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", dishes=" + dishes +
                '}';
    }
}
