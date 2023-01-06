package com.amplet.db;

import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;

@DatabaseTable(tableName = "lecon_tags")
public class DbLeconTags {

    public final static String ID_FIELD_NAME = "id";
    public final static String ID_LECON_FIELD_NAME = "id_lecon";
    public final static String TAG_FIELD_NAME = "tag";

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true, columnName = ID_LECON_FIELD_NAME)
    private DbLecon lecon;

    @DatabaseField(columnName = TAG_FIELD_NAME)
    private String tag;

    public DbLeconTags() {
        // ORMLite needs a no-arg constructor
    }

    public DbLeconTags(DbLecon lecon, String tag) {
        this.lecon = lecon;
        this.tag = tag;
    }

    public static String getIdFieldName() {
        return ID_FIELD_NAME;
    }

    public static String getIdLeconFieldName() {
        return ID_LECON_FIELD_NAME;
    }

    public static String getTagFieldName() {
        return TAG_FIELD_NAME;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DbLecon getLecon() {
        return lecon;
    }

    public void setLecon(DbLecon lecon) {
        this.lecon = lecon;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
