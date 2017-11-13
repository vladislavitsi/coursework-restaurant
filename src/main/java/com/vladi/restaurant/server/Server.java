package com.vladi.restaurant.server;

import com.vladi.restaurant.common.pojo.Order;
import com.vladi.restaurant.server.control.DBManager;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

public class Server {
    /*
     * Singleton implementation.
     */
    private static final Server instant = new Server();
    private Server(){
        clientsList = new ArrayList<>();
    }
    public static Server getInstant(){
        return instant;
    }
    /*
     * The Server
     */
    private String serverName;
    private int port;
    private String password;
    private ServerSocket serverSocket;
    private ArrayList<Client> clientsList;
    private LinkedList<Order> newOrders;
    private LinkedList<Order> doneOrders;

    public void initialization(String serverName, int port, String password){
        this.serverName = serverName;
        this.port = port;
        this.password = password;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        launchConnector();
        System.out.println(DBManager.getMenuFromDatabase());
    }

    private void launchConnector(){
        new Thread(()->{
            while (true){
                try {
                    Socket newClientSocket = serverSocket.accept();
                    Client newClient = createClient(newClientSocket);
                    clientsList.add(newClient);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    synchronized public boolean checkPassword(String password){
        return this.password.equals(password);
    }

    private static Client createClient(Socket socket){
        return new Client(socket);
    }

    public String getServerName() {
        return serverName;
    }

    public int getPort() {
        return port;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public ArrayList<Client> getClientsList() {
        return clientsList;
    }

    public LinkedList<Order> getNewOrders() {
        return newOrders;
    }

    public LinkedList<Order> getDoneOrders() {
        return doneOrders;
    }
}

