package org.example;

import org.example.config.ObjectFactory;
import org.example.model.Room;
import org.example.service.CoronaDesinfector;

public class Main {
    public static void main(String[] args) {
        CoronaDesinfector coronaDesinfector = ObjectFactory.getInstance().creatObject(CoronaDesinfector.class);
        coronaDesinfector.start(new Room());
    }
}