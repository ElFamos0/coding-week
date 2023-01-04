package com.amplet.views;

import java.io.IOException;
import java.util.ArrayList;
import com.amplet.app.App;
import com.amplet.app.Carte;
import com.amplet.app.Context;
import com.amplet.app.Model;
import com.amplet.app.Pile;
import com.amplet.app.ViewController;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;

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
    ArrayList<Pile> listeSelectedPiles = new ArrayList<Pile>();
    ArrayList<String> listePileNames = new ArrayList<String>();
    ArrayList<Integer> listePileIds = new ArrayList<Integer>();
    ArrayList<HBox> listeHBox = new ArrayList<HBox>();
    ArrayList<String> listeSelectedPileNames = new ArrayList<String>();

    public class EcouteurPileRemove implements EventHandler {

        int pileId;

        public EcouteurPileRemove(int pileId) {
            this.pileId = pileId;
        }

        @Override
        public void handle(Event event) {
            removePile(pileId);
        }
    }

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
        listeSelectedPiles = new ArrayList<Pile>();
        listeSelectedPileNames = new ArrayList<String>();
        listePileNames = new ArrayList<String>();
        listePileIds = new ArrayList<Integer>();
        listeHBox = new ArrayList<HBox>();

        warningNb.setText("");
        warningTps.setText("");
        warningPiles.setText("");


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

        choicePile.getSelectionModel().selectedIndexProperty()
                .addListener(new ChangeListener<Number>() {

                    public void changed(ObservableValue<? extends Number> observable,
                            Number oldValue, Number newValue) {
                        if (newValue.intValue() >= 0) {
                            String selectedPileName = listePileNames.get(newValue.intValue());
                            int selectedCartesPileId = listePileIds.get(newValue.intValue());
                            Pile selectedPile = model.getPileById(selectedCartesPileId);
                            if (selectedPile != null) {
                                if (selectedPile.getCartes().size() > 0) {
                                    if (!(isElementInArrayList(listeSelectedPiles, selectedPile))) {
                                        listeSelectedPiles.add(selectedPile);
                                        listeSelectedPileNames.add(selectedPileName);
                                        selectNewPile(selectedPileName);
                                    }
                                }
                            }
                        }

                    }
                });

        update();
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
    private ChoiceBox<String> choicePile;

    @FXML
    private VBox vboxPile;

    @FXML
    private Label warningNb;

    @FXML
    private Label warningTps;

    @FXML
    private Label warningPiles;

    @FXML
    public void switchRandom() {
        random = !random;
    }

    @FXML
    public void switchRepetition() {
        repetition = !repetition;
    }

    @FXML
    public void selectNewPile(String pileName) {
        HBox hbox = new HBox();
        hbox.setSpacing(10);
        Label label = new Label(pileName);
        label.setAlignment(Pos.CENTER_LEFT);
        label.setPrefWidth(335);
        label.setMaxWidth(335);
        label.setFont(new Font(14));;
        Button bout = new Button("X");
        bout.setOnAction(new EcouteurPileRemove(listeSelectedPiles.size() - 1));
        hbox.setAlignment(Pos.CENTER);
        this.listeHBox.add(hbox);
        bout.setFont(new Font(12));
        hbox.getChildren().add(label);
        hbox.getChildren().add(bout);

        vboxPile.getChildren().add(hbox);
    }

    @FXML
    public void validerParam() throws IOException {

        if (test_validation()) {

            ctx.setNbCartes(nbCartes);
            ctx.setTempsReponse(tempsReponse);
            ctx.setRepetitionProbability(valSliderRepetition);
            ctx.setRandomSelected(random);
            ctx.setFavorisedFailedSelected(repetition);
            ctx.resetSelectedCartes();
            for (Pile p : listeSelectedPiles) {
                for (Carte c : p.getCartes()) {
                    if (!(isElementInArrayList(ctx.getSelectedCartes(), c))) {
                        ctx.getSelectedCartes().add(c);
                    }
                }
            }

            System.out.println("Apprentissage in game");
            App.setRoot("apprIg");

        }
    }


    public void update() {

        loadPileNames();
        if (choicePile.getItems().size() != listePileNames.size()) {
            choicePile.setItems(FXCollections.observableArrayList(listePileNames));
        }

        if (listeHBox.size() != listeSelectedPileNames.size()) {
            ArrayList<HBox> listeHBox = new ArrayList<HBox>();
            vboxPile.getChildren().clear();
            int i = 0;
            for (Pile p : listeSelectedPiles) {
                HBox hbox = new HBox();
                hbox.setSpacing(10);
                Label label = new Label(listeSelectedPileNames.get(i));
                label.setAlignment(Pos.CENTER_LEFT);
                label.setPrefWidth(335);
                label.setMaxWidth(335);
                label.setFont(new Font(14));;
                Button bout = new Button("X");
                bout.setOnAction(new EcouteurPileRemove(i));
                hbox.setAlignment(Pos.CENTER);
                this.listeHBox.add(hbox);
                bout.setFont(new Font(12));
                hbox.getChildren().add(label);
                hbox.getChildren().add(bout);
                i++;
                vboxPile.getChildren().add(hbox);
            }
        }

    }

    public void loadPileNames() {

        ArrayList<Pile> piles = model.getAllPiles();
        listePileIds = new ArrayList<Integer>();
        listePileNames = new ArrayList<String>();
        for (Pile p : piles) {
            if (p.getCartes().size() > 0) {
                listePileNames.add(p.getNom());
                listePileIds.add(p.getId());
            }
        }
    }

    public <T> boolean isElementInArrayList(ArrayList<T> array, T element) {

        for (T array_element : array) {
            if (element.equals(array_element)) {
                return true;
            }
        }
        return false;
    }

    @FXML
    public void addPile() {
        int value = choicePile.getSelectionModel().selectedIndexProperty().intValue();
        String selectedPileName = listePileNames.get(value);
        int selectedCartesPileId = listePileIds.get(value);
        Pile selectedPile = model.getPileById(selectedCartesPileId);
        System.out.println(listeSelectedPiles.size());
        if (!(isElementInArrayList(listeSelectedPiles, selectedPile))) {
            listeSelectedPiles.add(selectedPile);
            listeSelectedPileNames.add(selectedPileName);
            selectNewPile(selectedPileName);

        }
    }

    public void removePile(int id) {
        listeSelectedPiles.remove(id);
        listeSelectedPileNames.remove(id);
        update();
    }

    public boolean test_validation() {
        boolean state = true;

        String strNb = labelNbCartes.getText();
        String strTps = labelTempsReponse.getText();

        try {
            int i = Integer.parseInt(strNb);
            nbCartes = i;
            warningNb.setText("");
        } catch (NumberFormatException nfe) {
            state = false;
            warningNb.setText("Veuillez entrer une valeur entière !");
        }

        try {
            int i = Integer.parseInt(strTps);
            tempsReponse = i;
            warningTps.setText("");
        } catch (NumberFormatException nfe) {
            state = false;
            warningTps.setText("Veuillez entrer une valeur entière !");
        }

        if (listeSelectedPiles.size() < 1) {
            state = false;
            warningPiles.setText("Veuillez sélectionner au moins une pile non vide !");
        } else {
            ctx.resetSelectedCartes();
            for (Pile p : listeSelectedPiles) {
                for (Carte c : p.getCartes()) {
                    if (!(isElementInArrayList(ctx.getSelectedCartes(), c))) {
                        ctx.getSelectedCartes().add(c);
                    }
                }
            }
            if (ctx.getSelectedCartes().size() < 1) {
                state = false;
                warningPiles.setText("Veuillez sélectionner au moins une pile non vide !");
            } else {
                warningPiles.setText("");
            }

        }
        return state;
    }
}
