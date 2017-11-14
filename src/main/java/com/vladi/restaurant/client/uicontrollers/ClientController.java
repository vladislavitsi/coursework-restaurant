package com.vladi.restaurant.client.uicontrollers;

import com.vladi.restaurant.client.managing.Client;
import com.vladi.restaurant.client.managing.SceneManager;
import com.vladi.restaurant.common.Requests;
import com.vladi.restaurant.common.pojo.History;
import com.vladi.restaurant.common.pojo.Menu;
import javafx.fxml.FXML;

import java.io.DataInputStream;
import java.io.IOException;

public class ClientController {

    @FXML
    public void exit(){
        SceneManager.getInstance().setScene(SceneManager.Views.LOGIN_CLIENT);
    }

    @FXML
    public void newOrder(){
        SceneManager.getInstance().changeView(SceneManager.Views.NEW_ORDER);
    }

    @FXML
    public void openMenu(){
//        SceneManager.getInstance().changeView(SceneManager.Views.MENU);
        System.out.println(Client.getInstant().performRequest(Requests.GET_MENU, Menu.class));
    }

    @FXML
    public void openSettings(){
        SceneManager.getInstance().changeView(SceneManager.Views.SETTINGS);
    }

     @FXML
    public void openHistory(){
//        SceneManager.getInstance().changeView(SceneManager.Views.HISTORY);
         System.out.println(Client.getInstant().performRequest(Requests.GET_HISTORY, History.class));

     }

}
