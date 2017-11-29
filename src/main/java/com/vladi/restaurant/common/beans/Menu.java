package com.vladi.restaurant.common.beans;

import java.io.Serializable;
import java.util.ArrayList;

public class Menu implements Serializable{

    private ArrayList<Collection> collections;

    public Menu(ArrayList<Collection> collections) {
        this.collections = collections;
    }

    public ArrayList<Collection> getCollections() {
        return collections;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "collections=" + collections +
                '}';
    }
}