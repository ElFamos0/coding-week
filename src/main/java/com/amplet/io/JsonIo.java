package com.amplet.io;

import java.io.File;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.amplet.app.Carte;
import com.amplet.app.Pile;

public class JsonIo {

    private String filePath;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public JsonIo(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String dumpString(Pile pile) {
        return gson.toJson(pile);
    }

    public String dumpString(Carte carte) {
        return gson.toJson(carte);
    }

}
