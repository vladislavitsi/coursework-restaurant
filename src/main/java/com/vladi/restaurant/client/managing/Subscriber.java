package com.vladi.restaurant.client.managing;

import com.vladi.restaurant.common.ClientRequests;

import java.io.IOException;

public class Subscriber extends Connection {
    private SubscriberListenerThread listenerThread;

    @Override
    public void adjust() {
        listenerThread = new SubscriberListenerThread();
        listenerThread.setDaemon(true);
        listenerThread.start();
    }

    @Override
    public void close() {
        if(listenerThread!=null && listenerThread.isAlive()){
            listenerThread.interrupt();
            listenerThread = null;
        }
        super.close();
    }

    @Override
    public synchronized <T> T performRequest(ClientRequests request, Class<T> tClass){
        throw new IllegalStateException();
    }

    class SubscriberListenerThread extends Thread{
        @Override
        public void run() {
            try {
                while (true){
                    String s = getInputStream().readUTF();
                    System.out.println(s);
                    s = getInputStream().readUTF();
                    System.out.println(s);
                }
            } catch (IOException e) {
                close();
//                SceneManager.returnToLoginStage();
            }
        }
    }
}
