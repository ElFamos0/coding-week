package com.amplet.views;

import java.io.IOException;
import com.amplet.app.App;
import com.amplet.app.Observer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class Menu implements Observer, Initializable {

    @FXML
    private void retourAccueil() throws IOException {
        System.out.println("Retour à l'accueil");
        App.setRoot("index");
    }

    @FXML
    private void test() {
        System.out.println("Retour à l'accueil");
    }

    public void update() {
        System.out.println("Menu updated");
    }

    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {}
}
