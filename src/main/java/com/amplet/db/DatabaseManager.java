package com.amplet.db;

import java.sql.SQLException;
import java.util.List;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.table.TableUtils;

public class DatabaseManager {

    private JdbcPooledConnectionSource connectionSource;
    private Dao<Carte, Integer> carteDao;
    private Dao<Pile, Integer> pileDao;
    private Dao<PileDeCartes, Integer> pileDeCartesDao;

    public DatabaseManager() throws SQLException {
        String tempDir = System.getProperty("java.io.tmpdir");
        this.connectionSource =
                new JdbcPooledConnectionSource("jdbc:sqlite:" + tempDir + "/amplet.db");
        TableUtils.createTableIfNotExists(this.connectionSource, Carte.class);
        TableUtils.createTableIfNotExists(this.connectionSource, Pile.class);
        TableUtils.createTableIfNotExists(this.connectionSource, PileDeCartes.class);

        this.carteDao = DaoManager.createDao(this.connectionSource, Carte.class);
        this.pileDao = DaoManager.createDao(this.connectionSource, Pile.class);
        this.pileDeCartesDao = DaoManager.createDao(this.connectionSource, PileDeCartes.class);
    }

    public DatabaseManager(String dbName) throws SQLException {
        // This class is used for tests, it will reset the database
        this.connectionSource = new JdbcPooledConnectionSource("jdbc:sqlite:" + dbName);
        // Drop the tables to reset the database
        TableUtils.dropTable(connectionSource, Carte.class, true);
        TableUtils.dropTable(connectionSource, Pile.class, true);
        TableUtils.dropTable(connectionSource, PileDeCartes.class, true);

        TableUtils.createTable(this.connectionSource, Carte.class);
        TableUtils.createTable(this.connectionSource, Pile.class);
        TableUtils.createTable(this.connectionSource, PileDeCartes.class);

        this.carteDao = DaoManager.createDao(this.connectionSource, Carte.class);
        this.pileDao = DaoManager.createDao(this.connectionSource, Pile.class);
        this.pileDeCartesDao = DaoManager.createDao(this.connectionSource, PileDeCartes.class);
    }

    public void closeConnection() {
        this.connectionSource.closeQuietly();
    }

    // CREATE

    public Carte createCarte(String titre, String question, String reponse, String info,
            String metadata) throws SQLException {
        Carte carte = new Carte(titre, question, reponse, info, metadata);
        this.carteDao.create(carte);
        return carte;
    }

    public Pile createPile(String titre, String description) throws SQLException {
        Pile pile = new Pile(titre, description);
        this.pileDao.create(pile);
        return pile;
    }

    public PileDeCartes addCarteToPile(int carteId, int pileId) throws SQLException {
        Carte carte = this.carteDao.queryForId(carteId);
        Pile pile = this.pileDao.queryForId(pileId);
        PileDeCartes pileDeCartes = new PileDeCartes(carte, pile);
        this.pileDeCartesDao.create(pileDeCartes);
        return pileDeCartes;
    }

    // READ

    public Dao<Carte, Integer> getCarteDao() {
        return this.carteDao;
    }

    public Dao<Pile, Integer> getPileDao() {
        return this.pileDao;
    }

    public List<Carte> getCartes() throws SQLException {
        return this.carteDao.queryForAll();
    }

    public List<Pile> getPiles() throws SQLException {
        return this.pileDao.queryForAll();
    }

    public Carte getCarteById(int id) throws SQLException {
        return this.carteDao.queryForId(id);
    }

    public Pile getPileById(int id) throws SQLException {
        return this.pileDao.queryForId(id);
    }

    public List<Carte> getCartesFromPile(Pile pile) throws SQLException {
        QueryBuilder<PileDeCartes, Integer> pileDeCartesQb = this.pileDeCartesDao.queryBuilder();
        pileDeCartesQb.selectColumns(PileDeCartes.CARTE_ID_FIELD_NAME);
        SelectArg pileSelectArg = new SelectArg();
        pileDeCartesQb.where().eq(PileDeCartes.PILE_ID_FIELD_NAME, pileSelectArg);
        QueryBuilder<Carte, Integer> carteQb = this.carteDao.queryBuilder();
        carteQb.where().in(Carte.ID_FIELD_NAME, pileDeCartesQb);
        PreparedQuery<Carte> preparedCarteQb = carteQb.prepare();
        preparedCarteQb.setArgumentHolderValue(0, pile);
        return this.carteDao.query(preparedCarteQb);
    }

    public List<Pile> getPilesForCarte(Carte carte) throws SQLException {
        QueryBuilder<PileDeCartes, Integer> pileDeCartesQb = this.pileDeCartesDao.queryBuilder();
        pileDeCartesQb.selectColumns(PileDeCartes.PILE_ID_FIELD_NAME);
        SelectArg carteSelectArg = new SelectArg();
        pileDeCartesQb.where().eq(PileDeCartes.CARTE_ID_FIELD_NAME, carteSelectArg);
        QueryBuilder<Pile, Integer> pileQb = this.pileDao.queryBuilder();
        pileQb.where().in(Pile.ID_FIELD_NAME, pileDeCartesQb);
        PreparedQuery<Pile> preparedPileQb = pileQb.prepare();
        preparedPileQb.setArgumentHolderValue(0, carte);
        return this.pileDao.query(preparedPileQb);
    }

    // UPDATE

