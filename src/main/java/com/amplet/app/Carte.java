package com.amplet.app;

public class Carte {
    private transient Integer id;
    private String titre;
    private String question;
    private String reponse;
    private String metadata;
    private String description;
    private transient int nbJouees = 0;
    private transient int nbSucces = 0;

    public Carte(Integer id, String titre, String question, String reponse, String metadata,
            String description, int nbJouees, int nbSucces) {
        this.id = id; // c'est pour tristan il en a besoin pour drag and drop.
        this.titre = titre;
        this.question = question;
        this.reponse = reponse;
        this.metadata = metadata;
        this.description = description;
        this.nbJouees = nbJouees;
        this.nbSucces = nbSucces;
    }

    public Carte(String titre, String question, String reponse, String metadata,
            String description) {
        this.titre = titre;
        this.question = question;
        this.reponse = reponse;
        this.metadata = metadata;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getQuestion() {
        return question;
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

    public int getNbJouees() {
        return nbJouees;
    }

    public int getNbSucces() {
        return nbSucces;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setQuestion(String question) {
        this.question = question;
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

    public void setNbJouees(int nbJouees) {
        this.nbJouees = nbJouees;
    }

    public void setNbSucces(int nbSucces) {
        this.nbSucces = nbSucces;
    }

}
