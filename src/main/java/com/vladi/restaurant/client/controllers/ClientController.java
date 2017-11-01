package com.vladi.restaurant.client.controllers;

import com.vladi.restaurant.client.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class ClientController {

    @FXML
    private GridPane prepareGrid;

    @FXML
    public void exit(){
        SceneManager.getInstance().changeScene(SceneManager.Scenes.LOGIN_CLIENT);
    }

    private static int i = 0;
    @FXML
    public void newOrder(){
        try {
            Pane order = FXMLLoader.load(getClass().getResource("/client/Order.fxml"));
//            if (prepareGrid.getRowConstraints().size()==1){
                prepareGrid.add(order,0,i);
                i++;
//            }else {
//
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
