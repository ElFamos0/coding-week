package com.amplet.views;

import java.io.IOException;
import com.amplet.app.App;
import com.amplet.app.Model;
import com.amplet.app.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Menu extends ViewController {
    public Menu(Model model) {
        super(model);
        model.addObserver(this);
    }

    public Menu(Model model, Object... args) {
        super(model, args);
    }

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @FXML
    private void retourAccueil() throws IOException {
        // System.out.println("Retour à l'accueil");
        App.setRoot("index");
    }

    @FXML
    private javafx.scene.control.Menu button;

    @FXML
    private void test() {
        // System.out.println("Retour à l'accueil");
    }

    public void update() {
        // System.out.println("Menu updated");
    }

    @FXML
    public void initialize() {
        button.setStyle("-fx-focus-color: #00000000; -fx-faint-focus-color: #00000000;");
    }

    @FXML
    private void vaEditionPile() throws IOException {
        // System.out.println("Edition de pile");
        App.setRoot("editionPile");
    }

    @FXML
    private void vaEditionCarte() throws IOException {
        // System.out.println("Edition de carte");
        App.setRoot("editionCarte");
    }

    @FXML
    private void vaApprIg() throws IOException {
        // System.out.println("Apprentissage in game");
        App.setRoot("apprIg");
    }

    @FXML
    private void vaApprResultat() throws IOException {
        // System.out.println("Apprentissage resultats");
        App.setRoot("apprResultat");
    }

    @FXML
    private void vaApprParam() throws IOException {
        // System.out.println("Apprentissage param");
        App.setRoot("apprParam");
    }
}