    public Carte updateCarteTitre(int id, String titre) throws SQLException {
        Carte carte = this.carteDao.queryForId(id);
        carte.setTitre(titre);
        this.carteDao.update(carte);
        return carte;
    }

    public Carte updateCarteQuestion(int id, String question) throws SQLException {
        Carte carte = this.carteDao.queryForId(id);
        carte.setQuestion(question);
        this.carteDao.update(carte);
        return carte;
    }

    public Carte updateCarteReponse(int id, String reponse) throws SQLException {
        Carte carte = this.carteDao.queryForId(id);
        carte.setReponse(reponse);
        this.carteDao.update(carte);
        return carte;
    }

    public Carte updateCarteInfo(int id, String info) throws SQLException {
        Carte carte = this.carteDao.queryForId(id);
        carte.setInfo(info);
        this.carteDao.update(carte);
        return carte;
    }

    public Carte updateCarteMetadata(int id, String metadata) throws SQLException {
        Carte carte = this.carteDao.queryForId(id);
        carte.setMetadata(metadata);
        this.carteDao.update(carte);
        return carte;
    }

    public Carte updateCarteAll(int id, String titre, String question, String reponse, String info,
            String metadata) throws SQLException {
        Carte carte = this.carteDao.queryForId(id);
        carte.setTitre(titre);
        carte.setQuestion(question);
        carte.setReponse(reponse);
        carte.setInfo(info);
        carte.setMetadata(metadata);
        this.carteDao.update(carte);
        return carte;
    }

    public Pile updatePileNom(int id, String nom) throws SQLException {
        Pile pile = this.pileDao.queryForId(id);
        pile.setNom(nom);
        this.pileDao.update(pile);
        return pile;
    }

    public Pile updatePileDescription(int id, String description) throws SQLException {
        Pile pile = this.pileDao.queryForId(id);
        pile.setDescription(description);
        this.pileDao.update(pile);
        return pile;
    }

    public Pile updatePileAll(int id, String nom, String description) throws SQLException {
        Pile pile = this.pileDao.queryForId(id);
        pile.setNom(nom);
        pile.setDescription(description);
        this.pileDao.update(pile);
        return pile;
    }

    public void incrementNbJouees(int id) throws SQLException {
        Pile pile = this.pileDao.queryForId(id);
        pile.setNbJouees(pile.getNbJouees() + 1);
        this.pileDao.update(pile);
    }

    public void addCarteSucces(int carteId, int pileId) throws SQLException {
        QueryBuilder<PileDeCartes, Integer> pileDeCartesQb = this.pileDeCartesDao.queryBuilder();
        pileDeCartesQb.where().eq(PileDeCartes.CARTE_ID_FIELD_NAME, carteId).and()
                .eq(PileDeCartes.PILE_ID_FIELD_NAME, pileId);
        PreparedQuery<PileDeCartes> preparedPileDeCartesQb = pileDeCartesQb.prepare();
        List<PileDeCartes> pileDeCartes = this.pileDeCartesDao.query(preparedPileDeCartesQb);
        for (PileDeCartes pileDeCarte : pileDeCartes) {
            pileDeCarte.setNbJustes(pileDeCarte.getNbJustes() + 1);
            pileDeCarte.setNbJouees(pileDeCarte.getNbJouees() + 1);
            this.pileDeCartesDao.update(pileDeCarte);
        }
    }

    public void addCarteEchec(int carteId, int pileId) throws SQLException {
        QueryBuilder<PileDeCartes, Integer> pileDeCartesQb = this.pileDeCartesDao.queryBuilder();
        pileDeCartesQb.where().eq(PileDeCartes.CARTE_ID_FIELD_NAME, carteId).and()
                .eq(PileDeCartes.PILE_ID_FIELD_NAME, pileId);
        PreparedQuery<PileDeCartes> preparedPileDeCartesQb = pileDeCartesQb.prepare();
        List<PileDeCartes> pileDeCartes = this.pileDeCartesDao.query(preparedPileDeCartesQb);
        for (PileDeCartes pileDeCarte : pileDeCartes) {
            pileDeCarte.setNbJouees(pileDeCarte.getNbJouees() + 1);
            this.pileDeCartesDao.update(pileDeCarte);
        }
    }

    // DELETE

    public void deleteCarte(int id) throws SQLException {
        Carte carte = this.carteDao.queryForId(id);
        this.carteDao.delete(carte);
    }

    public void deletePile(int id) throws SQLException {
        Pile pile = this.pileDao.queryForId(id);
        this.pileDao.delete(pile);
    }

    public void removeCarteFromPile(int carteId, int pileId) throws SQLException {
        Carte carte = this.carteDao.queryForId(carteId);
        Pile pile = this.pileDao.queryForId(pileId);
        QueryBuilder<PileDeCartes, Integer> pileDeCartesQb = this.pileDeCartesDao.queryBuilder();
        pileDeCartesQb.where().eq(PileDeCartes.CARTE_ID_FIELD_NAME, carte.getId()).and()
                .eq(PileDeCartes.PILE_ID_FIELD_NAME, pile.getId());
        PreparedQuery<PileDeCartes> preparedPileDeCartesQb = pileDeCartesQb.prepare();
        List<PileDeCartes> pileDeCartes = this.pileDeCartesDao.query(preparedPileDeCartesQb);
        for (PileDeCartes pileDeCarte : pileDeCartes) {
            this.pileDeCartesDao.delete(pileDeCarte);
        }
    }

}
