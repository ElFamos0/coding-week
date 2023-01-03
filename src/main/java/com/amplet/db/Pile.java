package com.amplet.db;

import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;

@DatabaseTable(tableName = "pile")
public class Pile {

    public final static String ID_FIELD_NAME = "id";

    @DatabaseField(generatedId = true, columnName = ID_FIELD_NAME)
    private int id;

    @DatabaseField
    private String nom;

    @DatabaseField
    private String description;

    @DatabaseField
    private int nbJouees;

    public Pile() {
        // ORMLite needs a no-arg constructor
    }

    public int getId() {
        return this.id;
    }

    public String getNom() {
        return this.nom;
    }

    public String getDescription() {
        return this.description;
    }

    public int getNbJouees() {
        return this.nbJouees;
    }

}
