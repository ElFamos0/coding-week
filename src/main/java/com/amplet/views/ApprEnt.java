package com.amplet.views;


import com.amplet.app.Model;
import com.amplet.app.ViewController;
import com.amplet.app.App;
import com.amplet.app.Carte;
import com.amplet.app.Context;
import com.amplet.app.Model;
import com.amplet.app.Pile;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.application.Platform;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ApprEnt extends ViewController {

    @FXML
    private VBox carte;
    @FXML
    private Label timertext;
    @FXML
    private Label nbdecarte;
    @FXML
    private Label titrecarte;
    @FXML
    private Label question;
    @FXML
    private Button boutonrefuser;
    @FXML
    private Button boutonvalider;
    @FXML
    private ProgressBar progress;


    private ArrayList<Carte> cartesaproposer;
    private ArrayList<Carte> cartesapprouvees;

    int interval;
    int cartesrestantes = 1;
    int cartesvues = 0;
    boolean isRandomSelected;
    int repetitionProbability;
    boolean isFavorisedFailedSelected;
    int tempsreponse;

    Carte currentCarte;


    Context ctx;
    private VBox carteFront;
    private BorderPane carteBack;
    private boolean isFront = true;
    private boolean isFlipping = false;

    public ApprEnt(Model model) {
        super(model);
        model.addObserver(this);
        ctx = model.getCtx();
        this.cartesaproposer = ctx.getSelectedCartes();
        this.cartesapprouvees = new ArrayList<Carte>();
        this.isRandomSelected = ctx.isRandomSelected();
        this.repetitionProbability = ctx.getRepetitionProbability();
        this.isFavorisedFailedSelected = ctx.isFavorisedFailedSelected();

    }

    @Override
    public String getName() {
        return this.getClass().getName();
    }


    @FXML
    public void initialize() {
        carteFront = new VBox();
        carteFront.getChildren().addAll(carte.getChildren());
        carte.getChildren().clear();
        carte.getChildren().addAll(carteFront);
        nbdecarte.setText("Nombre de cartes : 0/" + cartesaproposer.size());
        dealcard();
        timertext.setText("MODE APPRENTISSAGE");
    }


    public void dealcard() {
        if (isRandomSelected) {
            int random = (int) (Math.random() * cartesaproposer.size());
            currentCarte = cartesaproposer.get(random);
            cartesaproposer.remove(random);
        } else {
            currentCarte = cartesaproposer.get(0);
            cartesaproposer.remove(0);
        }
        update();

    }

    @FXML
    public void valider() {
        System.out.println("valider");
        cartesapprouvees.add(currentCarte);
        cartesvues++;
        if (cartesaproposer.size() == 0) {
            try {
                App.setRoot("apprResultat");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (!isFront) {
                flipCard(e -> {
                    dealcard();
                    update();
                });
            } else {
                dealcard();
                update();
            }
        }

    }

    @FXML
    public void refuser() {
        System.out.println("refuser");
        cartesapprouvees.add(currentCarte);
        cartesvues++;
        cartesaproposer.add(currentCarte);
        if (cartesaproposer.size() == 0) {
            try {
                App.setRoot("apprResultat");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (!isFront) {
                flipCard(e -> {
                    dealcard();
                    update();
                });
            } else {
                dealcard();
                update();
            }
        }
    }

    @FXML
    public void clickcarte() {
        flipCard(null);
    }


    // fonction d'animation de résolution de la carte.
    @FXML
    private void flipCard(javafx.event.EventHandler<ActionEvent> value) {
        if (isFlipping) {
            return;
        }
        isFlipping = true;
        carteBack = new BorderPane();
        Label backLabel = new Label(currentCarte.getReponse());
        backLabel.setStyle("-fx-font-size: 20px;");
        backLabel.setStyle("-fx-font-weight: bold;");
        carteBack.setCenter(backLabel);
        carteBack.setPrefSize(carte.getWidth(), carte.getHeight());
        carteBack.setMaxSize(carte.getWidth(), carte.getHeight());
        carteBack.setMinSize(carte.getWidth(), carte.getHeight());
        if (isFront) {
            RotateTransition rotateTransition = new RotateTransition(Duration.seconds(0.2), carte);
            rotateTransition.setByAngle(90);
            rotateTransition.setAxis(Rotate.X_AXIS);
            rotateTransition.play();
            rotateTransition.setOnFinished(event -> {
                // Put upside down
                RotateTransition flip = new RotateTransition(Duration.seconds(0.2), carte);
                flip.setByAngle(-90);
                flip.setAxis(Rotate.X_AXIS);
                flip.play();
                carte.getChildren().clear();
                carte.getChildren().addAll(carteBack);
                flip.setOnFinished(event1 -> {
                    isFlipping = false;
                    if (value != null)
                        value.handle(null);
                });
            });
        } else {
            RotateTransition rotateTransition = new RotateTransition(Duration.seconds(0.2), carte);
            rotateTransition.setByAngle(90);
            rotateTransition.setAxis(Rotate.X_AXIS);
            rotateTransition.play();
            rotateTransition.setOnFinished(event -> {
                // Put upside down
                RotateTransition flip = new RotateTransition(Duration.seconds(0.2), carte);
                flip.setByAngle(-90);
                flip.setAxis(Rotate.X_AXIS);
                flip.play();
                carte.getChildren().clear();
                carte.getChildren().addAll(carteFront);
                flip.setOnFinished(event1 -> {
                    isFlipping = false;
                    if (value != null)
                        value.handle(null);
                });
            });
        }
        isFront = !isFront;
    }

    public void update() {
        titrecarte.setText(currentCarte.getTitre());
        question.setText(currentCarte.getQuestion());
        progress.setProgress(
                (double) cartesvues / (double) (cartesvues + cartesaproposer.size() + 1));
        nbdecarte.setText("Nombre de cartes : " + cartesvues + "/"
                + (cartesvues + cartesaproposer.size() + 1));
    }

}
