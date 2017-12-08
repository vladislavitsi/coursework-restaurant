package com.vladi.restaurant.common.beans;

import java.io.Serializable;

public class Dish implements Serializable{
    private String name;
    private String desc;
    private String price;

    public Dish() {
    }

    public Dish(String name, String desc, String price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dish)) return false;

        Dish dish = (Dish) o;

        return (name != null ? name.equals(dish.name) : dish.name == null)
                && (desc != null ? desc.equals(dish.desc) : dish.desc == null)
                && (price != null ? price.equals(dish.price) : dish.price == null);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
