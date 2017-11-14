package com.vladi.restaurant.server;

import com.vladi.restaurant.common.Requests;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

class ClientHandler extends ConnectionHandler{


    public ClientHandler(Socket socket) {
        super(socket);
    }

    @Override
    protected void running() throws IOException {
        String password = getIn().readUTF();
        if (Server.getInstant().checkPassword(password)){
            getOut().writeBoolean(true);
            while (true) {
                String message = getIn().readUTF();
                System.out.println(message);
                Requests.valueOf(message).response(getOut(), getIn());
            }
        } else {
            getOut().writeBoolean(false);
            System.out.println("Wrong password");
        }
    }
}

