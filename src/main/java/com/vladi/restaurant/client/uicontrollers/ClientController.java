package com.vladi.restaurant.client.uicontrollers;

import com.vladi.restaurant.client.managing.Client;
import com.vladi.restaurant.client.managing.OrdersHandler;
import com.vladi.restaurant.client.managing.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class ClientController {

    @FXML
    private GridPane prepareGrid;

    @FXML
    private GridPane preparedGrid;

    @FXML
    public void initialize(){
        OrdersHandler.getInstance().setPrepareGrid(prepareGrid);
        OrdersHandler.getInstance().setPreparedGrid(preparedGrid);
        OrdersHandler.getInstance().update();
    }

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
     }

    @FXML
    public static void exitApplication() {
        Client.getInstant().close();
    }


}
