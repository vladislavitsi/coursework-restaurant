package com.vladi.restaurant.client.uicontrollers;

import com.vladi.restaurant.client.SceneManager;
import com.vladi.restaurant.common.ResourceManager;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

public final class LoginController {

    private static final String LOGIN_SETTINGS = "lastLoginInfo.txt";

    @FXML
    private TextField serverIp;

    @FXML
    private TextField port;

    @FXML
    private PasswordField passwordField;


    @FXML
    private void initialize() {
        ArrayList<String> strings = ResourceManager.getLastSettings(LOGIN_SETTINGS);
        serverIp.appendText(strings.get(0));
        port.appendText(strings.get(1));
    }

    @FXML
    public void implementConnect(){
        new Thread(()->{
            ArrayList<String> configurations = new ArrayList<>();
            configurations.add(serverIp.getText());
            configurations.add(port.getText());
            ResourceManager.saveLastSettings(LOGIN_SETTINGS, configurations);
        }).start();
        SceneManager.getInstance().setScene(SceneManager.Views.CLIENT);
    }

    @FXML
    public void submit(KeyEvent e){
        if(e.getCode()== KeyCode.ENTER){
            implementConnect();
        }
    }
}
