package com.amplet.views;


import com.amplet.app.Model;
import com.amplet.app.ViewController;
import java.io.File;
import com.amplet.app.App;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import javafx.event.ActionEvent;

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
    @FXML
    private ImageView image;

    int interval;
    int cartesrestantes = 1;
    int cartesvues = 1;
    boolean isRandomSelected;
    int repetitionProbability;
    boolean isFavorisedFailedSelected;
    int tempsreponse;


    private VBox carteFront;
    private BorderPane carteBack;
    private boolean isFront = true;
    private boolean isFlipping = false;

    public ApprEnt(Model model) {
        super(model);
        model.addObserver(this);
        ctx = model.getCtx();
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
        nbdecarte.setText("Nombre de cartes : 1/" + ctxEnt.getCartesProposées().size());
        dealcard();
        timertext.setText("MODE APPRENTISSAGE");
    }


    public void dealcard() {
        if (isRandomSelected) {
            ctxEnt.setCarteCouranteRandom();
        } else {
            ctxEnt.setCarteCouranteNext();
        }
        update();

    }

    @FXML
    public void valider() {
        System.out.println("valider");
        ctxEnt.approuverCarteCourante();
        cartesvues++;
        if (!ctxEnt.ilResteDesCartes()) {
            try {
                App.setRoot("apprFinEntrainement");
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
        ctxEnt.rejeterCarteCourante();
        cartesvues++;
        if (!ctxEnt.ilResteDesCartes()) {
            try {
                App.setRoot("apprFinEntrainement");
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
        Label backLabel = new Label(ctxEnt.getCarteCourante().getReponse());
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
        titrecarte.setText(ctxEnt.getCarteCourante().getTitre());
        question.setText(ctxEnt.getCarteCourante().getQuestion());
        progress.setProgress(
                (double) cartesvues / (double) (cartesvues + ctxEnt.getNbCartesProposées()));
        nbdecarte.setText("Nombre de cartes : " + cartesvues + "/"
                + (cartesvues + ctxEnt.getNbCartesProposées()));
        // set the image
        Image picture = ctxEnt.getCarteCourante().getImage();
        if (picture != null) {
            image.setImage(picture);
        } else {
            image.setImage(null);
        }

    }

}
