package org.example.model;

import lombok.AllArgsConstructor;
import org.example.annotation.InjectByType;
import org.example.config.ObjectFactory;
import org.example.interfaces.Announcer;
import org.example.interfaces.Recommendator;

public class ConsoleAnnouncer implements Announcer {

    @InjectByType
    private Recommendator recommendator;

    @Override
    public void announce(String message) {
        System.out.println(message);
        recommendator.recommend();
    }
}
