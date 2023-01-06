package com.amplet.app;

import com.amplet.context.ContextEnt;
import com.amplet.context.ContextIg;
import com.amplet.context.ContextResultat;
import com.amplet.context.ContextStats;

public abstract class ViewController implements Observer {
    protected Model model;
    protected Context ctx;
    protected ContextEnt ctxEnt;
    protected ContextIg ctxIg;
    protected ContextResultat ctxResultat;
    protected ContextStats ctxStats;

    public ViewController(Model model) {
        this.model = model;
        this.ctx = model.ctx;
        this.ctxEnt = ctx.contextEnt;
        this.ctxIg = ctx.contextIg;
        this.ctxResultat = ctx.contextResultat;
        this.ctxStats = ctx.contextStats;
    }

    public ViewController(Model model, Object... args) {
        this.model = model;
        this.ctx = model.ctx;
        this.ctxEnt = ctx.contextEnt;
        this.ctxIg = ctx.contextIg;
        this.ctxResultat = ctx.contextResultat;
        this.ctxStats = ctx.contextStats;
    }

    public abstract void update();
}
