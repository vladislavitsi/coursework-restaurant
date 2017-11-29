package com.vladi.restaurant.server;

import com.vladi.restaurant.common.beans.Order;
import com.vladi.restaurant.server.connection.Subscribable;

import java.util.LinkedList;
import java.util.List;

public class OrderHandler {
    private List<Order> newOrders;
    private List<Order> doneOrders;

    public OrderHandler() {
        newOrders = new LinkedList<>();
        doneOrders = new LinkedList<>();
    }

    public synchronized void deleteOldOrder(Order oldOrder){
        doneOrders.remove(oldOrder);
    }

    public synchronized void orderDone(Order doneOrder){
        newOrders.remove(doneOrder);
        doneOrders.add(doneOrder);
    }

    public synchronized void putOrder(Order newOrder){
        newOrders.add(newOrder);
    }

    public synchronized void updateOrders(List<Subscribable<Order>> updateSubscriberClients){
        new Thread(()->{
            for (Subscribable<Order> updateSubscriberClient : updateSubscriberClients) {
                updateSubscriberClient.update(newOrders);
                updateSubscriberClient.update(doneOrders);
            }
        }).start();
    }
}
