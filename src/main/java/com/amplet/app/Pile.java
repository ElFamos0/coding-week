package com.amplet.app;

import java.util.ArrayList;

public class Pile {
    private transient Integer id;
    private String nom;
    private String description;
    private ArrayList<String> tags;
    private ArrayList<Carte> cartes;

    public Pile(Integer id, String nom, String description) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.cartes = new ArrayList<Carte>();
        this.tags = new ArrayList<String>();
    }

    public Pile(String nom, String description) {
        this.nom = nom;
        this.description = description;
        this.cartes = new ArrayList<Carte>();
        this.tags = new ArrayList<String>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Pile pile = (Pile) o;

        if (id != null ? !id.equals(pile.id) : pile.id != null)
            return false;
        if (nom != null ? !nom.equals(pile.nom) : pile.nom != null)
            return false;
        if (description != null ? !description.equals(pile.description)
                : pile.description != null)
            return false;
        if (tags != null ? !tags.equals(pile.tags) : pile.tags != null)
            return false;
        return cartes != null ? cartes.equals(pile.cartes) : pile.cartes == null;
    }

    public void addCarte(Carte carte) {
        this.cartes.add(carte);
    }

    public void removeCarte(Carte carte) {
        this.cartes.remove(carte);
    }

    public void addTag(String tag) {
        this.tags.add(tag);
    }

    public void removeTag(String tag) {
        this.tags.remove(tag);
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

    public ArrayList<String> getTags() {
        return tags;
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
