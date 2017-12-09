package com.vladi.restaurant.server.control;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.vladi.restaurant.common.beans.*;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class DBManager {

    private static final String CONFIG_PROPERTY = "server/config/config";

    private static final String HOST = ResourceBundle.getBundle(CONFIG_PROPERTY).getString("db.hostname");
    private static final String DB_NAME = ResourceBundle.getBundle(CONFIG_PROPERTY).getString("db.dbname");
    private static final String MENU_COLLECTION_NAME = ResourceBundle.getBundle(CONFIG_PROPERTY).getString("db.collections.menu");
    private static final String HISTORY_COLLECTION_NAME = ResourceBundle.getBundle(CONFIG_PROPERTY).getString("db.collections.history");

    synchronized public static Menu getMenuFromDatabase(){
        MongoCollection<Collection> collection = getDBCollection(MENU_COLLECTION_NAME, Collection.class);
        return new Menu(new ArrayList<>(getListFromMongoCollection(collection)));
    }

    synchronized public static History getHistoryFromDatabase(){
        MongoCollection<Order> collection = getDBCollection(HISTORY_COLLECTION_NAME, Order.class);
        return new History(new ArrayList<>(getListFromMongoCollection(collection)));
    }

    synchronized public static void putOrderToHistory(Order order){
        MongoCollection<Order> collection = getDBCollection(HISTORY_COLLECTION_NAME, Order.class);
        collection.insertOne(order);
    }

    synchronized private static <T> MongoCollection<T> getDBCollection(final String collectionName,
                                                                       Class<T> tClass){
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoClient mongoClient = new MongoClient(DBManager.HOST, MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());
        MongoDatabase database =  mongoClient.getDatabase(DBManager.DB_NAME).withCodecRegistry(pojoCodecRegistry);
        return database.getCollection(collectionName, tClass);
    }

    synchronized private static <T> ArrayList<T> getListFromMongoCollection(MongoCollection<T> mongoCollection){
        ArrayList<T> list = new ArrayList<>();
        for (T coll:mongoCollection.find()){
            list.add(coll);
        }
        return list;
    }

}
