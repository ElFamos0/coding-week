package com.amplet.views;

import java.io.IOException;
import com.amplet.app.App;
import javafx.fxml.FXML;

public class Menu {

    @FXML
    private void retourAccueil() throws IOException {
        System.out.println("Retour à l'accueil");
        App.setRoot("index");
    }

    @FXML
    private void test() {
        System.out.println("Retour à l'accueil");
    }
}
