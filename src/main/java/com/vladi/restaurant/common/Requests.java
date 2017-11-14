package com.vladi.restaurant.common;

import com.google.gson.Gson;
import com.vladi.restaurant.common.pojo.History;
import com.vladi.restaurant.common.pojo.Menu;
import com.vladi.restaurant.common.pojo.Order;
import com.vladi.restaurant.server.Server;
import com.vladi.restaurant.server.control.DBManager;

import java.io.*;
import java.util.Date;

public enum Requests {
    ECHO {
        @Override
        synchronized public void response(final DataOutputStream out, final DataInputStream in) {
            Requests.send("Echo from "+Server.getInstant().getServerName()+" at "+new Date(), out);
        }
    },
    GET_MENU {
        @Override
        synchronized public void response(final DataOutputStream out, final DataInputStream in) {
            Menu menu = DBManager.getMenuFromDatabase();
            String objectJson = new Gson().toJson(menu);
            Requests.send(objectJson, out);
        }
    },
    GET_HISTORY {
        @Override
        synchronized public void response(final DataOutputStream out, final DataInputStream in) {
            History history = DBManager.getHistoryFromDatabase();
            String objectJson = new Gson().toJson(history);
            Requests.send(objectJson, out);
        }
    },
    PUT_ORDER {
        @Override
        synchronized public void response(final DataOutputStream out, final DataInputStream in) {
            String jsonOrder = "";
            try {
                jsonOrder = in.readUTF();
                Order order = new Gson().fromJson(jsonOrder, Order.class);
                DBManager.putOrderToHistory(order);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    private synchronized static void send(String message, DataOutputStream out){
        try {
            out.writeUTF(message);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract void response(final DataOutputStream out, final DataInputStream in);
}
