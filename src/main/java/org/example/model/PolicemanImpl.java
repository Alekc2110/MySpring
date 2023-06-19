package org.example.model;

import org.example.annotation.InjectByType;
import org.example.interfaces.Policeman;
import org.example.interfaces.Recommendator;

import javax.annotation.PostConstruct;


public class PolicemanImpl implements Policeman {
    @InjectByType
    private Recommendator recommendator;

    public PolicemanImpl() {
        System.out.println("PolicemanImpl was created");
    }

    @PostConstruct
    public void init() {
       System.out.println("recommendator object: " + recommendator.getClass());
    }

    @Override
    public void makePeopleLeaveRoom() {
        System.out.println("все пошли вон!");
    }
}
