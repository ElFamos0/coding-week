package com.amplet.context;

import java.util.HashMap;
import java.util.Map;
import com.amplet.app.Carte;
import com.amplet.app.Lecon;
import com.amplet.app.Pile;

public class ContextEdit {
    Pile pileCourante;
    Carte carteCourante;
    Lecon leconCourante;
    private Map<String, Carte> cartes;

    public ContextEdit() {
        cartes = new HashMap<>();
    }

    public Pile getPileCourante() {
        return pileCourante;
    }

    public void setPileCourante(Pile pileCourante) {
        this.pileCourante = pileCourante;
    }

    public Carte getCarteCourante() {
        return carteCourante;
    }

    public void setCarteCourante(Carte carteCourante) {
        this.carteCourante = carteCourante;
    }

    public Map<String, Carte> getCartes() {
        return cartes;
    }

    public Carte getCarte(String nom) {
        return cartes.get(nom);
    }

    public void putCarte(String nom, Carte carte) {
        cartes.put(nom, carte);
    }

    public void resetCartes() {
        cartes = new HashMap<>();
    }

    public Lecon getLeconCourante() {
        return leconCourante;
    }

    public void setLeconCourante(Lecon leconCourante) {
        this.leconCourante = leconCourante;
    }

    public void setCartes(Map<String, Carte> cartes) {
        this.cartes = cartes;
    }
}
