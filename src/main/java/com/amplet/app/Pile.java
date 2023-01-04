package com.amplet.app;

import java.util.ArrayList;

public class Pile {
    Integer id;
    String nom;
    String description;
    ArrayList<Carte> cartes;

    public Pile(Integer id, String nom, String description) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.cartes = new ArrayList<Carte>();
    }

    public void addCarte(Carte carte) {
        this.cartes.add(carte);
    }

    public void removeCarte(Carte carte) {
        this.cartes.remove(carte);
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Carte> getCartes() {
        return cartes;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCartes(ArrayList<Carte> cartes) {
        this.cartes = cartes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
