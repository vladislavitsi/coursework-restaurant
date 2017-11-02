package com.vladi.restaurant.client.managing;

import java.io.*;
import java.util.Properties;

public abstract class ResourceManager {
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
        return getProperty("/client/config/config.properties", key);
    }
}
