package com.vladi.restaurant.client;

import com.vladi.restaurant.common.ResourceManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
    public enum Views {
        LOGIN_CLIENT,
        CLIENT,
        MENU,
        SETTINGS,
        NEW_ORDER,
        HISTORY,
        ORDER_REPRESENTATION;

        private final String title;
        private final String resource;

        private static final String CONFIG_PROPERTY = "/client/config/config.properties";

        private String getTitle() {
            return title;
        }

        Views() {
            this.title = ResourceManager.getProperty(CONFIG_PROPERTY, "view."+toString()+".title");
            this.resource = ResourceManager.getProperty(CONFIG_PROPERTY, "view."+toString()+".path");
        }

        private Parent getRoot(){
            try {
                return FXMLLoader.load(getClass().getResource(resource));
            } catch (IOException e) {
                System.err.println("Desired fxml for "+title+" scene is not found");
            }
            return null;
        }

        @Override
        public String toString() {
            return name();
        }
    }

    private static SceneManager instance;
    private static Stage primaryStage;

    private SceneManager(){
    }

    public static SceneManager getInstance() {
        if(instance == null){
            instance = new SceneManager();
        }
        return instance;
    }

    public void setStage(Stage stage){
        SceneManager.primaryStage = stage;
        stage.setResizable(false);
    }

    public void setScene(Views view){
        primaryStage.hide();
        primaryStage.setScene(getScene(view));
        primaryStage.setTitle(view.getTitle());
        primaryStage.show();
    }

    public void addPopUpScene(Views view){
        PopUpStage popUpStage = new PopUpStage(view.getTitle(), primaryStage);
        popUpStage.setScene(getScene(view));
        popUpStage.showAndWait();
    }

    public void changeView(Views view){
        primaryStage.getScene().setRoot(view.getRoot());
        primaryStage.setTitle(view.getTitle());
    }

    private Scene getScene(Views view){
        try {
            return new Scene(FXMLLoader.load(getClass().getResource(view.resource)));
        } catch (IOException e) {
            System.err.println("Des ired fxml for "+view.getTitle()+" scene is not found");
        }
        return null;
    }

    private class PopUpStage extends Stage{
        private PopUpStage(String title, Stage primaryStage) {
            this.setTitle(title);
            this.setResizable(false);
            this.initModality(Modality.APPLICATION_MODAL);
            this.initOwner(primaryStage);
        }
    }
}
