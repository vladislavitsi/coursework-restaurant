package com.vladi.restaurant.server.control;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.vladi.restaurant.common.pojo.Collection;
import com.vladi.restaurant.common.pojo.Menu;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.io.*;
import java.util.ArrayList;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class DBManager {
    public static Menu getMenuFromDatabase(){
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoClient mongoClient = new MongoClient("localhost", MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());
        MongoDatabase database = mongoClient.getDatabase("restaurant").withCodecRegistry(pojoCodecRegistry);

        MongoCollection<Collection> collection = database.getCollection("menu", Collection.class);
        ArrayList<Collection> collections = new ArrayList<>();
        for (Collection coll:collection.find()){
            collections.add(coll);
        }
         return new Menu(new ArrayList<>(collections));
    }

    public static void main(String[] args) {
        Gson gson = new Gson();
        File file1 = new File("1.txt");
        File file2 = new File("2.txt");
        try (BufferedWriter w1 = new BufferedWriter(new FileWriter(file1));
            ObjectOutputStream w2 = new ObjectOutputStream(new FileOutputStream(file2))){
            w1.write(gson.toJson(getMenuFromDatabase()));
            w2.writeObject(getMenuFromDatabase());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
