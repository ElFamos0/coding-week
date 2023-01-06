package com.amplet.views;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import com.amplet.app.App;
import com.amplet.app.Carte;
import com.amplet.app.Context;
import com.amplet.app.Model;
import com.amplet.app.Pile;
import com.amplet.app.ViewController;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ApprParam extends ViewController {

    public class Row {
        private CheckBox checkBox;
        private Label nom;

        public Row(Pile pile, Boolean isSelected) {
            nom = new Label(pile.getNom());
            nom.setFont(new Font("Arial", 15));
            nom.setPrefWidth(200);
            nom.setAlignment(Pos.CENTER);
            checkBox = new CheckBox();
            checkBox.setSelected(false);
            checkBox.setOnAction(e -> {
                if (checkBox.isSelected()) {
                    listeSelectedPiles.add(pile);
                } else {
                    listeSelectedPiles.remove(pile);
                }
                update();
            });
            if (isSelected) {
                checkBox.setSelected(true);
            }
        }

        public CheckBox getCheckBox() {
            return checkBox;
        }

        public Label getNom() {
            return nom;
        }
    }

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
    String modeDeJeu = "Apprentissage";

    public void setValSliderRepetition(int valSliderRepetition) {
        this.valSliderRepetition = valSliderRepetition;
    }

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @FXML
    public void retourAccueil() throws IOException {
        App.setRoot("index");
    }

    @FXML
    public void initialize() {
        customTooltip.setText(
                "Détermine la probabilité qu'a une carté déjà passée de réapparaître à nouveau.\n Seul le mode d'évaluation enregistre les statistiques.");

        labelNbCartes.setText("20");
        labelTempsReponse.setText("20");
        // On charge les colonnes
        TableColumn<Row, CheckBox> checkBoxCol = new TableColumn<>("Active");
        TableColumn<Row, TextField> titreCol = new TableColumn<>("Nom");

        // On charge les propriétés des colonnes
        checkBoxCol.setCellValueFactory(new PropertyValueFactory<>("checkBox"));
        titreCol.setCellValueFactory(new PropertyValueFactory<>("nom"));

        // On ajoute les colonnes
        tablePile.getColumns().add(checkBoxCol);
        tablePile.getColumns().add(titreCol);

        // On met les colonnes pour prendre tout l'espace
        checkBoxCol.prefWidthProperty().bind(tablePile.widthProperty().multiply(0.25));
        titreCol.prefWidthProperty().bind(tablePile.widthProperty().multiply(0.75));

        // On centre les objets dans les colonnes
        checkBoxCol.setStyle("-fx-alignment: CENTER;");
        titreCol.setStyle("-fx-alignment: CENTER;");

        nbCartes = 20;
        tempsReponse = 20;
        random = false;
        repetition = false;
        valSliderRepetition = 0;
        listeSelectedPiles = new ArrayList<Pile>();

        warningNb.setText("");
        warningTps.setText("");
        warningPiles.setText("");

        sliderRepetition.setOnMouseReleased(e -> {
            setValSliderRepetition((int) sliderRepetition.getValue());
            labelRepetition.setText(Integer.toString((int) sliderRepetition.getValue()) + " %");
        });

        recherchePile.setOnKeyReleased(e -> {
            update();
        });

        radioApprentissage.setOnAction(e -> {
            modeDeJeu = "Apprentissage";
            radioEval.setSelected(false);
        });

        radioEval.setOnAction(e -> {
            modeDeJeu = "Evaluation";
            radioApprentissage.setSelected(false);
        });

        radioApprentissage.setSelected(true);

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
    private TableView<Row> tablePile;

    @FXML
    private Label warningNb;

    @FXML
    private Label warningTps;

    @FXML
    private Label warningPiles;

    @FXML
    private TextField recherchePile;

    @FXML
    private RadioButton radioApprentissage;

    @FXML
    private RadioButton radioEval;


    @FXML
    private Label labelTooltip;

    final Tooltip customTooltip = new Tooltip();


    @FXML
    public void showToolTip() {

        Point2D p = labelTooltip.localToScene(0.0, 0.0);
        Stage stage = (Stage) labelTooltip.getScene().getWindow();
        customTooltip.show(stage, stage.getX() + labelTooltip.getScene().getX() + p.getX(),
                stage.getY() + labelTooltip.getScene().getY() + p.getY());
    }


    @FXML
    public void hidetoolTip() {
        customTooltip.hide();
    }

    @FXML
    public void switchRandom() {
        random = !random;
    }

    @FXML
    public void switchRepetition() {
        repetition = !repetition;
    }

    @FXML
    public void validerParam() throws IOException {
        if (testValidation()) {
            ctx.setNbCartes(nbCartes);
            ctx.setTempsReponse(tempsReponse);
            ctx.setRepetitionProbability(valSliderRepetition);
            ctx.setRandomSelected(random);
            ctx.setFavorisedFailedSelected(repetition);

            if (modeDeJeu.equals("Apprentissage")) {
                ctxEnt.ajoutePiles(listeSelectedPiles);
                App.setRoot("apprEnt");
            } else if (modeDeJeu.equals("Evaluation")) {
                ctxIg.ajoutePilesJeu(listeSelectedPiles);
                App.setRoot("apprIg");
            }
        }
    }


    public void update() {
        tablePile.getItems().clear();
        ArrayList<Pile> piles = model.getAllPiles();
        for (Pile p : piles) {
            if (p.getCartes().size() > 0) {
                // on vérifie si la pile est déjà sélectionnée
                boolean isSelected = false;
                for (Pile p2 : listeSelectedPiles) {
                    if (p2.getNom().equals(p.getNom())) {
                        isSelected = true;
                    }
                }
                if (recherchePile.getText().equals("")
                        || p.getNom().toLowerCase().contains(recherchePile.getText().toLowerCase())
                        || isSelected) {
                    tablePile.getItems().add(new Row(p, isSelected));
                }
            }
        }
    }

    public boolean testValidation() {
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
            Boolean valid = false;

            if (modeDeJeu.equals("Apprentissage")) {
                ctxEnt.ajoutePiles(listeSelectedPiles);
                valid = ctxEnt.getNbCartesProposées() >= 1;
            } else if (modeDeJeu.equals("Evaluation")) {
                ctxIg.ajoutePiles(listeSelectedPiles);
                valid = ctxIg.getNbCartesProposées() >= 1;
            }

            if (!valid) {
                state = false;
                warningPiles.setText("Veuillez sélectionner au moins une pile non vide !");
            } else {
                warningPiles.setText("");
            }
        }
        return state;
    }

}
