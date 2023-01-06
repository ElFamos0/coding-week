package com.amplet.db;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DatabaseManagerTest {

    private static DatabaseManager dbManager;


    @BeforeAll
    public static void setup() throws SQLException {
        dbManager = new DatabaseManager("test.db");
    }

    @AfterAll
    public static void teardown() {
        dbManager.closeConnection();
    }

    @Test
    public void testCreateCarte() throws SQLException {
        dbManager.createCarte("titre1", "question1", "reponse1", "info1", "metadata1");
    }

    @Test
    public void testCreatePile() throws SQLException {
        dbManager.createPile("titre1", "description1");
    }

    @Test
    public void testCreatePileWithCarte() throws SQLException {
        DbPile dbPile = dbManager.createPile("titre2", "description2");
        DbCarte dbCarte =
                dbManager.createCarte("titre2", "question2", "reponse2", "info2", "metadata2");
        dbManager.addCarteToPile(dbPile.getId(), dbCarte.getId());
    }

    @Test
    public void testCreatePileWithCarteAndTag() throws SQLException {
        DbPile dbPile = dbManager.createPile("titre3", "description3");
        int dbPileId = dbPile.getId();
        dbManager.createCarte("titre3", "question3", "reponse3", "info3", "metadata3");
        dbManager.addTagToPile(dbPileId, "tag3");
    }

}
