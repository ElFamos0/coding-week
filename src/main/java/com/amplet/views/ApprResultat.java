package com.amplet.views;

import java.io.IOException;
import java.util.ArrayList;
import com.amplet.app.App;
import com.amplet.app.Carte;
import com.amplet.app.Model;
import com.amplet.app.ViewController;
import com.amplet.db.DatabaseManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ApprResultat extends ViewController {

    ArrayList<Carte> playedCartes;
    ArrayList<Boolean> playedReponses;


    public ApprResultat(Model model) {
        super(model);
        model.addObserver(this);
    }

    public class Row {
        private Label titreCarte;
        private Label reponse;
        private Label nbJouee;
        private Label nbReussi;
        private Label accuracy;

        public Row(Carte carte, boolean reponse) {
            this.titreCarte = new Label(carte.getTitre());
            if (reponse) {
                Label label = new Label("Juste");
                label.setStyle("-fx-text-inner-color: #67d037;");
                this.reponse = label;
            } else {
                Label label = new Label("Faux");
                label.setStyle("-fx-text-inner-color: #e41515;");
                this.reponse = label;
            }

            this.nbJouee = new Label(Integer.toString(carte.getNbJouees()));
            this.nbReussi = new Label(Integer.toString(carte.getNbSucces()));
            if (carte.getNbJouees() > 0) {
                this.accuracy = new Label(
                        Integer.toString(carte.getNbSucces() * 100 / carte.getNbJouees()) + " %");
            } else {
                this.accuracy = new Label("100 %");
            }

        }

        public Label getTitre() {
            return titreCarte;
        }

        public Label getReponse() {
            return reponse;
        }

        public Label getNbJouee() {
            return nbJouee;
        }

        public Label getNbReussi() {
            return nbReussi;
        }

        public Label getPrecision() {
            return accuracy;
        }
    }

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

    @FXML
    public void switchToIndex() throws IOException {
        App.setRoot("index");
    }

    @FXML
    private TableView<Row> tableResult;

    @FXML
    public void initialize() {

        ArrayList<Carte> playedCartes = model.getCtx().getPlayedCartes();
        ArrayList<Boolean> playedReponses = model.getCtx().getPlayedReponse();

        if (playedCartes == null) {
            playedCartes = new ArrayList<Carte>();
        }

        if (playedReponses == null) {
            playedReponses = new ArrayList<Boolean>();
        }

        // On charge les colonnes
        TableColumn<Row, Label> titreCol = new TableColumn<>("Carte");
        TableColumn<Row, Label> reponseCol = new TableColumn<>("Réponse");
        TableColumn<Row, Label> joueeCol = new TableColumn<>("Nb Réponses");
        TableColumn<Row, Label> reussiCol = new TableColumn<>("Nb Réussites");
        TableColumn<Row, Label> accuracyCol = new TableColumn<>("Précision");


        titreCol.setCellValueFactory(new PropertyValueFactory<>("titre"));
        reponseCol.setCellValueFactory(new PropertyValueFactory<>("reponse"));
        joueeCol.setCellValueFactory(new PropertyValueFactory<>("nbJouee"));
        reussiCol.setCellValueFactory(new PropertyValueFactory<>("nbReussi"));
        accuracyCol.setCellValueFactory(new PropertyValueFactory<>("precision"));


        // On ajoute les colonnes
        tableResult.getColumns().add(titreCol);
        tableResult.getColumns().add(reponseCol);
        tableResult.getColumns().add(joueeCol);
        tableResult.getColumns().add(reussiCol);
        tableResult.getColumns().add(accuracyCol);

        // On met les colonnes pour prendre tout l'espace
        titreCol.prefWidthProperty().bind(tableResult.widthProperty().multiply(0.40));
        reponseCol.prefWidthProperty().bind(tableResult.widthProperty().multiply(0.14));
        joueeCol.prefWidthProperty().bind(tableResult.widthProperty().multiply(0.14));
        reussiCol.prefWidthProperty().bind(tableResult.widthProperty().multiply(0.14));
        accuracyCol.prefWidthProperty().bind(tableResult.widthProperty().multiply(0.14));

        // On centre les objets dans les colonnes
        titreCol.setStyle("-fx-alignment: CENTER;");
        reponseCol.setStyle("-fx-alignment: CENTER;");
        joueeCol.setStyle("-fx-alignment: CENTER;");
        reussiCol.setStyle("-fx-alignment: CENTER;");
        accuracyCol.setStyle("-fx-alignment: CENTER;");

        for (int i = 0; i < playedCartes.size(); i++) {
            Carte currentCarte = playedCartes.get(i);
            Boolean currentReponse = playedReponses.get(i);
            int currentCarteId = currentCarte.getId();
            Row row = new Row(currentCarte, currentReponse);
            currentCarte.setNbJouees(currentCarte.getNbJouees() + 1);
            if (currentReponse) {
                currentCarte.setNbSucces(currentCarte.getNbSucces() + 1);
            }
            tableResult.getItems().add(row);
        }

    }

}
