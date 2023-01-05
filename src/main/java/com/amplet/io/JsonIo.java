package com.amplet.io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.Buffer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
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
        return this.gson.toJson(pile);
    }

    public String dumpString(Carte carte) {
        return this.gson.toJson(carte);
    }

    public <T> T loadString(String json, Type type) throws JsonSyntaxException {
        if (type != Pile.class && type != Carte.class)
            throw new IllegalArgumentException("Type must be Pile or Carte");
        return this.gson.fromJson(json, type);
    }

    public void export(Pile pile) throws IOException {
        String output = this.gson.toJson(pile, Pile.class);
        BufferedWriter writer = new BufferedWriter(new FileWriter(this.filePath));
        writer.write(output);
        writer.close();
    }

    public void export(Carte carte) throws IOException {
        String output = this.gson.toJson(carte, Carte.class);
        BufferedWriter writer = new BufferedWriter(new FileWriter(this.filePath));
        writer.write(output);
        writer.close();
    }

    public <T> T importFromFile(Type type) throws IOException {
        if (type != Pile.class && type != Carte.class)
            throw new IllegalArgumentException("Type must be Pile or Carte");
        return this.gson.fromJson(new java.io.FileReader(this.filePath), type);
    }

}
