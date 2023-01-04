package com.amplet.views;

import java.util.ArrayList;
import com.amplet.app.Model;
import com.amplet.app.Pile;
import com.amplet.app.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListePile extends ViewController {

    public class Row {
        private Label nom;
        private Button modifier;
        private Button supprimer;

        public Row(String nom) {
            this.nom = new Label(nom);
            this.modifier = new Button("Modifier");
            this.supprimer = new Button("Supprimer");
        }

        public Label getNom() {
            return nom;
        }

        public Button getModifier() {
            return modifier;
        }

        public Button getSupprimer() {
            return supprimer;
        }
    }

    @FXML
    private TableView tablePile;

    public ListePile(Model model) {
        super(model);
        model.addObserver(this);
    }

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public void update() {
        // Charge les piles
        ArrayList<Pile> piles = model.getAllPiles();
        // On vide la table
        tablePile.getItems().clear();
        // On ajoute les piles
        for (Pile pile : piles) {
            // Trois colonne : nom, bouton modifier, bouton supprimer
            Row row = new Row(pile.getNom());
            tablePile.getItems().add(row);
        }
    }

    @FXML
    public void initialize() {
        // On charge les colonnes
        TableColumn nomCol = new TableColumn<>("Nom");
        TableColumn modifierCol = new TableColumn<>("Modifier");
        TableColumn supprimerCol = new TableColumn<>("Supprimer");

        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        modifierCol.setCellValueFactory(new PropertyValueFactory<>("modifier"));
        supprimerCol.setCellValueFactory(new PropertyValueFactory<>("supprimer"));

        tablePile.getColumns().addAll(nomCol, modifierCol, supprimerCol);

        // On met les colonnes pour prendre tout l'espace
        nomCol.prefWidthProperty().bind(tablePile.widthProperty().multiply(0.5));
        modifierCol.prefWidthProperty().bind(tablePile.widthProperty().multiply(0.25));
        supprimerCol.prefWidthProperty().bind(tablePile.widthProperty().multiply(0.25));

        // On centre les objets dans les colonnes
        nomCol.setStyle("-fx-alignment: CENTER;");
        modifierCol.setStyle("-fx-alignment: CENTER;");
        supprimerCol.setStyle("-fx-alignment: CENTER;");

        // On charge les piles
        update();
    }
}
