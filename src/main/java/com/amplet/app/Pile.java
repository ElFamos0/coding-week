package com.amplet.app;

import java.util.ArrayList;

public class Pile {
    private transient Integer id;
    private String nom;
    private String description;
    private ArrayList<String> tags = new ArrayList<String>();
    private ArrayList<Carte> cartes = new ArrayList<Carte>();
    private transient int nbJouees = 0;

    public Pile(Integer id, String nom, String description, int nbJouees) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.nbJouees = nbJouees;
    }

    public Pile(String nom, String description) {
        this.nom = nom;
        this.description = description;
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
        if (description != null ? !description.equals(pile.description) : pile.description != null)
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

    public int getNbJouees() {
        return nbJouees;
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

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public void setNbJouees(int nbJouees) {
        this.nbJouees = nbJouees;
    }

    public boolean hasTag(String tag) {
        for (String t : tags) {
            if (t.equals(tag)) {
                return true;
            }
        }

        return false;
    }

}
