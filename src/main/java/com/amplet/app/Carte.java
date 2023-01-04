package com.amplet.app;

public class Carte {
    int id;
    String titre;
    String question;
    String reponse;
    String metadata;
    String description;

    public Carte(int id, String titre, String question, String reponse, String metadata, String description) {
        this.id = id; // c'est pour tristan il en a besoin pour drag and drop.
        this.titre = titre;
        this.reponse = reponse;
        this.metadata = metadata;
        this.description = description;
    }

    public Carte() {}

    public String getTitre() {
        return titre;
    }

    public String getReponse() {
        return reponse;
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

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
