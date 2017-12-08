package com.vladi.restaurant.server.connection;

import com.google.gson.Gson;
import com.vladi.restaurant.common.ClientRequests;
import com.vladi.restaurant.common.beans.Order;
import com.vladi.restaurant.server.Server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class SubscriberConnection extends Connection implements Subscribable<Order> {

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
    public void update(List<Order> orders) {
        String jsonOrders = new Gson().toJson(orders);
        try {
            getOut().writeUTF(jsonOrders);
            getOut().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
