package com.amplet.views;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void vaEditionPile() throws IOException {
        System.out.println("Edition de pile");
        App.setRoot("editionPile");
    }

    @FXML
    private void vaEditionCarte() throws IOException {
        System.out.println("Edition de carte");
        App.setRoot("editionCarte");
    }

    @FXML
    private void vaApprIg() throws IOException {
        System.out.println("Apprentissage in game");
        App.setRoot("ApprIg");
    }

    @FXML
    private void vaApprResultat() throws IOException {
        System.out.println("Apprentissage resultats");
        App.setRoot("apprResultat");
    }
}
