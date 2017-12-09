package com.vladi.restaurant.client.uicontrollers;

import com.google.gson.Gson;
import com.vladi.restaurant.client.managing.Client;
import com.vladi.restaurant.client.managing.SceneManager;
import com.vladi.restaurant.common.Requests;
import com.vladi.restaurant.common.beans.*;
import com.vladi.restaurant.common.beans.Collection;
import com.vladi.restaurant.common.beans.Menu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.util.*;

import static com.vladi.restaurant.common.Requests.PUT_ORDER;

public class NewOrderController {

    @FXML
    private GridPane categoryPane;

    @FXML
    private GridPane dishesPane;

    @FXML
    private GridPane orderPane;

    @FXML
    private Button order;

    @FXML
    private Label totalPriceLabel;

    private Map<Dish, Integer> orderMap;

    @FXML
    public void initialize() throws IOException {
        orderMap = new HashMap<>();
        Menu menu = Client.getInstant().performRequest(Requests.GET_MENU, Menu.class);
        int i = 0;
        for (Collection collection : menu.getCollections()) {
            Button category = new Button();
            category.getStyleClass().add("category");
            category.setText(collection.getName());
            category.setOnAction(event -> openCategory(collection.getDishes()));
            categoryPane.addRow(i, category);
            i++;
        }
    }

    private void openCategory(List<Dish> dishes){
        dishesPane.getChildren().clear();
        dishesPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
        int i=0;
        for (Dish dish : dishes) {
            Label name =  new Label(dish.getName());
            String description = dish.getDesc();
            if(!description.trim().isEmpty()){
                name.setTooltip(new Tooltip(description));
            }
            Button addButton = new Button("add");
            addButton.setOnAction(event -> addButtonOnAction(dish));
            dishesPane.addRow(i, name, new Label(dish.getPrice()+" $"), addButton);
            i++;
        }
    }

    private void addButtonOnAction(Dish dish) {
        if (orderMap.containsKey(dish)) {
            orderMap.put(dish, orderMap.get(dish) + 1);
        } else {
            orderMap.put(dish, 1);
        }
        updateOrders();
    }

    private void updateOrders(){
        orderPane.getChildren().clear();
        USD totalSum = new USD(0);
        int i=0;
        for (Map.Entry<Dish, Integer> entry : orderMap.entrySet()) {
            Label name = new Label(entry.getKey().getName());
            Label price = new Label(entry.getKey().getPrice()+" $");
            totalSum = totalSum.add(new USD(Double.valueOf(entry.getKey().getPrice())*entry.getValue()));
            TextField number = new TextField(entry.getValue().toString());
            number.setOnAction(event -> orderNumberOnAction(event, entry));
            Button remove = new Button("remove");
            remove.setOnAction(event -> removeDish(entry));
            orderPane.addRow(i, name, price, number, remove);
            i++;
        }
        if(orderMap.isEmpty()){
            order.setDisable(true);
        }else {
            order.setDisable(false);
        }
        totalPriceLabel.setText(totalSum+" $");
    }

    private void orderNumberOnAction(ActionEvent event, Map.Entry<Dish, Integer> entry){
        TextField textField = (TextField)event.getSource();
        try {
            int number = Integer.valueOf(textField.getText());
            if((number<1)||(number>100)){
                throw new NumberFormatException();
            }
            entry.setValue(number);
            updateOrders();
        }catch (NumberFormatException ignored){
            textField.setText("1");
        }
    }

    private void removeDish(Map.Entry<Dish, Integer> entry){
        orderMap.remove(entry.getKey());
        updateOrders();
    }

    @FXML
    public void orderOnAction(){
        Order order = new Order();
        order.setDate(new Date());
        List<Dish> dishes = new ArrayList<>();
        for (Map.Entry<Dish, Integer> entry : orderMap.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                dishes.add(entry.getKey());
            }
        }
        order.setDishes(dishes);
        Client.getInstant().getRequestConnection().send(PUT_ORDER.name());
        Client.getInstant().getRequestConnection().send(new Gson().toJson(order));
        orderPane.getChildren().clear();
        SceneManager.sendPopUpMessage(Alert.AlertType.INFORMATION,"Sending", "Order is committed",null);
        SceneManager.getInstance().changeView(SceneManager.Views.CLIENT);
    }

    @FXML
    public void backButtonOnAction(){
        SceneManager.getInstance().changeView(SceneManager.Views.CLIENT);
    }
}
