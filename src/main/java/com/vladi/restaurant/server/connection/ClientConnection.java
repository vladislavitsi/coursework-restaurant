package com.vladi.restaurant.server.connection;

import com.vladi.restaurant.common.Requests;
import java.io.IOException;
import java.net.Socket;

public class ClientConnection extends Connection {

    public ClientConnection(Socket socket, ClientKind clientKind) throws IOException {
        super(socket, clientKind);
    }

    @Override
    protected void running() throws IOException {
        while (true) {
            String message = getIn().readUTF();
            System.out.println(message);
            Requests.valueOf(message).response(this);
        }
    }

}

