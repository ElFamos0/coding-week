package com.amplet.views;

import com.amplet.app.App;
import com.amplet.app.Lecon;
import com.amplet.app.Model;
import com.amplet.app.ViewController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;


public class EditionLecon extends ViewController {
    public EditionLecon(Model model, Object... args) {
        super(model);
        model.addObserver(this);
        ctxEdit.setLeconCourante((Lecon) args[0]);
    }

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @FXML
    private TextField nomLecon;
    @FXML
    private TextField tagField;
    @FXML
    private HBox tags;
    @FXML
    private Label labelTagsCount;

    @FXML
    public void initialize() {
        nomLecon.setText(ctxEdit.getLeconCourante().getNom());
        nomLecon.textProperty().addListener((observable, oldValue, newValue) -> {
            ctxEdit.getLeconCourante().setNom(newValue);
            try {
                model.update(ctxEdit.getLeconCourante());
            } catch (Exception e) {
                // System.out.println(e.getMessage());
            }
        });

        tagField.textProperty().addListener((observable, oldValue, newValue) -> {
            // If it ends with a ';', save tag and clear field
            if (newValue.endsWith(";")) {
                String tag = newValue.substring(0, newValue.length() - 1);
                if (!tag.isEmpty()) {
                    try {
                        if (ctxEdit.getLeconCourante().getTags().size() < 10
                                && !(ctxEdit.getLeconCourante().hasTag(tag))) {
                            model.create(ctxEdit.getLeconCourante(), tag);
                        }
                    } catch (Exception e) {
                        // System.out.println(e.getMessage());
                    }
                    update();
                    Platform.runLater(() -> {
                        tagField.positionCaret(0);
                        tagField.clear();
                    });
                }
            }
        });

        // Validate on enter
        tagField.setOnAction(event -> {
            String tag = tagField.getText();
            if (!tag.isEmpty()) {
                try {
                    if (ctxEdit.getLeconCourante().getTags().size() < 10
                            && !(ctxEdit.getLeconCourante().hasTag(tag))) {
                        model.create(ctxEdit.getLeconCourante(), tag);
                    }
                } catch (Exception e) {
                    // System.out.println(e.getMessage());
                }
                update();
                Platform.runLater(() -> {
                    tagField.positionCaret(0);
                    tagField.clear();
                });
            }
        });

        update();
    }

    public void update() {
        // Add the tags
        tags.getChildren().clear();
        for (String tag : ctxEdit.getLeconCourante().getTags()) {
            Button tagButton = new Button();
            tagButton.setText(tag);
            tagButton.setOnAction(event -> {
                try {
                    model.delete(ctxEdit.getLeconCourante(), tag);
                } catch (Exception e) {
                    // System.out.println(e.getMessage());
                }
                update();
            });
            tagButton.getStylesheets().add(App.class.getResource("boutonTag.css").toExternalForm());
            tagButton.getStyleClass().add("tag");
            tags.getChildren().add(tagButton);
        }

        labelTagsCount
                .setText(Integer.toString(ctxEdit.getLeconCourante().getTags().size()) + "/10");
    }

    @FXML
    public void retourListe() {
        try {
            App.setRoot("listeLecons");
        } catch (Exception e) {
            // System.out.println(e.getMessage());
        }
    }
}
