package com.amplet.views;

import com.amplet.app.Model;
import com.amplet.app.ViewController;

public class ListePile extends ViewController {
    public ListePile(Model model) {
        super(model);
        model.addObserver(this);
    }

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

}
