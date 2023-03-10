package com.amplet.views;

import java.util.ArrayList;
import com.amplet.app.App;
import com.amplet.app.Model;
import com.amplet.app.Pile;
import com.amplet.app.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListePile extends ViewController {

    public class Row {
        private TextField nom;
        private Button modifier;
        private Button supprimer;

        public Row(Pile pile) {
            this.nom = new TextField(pile.getNom());
            this.modifier = new Button("Modifier");
            this.supprimer = new Button("Supprimer");

            // On ajoute les actions
            this.supprimer.setOnAction(evt -> {
                // On supprime la pile
                try {
                    model.delete(pile);
                } catch (Exception e) {
                    // System.out.println(e.getMessage());
                }
            });

            this.modifier.setOnAction(evt -> {
                // On switch vers la vue d'édition de pile
                try {
                    App.setRoot("editionPile", pile);
                } catch (Exception e) {
                    // System.out.println(e.getMessage());
                }
            });

            // Quand le texte n'est plus en focus, on sauvegarde
            this.nom.focusedProperty().addListener((obs, oldVal, newVal) -> {
                if (!newVal) {
                    try {
                        pile.setNom(this.nom.getText());
                        model.update(pile);
                    } catch (Exception e) {
                        // System.out.println(e.getMessage());
                    }
                }
            });

            // On modifie le style du text field pour qu'il ressemble à un label
            this.nom.setStyle(
                    "-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: black;");
        }

        public TextField getNom() {
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
    private TableView<Row> tablePile;
    @FXML
    private TextField recherchePile;

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
            if (recherchePile.getText().length() > 0) {
                if (!pile.getNom().toLowerCase().contains(recherchePile.getText().toLowerCase())) {
                    continue;
                }
            }
            Row row = new Row(pile);
            tablePile.getItems().add(row);
        }
    }

    @FXML
    public void initialize() {
        // On charge les colonnes
        TableColumn<Row, TextField> nomCol = new TableColumn<>("Nom");
        TableColumn<Row, Button> modifierCol = new TableColumn<>("Modifier");
        TableColumn<Row, Button> supprimerCol = new TableColumn<>("Supprimer");

        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        modifierCol.setCellValueFactory(new PropertyValueFactory<>("modifier"));
        supprimerCol.setCellValueFactory(new PropertyValueFactory<>("supprimer"));

        // On ajoute les colonnes
        tablePile.getColumns().add(nomCol);
        tablePile.getColumns().add(modifierCol);
        tablePile.getColumns().add(supprimerCol);

        // On met les colonnes pour prendre tout l'espace
        nomCol.prefWidthProperty().bind(tablePile.widthProperty().multiply(0.5));
        modifierCol.prefWidthProperty().bind(tablePile.widthProperty().multiply(0.25));
        supprimerCol.prefWidthProperty().bind(tablePile.widthProperty().multiply(0.25));

        // On centre les objets dans les colonnes
        nomCol.setStyle("-fx-alignment: CENTER;");
        modifierCol.setStyle("-fx-alignment: CENTER;");
        supprimerCol.setStyle("-fx-alignment: CENTER;");

        // On fait la recherche aussi
        recherchePile.setOnKeyReleased(evt -> {
            update();
        });

        // On charge les piles
        update();
    }

    @FXML
    public void creerPile() {
        try {
            Pile pile = new Pile("Nouvelle pile", "Description pour la pile");
            model.create(pile);
        } catch (Exception e) {
            // System.out.println(e.getMessage());
        }
    }

    @FXML
    public void retourIndex() {
        try {
            App.setRoot("index");
        } catch (Exception e) {
            // System.out.println(e.getMessage());
        }
    }
}
