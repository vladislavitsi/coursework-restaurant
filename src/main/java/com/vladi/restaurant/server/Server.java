package com.vladi.restaurant.server;

import com.vladi.restaurant.common.pojo.Menu;
import com.vladi.restaurant.server.control.DBManager;

public class Server {
    /*
     * Singleton implementation.
     */
    private static final Server instant = new Server();
    private Server(){
//        initialization();
    }
    public static Server getInstant(){
        return instant;
    }
    /*
     * Other
     */
    private String serverName;
    private String port;
    private String password;

    private Menu menu;

    public void initialization(){
        menu = DBManager.getMenuFromDatabase();
        System.out.println(menu);
    }
}
