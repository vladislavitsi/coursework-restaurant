package com.vladi.restaurant.server.connection;

import com.vladi.restaurant.server.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public abstract class Connection extends Thread {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private ClientKind clientKind;

    public Connection(Socket socket, ClientKind clientKind) throws IOException {
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
        }catch (SocketException s){
            System.out.println("The connection is over");
        } catch (IOException e) {
            System.err.println("Exception in Thread"+Thread.currentThread().getName()+", Connection Handler");
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
                    Server.getInstance().deleteFromSubscriberList(this);
                    break;
            }
            interrupt();
        } catch (IOException e) {
            System.err.println("Exception closing socket");
        }
    }

    public void send(String message){
        try {
            out.writeUTF(message);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(boolean bool){
        try {
            out.writeBoolean(bool);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public Socket getSocket() {
        return socket;
    }

    public DataInputStream getIn() {
        return in;
    }

    public DataOutputStream getOut() {
        return out;
    }
}
