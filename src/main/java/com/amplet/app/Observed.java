package com.amplet.app;


public interface Observed {

    public void addObserver(Observer o);

    public void notifyAllObservers();

}
