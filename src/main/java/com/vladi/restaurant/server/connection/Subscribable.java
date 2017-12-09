package com.vladi.restaurant.server.connection;

import java.util.SortedMap;

public interface Subscribable <K, V>{
    void update(SortedMap<K, V> t);

    void close();
}
