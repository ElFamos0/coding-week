package com.amplet.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import com.amplet.io.apkg.db.Card;
import com.amplet.io.apkg.db.Collection;
import com.amplet.io.apkg.db.Notes;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;

public class ApkgIo {

    private JdbcConnectionSource connectionSource;
    private Dao<Card, Integer> cardDao;
    private Dao<Collection, Integer> collectionDao;
    private Dao<Notes, Integer> notesDao;

    public ApkgIo(String dbPath) throws FileNotFoundException, IOException, SQLException {
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
    }
}
