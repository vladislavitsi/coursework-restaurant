package com.vladi.restaurant.client.uicontrollers;

import com.vladi.restaurant.client.SceneManager;
import com.vladi.restaurant.client.managing.ResourceManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public final class LoginController {

    @FXML
    private TextField serverIp;

    @FXML
    private TextField port;

    @FXML
    private PasswordField passwordField;


    @FXML
    private void initialize() {
        String[] strings = ResourceManager.getLastLoginInfo();
        serverIp.appendText(strings[0]);
        port.appendText(strings[1]);
    }

    @FXML
    public void implementConnect(){
        new Thread(()->{
            String[] strings = {serverIp.getText(), port.getText()};
            ResourceManager.saveLoginInfo(strings);
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
