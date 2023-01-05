package com.amplet.db;

import java.sql.SQLException;
import java.util.ArrayList;
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
    private Dao<DbCarte, Integer> carteDao;
    private Dao<DbPile, Integer> pileDao;
    private Dao<PileDeCartes, Integer> pileDeCartesDao;
    private Dao<DbTag, Integer> tagDao;

    public DatabaseManager() throws SQLException {
        String tempDir = System.getProperty("java.io.tmpdir");
        this.connectionSource =
                new JdbcPooledConnectionSource("jdbc:sqlite:" + tempDir + "/amplet.db");
        TableUtils.createTableIfNotExists(this.connectionSource, DbCarte.class);
        TableUtils.createTableIfNotExists(this.connectionSource, DbPile.class);
        TableUtils.createTableIfNotExists(this.connectionSource, PileDeCartes.class);
        TableUtils.createTableIfNotExists(this.connectionSource, DbTag.class);

        this.carteDao = DaoManager.createDao(this.connectionSource, DbCarte.class);
        this.pileDao = DaoManager.createDao(this.connectionSource, DbPile.class);
        this.pileDeCartesDao = DaoManager.createDao(this.connectionSource, PileDeCartes.class);
        this.tagDao = DaoManager.createDao(this.connectionSource, DbTag.class);
    }

    public DatabaseManager(String dbName) throws SQLException {
        // This class is used for tests, it will reset the database
        this.connectionSource = new JdbcPooledConnectionSource("jdbc:sqlite:" + dbName);
        // Drop the tables to reset the database
        TableUtils.dropTable(connectionSource, DbCarte.class, true);
        TableUtils.dropTable(connectionSource, DbPile.class, true);
        TableUtils.dropTable(connectionSource, PileDeCartes.class, true);
        TableUtils.dropTable(connectionSource, DbTag.class, true);

        TableUtils.createTable(this.connectionSource, DbCarte.class);
        TableUtils.createTable(this.connectionSource, DbPile.class);
        TableUtils.createTable(this.connectionSource, PileDeCartes.class);
        TableUtils.createTable(this.connectionSource, DbTag.class);

        this.carteDao = DaoManager.createDao(this.connectionSource, DbCarte.class);
        this.pileDao = DaoManager.createDao(this.connectionSource, DbPile.class);
        this.pileDeCartesDao = DaoManager.createDao(this.connectionSource, PileDeCartes.class);
        this.tagDao = DaoManager.createDao(this.connectionSource, DbTag.class);
    }

    public void closeConnection() {
        this.connectionSource.closeQuietly();
    }

    // CREATE

    public DbCarte createCarte(String titre, String question, String reponse, String info,
            String metadata) throws SQLException {
        DbCarte carte = new DbCarte(titre, question, reponse, info, metadata);
        this.carteDao.create(carte);
        return carte;
    }

    public DbPile createPile(String titre, String description) throws SQLException {
        DbPile pile = new DbPile(titre, description);
        this.pileDao.create(pile);
        return pile;
    }

    public PileDeCartes addCarteToPile(int carteId, int pileId) throws SQLException {
        DbCarte carte = this.carteDao.queryForId(carteId);
        DbPile pile = this.pileDao.queryForId(pileId);
        PileDeCartes pileDeCartes = new PileDeCartes(carte, pile);
        this.pileDeCartesDao.create(pileDeCartes);
        return pileDeCartes;
    }

    public DbTag addTagToPile(int pileId, String tag) throws SQLException {
        DbPile pile = this.pileDao.queryForId(pileId);
        DbTag dbTag = new DbTag(pile, tag);
        this.tagDao.create(dbTag);
        return dbTag;
    }

    // READ

    public Dao<DbCarte, Integer> getCarteDao() {
        return this.carteDao;
    }

    public Dao<DbPile, Integer> getPileDao() {
        return this.pileDao;
    }

    public List<DbCarte> getCartes() throws SQLException {
        return this.carteDao.queryForAll();
    }

    public List<DbPile> getPiles() throws SQLException {
        return this.pileDao.queryForAll();
    }

    public DbCarte getCarteById(int id) throws SQLException {
        return this.carteDao.queryForId(id);
    }

    public DbPile getPileById(int id) throws SQLException {
        return this.pileDao.queryForId(id);
    }

    public List<DbCarte> getCartesFromPile(DbPile pile) throws SQLException {
        QueryBuilder<PileDeCartes, Integer> pileDeCartesQb = this.pileDeCartesDao.queryBuilder();
        pileDeCartesQb.selectColumns(PileDeCartes.CARTE_ID_FIELD_NAME);
        SelectArg pileSelectArg = new SelectArg();
        pileDeCartesQb.where().eq(PileDeCartes.PILE_ID_FIELD_NAME, pileSelectArg);
        QueryBuilder<DbCarte, Integer> carteQb = this.carteDao.queryBuilder();
        carteQb.where().in(DbCarte.ID_FIELD_NAME, pileDeCartesQb);
        PreparedQuery<DbCarte> preparedCarteQb = carteQb.prepare();
        preparedCarteQb.setArgumentHolderValue(0, pile);
        return this.carteDao.query(preparedCarteQb);
    }

    public List<String> getTagsFromPile(DbPile pile) throws SQLException {
        QueryBuilder<DbTag, Integer> tagQb = this.tagDao.queryBuilder();
        tagQb.selectColumns(DbTag.TAG_FIELD_NAME);
        SelectArg pileSelectArg = new SelectArg();
        tagQb.where().eq(DbTag.PILE_ID_FIELD_NAME, pileSelectArg);
        PreparedQuery<DbTag> preparedTagQb = tagQb.prepare();
        preparedTagQb.setArgumentHolderValue(0, pile);
        List<DbTag> tags = this.tagDao.query(preparedTagQb);
        List<String> tagsString = new ArrayList<String>();
        for (DbTag tag : tags) {
            tagsString.add(tag.getTag());
        }
        return tagsString;
    }

    public List<DbPile> getPilesForCarte(DbCarte carte) throws SQLException {
        QueryBuilder<PileDeCartes, Integer> pileDeCartesQb = this.pileDeCartesDao.queryBuilder();
        pileDeCartesQb.selectColumns(PileDeCartes.PILE_ID_FIELD_NAME);
        SelectArg carteSelectArg = new SelectArg();
        pileDeCartesQb.where().eq(PileDeCartes.CARTE_ID_FIELD_NAME, carteSelectArg);
        QueryBuilder<DbPile, Integer> pileQb = this.pileDao.queryBuilder();
        pileQb.where().in(DbPile.ID_FIELD_NAME, pileDeCartesQb);
        PreparedQuery<DbPile> preparedPileQb = pileQb.prepare();
        preparedPileQb.setArgumentHolderValue(0, carte);
        return this.pileDao.query(preparedPileQb);
    }

    public List<DbPile> getPilesForTag(DbTag tag) throws SQLException {
        QueryBuilder<DbTag, Integer> tagQb = this.tagDao.queryBuilder();
        tagQb.selectColumns(DbTag.PILE_ID_FIELD_NAME);
        SelectArg tagSelectArg = new SelectArg();
        tagQb.where().eq(DbTag.PILE_ID_FIELD_NAME, tagSelectArg);
        QueryBuilder<DbPile, Integer> pileQb = this.pileDao.queryBuilder();
        pileQb.where().in(DbPile.ID_FIELD_NAME, tagQb);
        PreparedQuery<DbPile> preparedPileQb = pileQb.prepare();
        preparedPileQb.setArgumentHolderValue(0, tag);
        return this.pileDao.query(preparedPileQb);
    }

    public int getNbJoueesForCarte(int id) throws SQLException {
        return this.pileDeCartesDao.queryBuilder().where().eq(PileDeCartes.CARTE_ID_FIELD_NAME, id)
                .query().stream().map(pdc -> pdc.getNbJouees()).reduce(0, (a, b) -> a + b);
    }

    public int getNbJustesForCarte(int id) throws SQLException {
        return this.pileDeCartesDao.queryBuilder().where().eq(PileDeCartes.CARTE_ID_FIELD_NAME, id)
                .query().stream().map(pdc -> pdc.getNbJustes()).reduce(0, (a, b) -> a + b);
    }

    // UPDATE

    public DbCarte updateCarteTitre(int id, String titre) throws SQLException {
        DbCarte carte = this.carteDao.queryForId(id);
        carte.setTitre(titre);
        this.carteDao.update(carte);
        return carte;
    }

    public DbCarte updateCarteQuestion(int id, String question) throws SQLException {
        DbCarte carte = this.carteDao.queryForId(id);
        carte.setQuestion(question);
        this.carteDao.update(carte);
        return carte;
    }

    public DbCarte updateCarteReponse(int id, String reponse) throws SQLException {
        DbCarte carte = this.carteDao.queryForId(id);
        carte.setReponse(reponse);
        this.carteDao.update(carte);
        return carte;
    }

    public DbCarte updateCarteInfo(int id, String info) throws SQLException {
        DbCarte carte = this.carteDao.queryForId(id);
        carte.setInfo(info);
        this.carteDao.update(carte);
        return carte;
    }

    public DbCarte updateCarteMetadata(int id, String metadata) throws SQLException {
        DbCarte carte = this.carteDao.queryForId(id);
        carte.setMetadata(metadata);
        this.carteDao.update(carte);
        return carte;
    }

    public DbCarte updateCarteAll(int id, String titre, String question, String reponse,
            String info, String metadata) throws SQLException {
        DbCarte carte = this.carteDao.queryForId(id);
        carte.setTitre(titre);
        carte.setQuestion(question);
        carte.setReponse(reponse);
        carte.setInfo(info);
        carte.setMetadata(metadata);
        this.carteDao.update(carte);
        return carte;
    }

    public DbPile updatePileNom(int id, String nom) throws SQLException {
        DbPile pile = this.pileDao.queryForId(id);
        pile.setNom(nom);
        this.pileDao.update(pile);
        return pile;
    }

    public DbPile updatePileDescription(int id, String description) throws SQLException {
        DbPile pile = this.pileDao.queryForId(id);
        pile.setDescription(description);
        this.pileDao.update(pile);
        return pile;
    }

    public DbPile updatePileAll(int id, String nom, String description) throws SQLException {
        DbPile pile = this.pileDao.queryForId(id);
        pile.setNom(nom);
        pile.setDescription(description);
        this.pileDao.update(pile);
        return pile;
    }

    public void incrementNbJouees(int id) throws SQLException {
        DbPile pile = this.pileDao.queryForId(id);
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
        DbCarte carte = this.carteDao.queryForId(id);
        this.carteDao.delete(carte);
    }

    public void deletePile(int id) throws SQLException {
        DbPile pile = this.pileDao.queryForId(id);
        this.pileDao.delete(pile);
    }

    public void removeCarteFromPile(int carteId, int pileId) throws SQLException {
        DbCarte carte = this.carteDao.queryForId(carteId);
        DbPile pile = this.pileDao.queryForId(pileId);
        QueryBuilder<PileDeCartes, Integer> pileDeCartesQb = this.pileDeCartesDao.queryBuilder();
        pileDeCartesQb.where().eq(PileDeCartes.CARTE_ID_FIELD_NAME, carte.getId()).and()
                .eq(PileDeCartes.PILE_ID_FIELD_NAME, pile.getId());
        PreparedQuery<PileDeCartes> preparedPileDeCartesQb = pileDeCartesQb.prepare();
        List<PileDeCartes> pileDeCartes = this.pileDeCartesDao.query(preparedPileDeCartesQb);
        for (PileDeCartes pileDeCarte : pileDeCartes) {
            this.pileDeCartesDao.delete(pileDeCarte);
        }
    }

    public void removeTagFromPile(int pileId, String tag) throws SQLException {
        DbPile pile = this.pileDao.queryForId(pileId);
        QueryBuilder<DbTag, Integer> tagQb = this.tagDao.queryBuilder();
        tagQb.where().eq(DbTag.TAG_FIELD_NAME, tag).and().eq(DbTag.PILE_ID_FIELD_NAME,
                pile.getId());
        PreparedQuery<DbTag> preparedTagQb = tagQb.prepare();
        List<DbTag> tags = this.tagDao.query(preparedTagQb);
        for (DbTag dbTag : tags) {
            this.tagDao.delete(dbTag);
        }
    }

}
