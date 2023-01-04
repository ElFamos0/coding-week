package com.amplet.db;

import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;


@DatabaseTable(tableName = "carte")
public class DbCarte {

    public final static String ID_FIELD_NAME = "id";

    @DatabaseField(generatedId = true, columnName = ID_FIELD_NAME)
    private int id;

    @DatabaseField
    private String titre;

    @DatabaseField
    private String question;

    @DatabaseField
    private String reponse;

    @DatabaseField
    private String info;

    @DatabaseField
    private String metadata;

    public DbCarte() {
        // ORMLite needs a no-arg constructor
    }

    public DbCarte(String titre, String question, String reponse, String info, String metadata) {
        this.titre = titre;
        this.question = question;
        this.reponse = reponse;
        this.info = info;
        this.metadata = metadata;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return this.titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getQuestion() {
        return this.question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getReponse() {
        return this.reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

}
