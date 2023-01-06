package com.amplet.context;

import java.util.ArrayList;
import com.amplet.app.Carte;
import com.amplet.app.Pile;

public class ContextIg {
    private ArrayList<Carte> cartesProposées;
    private ArrayList<Integer> cartesProposéesIdPile;
    private ArrayList<Carte> cartesApprouvées;
    private ArrayList<Carte> cartesRejetées;
    private Carte carteCourante;
    private Integer carteCouranteIdPile;
    private int nbCartesPassées;

    public int getNbCartesPassées() {
        return nbCartesPassées;
    }

    public void setNbCartesPassées(int nbCartesPassées) {
        this.nbCartesPassées = nbCartesPassées;
    }

    public ContextIg() {
        this.cartesProposées = new ArrayList<Carte>();
        this.cartesProposéesIdPile = new ArrayList<Integer>();
        this.cartesApprouvées = new ArrayList<Carte>();
        this.cartesRejetées = new ArrayList<Carte>();
        this.nbCartesPassées = 0;
    }

    public ArrayList<Carte> getCartesProposées() {
        return cartesProposées;
    }

    public void setCartesProposées(ArrayList<Carte> selectedCartes) {
        this.cartesProposées = selectedCartes;
    }

    public void addCartesProposées(Carte carte, Integer idPile) {
        this.cartesProposées.add(carte);
        this.cartesProposéesIdPile.add(idPile);
    }

    public ArrayList<Carte> getCartesRejetées() {
        return cartesRejetées;
    }

    public void setCartesRejetées(ArrayList<Carte> cartesRejetées) {
        this.cartesRejetées = cartesRejetées;
    }

    public void removeCartesRejetées(Carte carte) {
        this.cartesRejetées.remove(carte);
    }

    public void addCartesRejetées(Carte carte) {
        this.cartesRejetées.add(carte);
    }

    public void removeCartesProposées(Carte carte) {
        this.cartesProposées.remove(carte);
    }

    public ArrayList<Carte> getCartesApprouvées() {
        return cartesApprouvées;
    }

    public void setCartesApprouvées(ArrayList<Carte> cartesApprouvées) {
        this.cartesApprouvées = cartesApprouvées;
    }

    public void addCarteChoisie(Carte carte) {
        this.cartesApprouvées.add(carte);
    }

    public void removeCarteChoisie(Carte carte) {
        this.cartesApprouvées.remove(carte);
    }

    public Carte getCarteCourante() {
        return carteCourante;
    }

    public void setCarteCourante(Carte carteCourante) {
        this.carteCourante = carteCourante;
    }

    public Integer getCarteCouranteIdPile() {
        return carteCouranteIdPile;
    }

    public void setCarteCourante(Carte carteCourante, Integer idPile) {
        this.carteCourante = carteCourante;
        this.carteCouranteIdPile = idPile;
    }

    public void setCarteCouranteRandom() {
        int randomIndex = (int) (Math.random() * cartesProposées.size());
        this.carteCourante = cartesProposées.get(randomIndex);
        this.carteCouranteIdPile = cartesProposéesIdPile.get(randomIndex);
        // on supprime de la liste des cartes proposées la carte courante
        cartesProposées.remove(randomIndex);
        cartesProposéesIdPile.remove(randomIndex);
    }

    public void setCarteCouranteNext() {
        this.carteCourante = cartesProposées.get(0);
        this.carteCouranteIdPile = cartesProposéesIdPile.get(0);
        // on supprime de la liste des cartes proposées la carte courante
        cartesProposées.remove(0);
        cartesProposéesIdPile.remove(0);
    }

    public void approuverCarteCourante() {
        cartesApprouvées.add(carteCourante);
    }

    public void rejeterCarteCourante() {
        cartesApprouvées.add(carteCourante);
    }

    public Boolean ilResteDesCartes(int id) {
        return id - this.getNbCartesPassées() > 0;
    }

    public Integer getNbCartesApprouvées() {
        return cartesApprouvées.size();
    }

    public Integer getNbCartesProposées() {
        return cartesProposées.size();
    }

    public ArrayList<Integer> getCartesProposéesIdPile() {
        return cartesProposéesIdPile;
    }

    public void setCartesProposéesIdPile(ArrayList<Integer> cartesProposéesIdPile) {
        this.cartesProposéesIdPile = cartesProposéesIdPile;
    }

    public void addCartesProposéesIdPile(Integer idPile) {
        this.cartesProposéesIdPile.add(idPile);
    }

    public void removeCartesProposéesIdPile(Integer idPile) {
        this.cartesProposéesIdPile.remove(idPile);
    }

    public Boolean hasCarteProposée(Carte carte) {
        for (Carte c : cartesProposées) {
            if (c.getId() == carte.getId()) {
                return true;
            }
        }
        return false;
    }

    public void ajoutePiles(ArrayList<Pile> piles) {
        reset();
        for (Pile p : piles) {
            for (Carte c : p.getCartes()) {
                // On vérifie que la carte n'est pas déjà dans la liste
                if (!hasCarteProposée(c)) {
                    addCartesProposées(c, p.getId());
                }
            }
        }
    }

    public void ajoutePilesJeu(ArrayList<Pile> piles) {
        reset();
        for (Pile p : piles) {
            p.setNbJouees(p.getNbJouees() + 1);
            for (Carte c : p.getCartes()) {
                // On vérifie que la carte n'est pas déjà dans la liste
                if (!hasCarteProposée(c)) {
                    addCartesProposées(c, p.getId());
                }
            }
        }
    }

    public void reset() {
        this.cartesProposées = new ArrayList<Carte>();
        this.cartesProposéesIdPile = new ArrayList<Integer>();
        this.cartesApprouvées = new ArrayList<Carte>();
        this.carteCourante = null;
        this.carteCouranteIdPile = null;
        this.nbCartesPassées = 0;
    }
}
