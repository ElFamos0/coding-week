package com.amplet.views;

import java.io.IOException;
import java.sql.SQLException;
import com.amplet.app.App;
import com.amplet.app.Model;
import com.amplet.app.ViewController;
import javafx.fxml.FXML;

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
    private void test() {
        // //System.out.println("Retour à l'accueil");
    }

    public void update() {
        // //System.out.println("Menu updated");
    }

    @FXML
    public void retourAccueil() throws IOException {
        App.setRoot("index");
    }

    @FXML
    public void initialize() {

    }

    @FXML
    private void importCarteMenu() throws IOException, SQLException {
        App.importCarte();
    }

    @FXML
    private void importPileMenu() throws IOException, SQLException {
        App.importPile();
    }
}
