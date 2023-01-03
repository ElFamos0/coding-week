package com.amplet.app;


public interface Observed {

    public void add_observer(Observer o);

    public void remove_observer(Observer o);

    public void notify_all();

}
