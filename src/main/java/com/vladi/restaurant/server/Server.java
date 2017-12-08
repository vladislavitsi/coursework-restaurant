package com.vladi.restaurant.server;

import com.vladi.restaurant.common.beans.Order;
import com.vladi.restaurant.server.connection.ClientKind;
import com.vladi.restaurant.server.connection.Connection;
import com.vladi.restaurant.server.connection.ConnectionListener;
import com.vladi.restaurant.server.connection.Subscribable;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.LinkedList;
import java.util.List;

public class Server {
    /*
     * Singleton implementation.
     */
    private static final Server INSTANCE = new Server();
    private Server(){}
    public static Server getInstance(){
        return INSTANCE;
    }

    /*
     * The Server
     */
    private String serverName;
    private int port;
    private String password;

    private ServerSocket requestServerSocket;
    private ServerSocket subscriptionServerSocket;

    private ConnectionListener clientListener;
    private ConnectionListener subscriberListener;

    private List<Connection> requestClients;
    private List<Subscribable<Order>> updateSubscriberClients;

    private OrderHandler orderHandler;

    /**
     * Initializations
     * @param serverName name of the server.
     * @param port server port.
     * @param password server password.
     */
    public void initialization(String serverName, int port, String password){
        this.serverName = serverName;
        this.port = port;
        this.password = password;
        try {
            requestServerSocket = new ServerSocket(port);
            subscriptionServerSocket = new ServerSocket(port + 1);
            System.out.println("Server is started");

            requestClients = new LinkedList<>();
            updateSubscriberClients = new LinkedList<>();
            orderHandler = new OrderHandler();

            clientListener = listenToConnections(ClientKind.REQUEST_CLIENT, requestServerSocket);
            subscriberListener = listenToConnections(ClientKind.SUBSCRIBER, subscriptionServerSocket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void orderDone(Order doneOrder){
        orderHandler.orderDone(doneOrder);
    }

    public synchronized void deleteOldOrder(Order oldOrder){
        orderHandler.deleteOldOrder(oldOrder);
    }

    public synchronized void putOrder(Order newOrder){
        orderHandler.putOrder(newOrder);
    }

    public synchronized void updateOrders(){
        orderHandler.updateOrders(updateSubscriberClients);
    }

    /**
     * Listen to new connection to Server in new Thread.
     * @param clientType the type of the client.
     * @param serverSocket server socket.
     */
    private static ConnectionListener listenToConnections(ClientKind clientType, ServerSocket serverSocket){
        ConnectionListener connectionListener = new ConnectionListener(serverSocket, clientType);
        connectionListener.start();
        return connectionListener;
    }

    public void closeConnection() throws IOException {
        orderHandler = null;

        if(requestClients!=null){
            System.out.println(requestClients.size());
            for (Connection requestClient : requestClients) {
                requestClient.close();
            }
            requestClients = null;
        }

        if(updateSubscriberClients!=null){
            System.out.println(updateSubscriberClients.size());
            for (Subscribable<Order> updateSubscriberClient : updateSubscriberClients) {
                updateSubscriberClient.close();
            }
            updateSubscriberClients = null;
        }

        if (clientListener!=null && clientListener.isAlive()){
            clientListener.interrupt();
            clientListener = null;
        }

        if (subscriberListener!=null && subscriberListener.isAlive()){
            subscriberListener.interrupt();
            subscriberListener = null;
        }

        if (requestServerSocket!=null && !requestServerSocket.isClosed()){
            requestServerSocket.close();
            requestServerSocket = null;
        }

        if (subscriptionServerSocket!=null && !subscriptionServerSocket.isClosed()){
            subscriptionServerSocket.close();
            subscriptionServerSocket = null;
        }
    }

    synchronized public void addToList(ClientKind clientKind, Connection connection){
        switch (clientKind){
            case SUBSCRIBER:
                updateSubscriberClients.add((Subscribable<Order>)connection);
                updateOrders();
                break;
            case REQUEST_CLIENT:
                requestClients.add(connection);
                break;
        }
    }

    synchronized public void deleteFromClientList(Connection connection){
        if(requestClients!=null){
            requestClients.remove(connection);
        }
    }

    synchronized public void deleteFromSubscriberList(Subscribable<Order> connection){
        if (updateSubscriberClients!=null){
            updateSubscriberClients.remove(connection);
        }
    }

    synchronized public boolean checkPassword(String password){
        return this.password.equals(password);
    }

    public int getPort() {
        return port;
    }

    public String getServerName() {
        return serverName;
    }
}

