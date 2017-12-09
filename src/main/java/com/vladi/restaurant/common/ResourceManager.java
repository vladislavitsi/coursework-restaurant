package com.vladi.restaurant.common;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

public abstract class ResourceManager {

    public static ArrayList<String> getLastSettings(final String lastSettingsFile){
        ArrayList<String> configurations = new ArrayList<>();
        try (Scanner in = new Scanner(new File(lastSettingsFile))){
            while (in.hasNext()){
                configurations.add(in.next());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return configurations;
    }

    public static void saveLastSettings(final String lastSettings, ArrayList<String> configurations){
        try (BufferedWriter out = new BufferedWriter(new FileWriter(new File(lastSettings)))){
            for (String s:configurations){
                out.append(s).append(" ");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static String getProperty(final String key){
        String value = "";
        try (InputStream is = ResourceManager.class.getResourceAsStream(com.vladi.restaurant.client.managing.SceneManager.Views.CONFIG_PROPERTY)){
            Properties property = new Properties();
            property.load(is);
            value = property.getProperty(key);
        }catch (IOException e){
            e.printStackTrace();
        }
        return value;
    }


}
