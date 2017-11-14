package com.vladi.restaurant.server;

import com.vladi.restaurant.common.pojo.Order;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server {
    /*
     * Singleton implementation.
     */
    private static final Server instant = new Server();
    private Server(){
        subscribers = new LinkedList<>();
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
    private ServerSocket requestServerSocket;
    private ServerSocket subscriptionServerSocket;
    private LinkedList<Subscriber> subscribers;
    private Thread listenToClients;
    private Thread listenToSubscribers;
    private LinkedList<Order> newOrders;
    private LinkedList<Order> doneOrders;

    public void initialization(String serverName, int port, String password){
        this.serverName = serverName;
        this.port = port;
        this.password = password;
        try {
            requestServerSocket = new ServerSocket(port);
            subscriptionServerSocket = new ServerSocket(port+1);
            System.out.println("Server is started");
        } catch (IOException e) {
            e.printStackTrace();
        }
        listenToClients();
        listenToSubscribers();
    }

    private void listenToClients(){
        listenToClients = new Thread(()->{
            while (true){
                try {
                    Socket newClientSocketRequest = requestServerSocket.accept();
                    ClientHandler newClient = createClient(newClientSocketRequest);
                    new Thread(newClient).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        listenToClients.start();
    }

    private void listenToSubscribers(){
        listenToSubscribers = new Thread(()->{
            while (true){
                try {
                    Socket newClientSocketSubscription = subscriptionServerSocket.accept();
                    Subscriber subscriber = new Subscriber(newClientSocketSubscription);
                    subscribers.add(subscriber);
                    new Thread(subscriber).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        listenToSubscribers.start();
    }

    synchronized public boolean checkPassword(String password){
        return this.password.equals(password);
    }

    private static ClientHandler createClient(Socket requestSocket){
        return new ClientHandler(requestSocket);
    }

    public String getServerName() {
        return serverName;
    }

    public int getPort() {
        return port;
    }

    public ServerSocket getRequestServerSocket() {
        return requestServerSocket;
    }

    public LinkedList<Subscriber> getSubscribers() {
        return subscribers;
    }

    public LinkedList<Order> getNewOrders() {
        return newOrders;
    }

    public LinkedList<Order> getDoneOrders() {
        return doneOrders;
    }
}

