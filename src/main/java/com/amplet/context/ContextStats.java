package com.amplet.context;

import java.util.ArrayList;
import com.amplet.app.Carte;
import com.amplet.app.Pile;

public class ContextStats {
    ArrayList<Pile> piles;
    ArrayList<Carte> cartesSelectionées;
    Pile pileCourante;

    public ContextStats() {
        this.piles = new ArrayList<Pile>();
        this.cartesSelectionées = new ArrayList<Carte>();
        this.pileCourante = null;
    }

    public ArrayList<Pile> getPiles() {
        return piles;
    }

    public void setPiles(ArrayList<Pile> piles) {
        this.piles = piles;
    }

    public ArrayList<Carte> getCartesSelectionées() {
        return cartesSelectionées;
    }

    public void setCartesSelectionées(ArrayList<Carte> cartesSelectionées) {
        this.cartesSelectionées = cartesSelectionées;
    }

    public Pile getPileCourante() {
        return pileCourante;
    }

    public void setPileCourante(Pile pileCourante) {
        this.pileCourante = pileCourante;
    }

    public void setPileCourante(int index) {
        this.pileCourante = this.piles.get(index);
    }

    public void addCarteSelectionée(Carte carte) {
        this.cartesSelectionées.add(carte);
    }

    public ArrayList<String> getPilesNames() {
        ArrayList<String> pilesNames = new ArrayList<String>();
        for (Pile pile : this.piles) {
            pilesNames.add(pile.getNom());
        }
        return pilesNames;
    }

    public void reset() {
        this.piles = new ArrayList<Pile>();
        this.cartesSelectionées = new ArrayList<Carte>();
        this.pileCourante = null;
    }
}
