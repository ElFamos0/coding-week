package com.amplet.io.apkg.json;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import com.amplet.io.apkg.ApkgDeck;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class DeckDeserializer implements JsonDeserializer<List<ApkgDeck>>{

    @Override
    public List<ApkgDeck> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        Map<String, JsonElement> jsonMap = jsonObject.asMap();
        Collection<JsonElement> decks = jsonMap.values();
        List<ApkgDeck> apkgDecks = new ArrayList<ApkgDeck>();
        decks.forEach(deck -> {
            JsonObject jsonObjectDeck = deck.getAsJsonObject();
            ApkgDeck apkgDeck = new ApkgDeck(jsonObjectDeck.getAsJsonObject().get("id").getAsInt(),
                    jsonObjectDeck.getAsJsonObject().get("name").getAsString(),
                    jsonObjectDeck.getAsJsonObject().get("desc").getAsString());
            apkgDecks.add(apkgDeck);
        });
        return apkgDecks;
    }
    
}
