package com.amplet.views;

import java.util.ArrayList;
import com.amplet.app.App;
import com.amplet.app.Carte;
import com.amplet.app.Model;
import com.amplet.app.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListeCarte extends ViewController {

    public class Row {
        private TextField titre;
        private Button modifier;
        private Button supprimer;

        public Row(Carte carte) {
            this.titre = new TextField(carte.getTitre());
            this.modifier = new Button("Modifier");
            this.supprimer = new Button("Supprimer");

            // On ajoute les actions
            this.supprimer.setOnAction(evt -> {
                // On supprime la pile
                try {
                    model.delete(carte);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            });

            this.modifier.setOnAction(evt -> {
                // On switch vers la vue d'édition de pile
                try {
                    App.setRoot("editionCarte", carte, "listeCarte");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            });

            this.titre.setOnKeyReleased(evt -> {
                // On modifie le nom de la pile
                try {
                    carte.setTitre(this.titre.getText());
                    model.update(carte);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            });

            // On modifie le style du text field pour qu'il ressemble à un label
            this.titre.setStyle(
                    "-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: black;");
        }

        public TextField getTitre() {
            return titre;
        }

        public Button getModifier() {
            return modifier;
        }

        public Button getSupprimer() {
            return supprimer;
        }
    }

    @FXML
    private TableView<Row> tableCartes;
    @FXML
    private TextField rechercheCarte;

    public ListeCarte(Model model) {
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
        ArrayList<Carte> cartes = model.getAllCartes();
        // On vide la table
        tableCartes.getItems().clear();
        // On ajoute les piles
        for (Carte carte : cartes) {
            // Trois colonne : nom, bouton modifier, bouton supprimer
            if (rechercheCarte.getText().length() > 0) {
                if (!carte.getTitre().toLowerCase()
                        .contains(rechercheCarte.getText().toLowerCase())) {
                    continue;
                }
            }
            Row row = new Row(carte);
            tableCartes.getItems().add(row);
        }
    }

    @FXML
    public void initialize() {
        // On charge les colonnes
        TableColumn<Row, TextField> titreCol = new TableColumn<>("Titre");
        TableColumn<Row, Button> modifierCol = new TableColumn<>("Modifier");
        TableColumn<Row, Button> supprimerCol = new TableColumn<>("Supprimer");

        titreCol.setCellValueFactory(new PropertyValueFactory<>("titre"));
        modifierCol.setCellValueFactory(new PropertyValueFactory<>("modifier"));
        supprimerCol.setCellValueFactory(new PropertyValueFactory<>("supprimer"));

        // On ajoute les colonnes
        tableCartes.getColumns().add(titreCol);
        tableCartes.getColumns().add(modifierCol);
        tableCartes.getColumns().add(supprimerCol);

        // On met les colonnes pour prendre tout l'espace
        titreCol.prefWidthProperty().bind(tableCartes.widthProperty().multiply(0.5));
        modifierCol.prefWidthProperty().bind(tableCartes.widthProperty().multiply(0.25));
        supprimerCol.prefWidthProperty().bind(tableCartes.widthProperty().multiply(0.25));

        // On centre les objets dans les colonnes
        titreCol.setStyle("-fx-alignment: CENTER;");
        modifierCol.setStyle("-fx-alignment: CENTER;");
        supprimerCol.setStyle("-fx-alignment: CENTER;");

        // On fait la recherche aussi
        rechercheCarte.setOnKeyReleased(evt -> {
            update();
        });

        // On charge les piles
        update();
    }

    @FXML
    public void creerCarte() {
        try {
            Carte c = new Carte("Nouvelle carte", "Question", "Réponse", "", "Description");
            model.create(c);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void retourIndex() {
        try {
            App.setRoot("index");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
