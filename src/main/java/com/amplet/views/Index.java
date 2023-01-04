package com.amplet.views;

import java.io.IOException;
import com.amplet.app.App;
import com.amplet.app.Model;
import com.amplet.app.ViewController;
import javafx.fxml.FXML;

public class Index extends ViewController {
    public Index(Model model) {
        super(model);
        model.addObserver(this);
    }

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @FXML
    public void switchToListePile() throws IOException {
        System.out.println("Liste de piles");
        App.setRoot("listePile");
    }

    @FXML
    public void switchToApprParam() throws IOException {
        System.out.println("ApprParam");
        App.setRoot("apprParam");


    }

    @FXML
    public void switchToStatistiques() throws IOException {
        System.out.println("Stas");
        App.setRoot("statistiques");
    }

    public void update() {
        // System.out.println("Index updated");
    }

    public void initialize() {}
}
