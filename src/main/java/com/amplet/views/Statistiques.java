package com.amplet.views;

import java.util.ArrayList;
import java.util.List;

import com.amplet.app.Model;
import com.amplet.app.Pile;
import com.amplet.app.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class Statistiques extends ViewController {
    public Statistiques(Model model) {
        super(model);
        model.addObserver(this);
    }

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    private List<Pile> piles;
    private List<String> pilesNames = new ArrayList<String>();
    private Pile selectedPile;

    @FXML
    public void initialize() {
        this.piles = model.getAllPiles();
        this.selectedPile = this.piles.get(0);
        this.piles.forEach(pile -> this.pilesNames.add(pile.getNom()));
        this.choiceBoxPile.getItems().clear();
        this.choiceBoxPile.getItems().addAll(this.pilesNames);
        this.choiceBoxPile.setValue(selectedPile.getNom());
    }

    @FXML
    private ChoiceBox<String> choiceBoxPile;

    public void update() {

    }

}
