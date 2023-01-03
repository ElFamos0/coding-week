package com.amplet.db;

import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;


@DatabaseTable(tableName = "carte")
public class Carte {

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

    public Carte() {
        // ORMLite needs a no-arg constructor
    }

    public int getId() {
        return this.id;
    }

    public String getTitre() {
        return this.titre;
    }

    public String getQuestion() {
        return this.question;
    }

    public String getReponse() {
        return this.reponse;
    }

    public String getInfo() {
        return this.info;
    }

    public String getMetadata() {
        return metadata;
    }

}
