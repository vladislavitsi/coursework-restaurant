package com.vladi.restaurant.client.uicontrollers;

import com.google.gson.Gson;
import com.vladi.restaurant.client.managing.Client;
import com.vladi.restaurant.client.managing.SceneManager;
import com.vladi.restaurant.common.ClientRequests;
import com.vladi.restaurant.common.beans.History;
import com.vladi.restaurant.common.beans.Order;
import javafx.fxml.FXML;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class ClientController {

    @FXML
    public void exit(){
        Client.getInstant().close();
        SceneManager.getInstance().setScene(SceneManager.Views.LOGIN_CLIENT);
    }

    @FXML
    public void newOrder(){
        SceneManager.getInstance().changeView(SceneManager.Views.NEW_ORDER);
    }

     @FXML
    public void openHistory(){
        SceneManager.getInstance().changeView(SceneManager.Views.HISTORY);
//         System.out.println(Client.getInstant().performRequest(ClientRequests.GET_HISTORY, History.class));
     }

    @FXML
    public static void exitApplication() {
        Client.getInstant().close();
    }

}
