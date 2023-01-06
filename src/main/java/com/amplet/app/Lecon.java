package com.amplet.app;

import java.util.ArrayList;

public class Lecon {
    private Integer id;
    private String nom;
    private ArrayList<Pile> piles;
    private ArrayList<String> tags;

    public Lecon(Integer id, String nom) {
        this.id = id;
        this.nom = nom;
        this.piles = new ArrayList<>();
        this.tags = new ArrayList<>();
    }

    public Lecon(String nom) {
        this.nom = nom;
        this.piles = new ArrayList<>();
        this.tags = new ArrayList<>();
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public ArrayList<Pile> getPiles() {
        return piles;
    }

    public void setPiles(ArrayList<Pile> piles) {
        this.piles = piles;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void addPile(Pile pile) {
        if (piles == null) {
            piles = new ArrayList<>();
        }
        piles.add(pile);
    }

    public void addTag(String tag) {
        if (tags == null) {
            tags = new ArrayList<>();
        }
        tags.add(tag);
    }

    public void removePile(Pile pile) {
        if (piles != null) {
            piles.remove(pile);
        }
    }

    public void removeTag(String tag) {
        if (tags != null) {
            tags.remove(tag);
        }
    }

    public Boolean hasTag(String tag) {
        if (tags != null) {
            return tags.contains(tag);
        }
        return false;
    }
}
