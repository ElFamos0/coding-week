package com.amplet.db;

import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;


@DatabaseTable(tableName = "pile_de_carte")
public class PileDeCartes {

    @DatabaseField(foreign = true)
    private Carte carte;

    @DatabaseField(foreign = true)
    private Pile pile;

    @DatabaseField
    private int nbJouees;

    @DatabaseField
    private int nbJustes;

    public PileDeCartes() {
        // ORMLite needs a no-arg constructor
    }

    public Carte getCarte() {
        return this.carte;
    }

    public Pile getPile() {
        return this.pile;
    }

    public int getNbJouees() {
        return this.nbJouees;
    }

    public int getNbJustes() {
        return this.nbJustes;
    }
    
}
