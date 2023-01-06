package com.amplet.io.apkg.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "col")
public class Collection {

    public Collection() {
        // ORMLite needs a no-arg constructor
    }

    public Collection(int id, int crt, int mod, int scm, int ver, int dty, int usn, int ls, String conf, String models, String decks, String dconf, String tags) {
        this.id = id;
        this.crt = crt;
        this.mod = mod;
        this.scm = scm;
        this.ver = ver;
        this.dty = dty;
        this.usn = usn;
        this.ls = ls;
        this.conf = conf;
        this.models = models;
        this.decks = decks;
        this.dconf = dconf;
        this.tags = tags;
    }

    @DatabaseField
    private int id;

    @DatabaseField
    private int crt;

    @DatabaseField
    private int mod;

    @DatabaseField
    private int scm;

    @DatabaseField
    private int ver;

    @DatabaseField
    private int dty;

    @DatabaseField
    private int usn;

    @DatabaseField
    private int ls;

    @DatabaseField
    private String conf;

    @DatabaseField
    private String models;

    @DatabaseField
    private String decks;

    @DatabaseField
    private String dconf;

    @DatabaseField
    private String tags;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCrt() {
        return this.crt;
    }

    public void setCrt(int crt) {
        this.crt = crt;
    }

    public int getMod() {
        return this.mod;
    }

    public void setMod(int mod) {
        this.mod = mod;
    }

    public int getScm() {
        return this.scm;
    }

    public void setScm(int scm) {
        this.scm = scm;
    }

    public int getVer() {
        return this.ver;
    }

    public void setVer(int ver) {
        this.ver = ver;
    }

    public int getDty() {
        return this.dty;
    }

    public void setDty(int dty) {
        this.dty = dty;
    }

    public int getUsn() {
        return this.usn;
    }

    public void setUsn(int usn) {
        this.usn = usn;
    }

    public int getLs() {
        return this.ls;
    }

    public void setLs(int ls) {
        this.ls = ls;
    }

    public String getConf() {
        return this.conf;
    }

    public void setConf(String conf) {
        this.conf = conf;
    }

    public String getModels() {
        return this.models;
    }

    public void setModels(String models) {
        this.models = models;
    }

    public String getDecks() {
        return this.decks;
    }

    public void setDecks(String decks) {
        this.decks = decks;
    }

    public String getDconf() {
        return this.dconf;
    }

    public void setDconf(String dconf) {
        this.dconf = dconf;
    }

    public String getTags() {
        return this.tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
    
}
