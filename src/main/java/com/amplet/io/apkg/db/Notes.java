package com.amplet.io.apkg.db;

import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "notes")
public class Notes {

    public Notes() {
        // ORMLite needs a no-arg constructor
    }

    public Notes(int id, String guid, int mid, int mod, int usn, String tags, String flds, int sfld, int csum,
            int flags, String data) {
        this.id = id;
        this.guid = guid;
        this.mid = mid;
        this.mod = mod;
        this.usn = usn;
        this.tags = tags;
        this.flds = flds;
        this.sfld = sfld;
        this.csum = csum;
        this.flags = flags;
        this.data = data;
    }

    @DatabaseField
    private int id;

    @DatabaseField
    private String guid;

    @DatabaseField
    private int mid;

    @DatabaseField
    private int mod;

    @DatabaseField
    private int usn;

    @DatabaseField
    private String tags;

    @DatabaseField
    private String flds;

    @DatabaseField
    private int sfld;

    @DatabaseField
    private int csum;

    @DatabaseField
    private int flags;

    @DatabaseField
    private String data;

    List<String> fields = new ArrayList<String>();

    public int getId() {
        return id;
    }

    public String getGuid() {
        return guid;
    }

    public int getMid() {
        return mid;
    }

    public int getMod() {
        return mod;
    }

    public int getUsn() {
        return usn;
    }

    public String getTags() {
        return tags;
    }

    public String getFlds() {
        return flds;
    }

    public int getSfld() {
        return sfld;
    }

    public int getCsum() {
        return csum;
    }

    public int getFlags() {
        return flags;
    }

    public String getData() {
        return data;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public void setMod(int mod) {
        this.mod = mod;
    }

    public void setUsn(int usn) {
        this.usn = usn;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setFlds(String flds) {
        this.flds = flds;
    }

    public void setSfld(int sfld) {
        this.sfld = sfld;
    }

    public void setCsum(int csum) {
        this.csum = csum;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public void setData(String data) {
        this.data = data;
    }

}
