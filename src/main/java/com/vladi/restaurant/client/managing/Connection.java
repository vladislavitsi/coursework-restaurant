package com.vladi.restaurant.client.managing;

import com.google.gson.Gson;
import com.vladi.restaurant.common.Requests;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public abstract class Connection {
    private Socket socket;
    private DataOutputStream outputStream;
    private DataInputStream inputStream;

    public void connect(String serverIp, int port, String password) throws IOException, WrongPasswordException {
        socket = new Socket(serverIp, port);
        inputStream = new DataInputStream(socket.getInputStream());
        outputStream = new DataOutputStream(socket.getOutputStream());
        if (checkPassword(password)) {
            adjust();
        }else {
            throw new WrongPasswordException();
        }
    }

    synchronized public void send(String message) {
        try {
            outputStream.writeUTF(message);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (inputStream != null) {
                inputStream.close();
                inputStream = null;
            }
            if (outputStream != null) {
                outputStream.close();
                outputStream = null;
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
                socket = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    synchronized public <T> T performRequest(Requests request, Class<T> tClass) {
        String response = "";
        try {
            send(request.name());
            response = inputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Gson().fromJson(response, tClass);
    }

    private boolean checkPassword(String serverPassword) throws IOException {
        send(serverPassword);
        return inputStream.readBoolean();
    }

    protected abstract void adjust();

    public DataOutputStream getOutputStream() {
        return outputStream;
    }

    public DataInputStream getInputStream() {
        return inputStream;
    }
}
