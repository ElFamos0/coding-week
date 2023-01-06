package com.amplet.io;

import org.junit.jupiter.api.Test;
import com.amplet.app.Carte;
import com.amplet.app.Pile;
import com.google.gson.JsonSyntaxException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Assertions;

public class JsonIoTest {

    @Test
    public void testDumpCarteString() {
        JsonIo jsonIo = new JsonIo("");
        Carte carte = new Carte("titre", "question", "reponse", "metadata", "description");
        String expected =
                "{\n  \"titre\": \"titre\",\n  \"question\": \"question\",\n  \"reponse\": \"reponse\",\n  \"metadata\": \"metadata\",\n  \"description\": \"description\"\n}";
        String actual = jsonIo.dumpString(carte);
        assertEquals(expected, actual);
    }

    @Test
    public void testDumpPileString() {
        JsonIo jsonIo = new JsonIo("");
        Pile pile = new Pile("nom", "description");
        String expected =
                "{\n  \"nom\": \"nom\",\n  \"description\": \"description\",\n  \"tags\": [],\n  \"cartes\": []\n}";
        String actual = jsonIo.dumpString(pile);
        assertEquals(expected, actual);
    }

    @Test
    public void loadCarteFromString() {
        JsonIo jsonIo = new JsonIo("");
        String json =
                "{\n  \"titre\": \"titre\",\n  \"question\": \"question\",\n  \"reponse\": \"reponse\",\n  \"metadata\": \"metadata\",\n  \"description\": \"description\"\n}";
        Carte expected = new Carte("titre", "question", "reponse", "metadata", "description");
        Carte actual = jsonIo.loadString(json, Carte.class);
        assertEquals(expected, actual);
    }

    @Test
    public void testLoadCarteFromStringException() {
        JsonIo jsonIo = new JsonIo("");
        Assertions.assertThrows(JsonSyntaxException.class, () -> {
            jsonIo.loadString("{", Carte.class);
        });
    }

    @Test
    public void loadPileFromString() {
        JsonIo jsonIo = new JsonIo("");
        String json =
                "{\n  \"nom\": \"nom\",\n  \"description\": \"description\",\n  \"tags\": [],\n  \"cartes\": []\n}";
        Pile expected = new Pile("nom", "description");
        Pile actual = jsonIo.loadString(json, Pile.class);
        assertEquals(expected, actual);
    }

    @Test
    public void loadPileWithCardFromString() {
        JsonIo json = new JsonIo("");
        String jsonPile =
                "{\n  \"nom\": \"nom\",\n  \"description\": \"description\",\n  \"tags\": [],\n  \"cartes\": [\n    {\n      \"titre\": \"titre\",\n      \"question\": \"question\",\n      \"reponse\": \"reponse\",\n      \"metadata\": \"metadata\",\n      \"description\": \"description\"\n    }\n  ]\n}";
        Pile expected = new Pile("nom", "description");
        expected.addCarte(new Carte("titre", "question", "reponse", "metadata", "description"));
        Pile actual = json.loadString(jsonPile, Pile.class);
        assertEquals(expected, actual);
    }

    @Test
    public void loadPileWithTagFromString() {
        JsonIo json = new JsonIo("");
        String jsonPile =
                "{\n  \"nom\": \"nom\",\n  \"description\": \"description\",\n  \"tags\": [\n    \"tag1\",\n    \"tag2\"\n  ],\n  \"cartes\": []\n}";
        Pile expected = new Pile("nom", "description");
        expected.addTag("tag1");
        expected.addTag("tag2");
        Pile actual = json.loadString(jsonPile, Pile.class);
        assertEquals(expected, actual);
    }

    @Test
    public void loadPileWithCarteAndTagFromString() {
        JsonIo json = new JsonIo("");
        String jsonPile =
                "{\n  \"nom\": \"nom\",\n  \"description\": \"description\",\n  \"tags\": [\n    \"tag1\",\n    \"tag2\"\n  ],\n  \"cartes\": [\n    {\n      \"titre\": \"titre\",\n      \"question\": \"question\",\n      \"reponse\": \"reponse\",\n      \"metadata\": \"metadata\",\n      \"description\": \"description\"\n    }\n  ]\n}";
        Pile expected = new Pile("nom", "description");
        expected.addTag("tag1");
        expected.addTag("tag2");
        expected.addCarte(new Carte("titre", "question", "reponse", "metadata", "description"));
        Pile actual = json.loadString(jsonPile, Pile.class);
        assertEquals(expected, actual);
    }

    @Test
    public void testLoadPileStringException() {
        JsonIo jsonIo = new JsonIo("");
        Assertions.assertThrows(JsonSyntaxException.class, () -> {
            jsonIo.loadString("{", Carte.class);
        });
    }

}
