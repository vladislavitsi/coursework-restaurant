package com.vladi.restaurant.server.connection;

import java.util.List;

public interface Subscribable <T>{
    void update(List<T> t);

    void close();
}
