package com.amplet.app;

public class Carte {
    int id;
    String titre;
    String solution;
    String metadata;
    String description;

    public Carte(int id, String titre, String solution, String metadata, String description) {
        this.id = id; // c'est pour tristan il en a besoin pour drag and drop.
        this.titre = titre;
        this.solution = solution;
        this.metadata = metadata;
        this.description = description;
    }

    public Carte() {}

    public String getTitre() {
        return titre;
    }

    public String getSolution() {
        return solution;
    }

    public String getMetadata() {
        return metadata;
    }

    public String getDescription() {
        return description;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
