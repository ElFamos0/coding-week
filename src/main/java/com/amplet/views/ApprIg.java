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
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import javafx.application.Platform;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ApprIg extends ViewController {

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
    private Label reponse;
    @FXML
    private Button boutonrefuser;
    @FXML
    private Button boutonvalider;

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
    private VBox carteBack;
    private boolean isFront = true;
    private boolean isFlipping = false;

    public ApprIg(Model model) {
        super(model);
        model.addObserver(this);
        ctx = model.getCtx();
        this.cartesaproposer = ctx.getSelectedCartes();
        this.cartesapprouvees = new ArrayList<Carte>();
        this.isRandomSelected = ctx.isRandomSelected();
        this.repetitionProbability = ctx.getRepetitionProbability();
        this.isFavorisedFailedSelected = ctx.isFavorisedFailedSelected();
        this.tempsreponse = ctx.getTempsReponse();
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
        nbdecarte.setText("Nombre de cartes : " + ctx.getNbCartes());
        dealcard();
    }


    public void setTimer() {
        boutonrefuser.setDisable(true);
        boutonvalider.setDisable(true);
        Timer timer = new Timer();
        interval = tempsreponse;
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (interval > 0) {
                    Platform.runLater(() -> timertext.setText("Temps : " + interval));
                    interval--;
                } else {
                    flipCard();
                    boutonvalider.setDisable(false);
                    boutonrefuser.setDisable(false);
                    timer.cancel();
                }
            }
        }, 1000, 1000);
    }


    public void dealcard() {
        if (isRandomSelected) {
            int random = (int) (Math.random() * cartesaproposer.size());
            currentCarte = cartesaproposer.get(random);
            cartesaproposer.remove(random);
            update();
            setTimer();
        } else {
            currentCarte = cartesaproposer.get(0);
            cartesaproposer.remove(0);
        }

    }

    @FXML
    public void valider() {
        cartesapprouvees.add(currentCarte);
        cartesvues++;
        if (cartesaproposer.size() == 0) {
            try {
                App.setRoot("apprResultat", new ApprResultat(model));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            dealcard();
            flipCard();
            update();
        }
    }

    public void refuser() {
        cartesapprouvees.add(currentCarte);
        cartesvues++;
        cartesaproposer.add(currentCarte);
        if (cartesaproposer.size() == 0) {
            try {
                App.setRoot("apprResultat", new ApprResultat(model));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            dealcard();
            flipCard();
            update();
        }
    }



    // fonction d'animation de résolution de la carte.
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

    public void update() {
        titrecarte.setText(currentCarte.getTitre());
        question.setText(currentCarte.getQuestion());
        reponse.setText(currentCarte.getReponse());
    }

}
