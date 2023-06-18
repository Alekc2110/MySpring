package org.example;

import org.example.model.Room;
import org.example.service.CoronaDesinfector;

public class Main {
    public static void main(String[] args) {
        CoronaDesinfector coronaDesinfector = new CoronaDesinfector();
        coronaDesinfector.start(new Room());
    }
}