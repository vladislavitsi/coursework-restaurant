package com.vladi.restaurant.client.managing;

import com.google.gson.Gson;
import com.vladi.restaurant.common.Requests;
import com.vladi.restaurant.common.beans.Orders;

import java.io.IOException;

public class Subscriber extends Connection {
    private SubscriberListenerThread listenerThread;

    @Override
    public void adjust() {
        listenerThread = new SubscriberListenerThread();
        listenerThread.setDaemon(true);
        listenerThread.start();
    }

    @Override
    public void close() {
        if(listenerThread!=null && listenerThread.isAlive()){
            listenerThread.interrupt();
            listenerThread = null;
        }
        super.close();
    }

    @Override
    public synchronized <T> T performRequest(Requests request, Class<T> tClass){
        throw new IllegalStateException();
    }

    class SubscriberListenerThread extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    Orders orders = new Gson().fromJson(getInputStream().readUTF(), Orders.class);
                    OrdersHandler.getInstance().setNewOrders(orders.getList());

                    orders = new Gson().fromJson(getInputStream().readUTF(), Orders.class);
                    OrdersHandler.getInstance().setDoneOrders(orders.getList());

                    OrdersHandler.getInstance().update();
                }
            } catch (IOException e) {
                close();
            }
        }
    }



}
