package com.vladi.restaurant.server.connection;

import com.google.gson.Gson;
import com.vladi.restaurant.common.beans.Order;
import com.vladi.restaurant.common.beans.Orders;

import java.io.IOException;
import java.net.Socket;
import java.util.*;

public class SubscriberConnection extends Connection implements Subscribable<Integer, Order> {

    public SubscriberConnection(Socket socket, ClientKind clientKind) throws IOException {
        super(socket, clientKind);
    }

    @Override
    protected void running() throws IOException {
        while (true) {
            String message = getIn().readUTF();
            System.out.println(message);
        }
    }

    @Override
    public void update(SortedMap<Integer, Order> m) {
        String jsonOrders = new Gson().toJson(new Orders(new ArrayList<>(m.keySet())));
        try {
            getOut().writeUTF(jsonOrders);
            getOut().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
