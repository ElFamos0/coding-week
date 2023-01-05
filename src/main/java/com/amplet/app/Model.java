package com.amplet.app;

import java.sql.SQLException;
import java.util.ArrayList;
import com.amplet.db.DatabaseManager;
import com.amplet.db.DbCarte;
import com.amplet.db.DbPile;


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
        // this.dbManager.getCartes().forEach(dbCarte -> {
        // try {
        // this.allCartes.add(new Carte(dbCarte.getId(), dbCarte.getTitre(),
        // dbCarte.getQuestion(), dbCarte.getReponse(), dbCarte.getMetadata(),
        // dbCarte.getInfo(), this.dbManager.getNbJoueesForCarte(dbCarte.getId()),
        // this.dbManager.getNbJustesForCarte(dbCarte.getId())));
        // } catch (SQLException ex) {
        // System.err.println("SQL Exception " + ex);
        // }
        // });
        this.dbManager.getPiles().forEach(dbPile -> {
            Pile pile = new Pile(dbPile.getId(), dbPile.getNom(), dbPile.getDescription(),
                    dbPile.getNbJouees());
            try {
                this.dbManager.getCartesFromPile(dbPile).forEach(dbCarte -> {
                    try {
                        pile.addCarte(new Carte(dbCarte.getId(), dbCarte.getTitre(),
                                dbCarte.getQuestion(), dbCarte.getReponse(), dbCarte.getMetadata(),
                                dbCarte.getInfo(),
                                this.dbManager.getNbJoueesForCarte(dbCarte.getId()),
                                this.dbManager.getNbJustesForCarte(dbCarte.getId())));
                    } catch (SQLException ex) {
                        System.err.println("SQL Exception " + ex);
                    }
                });
                this.dbManager.getTagsFromPile(dbPile).forEach(dbTag -> pile.addTag(dbTag));
            } catch (SQLException ex) {
                System.err.println("SQL Exception " + ex);
            }
            this.allPiles.add(pile);
        });
        generateAllCartes();
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
        for (Observer o : observers) {
            o.update();
        }
    }

    public void create(Pile pile) throws SQLException {
        DbPile dbPile = this.dbManager.createPile(pile.getNom(), pile.getDescription());
        pile.setId(dbPile.getId());
        this.allPiles.add(pile);
        notifyAllObservers();
    }

    public void create(Carte carte) throws SQLException {
        DbCarte dbCarte = this.dbManager.createCarte(carte.getTitre(), carte.getQuestion(),
                carte.getReponse(), carte.getDescription(), carte.getMetadata());
        carte.setId(dbCarte.getId());
        this.allCartes.add(carte);
        notifyAllObservers();
    }

    public void create(Pile pile, Carte carte) throws SQLException {
        this.dbManager.addCarteToPile(carte.getId(), pile.getId());
        pile.addCarte(carte);
        notifyAllObservers();
    }

    public void create(Pile pile, String tag) throws SQLException {
        this.dbManager.addTagToPile(pile.getId(), tag);
        pile.addTag(tag);
        notifyAllObservers();
    }

    public void delete(Pile pile) throws SQLException {
        this.dbManager.deletePile(pile.getId());
        this.allPiles.remove(pile);
        notifyAllObservers();
    }

    public void delete(Carte carte) throws SQLException {
        this.dbManager.deleteCarte(carte.getId());
        this.allCartes.remove(carte);
        notifyAllObservers();
    }

    public void delete(Pile pile, Carte carte) throws SQLException {
        this.dbManager.removeCarteFromPile(carte.getId(), pile.getId());
        pile.removeCarte(carte);
        notifyAllObservers();
    }

    public void delete(Pile pile, String tag) throws SQLException {
        this.dbManager.removeTagFromPile(pile.getId(), tag);
        pile.removeTag(tag);
        notifyAllObservers();
    }

    public void update(Carte carte) throws SQLException {
        this.dbManager.updateCarteAll(carte.getId(), carte.getTitre(), carte.getQuestion(),
                carte.getReponse(), carte.getDescription(), carte.getMetadata());
        notifyAllObservers();
    }

    public void update(Pile pile) throws SQLException {
        this.dbManager.updatePileAll(pile.getId(), pile.getNom(), pile.getDescription());
        notifyAllObservers();
    }

    public void update(Carte carte, Pile pile, boolean reussite) throws SQLException {
        if (reussite) {
            this.dbManager.addCarteSucces(carte.getId(), pile.getId());
        } else {
            this.dbManager.addCarteEchec(carte.getId(), pile.getId());
        }
        notifyAllObservers();
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

    public Pile getPileById(int i) {
        Pile p = null;
        for (Pile pile : allPiles) {
            if (pile.getId() == i) {
                p = pile;
            }
        }
        return p;
    }

    public void getPilesByTags(ArrayList<Pile> arrayPiles, ArrayList<String> arrayTags) {

        ArrayList<Pile> piles = new ArrayList<Pile>(allPiles);

        for (String tag : arrayTags) {
            ArrayList<Pile> toRemove = new ArrayList<Pile>();
            for (Pile p : piles) {
                boolean state = false;
                for (String pileTag : p.getTags()) {
                    if (tag.equals(pileTag)) {
                        state = true;
                        break;
                    }
                }

                if (!state) {
                    toRemove.add(p);
                }
            }
            for (Pile p : toRemove) {
                piles.remove(p);
            }
        }

        for (Pile p : piles) {
            arrayPiles.add(p);
        }

    }

    public void generateAllCartes() throws SQLException {

        for (Pile p : allPiles) {
            for (Carte c : p.getCartes()) {
                if (!(isElementInArrayList(allCartes, c))) {
                    allCartes.add(c);
                }
            }
        }
        this.dbManager.getCartes().forEach(dbCarte -> {
            try {
                Carte test_carte = new Carte(dbCarte.getId(), dbCarte.getTitre(),
                        dbCarte.getQuestion(), dbCarte.getReponse(), dbCarte.getMetadata(),
                        dbCarte.getInfo(), this.dbManager.getNbJoueesForCarte(dbCarte.getId()),
                        this.dbManager.getNbJustesForCarte(dbCarte.getId()));
                if (!(isIdContained(allCartes, test_carte.getId()))) {
                    allCartes.add(test_carte);
                }
            } catch (SQLException ex) {
                System.err.println("SQL Exception " + ex);
            }
        });

    }

    public <T> boolean isElementInArrayList(ArrayList<T> array, T element) {

        for (T array_element : array) {
            if (element.equals(array_element)) {
                return true;
            }
        }
        return false;
    }

    public boolean isIdContained(ArrayList<Carte> array, int id) {

        for (Carte array_element : array) {
            if (id == array_element.getId()) {
                return true;
            }
        }
        return false;
    }
}

