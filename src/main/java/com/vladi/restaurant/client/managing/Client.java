package com.vladi.restaurant.client.managing;

import com.google.gson.Gson;
import com.vladi.restaurant.common.Requests;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    /*
     * Singleton implementation.
     */
    private static final Client instant = new Client();
    private Client(){
    }
    public static Client getInstant(){
        return instant;
    }

    /*
     *
     */
    private Socket requestSocket;
    private Socket subscriptionSocket;
    private DataOutputStream requestOut;
    private DataInputStream requestIn;
    private DataOutputStream subscriptionOut;
    private DataInputStream subscriptionIn;

    public void setConnection(String serverIp, int serverPort, String serverPassword) throws WrongPasswordException{
        try {
            requestSocket = new Socket(serverIp, serverPort);
            requestIn = new DataInputStream(requestSocket.getInputStream());
            requestOut = new DataOutputStream(requestSocket.getOutputStream());


            send(serverPassword);
            Boolean result = requestIn.readBoolean();
            if (result){
                subscriptionSocket = new Socket(serverIp, serverPort+1);
                requestIn = new DataInputStream(subscriptionSocket.getInputStream());
                requestOut = new DataOutputStream(subscriptionSocket.getOutputStream());
                System.out.println("connection established");
            }else {
                System.out.println("The wrong password, sorry");
                requestIn.close();
                requestOut.close();
                requestSocket.close();
                throw new WrongPasswordException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    synchronized public void send(String message){
        try {
            requestOut.writeUTF(message);
            requestOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    synchronized public <T> T performRequest(Requests request, Class<T> tClass){
        T object;
        StringBuilder response = new StringBuilder();
        try {
            send(request.name());
            response.append(requestIn.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Gson().fromJson(response.toString(), tClass);
    }

    synchronized public DataOutputStream getRequestOut() {
        return requestOut;
    }

    synchronized public DataInputStream getRequestIn() {
        return requestIn;
    }
}
