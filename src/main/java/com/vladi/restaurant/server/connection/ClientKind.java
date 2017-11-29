package com.vladi.restaurant.server.connection;

import java.io.IOException;
import java.net.Socket;

public enum ClientKind {
    REQUEST_CLIENT {
        @Override
        public Connection getNewClient(Socket socket) throws IOException {
            return new ClientConnection(socket, this);
        }
    },
    SUBSCRIBER {
        @Override
        public Connection getNewClient(Socket socket) throws IOException {
            return new SubscriberConnection(socket, this);
        }
    };

    public abstract Connection getNewClient(Socket socket) throws IOException;
}
