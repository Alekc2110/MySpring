package org.example.model;

import lombok.AllArgsConstructor;
import org.example.config.ObjectFactory;
import org.example.interfaces.Announcer;
import org.example.interfaces.Recommendator;

@AllArgsConstructor
public class ConsoleAnnouncer implements Announcer {

    private final Recommendator recommendator = ObjectFactory.getInstance().creatObject(Recommendator.class);

    @Override
    public void announce(String message) {
        System.out.println(message);
        recommendator.recommend();
    }
}
