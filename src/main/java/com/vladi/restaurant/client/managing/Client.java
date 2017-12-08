package com.vladi.restaurant.client.managing;

import com.vladi.restaurant.common.ClientRequests;
import java.io.IOException;

public class Client {
    /*
     * Singleton implementation.
     */
    private static final Client instant = new Client();

    private Client() {
    }

    public static Client getInstant() {
        return instant;
    }

    /*
     *
     */

    private Connection requestConnection;
    private Connection subscriber;

    public void setConnection(String serverIp, int serverPort, String serverPassword) throws WrongPasswordException, IOException {
        try {
            requestConnection = new RequestConnection();
            requestConnection.connect(serverIp, serverPort, serverPassword);
            subscriber = new Subscriber();
            subscriber.connect(serverIp, serverPort+1, serverPassword);
        } catch (WrongPasswordException e) {
            close();
            throw new WrongPasswordException();
        } catch (IOException e) {
            System.out.println("Connection is over");
            close();
            throw new IOException();
        }
    }

    synchronized public void send(Connection connection, String message) {
        connection.send(message);
    }

    public void close() {
        if (subscriber != null){
            subscriber.close();
            subscriber = null;
        }
        if(requestConnection != null){
            requestConnection.close();
            requestConnection = null;
        }
    }

    synchronized public <T> T performRequest(ClientRequests request, Class<T> tClass) {
        return requestConnection.performRequest(request, tClass);
    }

    public Connection getRequestConnection() {
        return requestConnection;
    }

    public Connection getSubscriber() {
        return subscriber;
    }
}