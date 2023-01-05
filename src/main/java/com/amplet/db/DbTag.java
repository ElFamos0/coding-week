package com.amplet.db;

import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;

@DatabaseTable(tableName = "tag_de_pile")
public class DbTag {

    public final static String ID_FIELD_NAME = "id";
    public final static String PILE_ID_FIELD_NAME = "pile_id";
    public final static String TAG_FIELD_NAME = "tag";

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true, columnName = PILE_ID_FIELD_NAME)
    private DbPile pile;

    @DatabaseField(columnName = TAG_FIELD_NAME)
    private String tag;

    public DbTag() {
        // ORMLite needs a no-arg constructor
    }

    public DbTag(DbPile pile, String tag) {
        this.pile = pile;
        this.tag = tag;
    }

    public int getId() {
        return this.id;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public DbPile getPile() {
        return this.pile;
    }

    public void setPile(DbPile pile) {
        this.pile = pile;
    }
}
