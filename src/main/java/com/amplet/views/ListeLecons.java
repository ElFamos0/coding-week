package com.amplet.views;

import java.util.ArrayList;
import com.amplet.app.App;
import com.amplet.app.Lecon;
import com.amplet.app.Model;
import com.amplet.app.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListeLecons extends ViewController {

    public class Row {
        private TextField nom;
        private Button modifier;
        private Button supprimer;

        public Row(Lecon lecon) {
            this.nom = new TextField(lecon.getNom());
            this.modifier = new Button("Modifier");
            this.supprimer = new Button("Supprimer");

            // On ajoute les actions
            this.supprimer.setOnAction(evt -> {
                // On supprime la pile
                try {
                    model.delete(lecon);
                } catch (Exception e) {
                    // System.out.println(e.getMessage());
                }
            });

            this.modifier.setOnAction(evt -> {
                // On switch vers la vue d'édition de pile
                try {
                    App.setRoot("editionLecon", lecon);
                } catch (Exception e) {
                    // System.out.println(e.getMessage());
                }
            });


            // Quand le texte n'est plus en focus, on sauvegarde
            this.nom.focusedProperty().addListener((obs, oldVal, newVal) -> {
                if (!newVal) {
                    try {
                        lecon.setNom(this.nom.getText());
                        model.update(lecon);
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
    private TableView<Row> tableLecons;
    @FXML
    private TextField rechercheLecon;

    public ListeLecons(Model model) {
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
        ArrayList<Lecon> lecons = model.getAllLecons();
        // On vide la table
        tableLecons.getItems().clear();
        // On ajoute les piles
        for (Lecon lecon : lecons) {
            // Trois colonne : nom, bouton modifier, bouton supprimer
            if (rechercheLecon.getText().length() > 0) {
                if (!lecon.getNom().toLowerCase()
                        .contains(rechercheLecon.getText().toLowerCase())) {
                    continue;
                }
            }
            Row row = new Row(lecon);
            tableLecons.getItems().add(row);
        }
    }

    @FXML
    public void initialize() {
        // On charge les colonnes
        TableColumn<Row, TextField> titreCol = new TableColumn<>("Nom");
        TableColumn<Row, Button> modifierCol = new TableColumn<>("Modifier");
        TableColumn<Row, Button> supprimerCol = new TableColumn<>("Supprimer");

        titreCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        modifierCol.setCellValueFactory(new PropertyValueFactory<>("modifier"));
        supprimerCol.setCellValueFactory(new PropertyValueFactory<>("supprimer"));

        // On ajoute les colonnes
        tableLecons.getColumns().add(titreCol);
        tableLecons.getColumns().add(modifierCol);
        tableLecons.getColumns().add(supprimerCol);

        // On met les colonnes pour prendre tout l'espace
        titreCol.prefWidthProperty().bind(tableLecons.widthProperty().multiply(0.5));
        modifierCol.prefWidthProperty().bind(tableLecons.widthProperty().multiply(0.25));
        supprimerCol.prefWidthProperty().bind(tableLecons.widthProperty().multiply(0.25));

        // On centre les objets dans les colonnes
        titreCol.setStyle("-fx-alignment: CENTER;");
        modifierCol.setStyle("-fx-alignment: CENTER;");
        supprimerCol.setStyle("-fx-alignment: CENTER;");

        // On fait la recherche aussi
        rechercheLecon.setOnKeyReleased(evt -> {
            update();
        });

        // On charge les piles
        update();
    }

    @FXML
    public void creerLecon() {
        try {
            Lecon l = new Lecon("Nouvelle leçon");
            model.create(l);
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
