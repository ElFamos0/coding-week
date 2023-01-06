package com.amplet.views;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import com.amplet.app.App;
import com.amplet.app.Carte;
import com.amplet.app.Model;
import com.amplet.app.Pile;
import com.amplet.app.ViewController;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;


public class EditionCarte extends ViewController {
    public EditionCarte(Model model, Object... args) {
        super(model);
        model.addObserver(this);
        ctxEdit.setCarteCourante((Carte) args[0]);

        // si on a une pile en argument, on va retourner a l'édition de pile
        // sinon on retourne a la liste des cartes
        // selon le type de args[1]

        if (args[1] instanceof Pile) {
            ctxEdit.setPileCourante((Pile) args[1]);
        } else if (args[1] instanceof String) {
            if (args[1].equals("listeCarte")) {
                ctxEdit.setPileCourante(null);
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
    private BorderPane carteBack;
    private boolean isFront = true;
    private boolean isFlipping = false;

    @FXML
    private Label labelQuestion;

    @FXML
    private Label labelTitre;

    @FXML
    private TextField prompttitre;

    @FXML
    private TextArea promptquestion;

    @FXML
    private TextField promptreponse;

    @FXML
    private ImageView image;

    @FXML
    private Button imageBtn;

    @FXML
    private Button imageDelBtn;

    @FXML
    public void initialize() {
        carteFront = new VBox();
        carteFront.getChildren().addAll(carte.getChildren());
        carte.getChildren().clear();
        carte.getChildren().addAll(carteFront);

        // update on text change
        prompttitre.textProperty().addListener((observable, oldValue, newValue) -> {
            ctxEdit.getCarteCourante().setTitre(newValue);
            try {
                model.update(ctxEdit.getCarteCourante());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        promptquestion.textProperty().addListener((observable, oldValue, newValue) -> {
            ctxEdit.getCarteCourante().setQuestion(newValue);
            try {
                model.update(ctxEdit.getCarteCourante());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        promptreponse.textProperty().addListener((observable, oldValue, newValue) -> {
            ctxEdit.getCarteCourante().setReponse(newValue);
            try {
                model.update(ctxEdit.getCarteCourante());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });



        // flip the card if needed when the prompt are focused
        prompttitre.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                if (!isFront) {
                    flipCard(null);
                }
            }
        });

        promptquestion.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                if (!isFront) {
                    flipCard(null);
                }
            }
        });

        promptreponse.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                if (isFront) {
                    flipCard(null);
                }
            }
        });

        imageBtn.setOnAction(event -> {
            System.out.println("imageBtn");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Images files",
                    "*.jpg", "*.png", "*.gif", "*.jpeg");
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(extFilter);
            fileChooser.setTitle("Open Resource File");
            File file = fileChooser
                    .showOpenDialog((Stage) ((Node) event.getSource()).getScene().getWindow());
            if (file != null) {
                // Move file to data folder, making sure it has a unique name and that the folder
                // exists
                File dataFolder = new File("./data");
                if (!dataFolder.exists()) {
                    dataFolder.mkdir();
                }
                File newFile = new File(dataFolder.getAbsolutePath() + "/" + file.getName());
                int i = 1;
                while (newFile.exists()) {
                    newFile = new File(dataFolder.getAbsolutePath() + "/" + i + file.getName());
                    i++;
                }
                try {
                    Files.copy(file.toPath(), newFile.toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ctxEdit.getCarteCourante().setImage(newFile.getName());
                try {
                    model.update(ctxEdit.getCarteCourante());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Image picture = ctxEdit.getCarteCourante().getImage();
                if (picture != null) {
                    image.setImage(picture);
                } else {
                    image.setImage(null);
                }
                if (!isFront) {
                    flipCard(null);
                }
            }
        });

        imageDelBtn.setOnAction(event -> {
            System.out.println("imageDelBtn");
            ctxEdit.getCarteCourante().setImage(null);
            try {
                model.update(ctxEdit.getCarteCourante());
            } catch (Exception e) {
                e.printStackTrace();
            }
            // image is in assets/defaut.png
            image.setImage(new Image(App.class.getResourceAsStream("assets/default.png")));
            if (!isFront) {
                flipCard(null);
            }
        });
        update();
    }

    @FXML
    private void clickcarte() {
        flipCard(null);
    }


    private void flipCard(javafx.event.EventHandler<ActionEvent> value) {
        if (isFlipping) {
            return;
        }
        isFlipping = true;
        carteBack = new BorderPane();
        Label backLabel = new Label(ctxEdit.getCarteCourante().getReponse());
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

    @FXML
    public void retour() {
        try {
            if (ctxEdit.getPileCourante() != null) {
                App.setRoot("editionPile", ctxEdit.getPileCourante());
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
            Carte newCarte = new Carte(ctxEdit.getCarteCourante().getTitre(),
                    ctxEdit.getCarteCourante().getQuestion(),
                    ctxEdit.getCarteCourante().getReponse(),
                    ctxEdit.getCarteCourante().getMetadata(),
                    ctxEdit.getCarteCourante().getMetadata());
            model.create(newCarte);
            App.setRoot("editionPile", ctxEdit.getPileCourante());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void supprimerCarte() {
        try {
            // si y'a un bug regarder ici
            model.delete(ctxEdit.getCarteCourante());
            ctxEdit.getPileCourante().removeCarte(ctxEdit.getCarteCourante());
            App.setRoot("editionPile", ctxEdit.getPileCourante());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void exporterCarte() {
        try {
            App.exportCarte(ctxEdit.getCarteCourante());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        labelTitre.setText(ctxEdit.getCarteCourante().getTitre());
        labelQuestion.setText(ctxEdit.getCarteCourante().getQuestion() + "\n\n");
        // Get the reponse from carteback
        if (carteBack != null) {
            Label backLabel = (Label) carteBack.getCenter();
            backLabel.setText(ctxEdit.getCarteCourante().getReponse());
        }


        promptquestion.setText(ctxEdit.getCarteCourante().getQuestion());
        promptreponse.setText(ctxEdit.getCarteCourante().getReponse());
        prompttitre.setText(ctxEdit.getCarteCourante().getTitre());

        Image picture = ctxEdit.getCarteCourante().getImage();
        if (picture != null) {
            image.setImage(picture);
        } else {
            // image is in assets/defaut.png
            image.setImage(new Image(App.class.getResourceAsStream("assets/default.png")));
        }
    }


}
