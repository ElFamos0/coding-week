package com.amplet.db;

import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;

@DatabaseTable(tableName = "lecon")
public class DbLecon {

    public final static String ID_FIELD_NAME = "id";
    public final static String NOM_FIELD_NAME = "nom";

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String nom = "";

    public DbLecon() {
        // ORMLite needs a no-arg constructor
    }

    public DbLecon(String nom) {
        this.nom = nom;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
