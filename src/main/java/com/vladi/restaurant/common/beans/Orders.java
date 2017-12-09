package com.vladi.restaurant.common.beans;

import java.util.List;

public class Orders {
    private List<Integer> list;

    public Orders(List<Integer> list) {
        this.list = list;
    }

    public List<Integer> getList() {
        return list;
    }
}
