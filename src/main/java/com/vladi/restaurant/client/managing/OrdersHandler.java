package com.vladi.restaurant.client.managing;

import com.vladi.restaurant.common.Requests;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class OrdersHandler {
    private static OrdersHandler INSTANCE;

    private OrdersHandler() {
        newOrders = new ArrayList<>();
        doneOrders = new ArrayList<>();
    }

    public static OrdersHandler getInstance(){
        if(INSTANCE == null){
            INSTANCE = new OrdersHandler();
        }
        return INSTANCE;
    }

    private List<Integer> newOrders;
    private List<Integer> doneOrders;

    private GridPane prepareGrid;
    private GridPane preparedGrid;

    public GridPane getPrepareGrid() {
        return prepareGrid;
    }

    public void setPrepareGrid(GridPane prepareGrid) {
        this.prepareGrid = prepareGrid;
    }

    public GridPane getPreparedGrid() {
        return preparedGrid;
    }

    public void setPreparedGrid(GridPane preparedGrid) {
        this.preparedGrid = preparedGrid;
    }

    public List<Integer> getNewOrders() {
        return newOrders;
    }

    public void setNewOrders(List<Integer> newOrders) {
        this.newOrders = newOrders;
    }

    public List<Integer> getDoneOrders() {
        return doneOrders;
    }

    public void setDoneOrders(List<Integer> doneOrders) {
        this.doneOrders = doneOrders;
    }

    public void update(){
        updateWall(newOrders,
                prepareGrid,
                Requests.DONE,
                "->");
        updateWall(doneOrders,
                preparedGrid,
                Requests.DELETE,
                "x");
    }

    public synchronized void updateWall(List<Integer> orders, GridPane gridPane, Requests requests, String buttonFill){
        Platform.runLater(() -> {
            if(gridPane!=null){
                gridPane.getChildren().clear();
                int i = 0;
                for (Integer newOrder : orders) {
                    Label label = new Label(newOrder.toString());
                    label.getStyleClass().add("wallLabel");
                    label.setMaxWidth(Double.MAX_VALUE);
                    Button button = new Button(buttonFill);
                    button.setOnAction(event -> {
                        Client.getInstant().send(Client.getInstant().getRequestConnection(), requests.toString());
                        Client.getInstant().send(Client.getInstant().getRequestConnection(), newOrder.toString());
                    });
                    gridPane.addRow(i, label, button);
                    i++;
                }
            }
        });
    }
}
