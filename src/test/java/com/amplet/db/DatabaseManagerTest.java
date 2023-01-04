package com.amplet.db;

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
        dbManager.createCarte("titre", "question", "reponse", "info", "metadata");
    }

    @Test
    public void testCreatePile() throws SQLException {
        dbManager.createPile("titre", "description");
    }

}
