package com.amplet.views;

import com.amplet.app.Model;
import com.amplet.app.ViewController;
import com.amplet.app.App;
import com.amplet.app.Carte;
import com.amplet.app.Context;
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
import javafx.application.Platform;
import javafx.event.ActionEvent;
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
    private TextField reponseuser;
    @FXML
    private Button textvalider;

    @FXML
    private ProgressBar progress;

    private ArrayList<Carte> cartesaproposer;
    private ArrayList<Carte> cartesapprouvees;
    private ArrayList<Integer> cartesaproposerPileId;

    int interval;
    int cartesrestantes = 1;
    int cartesvues = 1;
    boolean isRandomSelected;
    int repetitionProbability;
    boolean isFavorisedFailedSelected;
    boolean lockvalider = false;
    int tempsreponse;

    Carte currentCarte;
    int currentCartePileId;

    Context ctx;
    private VBox carteFront;
    private BorderPane carteBack;
    private boolean isFront = true;
    private boolean isFlipping = false;

    public ApprIg(Model model) {
        super(model);
        model.addObserver(this);
        ctx = model.getCtx();
        this.cartesaproposer = ctx.getSelectedCartes();
        this.cartesaproposerPileId = ctx.getSelectedCartesPileId();
        this.cartesapprouvees = new ArrayList<Carte>();
        this.isRandomSelected = ctx.isRandomSelected();
        this.repetitionProbability = ctx.getRepetitionProbability();
        this.isFavorisedFailedSelected = ctx.isFavorisedFailedSelected();
        this.tempsreponse = ctx.getTempsReponse() + 1;
        ctx.resetPlayed();
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
        nbdecarte.setText("Nombre de cartes : 1/" + cartesaproposer.size() + 1);
        dealcard();
        setTimer();
    }

    Timer timer = new Timer();

    public void setTimer() {
        interval = tempsreponse;
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (interval > 0) {
                    Platform.runLater(() -> timertext.setText("Temps : " + interval));
                    interval--;
                } else {
                    if (isFront && !isFlipping) {
                        flipCard(e -> {
                            attendre(1);
                            refuser();
                        });

                    }
                    Platform.runLater(() -> timertext.setText("Temps : 0"));
                }
            }
        }, 1000, 1000);
    }

    public void resetTimer() {
        interval = tempsreponse;
        timertext.setText("Temps : " + interval);
    }

    public void attendre(int secondes) {
        try {
            Thread.sleep(secondes * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void dealcard() {
        if (isRandomSelected) {
            int random = (int) (Math.random() * cartesaproposer.size());
            currentCarte = cartesaproposer.get(random);
            cartesaproposer.remove(random);
            currentCartePileId = cartesaproposerPileId.get(random);
            cartesaproposerPileId.remove(random);
            update();
        } else {
            currentCarte = cartesaproposer.get(0);
            cartesaproposer.remove(0);
            currentCartePileId = cartesaproposerPileId.get(0);
            cartesaproposerPileId.remove(0);
            update();
        }

    }

    @FXML
    public void valider() {
        if (lockvalider) {
            return;
        }
        lockvalider = true;
        System.out.println("valider");
        cartesapprouvees.add(currentCarte);
        ctx.addPlayed(currentCarte, true, currentCartePileId);
        cartesvues++;
        if (cartesaproposer.size() == 0) {
            try {
                timer.cancel();
                App.setRoot("apprResultat");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (isFront) {
                flipCard(e -> {
                    attendre(1);
                    flipCard(null);
                    dealcard();
                    update();
                    resetTimer();
                });
            } else {
                flipCard(null);
                dealcard();
                update();
                resetTimer();
            }
            lockvalider = false;
        }
    }

    @FXML
    public void refuser() {
        if (lockvalider) {
            return;
        }
        lockvalider = true;
        System.out.println("refuser");
        cartesapprouvees.add(currentCarte);
        cartesvues++;
        // take a random number between 0 and 100
        ctx.addPlayed(currentCarte, false, currentCartePileId);
        int random = (int) (Math.random() * 100);
        if (random < repetitionProbability) {
            cartesaproposer.add(currentCarte);
        }
        if (cartesaproposer.size() == 0) {
            try {
                timer.cancel();
                App.setRoot("apprResultat");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (isFront) {
                flipCard(e -> {
                    attendre(1);
                    flipCard(null);
                    dealcard();
                    update();
                    resetTimer();
                });
            } else {
                flipCard(null);
                dealcard();
                update();
                resetTimer();
            }
            lockvalider = false;
        }
    }

    @FXML
    public void clickcarte() {}

    @FXML
    public void textvalider() {
        String reponse = reponseuser.getText();
        if (reponse.equals("")) {
            return;
        }
        reponseuser.clear();
        if (reponse.toLowerCase().equals(currentCarte.getReponse().toLowerCase())) {
            valider();
        } else {
            refuser();
        }
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
        // set class to card-reponse
        backLabel.getStyleClass().add("card-reponse");
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
        nbdecarte.setText(
                "Nombre de cartes : " + cartesvues + "/" + (cartesvues + cartesaproposer.size()));
        progress.setProgress((double) cartesvues / (cartesvues + cartesaproposer.size()));
    }

}
