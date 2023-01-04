package com.amplet.db;

import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;


@DatabaseTable(tableName = "pile_de_carte")
public class PileDeCartes {

    public final static String CARTE_ID_FIELD_NAME = "carte_id";
    public final static String PILE_ID_FIELD_NAME = "pile_id";

    @DatabaseField(foreign = true, columnName = CARTE_ID_FIELD_NAME)
    private DbCarte carte;

    @DatabaseField(foreign = true, columnName = PILE_ID_FIELD_NAME)
    private DbPile pile;

    @DatabaseField
    private int nbJouees = 0;

    @DatabaseField
    private int nbJustes = 0;

    public PileDeCartes() {
        // ORMLite needs a no-arg constructor
    }

    public PileDeCartes(DbCarte carte, DbPile pile) {
        this.carte = carte;
        this.pile = pile;
    }

    public DbCarte getCarte() {
        return this.carte;
    }

    public void setCarte(DbCarte carte) {
        this.carte = carte;
    }

    public DbPile getPile() {
        return this.pile;
    }

    public void setPile(DbPile pile) {
        this.pile = pile;
    }

    public int getNbJouees() {
        return this.nbJouees;
    }

    public void setNbJouees(int nbJouees) {
        this.nbJouees = nbJouees;
    }

    public int getNbJustes() {
        return this.nbJustes;
    }

    public void setNbJustes(int nbJustes) {
        this.nbJustes = nbJustes;
    }

}
