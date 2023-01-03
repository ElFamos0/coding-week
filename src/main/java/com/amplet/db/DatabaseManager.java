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

    public void closeConnection() {
        this.connectionSource.closeQuietly();
    }

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

}
