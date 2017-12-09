package com.vladi.restaurant.server;

import com.vladi.restaurant.common.beans.Order;
import com.vladi.restaurant.server.connection.Subscribable;

import java.util.*;

class OrderHandler {
    private final SortedMap<Integer, Order> newOrders;
    private final SortedMap<Integer, Order> doneOrders;
    private static int orderId = 1;

    public OrderHandler() {
        newOrders = new TreeMap<>();
        doneOrders = new TreeMap<>();
    }

    private int getOrderId(){
        if (orderId >= 1000){
            orderId = 1;
        }
        return orderId++;
    }

    public synchronized void deleteOldOrder(Integer oldOrder){
        doneOrders.remove(oldOrder);
    }

    public synchronized void orderDone(int doneOrder){
        Order order = newOrders.get(doneOrder);
        newOrders.remove(doneOrder);
        doneOrders.put(doneOrder, order);
    }

    public synchronized void putOrder(Order newOrder){
        newOrders.put(getOrderId(), newOrder);
    }

    public synchronized void updateOrders(List<Subscribable<Integer, Order>> updateSubscriberClients){
        new Thread(()->{
            for (Subscribable<Integer, Order> updateSubscriberClient : updateSubscriberClients) {
                updateSubscriberClient.update(newOrders);
                updateSubscriberClient.update(doneOrders);
            }
        }).start();
    }
}
