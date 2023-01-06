package com.amplet.context;

import java.util.ArrayList;
import com.amplet.app.Carte;

public class ContextResultat {
    ArrayList<Carte> cartesJouées;
    ArrayList<Integer> cartesJouéesIdPile;
    ArrayList<Boolean> cartesJouéesValide;

    public ContextResultat() {
        this.cartesJouées = new ArrayList<Carte>();
        this.cartesJouéesIdPile = new ArrayList<Integer>();
        this.cartesJouéesValide = new ArrayList<Boolean>();
    }

    public ArrayList<Carte> getCartesJouées() {
        return cartesJouées;
    }

    public void setCartesJouées(ArrayList<Carte> cartesJouées) {
        this.cartesJouées = cartesJouées;
    }

    public ArrayList<Integer> getCartesJouéesIdPile() {
        return cartesJouéesIdPile;
    }

    public void setCartesJouéesIdPile(ArrayList<Integer> cartesJouéesIdPile) {
        this.cartesJouéesIdPile = cartesJouéesIdPile;
    }

    public ArrayList<Boolean> getCartesJouéesValide() {
        return cartesJouéesValide;
    }

    public void setCartesJouéesValide(ArrayList<Boolean> cartesJouéesValide) {
        this.cartesJouéesValide = cartesJouéesValide;
    }

    public void addCarteJouée(Carte carte, Integer idPile, Boolean valide) {
        this.cartesJouées.add(carte);
        this.cartesJouéesIdPile.add(idPile);
        this.cartesJouéesValide.add(valide);
    }

    public void reset() {
        this.cartesJouées = new ArrayList<Carte>();
        this.cartesJouéesIdPile = new ArrayList<Integer>();
        this.cartesJouéesValide = new ArrayList<Boolean>();
    }
}
