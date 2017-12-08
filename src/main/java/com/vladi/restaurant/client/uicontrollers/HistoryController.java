package com.vladi.restaurant.client.uicontrollers;

import com.vladi.restaurant.client.managing.Client;
import com.vladi.restaurant.client.managing.SceneManager;
import com.vladi.restaurant.common.ClientRequests;
import com.vladi.restaurant.common.beans.Dish;
import com.vladi.restaurant.common.beans.History;
import com.vladi.restaurant.common.beans.Order;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.text.SimpleDateFormat;
import java.util.*;


public class HistoryController {

    @FXML
    public Button back;

    @FXML
    private Label totalPriceLabel;


    @FXML
    private GridPane historyToken;

    @FXML
    private GridPane historyDetails;


    private static final String DATE_NEW_FORMAT = "hh:mm:ss  MMMM d, yyyy";

    private History history;

    @FXML
    public void initialize(){
        history = Client.getInstant().performRequest(ClientRequests.GET_HISTORY, History.class);
        List<Order> orders = history.getOrders();
        Collections.sort(orders);
        historyToken.getChildren().clear();
        SimpleDateFormat newDateFormat = new SimpleDateFormat(DATE_NEW_FORMAT);
        newDateFormat.setTimeZone(TimeZone.getDefault());
        int i=0;
        for (Order order : orders) {
            Button button = new Button(newDateFormat.format(order.getDate()));
            button.getStyleClass().add("history");
            button.setOnAction(event -> showDetails(order));
            historyToken.addRow(i, button);
            i++;
        }
    }

    private void showDetails(Order order){
        Map<Dish, Integer> orderMap = new HashMap<>();
        for (Dish dish : order.getDishes()) {
            if(orderMap.containsKey(dish)){
                orderMap.put(dish, orderMap.get(dish)+1);
            }else {
                orderMap.put(dish, 1);
            }
        }
        historyDetails.getChildren().clear();
        int i=0;
        for (Map.Entry<Dish, Integer> entry : orderMap.entrySet()) {
            Label name = new Label(entry.getKey().getName());
            Label price = new Label(entry.getKey().getPrice()+" $");
            Label number = new Label(entry.getValue().toString());
            historyDetails.addRow(i, name, price, number);
            i++;
        }
        totalPriceLabel.setDisable(false);
        totalPriceLabel.setText(order.totalPrice().toString()+" $");
    }

    @FXML
    void backButtonOnAction() {
        SceneManager.getInstance().changeView(SceneManager.Views.CLIENT);
    }
}
