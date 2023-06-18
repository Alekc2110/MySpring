package org.example.model;

import org.example.interfaces.Policeman;

public class AngryPoliceman implements Policeman {
    @Override
    public void makePeopleLeaveRoom() {
        System.out.println("Вон цуко или всех перестреляю!");
    }
}
