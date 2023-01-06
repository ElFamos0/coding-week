package com.amplet.app;

import com.amplet.context.*;

public class Context {
    // All the context variables are defined here
    public ContextEnt contextEnt = new ContextEnt();
    public ContextIg contextIg = new ContextIg();
    public ContextResultat contextResultat = new ContextResultat();
    public ContextStats contextStats = new ContextStats();
    public ContextEdit contextEdit = new ContextEdit();

    /* Context for ApprParam */

    boolean isRandomSelected;

    public boolean isRandomSelected() {
        return isRandomSelected;
    }

    public void setRandomSelected(boolean isRandomSelected) {
        this.isRandomSelected = isRandomSelected;
    }


    boolean isFavorisedFailedSelected;

    public boolean isFavorisedFailedSelected() {
        return isFavorisedFailedSelected;
    }

    public void setFavorisedFailedSelected(boolean isFavorisedFailedSelected) {
        this.isFavorisedFailedSelected = isFavorisedFailedSelected;
    }


    int repetitionProbability;

    public int getRepetitionProbability() {
        return repetitionProbability;
    }

    public void setRepetitionProbability(int repetitionProbability) {
        this.repetitionProbability = repetitionProbability;
    }


    int nbCartes;

    public int getNbCartes() {
        return nbCartes;
    }

    public void setNbCartes(int nbCartes) {
        this.nbCartes = nbCartes;
    }


    int tempsReponse;

    public int getTempsReponse() {
        return tempsReponse;
    }

    public void setTempsReponse(int tempsReponse) {
        this.tempsReponse = tempsReponse;
    }

    public Context() {

    }

}
