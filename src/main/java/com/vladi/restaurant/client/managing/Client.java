package com.vladi.restaurant.client.managing;

import com.google.gson.Gson;
import com.vladi.restaurant.common.ClientRequests;

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
            send(requestOut, serverPassword);
            Boolean result = requestIn.readBoolean();
            if (result){
                subscriptionSocket = new Socket(serverIp, serverPort+1);
                subscriptionIn = new DataInputStream(subscriptionSocket.getInputStream());
                subscriptionOut = new DataOutputStream(subscriptionSocket.getOutputStream());
                send(subscriptionOut, serverPassword);
                if(subscriptionIn.readBoolean()){
                    new Thread(()->{
                        while (true){
                            try {
                                String s = subscriptionIn.readUTF();
                                System.out.println(s);
                                s = subscriptionIn.readUTF();
                                System.out.println(s);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    System.out.println("connection established");
                }else {
                    subscriptionIn.close();
                    subscriptionOut.close();
                    subscriptionSocket.close();
                }
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

    synchronized public void send(DataOutputStream outputStream, String message){
        try {
            outputStream.writeUTF(message);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    synchronized public <T> T performRequest(ClientRequests request, Class<T> tClass){
        StringBuilder response = new StringBuilder();
        try {
            send(requestOut, request.name());
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
