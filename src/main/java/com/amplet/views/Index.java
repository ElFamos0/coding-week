package com.amplet.views;

import java.io.IOException;
import com.amplet.app.App;
import com.amplet.app.Observer;
import javafx.fxml.FXML;

public class Index implements Observer {

    @FXML
    public void switchToCreatePile() throws IOException {
        System.out.println("Création de pile");
        App.setRoot("editionPile");
    }

    @FXML
    public void switchToModifyPile() throws IOException {
        System.out.println("Edition de pile");
        App.setRoot("editionPile");
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
        System.out.println("Index updated");
    }

    public void initialize() {}
}
