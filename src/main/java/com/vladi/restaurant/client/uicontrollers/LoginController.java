package com.vladi.restaurant.client.uicontrollers;

import com.vladi.restaurant.client.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public final class LoginController {

    @FXML
    private TextField textField1;

    @FXML
    private TextField textField2;

    @FXML
    private PasswordField passwordField;


    @FXML
    public void implementConnect(){
        System.out.println(textField1.getText());
        SceneManager.getInstance().changeScene(SceneManager.Views.CLIENT);
    }
}
