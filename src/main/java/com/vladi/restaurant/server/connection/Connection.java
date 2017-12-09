package com.vladi.restaurant.server.connection;

import com.vladi.restaurant.common.beans.Order;
import com.vladi.restaurant.server.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public abstract class Connection extends Thread {

    private final Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private ClientKind clientKind;

    Connection(Socket socket, ClientKind clientKind) throws IOException {
        this.socket = socket;
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());
        this.clientKind = clientKind;
    }

    @Override
    public void run() {
        try {
            String password = getIn().readUTF();
            if (Server.getInstance().checkPassword(password)) {
                send(true);
                running();
            } else {
                send(false);
                throw new IllegalAccessException();
            }
        } catch (IOException e) {
            close();
        } catch (IllegalAccessException e) {
            System.out.println("Wrong password");
            close();
        }
    }

    public void close() {
        try {
            if (in != null){
                in.close();
            }
            if (out != null){
                out.close();
            }
            if (socket != null){
                socket.close();
            }
            switch (clientKind){
                case REQUEST_CLIENT:
                    Server.getInstance().deleteFromClientList(this);
                    break;
                case SUBSCRIBER:
                    Server.getInstance().deleteFromSubscriberList((Subscribable<Integer, Order>) this);
                    break;
            }
            interrupt();
        } catch (IOException e) {
            System.err.println("Exception closing socket");
        }
    }

    public void send(String message) throws IOException {
        out.writeUTF(message);
        out.flush();
    }

    public void send(boolean bool) throws IOException {
        out.writeBoolean(bool);
        out.flush();
    }

    public String get() {
        try {
            return in.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected abstract void running() throws IOException;

    public DataInputStream getIn() {
        return in;
    }

    public DataOutputStream getOut() {
        return out;
    }
}
