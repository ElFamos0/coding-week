package com.amplet.app;

import java.sql.SQLException;
import java.util.ArrayList;
import com.amplet.db.DatabaseManager;


public class Model implements Observed {
    ArrayList<Pile> allPiles;
    ArrayList<Carte> allCartes;
    ArrayList<Observer> observers;
    Context ctx;

    public Model() throws SQLException {

        allPiles = new ArrayList<Pile>();
        allCartes = new ArrayList<Carte>();
        observers = new ArrayList<Observer>();
        ctx = new Context();

        this.initModel();
    }

    void initModel() throws SQLException {
        DatabaseManager dbManager = new DatabaseManager();
        dbManager.getCartes()
                .forEach(dbCarte -> this.allCartes
                        .add(new Carte(dbCarte.getId(), dbCarte.getTitre(), dbCarte.getQuestion(),
                                dbCarte.getReponse(), dbCarte.getMetadata(), dbCarte.getInfo())));
        dbManager.getPiles().forEach(dbPile -> {
            Pile pile = new Pile(dbPile.getId(), dbPile.getNom(), dbPile.getDescription());
            try {
                dbManager.getCartesFromPile(dbPile)
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

}
