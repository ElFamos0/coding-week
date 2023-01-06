package com.amplet.views;

import com.amplet.app.Model;
import com.amplet.app.ViewController;
import com.amplet.app.App;
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
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    @FXML
    private ImageView image;

    int interval;
    int cartesrestantes = 1;
    int cartesvues = 1;
    boolean isRandomSelected;
    int repetitionProbability;
    boolean isFavorisedFailedSelected;
    boolean lockvalider = false;
    int tempsreponse;

    private VBox carteFront;
    private BorderPane carteBack;
    private boolean isFront = true;
    private boolean isFlipping = false;

    public ApprIg(Model model) {
        super(model);
        model.addObserver(this);
        this.isRandomSelected = ctx.isRandomSelected();
        this.repetitionProbability = ctx.getRepetitionProbability();
        this.isFavorisedFailedSelected = ctx.isFavorisedFailedSelected();
        this.tempsreponse = ctx.getTempsReponse() + 1;
        ctxResultat.reset();
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
        nbdecarte.setText("Nombre de cartes : 1/" + ctxIg.getNbCartesProposées() + 1);
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
            ctxIg.setCarteCouranteRandom();
            update();
        } else {
            ctxIg.setCarteCouranteNext();
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
        ctxIg.approuverCarteCourante();
        ctxResultat.addCarteJouée(ctxIg.getCarteCourante(), ctxIg.getCarteCouranteIdPile(), true);
        cartesvues++;
        if (!ctxIg.ilResteDesCartes()) {
            timer.cancel();
            flipCard(e -> {
                attendre(1);
                try {
                    App.setRoot("apprResultat");
                } catch (Exception f) {
                    f.printStackTrace();
                }
            });
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
        ctxIg.rejeterCarteCourante();
        // take a random number between 0 and 100
        ctxResultat.addCarteJouée(ctxIg.getCarteCourante(), ctxIg.getCarteCouranteIdPile(), false);
        cartesvues++;
        int random = (int) (Math.random() * 100);
        if (random < repetitionProbability) {
            ctxIg.addCartesProposées(ctxIg.getCarteCourante(), ctxIg.getCarteCouranteIdPile());
        }
        if (!ctxIg.ilResteDesCartes()) {
            timer.cancel();
            flipCard(e -> {
                attendre(1);
                try {
                    App.setRoot("apprResultat");
                } catch (Exception f) {
                    f.printStackTrace();
                }
            });
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
        if (reponse.toLowerCase().equals(ctxIg.getCarteCourante().getReponse().toLowerCase())) {
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
        Label backLabel = new Label(ctxIg.getCarteCourante().getReponse());
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
        titrecarte.setText(ctxIg.getCarteCourante().getTitre());
        question.setText(ctxIg.getCarteCourante().getQuestion());
        nbdecarte.setText("Nombre de cartes : " + cartesvues + "/"
                + (cartesvues + ctxIg.getNbCartesProposées()));
        progress.setProgress((double) cartesvues / (cartesvues + ctxIg.getNbCartesProposées()));
        Image picture = ctxIg.getCarteCourante().getImage();
        if (picture != null) {
            image.setImage(picture);
        } else {
            image.setImage(null);
        }
    }

}
