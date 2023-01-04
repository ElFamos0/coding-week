package com.amplet.views;

import com.amplet.app.Model;
import com.amplet.app.ViewController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.TilePane;

public class EditionPile extends ViewController {
    public EditionPile(Model model) {
        super(model);
        model.addObserver(this);
    }

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @FXML
    private TilePane availableCards;
    @FXML
    private TilePane chosenCards;

    @Override
    public void update() {}

    @FXML
    public void initialize() {
        // Add two cards to the available cards list
        availableCards.getChildren().add(createCard(0));
        availableCards.getChildren().add(createCard(1));

        // Add two cards to the chosen cards list
        chosenCards.getChildren().add(createCard(2));
        chosenCards.getChildren().add(createCard(3));

        availableCards.setOnDragOver(event -> {
            if (event.getGestureSource() != availableCards && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        availableCards.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            boolean success = false;
            if (dragboard.hasString()) {
                String id = dragboard.getString();
                for (Node node : availableCards.getChildren()) {
                    if (node instanceof Button) {
                        Button button = (Button) node;
                        if (button.getId().equals(id)) {
                            availableCards.getChildren().remove(button);
                            break;
                        }
                    }
                }
                for (Node node : chosenCards.getChildren()) {
                    if (node instanceof Button) {
                        Button button = (Button) node;
                        if (button.getId().equals(id)) {
                            chosenCards.getChildren().remove(button);
                            break;
                        }
                    }
                }
                Button card = createCard(Integer.parseInt(id));
                availableCards.getChildren().add(card);
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });

        chosenCards.setOnDragOver(event -> {
            if (event.getGestureSource() != chosenCards && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        chosenCards.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            boolean success = false;
            if (dragboard.hasString()) {
                String id = dragboard.getString();
                for (Node node : availableCards.getChildren()) {
                    if (node instanceof Button) {
                        Button button = (Button) node;
                        if (button.getId().equals(id)) {
                            availableCards.getChildren().remove(button);
                            break;
                        }
                    }
                }
                for (Node node : chosenCards.getChildren()) {
                    if (node instanceof Button) {
                        Button button = (Button) node;
                        if (button.getId().equals(id)) {
                            chosenCards.getChildren().remove(button);
                            break;
                        }
                    }
                }
                Button card = createCard(Integer.parseInt(id));
                chosenCards.getChildren().add(card);
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }

    public void emptyLists() {
        availableCards.getChildren().clear();
        chosenCards.getChildren().clear();
    }

    public Button createCard(Integer id) {
        Button card = new Button();
        card.setPrefSize(100, 100);
        card.setId(id.toString());
        card.setText("Card " + id.toString());

        card.setOnDragDetected(event -> {
            Dragboard dragboard = card.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(card.getId());
            dragboard.setContent(content);
            event.consume();
        });

        return card;
    }

}
