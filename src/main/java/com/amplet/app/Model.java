package com.amplet.app;

import java.sql.SQLException;
import java.util.ArrayList;
import com.amplet.db.DatabaseManager;


public class Model implements Observed {
    ArrayList<Pile> allPiles;
    ArrayList<Carte> allCartes;
    ArrayList<Observer> observers;
    Context ctx;
    private DatabaseManager dbManager;

    public Model() throws SQLException {

        allPiles = new ArrayList<Pile>();
        allCartes = new ArrayList<Carte>();
        observers = new ArrayList<Observer>();
        ctx = new Context();

        this.dbManager = new DatabaseManager();

        this.initModel();
    }

    void initModel() throws SQLException {
        this.dbManager.getCartes()
                .forEach(dbCarte -> this.allCartes
                        .add(new Carte(dbCarte.getId(), dbCarte.getTitre(), dbCarte.getQuestion(),
                                dbCarte.getReponse(), dbCarte.getMetadata(), dbCarte.getInfo())));
        this.dbManager.getPiles().forEach(dbPile -> {
            Pile pile = new Pile(dbPile.getId(), dbPile.getNom(), dbPile.getDescription());
            try {
                this.dbManager.getCartesFromPile(dbPile)
                        .forEach(dbCarte -> pile.addCarte(new Carte(dbCarte.getId(),
                                dbCarte.getTitre(), dbCarte.getQuestion(), dbCarte.getReponse(),
                                dbCarte.getMetadata(), dbCarte.getInfo())));
            } catch (SQLException ex) {
                System.err.println("SQL Exception " + ex);
            }
            this.allPiles.add(pile);
        });
    }

    public void addObserver(Observer o) {
        // Check if the class is already in the list of observers
        for (Observer observer : observers) {
            if (observer.getName() == o.getName()) {
                // Remove the old observer
                observers.remove(observer);
                break;
            }
        }

        observers.add(o);
    }

    public void notifyAllObservers() {

    }

    public void mangemoilepoiro() {
        System.out.println("mangemoilepoiro");
    }

    public void create(Pile pile) throws SQLException {
        this.dbManager.createPile(pile.getNom(), pile.getDescription());
        this.allPiles.add(pile);
    }

    public void create(Carte carte) throws SQLException {
        this.dbManager.createCarte(carte.getTitre(), carte.getQuestion(), carte.getReponse(),
                carte.getDescription(), carte.getMetadata());
        this.allCartes.add(carte);
    }

    public void create(Pile pile, Carte carte) throws SQLException {
        this.dbManager.addCarteToPile(carte.getId(), pile.getId());
    }

    public void delete(Pile pile) throws SQLException {
        this.dbManager.deletePile(pile.getId());
        this.allPiles.remove(pile);
    }

    public void delete(Carte carte) throws SQLException {
        this.dbManager.deleteCarte(carte.getId());
        this.allCartes.remove(carte);
    }

    public void delete(Pile pile, Carte carte) throws SQLException {
        this.dbManager.removeCarteFromPile(carte.getId(), pile.getId());
    }

    public void update(Carte carte) throws SQLException {
        this.dbManager.updateCarteAll(carte.getId(), carte.getTitre(), carte.getQuestion(),
                carte.getReponse(), carte.getDescription(), carte.getMetadata());
    }

    public void update(Pile pile) throws SQLException {
        this.dbManager.updatePileAll(pile.getId(), pile.getNom(), pile.getDescription());
    }

    public ArrayList<Pile> getAllPiles() {
        return allPiles;
    }

    public ArrayList<Carte> getAllCartes() {
        return allCartes;
    }

    public ArrayList<Observer> getObservers() {
        return observers;
    }

    public Context getCtx() {
        return ctx;
    }

    public DatabaseManager getDbManager() {
        return dbManager;
    }
}

