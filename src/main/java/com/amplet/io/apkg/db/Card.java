package com.amplet.io.apkg.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "cards")
public class Card {
    
    public Card() {
        // ORMLite needs a no-arg constructor
    }

    public Card(int id, int nid, int did, int ord, int mod, int usn, int type, int queue, int due, int ivl, int factor, int reps, int lapses, int left, int odue, int odid, int flags, String data) {
        this.id = id;
        this.nid = nid;
        this.did = did;
        this.ord = ord;
        this.mod = mod;
        this.usn = usn;
        this.type = type;
        this.queue = queue;
        this.due = due;
        this.ivl = ivl;
        this.factor = factor;
        this.reps = reps;
        this.lapses = lapses;
        this.left = left;
        this.odue = odue;
        this.odid = odid;
        this.flags = flags;
        this.data = data;
    }

    @DatabaseField
    private int id;

    @DatabaseField
    private int nid;

    @DatabaseField
    private int did;

    @DatabaseField
    private int ord;

    @DatabaseField
    private int mod;

    @DatabaseField
    private int usn;

    @DatabaseField
    private int type;

    @DatabaseField
    private int queue;

    @DatabaseField
    private int due;

    @DatabaseField
    private int ivl;

    @DatabaseField
    private int factor;

    @DatabaseField
    private int reps;

    @DatabaseField
    private int lapses;

    @DatabaseField
    private int left;

    @DatabaseField
    private int odue;

    @DatabaseField
    private int odid;

    @DatabaseField
    private int flags;

    @DatabaseField
    private String data;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNid() {
        return this.nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public int getDid() {
        return this.did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public int getOrd() {
        return this.ord;
    }

    public void setOrd(int ord) {
        this.ord = ord;
    }

    public int getMod() {
        return this.mod;
    }

    public void setMod(int mod) {
        this.mod = mod;
    }

    public int getUsn() {
        return this.usn;
    }

    public void setUsn(int usn) {
        this.usn = usn;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getQueue() {
        return this.queue;
    }

    public void setQueue(int queue) {
        this.queue = queue;
    }

    public int getDue() {
        return this.due;
    }

    public void setDue(int due) {
        this.due = due;
    }

    public int getIvl() {
        return this.ivl;
    }

    public void setIvl(int ivl) {
        this.ivl = ivl;
    }

    public int getFactor() {
        return this.factor;
    }

    public void setFactor(int factor) {
        this.factor = factor;
    }

    public int getReps() {
        return this.reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getLapses() {
        return this.lapses;
    }

    public void setLapses(int lapses) {
        this.lapses = lapses;
    }

    public int getLeft() {
        return this.left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getOdue() {
        return this.odue;
    }

    public void setOdue(int odue) {
        this.odue = odue;
    }

    public int getOdid() {
        return this.odid;
    }

    public void setOdid(int odid) {
        this.odid = odid;
    }

    public int getFlags() {
        return this.flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }
    
}
