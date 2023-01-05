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
    public void loadPileFromString() {
        JsonIo jsonIo = new JsonIo("");
        String json =
                "{\n  \"nom\": \"nom\",\n  \"description\": \"description\",\n  \"tags\": [],\n  \"cartes\": []\n}";
        Pile expected = new Pile("nom", "description");
        Pile actual = jsonIo.loadString(json, Pile.class);
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
    public void testLoadPileStringException() {
        JsonIo jsonIo = new JsonIo("");
        Assertions.assertThrows(JsonSyntaxException.class, () -> {
            jsonIo.loadString("{", Carte.class);
        });
    }

}
