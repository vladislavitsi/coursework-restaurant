package com.vladi.restaurant.server.control;

import com.vladi.restaurant.common.ResourceManager;
import com.vladi.restaurant.server.Server;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class ServerController {

    private static final String SERVER_SETTINGS = "serverSettings.txt";

    @FXML
    private TextField serverName;

    @FXML
    private TextField port;

    @FXML
    private TextField password;

    @FXML
    private Label status;

    @FXML
    private Button button;

    @FXML
    public void initialize(){
        ArrayList<String> configurations = ResourceManager.getLastSettings(SERVER_SETTINGS);
        if(configurations.size()>0){
            serverName.appendText(configurations.get(0));
            port.appendText(configurations.get(1));
            password.appendText(configurations.get(2));
        }
    }

    @FXML
    public void action(){
        if(isDisabled()){
            run();
        }else {
            stop();
        }
    }

    private boolean isDisabled(){
        return status.getText().equals("Disabled");
    }

    private void run(){
        new Thread(()->{
            ArrayList<String> configurations = new ArrayList<>();
            configurations.add(serverName.getText());
            configurations.add(port.getText());
            configurations.add(password.getText());
            configurations.add(button.getText());
            ResourceManager.saveLastSettings(SERVER_SETTINGS, configurations);
        }).start();
        Server.getInstant().initialization();
        serverName.setDisable(true);
        port.setDisable(true);
        password.setDisable(true);
        button.setText("Stop");
        status.setText("Running");
    }

    private void stop(){
        serverName.setDisable(false);
        port.setDisable(false);
        password.setDisable(false);
        button.setText("Start");
        status.setText("Disabled");
    }
}
