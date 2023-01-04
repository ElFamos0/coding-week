package com.amplet.views;

import java.util.HashMap;
import java.util.Map;
import com.amplet.app.App;
import com.amplet.app.Carte;
import com.amplet.app.Model;
import com.amplet.app.Pile;
import com.amplet.app.ViewController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.TilePane;

public class EditionPile extends ViewController {
    private Pile pile;
    private Map<String, Carte> cartes;

    public EditionPile(Model model) {
        super(model);
        model.addObserver(this);
    }

    public EditionPile(Model model, Object... args) {
        super(model, args);
        pile = (Pile) args[0];
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
    public void update() {
        // Clear the list
        availableCards.getChildren().clear();

        // Add the plus button
        Button button = createAddCard();
        availableCards.getChildren().add(button);

        // Add in chosenCards the cards that are in the pile
        for (Carte carte : pile.getCartes()) {
            Button card = createCard(carte);
            chosenCards.getChildren().add(card);
        }

        // Add the cards that are not in chosenCards
        for (Carte carte : model.getAllCartes()) {
            Boolean found = false;
            for (Node node : chosenCards.getChildren()) {
                if (node instanceof Button) {
                    Button b = (Button) node;
                    if (b.getId().equals(carte.getId().toString())) {
                        found = true;
                        break;
                    }
                }
            }
            if (!found) {
                Button card = createCard(carte);
                availableCards.getChildren().add(card);
            }
        }
    }

    @FXML
    public void initialize() {
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
                Carte c = cartes.get(id);
                Button card = createCard(c);
                availableCards.getChildren().add(card);
                success = true;
                try {
                    model.delete(pile, c);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
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
                Carte c = cartes.get(id);
                Button card = createCard(c);
                chosenCards.getChildren().add(card);
                success = true;
                try {
                    model.create(pile, c);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });

        cartes = new HashMap<>();

        update();
    }

    public void emptyLists() {
        availableCards.getChildren().clear();
        chosenCards.getChildren().clear();
    }

    public Button createCard(Carte c) {
        Button card = new Button();
        card.setPrefSize(100, 100);
        card.setId(c.getId().toString());
        card.setText(c.getId().toString() + " " + c.getTitre());

        // add text wrapping
        card.setWrapText(true);

        card.setOnDragDetected(event -> {
            Dragboard dragboard = card.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(card.getId());
            dragboard.setContent(content);
            event.consume();
        });

        card.setOnAction(event -> {
            try {
                App.setRoot("editionCarte", c, pile);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });

        cartes.put(card.getId(), c);
        return card;
    }

    public Button createAddCard() {
        Button card = new Button();
        card.setPrefSize(100, 100);
        card.setId("add");
        card.setText("+");
        // bold and bigger font
        card.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");

        // add a new card to the pile when clicked
        card.setOnAction(event -> {
            Carte c = new Carte("Nouvelle carte", "Question", "Réponse", "", "Description");
            try {
                model.create(c);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Button newCard = createCard(c);
            availableCards.getChildren().add(newCard);
        });

        return card;
    }

}
