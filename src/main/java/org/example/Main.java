package org.example;

import org.example.config.ApplicationContext;
import org.example.interfaces.Policeman;
import org.example.model.AngryPoliceman;
import org.example.model.Room;
import org.example.service.CoronaDesinfector;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = ApplicationRunner.run("org.example",
                new HashMap<>(Map.of(Policeman.class, AngryPoliceman.class)));
        CoronaDesinfector desinfector = context.getObject(CoronaDesinfector.class);
        desinfector.start(context.getObject(Room.class));

    }
}