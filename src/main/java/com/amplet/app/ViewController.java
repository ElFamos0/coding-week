package com.amplet.app;

public abstract class ViewController implements Observer {
    protected Model model;

    public ViewController(Model model) {
        this.model = model;
    }

    public abstract void update();
}
