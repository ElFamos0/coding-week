package com.amplet.views;

import com.amplet.app.Model;
import com.amplet.app.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class ApprParam extends ViewController {

    public ApprParam(Model model) {
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
    private TextField labelNbCartes;

    @FXML
    private TextField labelTempsReponse;

    @FXML
    private CheckBox checkboxRandom;

    @FXML
    private CheckBox checkboxRepetition;

    @FXML
    private Slider sliderRepetition;

    @FXML
    private Label labelRepetition;

    @FXML
    private ChoiceBox choicePile;

    @FXML
    private VBox vboxPile;

    @FXML
    public void switchRandom() {

    }

    @FXML
    public void switchRepetition() {

    }

    @FXML
    public void updateSlider() {

    }

    @FXML
    public void selectNewPile() {

    }

    @FXML
    public void removePile() {

    }

    @FXML
    public void validerParam() {

    }


    public void update() {


    }
}
