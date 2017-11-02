package com.vladi.restaurant.client;

import javafx.application.Application;
import javafx.stage.Stage;

public class Runner extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws InterruptedException {
        SceneManager.getInstance().setStage(primaryStage);
        SceneManager.getInstance().setScene(SceneManager.Views.LOGIN_CLIENT);
    }
}