package com.amplet.db;

import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;


@DatabaseTable(tableName = "carte")
public class Carte {

    @DatabaseField(generatedId = true)
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

    public Carte() {
        // ORMLite needs a no-arg constructor
    }

    public int getId() {
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

    public String getInfo() {
        return info;
    }

    public String getMetadata() {
        return metadata;
    }

}
