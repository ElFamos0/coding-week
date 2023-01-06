package com.amplet.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import com.amplet.io.apkg.ApkgDeck;
import com.amplet.io.apkg.ApkgModel;
import com.amplet.io.apkg.db.Card;
import com.amplet.io.apkg.db.Collection;
import com.amplet.io.apkg.db.Notes;
import com.amplet.io.apkg.json.DeckDeserializer;
import com.amplet.io.apkg.json.ModelDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;

public class ApkgIo {

    private JdbcConnectionSource connectionSource;
    private Dao<Card, Integer> cardDao;
    private Dao<Collection, Integer> collectionDao;
    private Dao<Notes, Integer> notesDao;

    private List<Card> cards = new ArrayList<Card>();
    private Collection collection;
    private List<Notes> notes = new ArrayList<Notes>();
    private Map<Integer, ApkgModel> models = new HashMap<Integer, ApkgModel>();
    private Map<Integer, ApkgDeck> decks = new HashMap<Integer, ApkgDeck>();

    private Gson deckGson = new GsonBuilder().registerTypeAdapter(List.class, new DeckDeserializer()).create();
    private Gson modelGson = new GsonBuilder().registerTypeAdapter(List.class, new ModelDeserializer()).create();

    public ApkgIo(String dbPath) throws Exception {
        // Extract the database from the .apkg file
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(dbPath));
        byte[] buffer = new byte[1024];
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        while (zipEntry != null) {
            if (zipEntry.getName().equals("collection.anki21")) {
                String tempDir = System.getProperty("java.io.tmpdir");
                FileOutputStream fileOutputStream =
                        new FileOutputStream(tempDir + "/collection.anki21");
                int len;
                while ((len = zipInputStream.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, len);
                }
                fileOutputStream.close();
                this.connectionSource =
                        new JdbcConnectionSource("jdbc:sqlite:" + tempDir + "/collection.anki21");
                this.cardDao = DaoManager.createDao(connectionSource, Card.class);
                this.collectionDao = DaoManager.createDao(connectionSource, Collection.class);
                this.notesDao = DaoManager.createDao(connectionSource, Notes.class);
                break;
            }
            zipEntry = zipInputStream.getNextEntry();
        }
        zipInputStream.closeEntry();
        zipInputStream.close();

        // Load the collection
        this.collection = collectionDao.queryForFirst();

        // Load the cards
        this.cards = cardDao.queryForAll();

        // Load the notes
        this.notes = notesDao.queryForAll();

        // Get the models from the collection
        String modelsJson = collection.getModels();
        ((List<ApkgModel>) modelGson.fromJson(modelsJson, List.class)).forEach(model -> {
            this.models.put(model.getId(), model);
        });

        // Get the decks from the collection
        String decksJson = collection.getDecks();
        ((List<ApkgDeck>) deckGson.fromJson(decksJson, List.class)).forEach(deck -> {
            this.decks.put(deck.getId(), deck);
        });

        // Close the connection
        this.connectionSource.close();
    }
}
