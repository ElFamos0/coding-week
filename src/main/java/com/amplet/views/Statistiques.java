package com.amplet.views;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
import javafx.scene.layout.HBox;
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
                    // System.out.println(e.getMessage());
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

    public class RowPile {
        private Label titreCarte;
        private Label nbJouee;
        private Label nbReussi;
        private Label accuracy;

        public RowPile(Carte carte) {
            this.titreCarte = new Label(carte.getTitre());
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

    @FXML
    public void retourAccueil() throws IOException {
        App.setRoot("index");
    }

    @FXML
    private TableView<Row> tableTag;

    @FXML
    private TableView<RowPile> tablePile;

    private ArrayList<String> selectedTagNames = new ArrayList<String>();
    private ArrayList<String> possibleTags = new ArrayList<String>();
    private ArrayList<PieChart.Data> globalData = new ArrayList<PieChart.Data>();
    private ArrayList<PieChart.Data> pileData = new ArrayList<PieChart.Data>();
    boolean isOnGlobal = true;

    @FXML
    private Label labelTooltip;

    @FXML
    private Label labelCartesPile;

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
                "Si aucun tag n'est choisi, alors toutes les piles sont s??lectionn??es.\nSinon, toutes les piles poss??dant tous les tags de la liste seront prises en compte.");
        isOnGlobal = true;
        ctxStats.reset();
        ctxStats.setPiles(model.getAllPiles());
        this.choiceBoxPile.getItems().clear();
        this.choiceBoxPile.getItems().addAll(ctxStats.getPilesNames());

        loadPossibleTags();
        /* Chargement du premier tableau */
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

        /* Chargement du second tableau */

        TableColumn<RowPile, Label> titreCol = new TableColumn<>("Carte");
        TableColumn<RowPile, Label> joueeCol = new TableColumn<>("Nb R??ponses");
        TableColumn<RowPile, Label> reussiCol = new TableColumn<>("Nb R??ussites");
        TableColumn<RowPile, Label> accuracyCol = new TableColumn<>("Pr??cision");


        titreCol.setCellValueFactory(new PropertyValueFactory<>("titre"));
        joueeCol.setCellValueFactory(new PropertyValueFactory<>("nbJouee"));
        reussiCol.setCellValueFactory(new PropertyValueFactory<>("nbReussi"));
        accuracyCol.setCellValueFactory(new PropertyValueFactory<>("precision"));


        // On ajoute les colonnes
        tablePile.getColumns().add(titreCol);
        tablePile.getColumns().add(joueeCol);
        tablePile.getColumns().add(reussiCol);
        tablePile.getColumns().add(accuracyCol);

        // On met les colonnes pour prendre tout l'espace
        titreCol.prefWidthProperty().bind(tablePile.widthProperty().multiply(0.44));
        joueeCol.prefWidthProperty().bind(tablePile.widthProperty().multiply(0.18));
        reussiCol.prefWidthProperty().bind(tablePile.widthProperty().multiply(0.18));
        accuracyCol.prefWidthProperty().bind(tablePile.widthProperty().multiply(0.18));

        // On centre les objets dans les colonnes
        titreCol.setStyle("-fx-alignment: CENTER;");
        joueeCol.setStyle("-fx-alignment: CENTER;");
        reussiCol.setStyle("-fx-alignment: CENTER;");
        accuracyCol.setStyle("-fx-alignment: CENTER;");

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
                            ctxStats.setPileCourante(newValue.intValue());
                            update();

                        }

                    }
                });

        tablePile.setPlaceholder(new Label("Il n'y a pas de cartes dans cette pile"));
        // On charge les tags d??j?? s??lectionn??s
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
    private Label labelTagsPile;

    @FXML
    private HBox hboxTags;

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
            // On ajoute les tags d??j?? s??lectionn??s
            for (String s : selectedTagNames) {
                Row row = new Row(s);
                tableTag.getItems().add(row);
            }
            loadPieChartData();
        } else {
            if (ctxStats.getPileCourante() == null) {
                warningPile.setText("Veillez choisir une pile");
                pieChartPile.setTitle("");
                labelPile.setText("");
                tablePile.setVisible(false);
                labelCartesPile.setVisible(false);
                labelTagsPile.setVisible(false);
                hboxTags.setVisible(false);
            } else {
                warningPile.setText("");
                pieChartPile.setTitle("Taux de r??ussite sur la pile "
                        + ctxStats.getPileCourante().getNom() + " :");
                loadPieChartDataPile();
                tablePile.getItems().clear();
                tablePile.setVisible(true);
                labelCartesPile.setVisible(true);
                for (Carte c : ctxStats.getPileCourante().getCartes()) {
                    RowPile row = new RowPile(c);
                    tablePile.getItems().add(row);
                }
                hboxTags.setVisible(true);
                labelTagsPile.setVisible(true);
                hboxTags.getChildren().clear();
                for (String tag : ctxStats.getPileCourante().getTags()) {
                    Label tagButton = new Label();
                    tagButton.setText(tag);
                    tagButton.getStylesheets()
                            .add(App.class.getResource("boutonTag.css").toExternalForm());
                    tagButton.getStyleClass().add("tag");
                    hboxTags.getChildren().add(tagButton);
                }
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
        for (Carte c : ctxStats.getCartesSelection??es()) {
            count_tot += c.getNbJouees();
            count_win += c.getNbSucces();
        }
        if (count_tot == 0) {
            count_tot = 1;
            count_win = 1;
            labelGlobalCartes
                    .setText("Aucune carte poss??dant les tags s??lectionn??s n'a ??t?? jou??e ");
        } else {
            labelGlobalCartes.setText("Au total, vous avez jou?? " + Integer.toString(count_tot)
                    + " cartes poss??dant les tags s??lectionn??s !");
        }
        PieChart.Data datawin = (new PieChart.Data(
                "Cartes R??ussies " + Integer.toString(count_win * 100 / count_tot) + " %",
                count_win));
        PieChart.Data datalose = (new PieChart.Data("Cartes Rat??es "
                + Integer.toString((count_tot - count_win) * 100 / count_tot) + " %",
                count_tot - count_win));

        globalData.add(datawin);
        globalData.add(datalose);
        pieChartGlobal.setData(FXCollections.observableArrayList(globalData));
        tabGlobal.getContent().requestFocus();

    }

    public void loadSelectedCartes() {
        ctxStats.setCartesSelection??es(new ArrayList<Carte>());
        if (selectedTagNames.size() > 0) {
            ArrayList<Pile> currentSelectedPiles = new ArrayList<Pile>();
            model.getPilesByTags(currentSelectedPiles, selectedTagNames);

            HashMap<Integer, Boolean> mapCartes = new HashMap<Integer, Boolean>();
            for (Pile p : currentSelectedPiles) {
                for (Carte c : p.getCartes()) {
                    if (!(mapCartes.containsKey(c.getId()))) {
                        mapCartes.put(c.getId(), true);
                        ctxStats.addCarteSelection??e(c);
                    }
                }
            }
        } else {
            ctxStats.setCartesSelection??es(model.getAllCartes());
        }
    }

    public void loadPieChartDataPile() {
        pieChartGlobal.getData().removeAll(pileData);
        pileData.clear();
        int count_tot = 0;
        int count_win = 0;
        for (Carte c : ctxStats.getPileCourante().getCartes()) {
            count_tot += c.getNbJouees();
            count_win += c.getNbSucces();
        }
        if (count_tot == 0) {
            count_tot = 1;
            count_win = 1;
            labelPile.setText("Aucune carte de cette pile n'a ??t?? jou??e ");
        } else {
            labelPile.setText("Jou??e " + Integer.toString(ctxStats.getPileCourante().getNbJouees())
                    + " fois, pour un total de " + Integer.toString(count_tot)
                    + " cartes test??es !");
        }
        PieChart.Data datawin = (new PieChart.Data(
                "Cartes R??ussies " + Integer.toString(count_win * 100 / count_tot) + " %",
                count_win));
        PieChart.Data datalose = (new PieChart.Data("Cartes Rat??es "
                + Integer.toString((count_tot - count_win) * 100 / count_tot) + " %",
                count_tot - count_win));
        pileData.add(datawin);
        pileData.add(datalose);
        pieChartPile.setData(FXCollections.observableArrayList(pileData));
        tabPile.getContent().requestFocus();

    }

}
