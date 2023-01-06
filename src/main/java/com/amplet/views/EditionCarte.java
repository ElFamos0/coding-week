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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
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
    private Button imageBtn;

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
                currentCarte.setImage(newFile.getName());
                try {
                    model.update(currentCarte);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
        // Get the reponse from carteback
        if (carteBack != null) {
            Label backLabel = (Label) carteBack.getCenter();
            backLabel.setText(currentCarte.getReponse());
        }


        promptquestion.setText(currentCarte.getQuestion());
        promptreponse.setText(currentCarte.getReponse());
        prompttitre.setText(currentCarte.getTitre());
    }


}
