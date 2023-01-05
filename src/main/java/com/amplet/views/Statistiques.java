package com.amplet.views;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.amplet.app.App;
import com.amplet.app.Model;
import com.amplet.app.Pile;
import com.amplet.app.ViewController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Statistiques extends ViewController {
    public Statistiques(Model model) {
        super(model);
        model.addObserver(this);
    }

    public class Row {
        private Label nomTag;
        private Button supprimer;

        public Row(String tag) {
            this.nomTag = new Label(tag);
            this.supprimer = new Button("Supprimer");

            // On ajoute les actions
            this.supprimer.setOnAction(evt -> {
                // On supprime le tag
                try {
                    selectedTagNames.remove(tag);
                    update();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            });
        }

        public Label getTag() {
            return nomTag;
        }

        public Button getSupprimer() {
            return supprimer;
        }
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
    private TableView<Row> tableTag;

    private ArrayList<Pile> piles;
    private ArrayList<String> pilesNames = new ArrayList<String>();
    private Pile selectedPile;
    private ArrayList<String> selectedTagNames = new ArrayList<String>();
    private ArrayList<String> possibleTags = new ArrayList<String>();

    @FXML
    public void initialize() {

        this.piles = model.getAllPiles();
        this.selectedPile = this.piles.get(0);
        this.piles.forEach(pile -> this.pilesNames.add(pile.getNom()));
        this.choiceBoxPile.getItems().clear();
        this.choiceBoxPile.getItems().addAll(this.pilesNames);
        this.choiceBoxPile.setValue(selectedPile.getNom());

        loadPossibleTags();

        // On charge les colonnes
        TableColumn<Row, Label> tagCol = new TableColumn<>("Tag");
        TableColumn<Row, Button> supprimerCol = new TableColumn<>("Supprimer");

        tagCol.setCellValueFactory(new PropertyValueFactory<>("tag"));
        supprimerCol.setCellValueFactory(new PropertyValueFactory<>("supprimer"));

        // On ajoute les colonnes
        tableTag.getColumns().add(tagCol);
        tableTag.getColumns().add(supprimerCol);

        // On met les colonnes pour prendre tout l'espace
        tagCol.prefWidthProperty().bind(tableTag.widthProperty().multiply(0.60));
        supprimerCol.prefWidthProperty().bind(tableTag.widthProperty().multiply(0.33));

        // On centre les objets dans les colonnes
        tagCol.setStyle("-fx-alignment: CENTER;");
        supprimerCol.setStyle("-fx-alignment: CENTER;");

        choiceBoxTag.setItems(FXCollections.observableArrayList(possibleTags));
        choiceBoxTag.getSelectionModel().selectedIndexProperty()
                .addListener(new ChangeListener<Number>() {

                    public void changed(ObservableValue<? extends Number> observable,
                            Number oldValue, Number newValue) {
                        if (newValue.intValue() >= 0) {

                            String selectedTag = possibleTags.get(newValue.intValue());
                            if (!(isElementInArrayList(selectedTagNames, selectedTag))) {
                                selectedTagNames.add(selectedTag);
                                Row row = new Row(selectedTag);
                                tableTag.getItems().add(row);
                            }

                        }

                    }
                });

        // On charge les tags déjà sélectionnés
        update();
    }

    @FXML
    private ChoiceBox<String> choiceBoxPile;

    @FXML
    private ChoiceBox<String> choiceBoxTag;

    public void update() {

        tableTag.getItems().clear();
        // On ajoute les tags déjà sélectionnés
        for (String s : selectedTagNames) {
            Row row = new Row(s);
            tableTag.getItems().add(row);
        }

    }

    public void loadPossibleTags() {
        possibleTags.clear();
        for (int i = 0; i < 50; i++) {
            if (!(isElementInArrayList(possibleTags, "Tag " + Integer.toString(i)))) {
                possibleTags.add("Tag " + Integer.toString(i));
            }
        }
        for (int i = 0; i < 10; i++) {
            if (!(isElementInArrayList(possibleTags, "Tag " + Integer.toString(i)))) {
                possibleTags.add("Tag " + Integer.toString(i));
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
}
