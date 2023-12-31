package org.example.service;

import org.example.annotation.InjectByType;
import org.example.annotation.Singleton;
import org.example.interfaces.Announcer;
import org.example.interfaces.Policeman;
import org.example.model.Room;

@Singleton
public class CoronaDesinfector {

    @InjectByType
    private Announcer announcer;
    @InjectByType
    private Policeman policeman;

    public CoronaDesinfector() {
        System.out.println("corona desinfector created*****");
    }

    public void start(Room room) {
        announcer.announce("надо выйти из помещения");
        policeman.makePeopleLeaveRoom();
        disinfect(room);
        announcer.announce("рискните вернуться, вроде короны не видно");
    }

    private void disinfect(Room room) {
        System.out.println("зачитывается молитва: 'корона изыди!)'");
    }
}
