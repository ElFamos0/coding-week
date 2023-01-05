package com.amplet.views;

import com.amplet.app.App;
import com.amplet.app.Carte;
import com.amplet.app.Model;
import com.amplet.app.Pile;
import com.amplet.app.ViewController;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;


public class EditionCarte extends ViewController {

    private Carte currentCarte;
    private Pile currentPile;


    public EditionCarte(Model model, Object... args) {
        super(model);
        model.addObserver(this);
        currentCarte = (Carte) args[0];

        // si on a une pile en argument, on va retourner a l'édition de pile
        // sinon on retourne a la liste des cartes
        // selon le type de args[1]

        if (args[1] instanceof Pile) {
            currentPile = (Pile) args[1];
        } else if (args[1] instanceof String) {
            if (args[1].equals("listeCarte")) {
                currentPile = null;
            }
        }

    }

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @FXML
    private VBox carte;

    private VBox carteFront;
    private VBox carteBack;
    private boolean isFront = true;
    private boolean isFlipping = false;

    @FXML
    private Label labelQuestion;

    @FXML
    private Label labelTitre;

    @FXML
    private Label labelReponse;

    @FXML
    private TextField prompttitre;

    @FXML
    private TextArea promptquestion;

    @FXML
    private TextField promptreponse;



    @FXML
    public void initialize() {
        carteFront = new VBox();
        carteFront.getChildren().addAll(carte.getChildren());
        carte.getChildren().clear();
        carte.getChildren().addAll(carteFront);

        // update on text change
        prompttitre.textProperty().addListener((observable, oldValue, newValue) -> {
            currentCarte.setTitre(newValue);
            try {
                model.update(currentCarte);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        promptquestion.textProperty().addListener((observable, oldValue, newValue) -> {
            currentCarte.setQuestion(newValue);
            try {
                model.update(currentCarte);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        promptreponse.textProperty().addListener((observable, oldValue, newValue) -> {
            currentCarte.setReponse(newValue);
            try {
                model.update(currentCarte);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        update();
    }

    @FXML
    private void flipCard() {
        if (isFlipping) {
            return;
        }
        isFlipping = true;
        carteBack = new VBox();
        // Vertically center a label
        Label backLabel = new Label("Back");
        backLabel.setTranslateY(-backLabel.getHeight() / 2);
        carteBack.getChildren().addAll(backLabel);
        if (isFront) {
            RotateTransition rotateTransition = new RotateTransition(Duration.seconds(0.5), carte);
            rotateTransition.setByAngle(90);
            rotateTransition.setAxis(Rotate.X_AXIS);
            rotateTransition.play();
            rotateTransition.setOnFinished(event -> {
                // Put upside down
                RotateTransition flip = new RotateTransition(Duration.seconds(0.5), carte);
                flip.setByAngle(-90);
                flip.setAxis(Rotate.X_AXIS);
                flip.play();
                carte.getChildren().clear();
                carte.getChildren().addAll(carteBack);
                flip.setOnFinished(event1 -> {
                    isFlipping = false;
                });
            });
        } else {
            RotateTransition rotateTransition = new RotateTransition(Duration.seconds(0.5), carte);
            rotateTransition.setByAngle(90);
            rotateTransition.setAxis(Rotate.X_AXIS);
            rotateTransition.play();
            rotateTransition.setOnFinished(event -> {
                // Put upside down
                RotateTransition flip = new RotateTransition(Duration.seconds(0.5), carte);
                flip.setByAngle(-90);
                flip.setAxis(Rotate.X_AXIS);
                flip.play();
                carte.getChildren().clear();
                carte.getChildren().addAll(carteFront);
                flip.setOnFinished(event1 -> {
                    isFlipping = false;
                });
            });
        }
        isFront = !isFront;
    }

    @FXML
    public void retour() {
        try {
            if (currentPile != null) {
                App.setRoot("editionPile", currentPile);
            } else {
                App.setRoot("listeCarte");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void dupliquerCarte() {
        try {
            Carte newCarte = new Carte(currentCarte.getTitre(), currentCarte.getQuestion(),
                    currentCarte.getReponse(), currentCarte.getMetadata(),
                    currentCarte.getMetadata());
            model.create(newCarte);
            App.setRoot("editionPile", currentPile);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void supprimerCarte() {
        try {
            // si y'a un bug regarder ici
            model.delete(currentCarte);
            currentPile.removeCarte(currentCarte);
            App.setRoot("editionPile", currentPile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void exporterCarte() {
        try {
            App.exportCarte(currentCarte);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        labelTitre.setText(currentCarte.getTitre());
        labelQuestion.setText(currentCarte.getQuestion());
        labelReponse.setText(currentCarte.getReponse());
        promptquestion.setText(currentCarte.getQuestion());
        promptreponse.setText(currentCarte.getReponse());
        prompttitre.setText(currentCarte.getTitre());
    }


}
