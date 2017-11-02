package com.vladi.restaurant.client.managing;

import java.io.*;
import java.util.Properties;
import java.util.Scanner;

public abstract class ResourceManager {

    private static final String CONFIG_PROPERTY = "/client/config/config.properties";
    private static final String LOGIN_INFO = "lastLoginInfo.txt";

    public static String getProperty(final String propertyPath, final String key){
        String value = "";
        try (InputStream is = ResourceManager.class.getResourceAsStream(propertyPath)){
            Properties property = new Properties();
            property.load(is);
            value = property.getProperty(key);
        }catch (IOException e){
            e.printStackTrace();
        }
        return value;
    }

    public static String getConfig(final String key){
        return getProperty(CONFIG_PROPERTY, key);
    }

    public static String[] getLastLoginInfo(){
        String[] strings = {"",""};
        try (Scanner in = new Scanner(new File(LOGIN_INFO))){
            strings[0] = in.next();
            strings[1] = in.next();
        }catch (Exception e){
            e.printStackTrace();
        }
        return strings;
    }

    public static void saveLoginInfo(String[] strings){
        try (BufferedWriter out = new BufferedWriter(new FileWriter(new File(LOGIN_INFO)))){
            for (String s:strings){
                out.append(s).append(" ");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
