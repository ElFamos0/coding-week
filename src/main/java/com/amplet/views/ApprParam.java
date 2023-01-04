package com.amplet.views;

import java.io.IOException;
import com.amplet.app.App;
import com.amplet.app.Context;
import com.amplet.app.Model;
import com.amplet.app.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class ApprParam extends ViewController {

    public ApprParam(Model model) {
        super(model);
        model.addObserver(this);
        ctx = model.getCtx();
    }

    int nbCartes;
    int tempsReponse;
    boolean random;
    boolean repetition;
    int valSliderRepetition;
    Context ctx;

    public void setValSliderRepetition(int valSliderRepetition) {
        this.valSliderRepetition = valSliderRepetition;
    }

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @FXML
    public void initialize() {

        nbCartes = 20;
        tempsReponse = 20;
        random = false;
        repetition = false;
        valSliderRepetition = 0;

        sliderRepetition.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue,
                    Number newValue) {
                if ((int) sliderRepetition.getValue() % 5 == 0) {
                    setValSliderRepetition((int) sliderRepetition.getValue());
                    labelRepetition
                            .setText(Integer.toString((int) sliderRepetition.getValue()) + " %");
                }
            }
        });
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
        random = !random;
    }

    @FXML
    public void switchRepetition() {
        repetition = !repetition;
    }

    @FXML
    public void selectNewPile() {

    }

    @FXML
    public void removePile() {

    }

    @FXML
    public void validerParam() throws IOException {

        ctx.setNbCartes(nbCartes);
        ctx.setTempsReponse(tempsReponse);
        ctx.setRepetitionProbability(valSliderRepetition);
        ctx.setRandomSelected(random);
        ctx.setFavorisedFailedSelected(repetition);

        System.out.println("Apprentissage in game");
        App.setRoot("apprIg");
    }


    public void update() {


    }
}
