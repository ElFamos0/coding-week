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


    ArrayList<Carte> selectedCartes;

    public ArrayList<Carte> getSelectedCartes() {
        return selectedCartes;
    }

    public void resetSelectedCartes() {
        this.selectedCartes = new ArrayList<Carte>();
    }


    public void addSelectedCarte(Carte carte) {
        this.selectedCartes.add(carte);
    }

    public void removeSelectedCarte(Integer id) {
        for (Carte carte : selectedCartes) {
            if (carte.getId() == id) {
                selectedCartes.remove(carte);
                break;
            }
        }
    }

    ArrayList<Integer> selectedCartesPileId;

    public ArrayList<Integer> getSelectedCartesPileId() {
        return selectedCartesPileId;
    }

    public void resetSelectedCartesPileId() {
        this.selectedCartesPileId = new ArrayList<Integer>();
    }


    public void addSelectedCartePileId(Integer id) {
        this.selectedCartesPileId.add(id);
    }

    public void removeSelectedCartePileId(Integer pileId) {
        this.selectedCartesPileId.remove(pileId);
    }

    /* Context for ApprResult */

    ArrayList<Carte> playedCartes;

    public ArrayList<Carte> getPlayedCartes() {
        return playedCartes;
    }


    ArrayList<Boolean> playedReponse;

    public ArrayList<Boolean> getPlayedReponse() {
        return playedReponse;
    }

    public void resetPlayed() {
        playedCartes = new ArrayList<Carte>();
        playedReponse = new ArrayList<Boolean>();
        playedCartesPileId = new ArrayList<Integer>();
    }

    public void addPlayed(Carte c, Boolean b, Integer pileId) {
        playedCartes.add(c);
        playedReponse.add(b);
        playedCartesPileId.add(pileId);
    }

    ArrayList<Integer> playedCartesPileId;

    public ArrayList<Integer> getPlayedCartesPileId() {
        return playedCartesPileId;
    }



    public Context() {

    }

}
