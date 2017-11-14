package com.vladi.restaurant.server;

import java.io.IOException;
import java.net.Socket;

public class Subscriber extends ConnectionHandler{

    public Subscriber(Socket socket) {
        super(socket);
    }

    @Override
    protected void running() throws IOException {
        String password = getIn().readUTF();
        if (Server.getInstant().checkPassword(password)){
            getOut().writeBoolean(true);
            while (true) {
                System.out.println(getIn().readUTF());

            }
        } else {
            getOut().writeBoolean(false);
            System.out.println("Wrong password");
        }
    }
}
