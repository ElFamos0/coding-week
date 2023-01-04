package com.amplet.views;

import com.amplet.app.Model;
import com.amplet.app.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Statistiques extends ViewController {
    public Statistiques(Model model) {
        super(model);
        model.addObserver(this);
    }

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @FXML
    public void initialize() {

    }

    @FXML
    private Label labelQuestion;

    @FXML
    private Label labelTitre;

    @FXML
    private Label labelReponse;

    @FXML
    public void validerTitre() {

    }

    @FXML
    public void validerQuestion() {

    }

    @FXML
    public void validerReponse() {

    }

    @FXML
    public void retour() {

    }

    @FXML
    public void dupliquerCarte() {

    }

    @FXML
    public void supprimerCarte() {

    }

    public void update() {

    }


}

