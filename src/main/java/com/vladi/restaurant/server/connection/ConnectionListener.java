package com.vladi.restaurant.server.connection;

import com.vladi.restaurant.server.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class ConnectionListener extends Thread{

    private final ServerSocket serverSocket;
    private final ClientKind clientKind;

    public ConnectionListener(ServerSocket serverSocket, ClientKind clientKind) {
        this.serverSocket = serverSocket;
        this.clientKind = clientKind;
    }

    @Override
    public void run() {
        try {
            while (true){
                Socket newClientSocketSubscription = serverSocket.accept();
                Connection connection = clientKind.getNewClient(newClientSocketSubscription);
                Server.getInstance().addToList(clientKind, connection);
                connection.start();
            }
        } catch (SocketException e){
            System.out.println("Socket "+ clientKind +" is closed");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

