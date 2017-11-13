package com.vladi.restaurant.common;

import com.google.gson.Gson;
import com.vladi.restaurant.common.pojo.History;
import com.vladi.restaurant.common.pojo.Menu;
import com.vladi.restaurant.common.pojo.Order;
import com.vladi.restaurant.server.Server;
import com.vladi.restaurant.server.control.DBManager;

import java.io.*;
import java.util.Date;
import java.util.Scanner;

public enum Requests {
    ECHO {
        @Override
        synchronized public void response(final PrintWriter out, final Scanner in) {
            out.print("Echo from "+Server.getInstant().getServerName()+" at "+new Date());
            out.flush();
        }
    },
    GET_MENU {
        @Override
        synchronized public void response(final PrintWriter out, final Scanner in) {
            Menu menu = DBManager.getMenuFromDatabase();
            String objectJson = new Gson().toJson(menu);
            out.print(objectJson);
            out.flush();
        }
    },
    GET_HISTORY {
        @Override
        synchronized public void response(final PrintWriter out, final Scanner in) {
            History history = DBManager.getHistoryFromDatabase();
            String objectJson = new Gson().toJson(history);
            out.print(objectJson);
            out.flush();
        }
    },
    PUT_ORDER {
        @Override
        synchronized public void response(final PrintWriter out, final Scanner in) {
            StringBuilder jsonOrder = new StringBuilder();
            while (in.hasNext()){
                jsonOrder.append(in.next());
            }
            Order order = new Gson().fromJson(jsonOrder.toString(), Order.class);
            DBManager.putOrderToHistory(order);
        }
    };

    public abstract void response(final PrintWriter out, final Scanner in);
}
