package com.vladi.restaurant.client;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
    public enum Scenes{
        LOGIN_CLIENT("Login", 400, 150){
            @Override
            Scene getScene() {
                try {
                    return new Scene(FXMLLoader.load(getClass().getResource("/client/LoginClient.fxml")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        },
        CLIENT("Client", 800, 540){
            @Override
            Scene getScene() {
                try {
                    return new Scene(FXMLLoader.load(getClass().getResource("/client/Client.fxml")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        private final String title;
        private final int width;
        private final int height;

        public String getTitle() {
            return title;
        }

        Scenes(String title, int width, int height) {
            this.title = title;
            this.width = width;
            this.height = height;
        }

        abstract Scene getScene();
    }

    private static SceneManager instance;
    private static Stage stage;

    private SceneManager(){
    }

    public static SceneManager getInstance() {
        if(instance == null){
            instance = new SceneManager();
        }
        return instance;
    }

    public void setStage(Stage stage){
        SceneManager.stage = stage;
        stage.setResizable(false);
    }

    public void changeScene(Scenes scene){
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double x = (screenBounds.getWidth() - scene.width) / 2;
        double y = (screenBounds.getHeight() - scene.height) / 2;
//        stage = new Stage();
//        stage.hide();
        stage.setX(x);
        stage.setY(y);
        stage.setScene(scene.getScene());
        stage.setTitle(scene.getTitle());
        stage.show();
    }
}
