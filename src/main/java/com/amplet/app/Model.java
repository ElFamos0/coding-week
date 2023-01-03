package com.amplet.app;

import java.util.ArrayList;

public class Model implements Observed {
    ArrayList<Pile> allpiles;
    ArrayList<Carte> allcartes;
    ArrayList<Observer> observers;

    public Model() {

        allpiles = new ArrayList<Pile>();
        allcartes = new ArrayList<Carte>();
        observers = new ArrayList<Observer>();

    }

    public void addObserver(Observer o) {
        observers.add(o);
    }

    public void notifyAllObservers() {

    }

    public void mangemoilepoiro() {
        System.out.println("mangemoilepoiro");
    }

}
