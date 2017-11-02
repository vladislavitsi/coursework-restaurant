package com.vladi.restaurant.client.uicontrollers;

import com.vladi.restaurant.client.SceneManager;
import javafx.fxml.FXML;

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
        SceneManager.getInstance().changeView(SceneManager.Views.MENU);
    }

    @FXML
    public void openSettings(){
        SceneManager.getInstance().changeView(SceneManager.Views.SETTINGS);
    }

     @FXML
    public void openHistory(){
        SceneManager.getInstance().changeView(SceneManager.Views.HISTORY);
     }

}
