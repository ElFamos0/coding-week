package com.amplet.views;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.amplet.app.App;
import com.amplet.app.Carte;
import com.amplet.app.Model;
import com.amplet.app.Pile;
import com.amplet.app.ViewController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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
    private Pile selectedPile = null;
    private ArrayList<String> selectedTagNames = new ArrayList<String>();
    private ArrayList<String> possibleTags = new ArrayList<String>();
    private ArrayList<PieChart.Data> globalData = new ArrayList<PieChart.Data>();
    private ArrayList<PieChart.Data> pileData = new ArrayList<PieChart.Data>();
    private ArrayList<Carte> selectedCartes = new ArrayList<Carte>();
    boolean isOnGlobal = true;

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
    public void initialize() {
        customTooltip.setText(
                "Si aucun tag n'est choisi, alors toutes les piles sont sélectionnées.\nSinon, toutes les piles possédant tous les tags de la liste seront prises en compte.");
        isOnGlobal = true;
        selectedPile = null;
        this.piles = model.getAllPiles();
        this.piles.forEach(pile -> this.pilesNames.add(pile.getNom()));
        this.choiceBoxPile.getItems().clear();
        this.choiceBoxPile.getItems().addAll(this.pilesNames);

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
                                update();
                            }
                        }
                    }
                });
        choiceBoxPile.getSelectionModel().selectedIndexProperty()
                .addListener(new ChangeListener<Number>() {

                    public void changed(ObservableValue<? extends Number> observable,
                            Number oldValue, Number newValue) {
                        if (newValue.intValue() >= 0) {
                            String selectedPileName = pilesNames.get(newValue.intValue());
                            selectedPile = piles.get(newValue.intValue());
                            update();

                        }

                    }
                });


        // On charge les tags déjà sélectionnés
        // update();

    }

    @FXML
    private ChoiceBox<String> choiceBoxPile;

    @FXML
    private ChoiceBox<String> choiceBoxTag;

    @FXML
    private PieChart pieChartGlobal;

    @FXML
    private PieChart pieChartPile;

    @FXML
    private Label labelGlobalCartes;

    @FXML
    private Label labelPile;

    @FXML
    private Label warningPile;

    @FXML
    private Tab tabGlobal;

    @FXML
    private Tab tabPile;

    @FXML
    private TabPane tabPane;

    @FXML
    public void viewGlobal() {
        isOnGlobal = true;
        update();
    }

    @FXML
    public void viewPile() {
        isOnGlobal = false;
        update();
    }

    public void update() {

        if (isOnGlobal) {
            tableTag.getItems().clear();
            loadPossibleTags();
            choiceBoxTag.setItems(FXCollections.observableArrayList(possibleTags));
            // On ajoute les tags déjà sélectionnés
            for (String s : selectedTagNames) {
                Row row = new Row(s);
                tableTag.getItems().add(row);
            }
            loadPieChartData();
        } else {
            if (selectedPile == null) {
                warningPile.setText("Veillez choisir une pile");
                pieChartPile.setTitle("");
                labelPile.setText("");
            } else {
                warningPile.setText("");
                pieChartPile
                        .setTitle("Taux de réussite sur la pile " + selectedPile.getNom() + " :");
                loadPieChartDataPile();
            }

        }



    }

    public void loadPossibleTags() {
        possibleTags.clear();
        for (Pile p : model.getAllPiles()) {
            for (String tag : p.getTags()) {
                if (!(isElementInArrayList(possibleTags, tag))) {
                    if (!(isElementInArrayList(selectedTagNames, tag)))
                        possibleTags.add(tag);
                }
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

    public void loadPieChartData() {
        pieChartGlobal.getData().removeAll(globalData);
        globalData.clear();
        loadSelectedCartes();
        int count_tot = 0;
        int count_win = 0;
        for (Carte c : selectedCartes) {
            count_tot += c.getNbJouees();
            count_win += c.getNbSucces();
        }
        if (count_tot == 0) {
            count_tot = 1;
            count_win = 1;
            labelGlobalCartes
                    .setText("Aucune carte possédant les tags sélectionnés n'a été jouée ");
        } else {
            labelGlobalCartes.setText("Au total, vous avez joué " + Integer.toString(count_tot)
                    + " cartes possédant les tags sélectionnés !");
        }
        PieChart.Data datawin = (new PieChart.Data(
                "Cartes Réussies " + Integer.toString(count_win * 100 / count_tot) + " %",
                count_win));
        PieChart.Data datalose = (new PieChart.Data("Cartes Ratées "
                + Integer.toString((count_tot - count_win) * 100 / count_tot) + " %",
                count_tot - count_win));

        globalData.add(datawin);
        globalData.add(datalose);
        pieChartGlobal.setData(FXCollections.observableArrayList(globalData));
        tabGlobal.getContent().requestFocus();

    }

    public void loadSelectedCartes() {
        selectedCartes.clear();
        if (selectedTagNames.size() > 0) {
            ArrayList<Pile> currentSelectedPiles = new ArrayList<Pile>();
            model.getPilesByTags(currentSelectedPiles, selectedTagNames);

            HashMap<Integer, Boolean> mapCartes = new HashMap<Integer, Boolean>();
            for (Pile p : currentSelectedPiles) {
                for (Carte c : p.getCartes()) {
                    if (!(mapCartes.containsKey(c.getId()))) {
                        mapCartes.put(c.getId(), true);
                        selectedCartes.add(c);
                    }
                }
            }
        } else {
            selectedCartes = new ArrayList<Carte>(model.getAllCartes());

        }

    }

    public void loadPieChartDataPile() {
        ArrayList<Carte> cartesToShow = selectedPile.getCartes();
        pieChartGlobal.getData().removeAll(pileData);
        pileData.clear();
        int count_tot = 0;
        int count_win = 0;
        for (Carte c : cartesToShow) {
            count_tot += c.getNbJouees();
            count_win += c.getNbSucces();
        }
        if (count_tot == 0) {
            count_tot = 1;
            count_win = 1;
            labelPile.setText("Aucune carte de cette pile n'a été jouée ");
        } else {
            labelPile.setText("Jouée " + Integer.toString(selectedPile.getNbJouees())
                    + " fois, pour un total de " + Integer.toString(count_tot)
                    + " cartes testées !");
        }
        PieChart.Data datawin = (new PieChart.Data(
                "Cartes Réussies " + Integer.toString(count_win * 100 / count_tot) + " %",
                count_win));
        PieChart.Data datalose = (new PieChart.Data("Cartes Ratées "
                + Integer.toString((count_tot - count_win) * 100 / count_tot) + " %",
                count_tot - count_win));
        pileData.add(datawin);
        pileData.add(datalose);
        pieChartPile.setData(FXCollections.observableArrayList(pileData));
        tabPile.getContent().requestFocus();

    }

}
