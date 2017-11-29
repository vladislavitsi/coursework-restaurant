package com.vladi.restaurant.server;

import com.vladi.restaurant.server.control.ServerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Runner extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            setUpStage(primaryStage);
        } catch (IOException e) {
            System.err.println("Template is not found");;
        }
    }

    private void setUpStage(Stage primaryStage) throws IOException{
        primaryStage.setResizable(false);
        primaryStage.setTitle("Server");
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/server/fxmls/server.fxml"))));
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            ServerController.exitApplication();
        });
    }
}