package com.vladi.restaurant.server;

import com.vladi.restaurant.common.Requests;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

class Client implements Runnable{
    private Socket socket;
    private PrintWriter out;
    private Scanner in;

    public Client(Socket socket) {
        this.socket = socket;
        try {
            out = new PrintWriter(socket.getOutputStream());
            in = new Scanner(socket.getInputStream());
            if (Server.getInstant().checkPassword(in.next())){
                Thread thread = new Thread(this);
                thread.start();
            }else {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handle(){
        while (true) {
            if(in.hasNext()) {
                String message = in.nextLine();
                System.out.println(message);
                Requests.valueOf(message).response(out, in);
            }
        }
    }


    public PrintWriter getOut() {
        return out;
    }

    public Scanner getIn() {
        return in;
    }

    @Override
    public void run() {
        handle();
    }
}

