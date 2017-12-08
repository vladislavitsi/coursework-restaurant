package com.vladi.restaurant.common;

import com.google.gson.Gson;
import com.vladi.restaurant.common.beans.History;
import com.vladi.restaurant.common.beans.Menu;
import com.vladi.restaurant.common.beans.Order;
import com.vladi.restaurant.server.Server;
import com.vladi.restaurant.server.connection.Connection;
import com.vladi.restaurant.server.control.DBManager;

import java.io.IOException;
import java.util.Date;

public enum ClientRequests {
    ECHO {
        @Override
        synchronized public void response(final Connection connection) {
            try {
                connection.send("Echo from "+Server.getInstance().getServerName()+" at "+new Date());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    },
    GET_MENU {
        @Override
        synchronized public void response(final Connection connection) {
            Menu menu = DBManager.getMenuFromDatabase();
            String objectJson = new Gson().toJson(menu);
            try {
                connection.send(objectJson);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    },
    GET_HISTORY {
        @Override
        synchronized public void response(final Connection connection) {
            History history = DBManager.getHistoryFromDatabase();
            String objectJson = new Gson().toJson(history);
            try {
                connection.send(objectJson);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    },
    PUT_ORDER {
        @Override
        synchronized public void response(final Connection connection) {
            String jsonOrder = connection.get();
            Order order = new Gson().fromJson(jsonOrder, Order.class);
            DBManager.putOrderToHistory(order);
            Server.getInstance().putOrder(order);
            Server.getInstance().updateOrders();
        }
    },
    DONE {
        @Override
        public void response(Connection connection) {
            String jsonDoneOrder = connection.get();
            Order doneOrder = new Gson().fromJson(jsonDoneOrder, Order.class);
            Server.getInstance().orderDone(doneOrder);
            Server.getInstance().updateOrders();
        }
    },
    DELETE {
        @Override
        public void response(Connection connection) {
            String jsonDoneOrder = connection.get();
            Order doneOrder = new Gson().fromJson(jsonDoneOrder, Order.class);
            Server.getInstance().deleteOldOrder(doneOrder);
            Server.getInstance().updateOrders();
        }
    };

    public abstract void response(final Connection connection);
}
