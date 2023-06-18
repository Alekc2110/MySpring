package org.example.model;

import org.example.interfaces.Policeman;

public class PolicemanImpl implements Policeman {
    @Override
    public void makePeopleLeaveRoom() {
        System.out.println("все пошли вон!");
    }
}
