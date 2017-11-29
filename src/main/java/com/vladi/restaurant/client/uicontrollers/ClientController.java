package com.vladi.restaurant.client.uicontrollers;

import com.google.gson.Gson;
import com.mongodb.gridfs.CLI;
import com.vladi.restaurant.client.managing.Client;
import com.vladi.restaurant.client.managing.SceneManager;
import com.vladi.restaurant.common.ClientRequests;
import com.vladi.restaurant.common.beans.History;
import com.vladi.restaurant.common.beans.Menu;
import com.vladi.restaurant.common.beans.Order;
import javafx.fxml.FXML;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class ClientController {

    @FXML
    public void exit(){
        SceneManager.getInstance().setScene(SceneManager.Views.LOGIN_CLIENT);
    }

    @FXML
    public void newOrder(){
//        SceneManager.getInstance().changeView(SceneManager.Views.NEW_ORDER);
        Client.getInstant().send(Client.getInstant().getRequestOut(), ClientRequests.PUT_ORDER.toString());
        try {
            Client.getInstant().getRequestOut().writeUTF(new Gson().toJson(new Order(new Date(), new ArrayList<>())));
            Client.getInstant().getRequestOut().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openMenu(){
//        SceneManager.getInstance().changeView(SceneManager.Views.MENU);
        System.out.println(Client.getInstant().performRequest(ClientRequests.GET_MENU, Menu.class));
    }

    @FXML
    public void openSettings(){
        SceneManager.getInstance().changeView(SceneManager.Views.SETTINGS);
    }

     @FXML
    public void openHistory(){
//        SceneManager.getInstance().changeView(SceneManager.Views.HISTORY);
         System.out.println(Client.getInstant().performRequest(ClientRequests.GET_HISTORY, History.class));

     }

}
