package com.amplet.app;

import java.util.ArrayList;

public class Context {

    /* Context for application current mod */

    String currentMod;

    public String getCurrentMod() {
        return currentMod;
    }

    public void setCurrentMod(String currentMod) {
        this.currentMod = currentMod;
    }


    /* Context for creationMod */

    Pile currentPile;

    public Pile getCurrentPile() {
        return currentPile;
    }

    public void setCurrentPile(Pile currentPile) {
        this.currentPile = currentPile;
    }


    Carte currentCarte;

    public Carte getCurrentCarte() {
        return currentCarte;
    }

    public void setCurrentCarte(Carte currentCarte) {
        this.currentCarte = currentCarte;
    }


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


    ArrayList<Pile> selectedPiles;

    public ArrayList<Pile> getSelectedPiles() {
        return selectedPiles;
    }

    public void setSelectedPiles(ArrayList<Pile> selectedPiles) {
        this.selectedPiles = selectedPiles;
    }

    public void addSelectedPile(Pile pile) {
        this.selectedPiles.add(pile);
    }

    public void removeSelectedPile(Integer id) {
        for (Pile pile : selectedPiles) {
            if (pile.getId() == id) {
                selectedPiles.remove(pile);
                break;
            }
        }
    }


    public Context() {

    }

}
