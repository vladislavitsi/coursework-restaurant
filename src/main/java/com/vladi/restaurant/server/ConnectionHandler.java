package com.vladi.restaurant.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public abstract class ConnectionHandler implements Runnable {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public ConnectionHandler(Socket socket) {
        this.socket = socket;
        try {
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            running();
        }catch (SocketException s){
            System.out.println("The connection is over");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
