package com.amplet.views;

import com.amplet.app.Model;
import com.amplet.app.ViewController;
import com.amplet.app.App;
import com.amplet.app.Carte;
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
    int nbCartes;
    boolean isRandomSelected;
    int repetitionProbability;
    boolean isFavorisedFailedSelected;
    boolean lockvalider = false;
    int tempsreponse;
    ArrayList<Integer> cartesPileId;
    ArrayList<Integer> cartesApprouvéesPileId;


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
    public void retour() throws Exception {
        timer.cancel();
        try {
            App.setRoot("index");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @FXML
    public void initialize() {
        cartesPileId = (ArrayList<Integer>) ctxIg.getCartesProposéesIdPile().clone();
        cartesApprouvéesPileId = new ArrayList<Integer>();
        ctxIg.getCartesRejetées().clear();
        isRandomSelected = ctx.isRandomSelected();
        repetitionProbability = ctx.getRepetitionProbability();
        isFavorisedFailedSelected = ctx.isFavorisedFailedSelected();
        nbCartes = ctx.getNbCartes();
        lockvalider = false;
        tempsreponse = ctx.getTempsReponse();
        carteFront = new VBox();
        carteFront.getChildren().addAll(carte.getChildren());
        carte.getChildren().clear();
        carte.getChildren().addAll(carteFront);
        initializeNbCartes();
        dealcard();
        setTimer();
        update();
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
        if (ctxIg.getCartesProposées().size() == 0) {
            ctxIg.setCartesProposées((ArrayList<Carte>) ctxIg.getCartesApprouvées().clone());
            ctxIg.setCartesApprouvées(new ArrayList<Carte>());
            ctxIg.setCartesRejetées(new ArrayList<Carte>());
            ctxIg.setCartesProposéesIdPile((ArrayList<Integer>) cartesPileId.clone());
        }
        if (isRandomSelected) {
            if (repetitionProbability > 0) {
                if (isFavorisedFailedSelected) {

                    double pa = ctxIg.getCartesProposées().size();
                    double pb = ctxIg.getCartesApprouvées().size();
                    double pc = ctxIg.getCartesRejetées().size();

                    double maxpoids = pa + (pb + 0.5 * pc) * repetitionProbability / 100;

                    pa = pa / maxpoids;
                    pb = pb / maxpoids;
                    pc = pc / maxpoids;
                    double r = Math.random();

                    if (r > pa + pb) {
                        int rbis =
                                ((int) (Math.random() * (ctxIg.getCartesRejetées().size() - 0.1)));
                        int carteId = ctxIg.getCartesRejetées().get(rbis).getId();
                        int i = 0;
                        while (ctxIg.getCartesApprouvées().get(i).getId() != carteId) {
                            i++;
                        }
                        int pileId = cartesApprouvéesPileId.get(i);

                        ctxIg.setCarteCourante(ctxIg.getCartesRejetées().get(rbis), pileId);

                    } else if (r > pa) {
                        int rbis = ((int) (Math.random()
                                * (ctxIg.getCartesApprouvées().size() - 0.1)));
                        ctxIg.setCarteCourante(ctxIg.getCartesApprouvées().get(rbis),
                                cartesApprouvéesPileId.get(rbis));
                    } else {
                        int rbis =
                                ((int) (Math.random() * (ctxIg.getCartesProposées().size() - 0.1)));
                        ctxIg.setCarteCourante(ctxIg.getCartesProposées().get(rbis),
                                ctxIg.getCartesProposéesIdPile().get(rbis));
                        ctxIg.getCartesProposéesIdPile().remove(rbis);
                    }



                } else {

                    double pa = ctxIg.getCartesProposées().size();
                    double pb = ctxIg.getCartesApprouvées().size();
                    // double pc = ctxIg.getCartesRejetées().size();
                    double maxpoids = pa + pb * repetitionProbability / 100;
                    pa = pa / maxpoids;
                    pb = pb / maxpoids;
                    double r = Math.random();
                    if (r > pa) {
                        int rbis = ((int) (Math.random()
                                * (ctxIg.getCartesApprouvées().size() - 0.1)));
                        ctxIg.setCarteCourante(ctxIg.getCartesApprouvées().get(rbis),
                                cartesApprouvéesPileId.get(rbis));

                    } else {
                        int rbis =
                                ((int) (Math.random() * (ctxIg.getCartesProposées().size() - 0.1)));
                        ctxIg.setCarteCourante(ctxIg.getCartesProposées().get(rbis),
                                ctxIg.getCartesProposéesIdPile().get(rbis));
                        ctxIg.getCartesProposéesIdPile().remove(rbis);
                    }
                }
            } else {

                int r = ((int) (Math.random() * (ctxIg.getCartesProposées().size() - 0.1)));
                ctxIg.setCarteCourante(ctxIg.getCartesProposées().get(r),
                        ctxIg.getCartesProposéesIdPile().get(r));
                ctxIg.getCartesProposéesIdPile().remove(r);
            }

        } else {
            ctxIg.setCarteCourante(ctxIg.getCartesProposées().get(0),
                    ctxIg.getCartesProposéesIdPile().get(0));
            ctxIg.getCartesProposéesIdPile().remove(0);
        }
    }

    @FXML
    public void valider() {
        if (lockvalider) {
            return;
        }
        lockvalider = true;
        System.out.println("valider");
        if (!(hasCarte(ctxIg.getCartesApprouvées(), ctxIg.getCarteCourante()))) {
            ctxIg.approuverCarteCourante();
            ctxIg.removeCartesProposées(ctxIg.getCarteCourante());
            cartesApprouvéesPileId.add(ctxIg.getCarteCouranteIdPile());
        }
        if ((hasCarte(ctxIg.getCartesRejetées(), ctxIg.getCarteCourante()))) {
            ctxIg.removeCartesRejetées(ctxIg.getCarteCourante());
        }
        ctxResultat.addCarteJouée(ctxIg.getCarteCourante(), ctxIg.getCarteCouranteIdPile(), true);
        cartesvues++;
        ctxIg.setNbCartesPassées(ctxIg.getNbCartesPassées() + 1);

        if (!ctxIg.ilResteDesCartes(nbCartes)) {
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
        if (!(hasCarte(ctxIg.getCartesApprouvées(), ctxIg.getCarteCourante()))) {
            ctxIg.approuverCarteCourante();
            ctxIg.removeCartesProposées(ctxIg.getCarteCourante());
            cartesApprouvéesPileId.add(ctxIg.getCarteCouranteIdPile());
        }
        if (!(hasCarte(ctxIg.getCartesRejetées(), ctxIg.getCarteCourante()))) {
            ctxIg.addCartesRejetées(ctxIg.getCarteCourante());
        }
        ctxResultat.addCarteJouée(ctxIg.getCarteCourante(), ctxIg.getCarteCouranteIdPile(), false);
        cartesvues++;
        ctxIg.setNbCartesPassées(ctxIg.getNbCartesPassées() + 1);
        if (!ctxIg.ilResteDesCartes(nbCartes)) {
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
        nbdecarte.setText("Nombre de cartes : " + cartesvues + "/" + Integer.toString(nbCartes));
        progress.setProgress((double) ctxIg.getNbCartesPassées() / nbCartes);
        Image picture = ctxIg.getCarteCourante().getImage();
        if (picture != null) {
            image.setImage(picture);
        } else {
            image.setImage(null);
        }
    }

    public void initializeNbCartes() {

        int i = ctxIg.getCartesProposées().size();
        if (repetitionProbability == 0) {
            if (nbCartes > i) {
                nbCartes = i;
            }
            nbCartes = Math.max(nbCartes, 1);
        }
        nbdecarte.setText("Nombre de cartes : 1/" + Integer.toString(nbCartes));
    }

    public boolean hasCarte(ArrayList<Carte> array, Carte carte) {

        for (Carte c : array) {
            if (c.equals(carte)) {
                return true;
            }
        }

        return false;

    }

}
