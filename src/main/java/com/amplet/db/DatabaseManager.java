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
        this.connectionSource = new JdbcPooledConnectionSource("jdbc:sqlite:" + dbName);
        TableUtils.createTableIfNotExists(this.connectionSource, Carte.class);
        TableUtils.createTableIfNotExists(this.connectionSource, Pile.class);
        TableUtils.createTableIfNotExists(this.connectionSource, PileDeCartes.class);

        this.carteDao = DaoManager.createDao(this.connectionSource, Carte.class);
        this.pileDao = DaoManager.createDao(this.connectionSource, Pile.class);
        this.pileDeCartesDao = DaoManager.createDao(this.connectionSource, PileDeCartes.class);
    }

    public void closeConnection() {
        this.connectionSource.closeQuietly();
    }

    // CREATE

    public void createCarte(String titre, String question, String reponse, String info,
            String metadata) throws SQLException {
        Carte carte = new Carte(titre, question, reponse, info, metadata);
        this.carteDao.create(carte);
    }

    public void createPile(String titre, String description) throws SQLException {
        Pile pile = new Pile(titre, description);
        this.pileDao.create(pile);
    }

    public void addCarteToPile(Carte carte, Pile pile) throws SQLException {
        PileDeCartes pileDeCartes = new PileDeCartes(carte, pile);
        this.pileDeCartesDao.create(pileDeCartes);
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

    public void updateCarteTitre(int id, String titre) throws SQLException {
        Carte carte = this.carteDao.queryForId(id);
        carte.setTitre(titre);
        this.carteDao.update(carte);
    }

    public void updateCarteQuestion(int id, String question) throws SQLException {
        Carte carte = this.carteDao.queryForId(id);
        carte.setQuestion(question);
        this.carteDao.update(carte);
    }

    public void updateCarteReponse(int id, String reponse) throws SQLException {
        Carte carte = this.carteDao.queryForId(id);
        carte.setReponse(reponse);
        this.carteDao.update(carte);
    }

    public void updateCarteInfo(int id, String info) throws SQLException {
        Carte carte = this.carteDao.queryForId(id);
        carte.setInfo(info);
        this.carteDao.update(carte);
    }

    public void updateCarteMetadata(int id, String metadata) throws SQLException {
        Carte carte = this.carteDao.queryForId(id);
        carte.setMetadata(metadata);
        this.carteDao.update(carte);
    }

    public void updatePileNom(int id, String nom) throws SQLException {
        Pile pile = this.pileDao.queryForId(id);
        pile.setNom(nom);
        this.pileDao.update(pile);
    }

    public void updatePileDescription(int id, String description) throws SQLException {
        Pile pile = this.pileDao.queryForId(id);
        pile.setDescription(description);
        this.pileDao.update(pile);
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

    public void deleteCarte(Carte carte) throws SQLException {
        this.carteDao.delete(carte);
    }

    public void deletePile(Pile pile) throws SQLException {
        this.pileDao.delete(pile);
    }

    public void removeCarteFromPile(Carte carte, Pile pile) throws SQLException {
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
