package com.amplet.db;

import java.sql.SQLException;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseManager {

    private JdbcPooledConnectionSource connectionSource;

    public DatabaseManager() throws SQLException {
        String tempDir = System.getProperty("java.io.tmpdir");
        this.connectionSource =
                new JdbcPooledConnectionSource("jdbc:sqlite:" + tempDir + "/amplet.db");
        TableUtils.createTableIfNotExists(this.connectionSource, Carte.class);
        TableUtils.createTableIfNotExists(this.connectionSource, Pile.class);
        TableUtils.createTableIfNotExists(this.connectionSource, PileDeCartes.class);
    }

    public void closeConnection() {
        this.connectionSource.closeQuietly();
    }

}
