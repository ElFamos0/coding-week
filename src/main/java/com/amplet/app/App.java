package com.amplet.app;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import com.amplet.db.DatabaseManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.amplet.io.JsonIo;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private DatabaseManager dbManager;
    private static Model model;

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        this.dbManager = new DatabaseManager();
        model = new Model();
        scene = new Scene(loadFXML("index"), 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static void setRoot(String fxml, Object... args) throws IOException {
        scene.setRoot(loadFXML(fxml, args));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        fxmlLoader.setControllerFactory(c -> {
            try {
                ViewController controller = (ViewController) c
                        .getDeclaredConstructor(model.getClass()).newInstance(model);
                return controller;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
        return fxmlLoader.load();
    }

    private static Parent loadFXML(String fxml, Object... args) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        fxmlLoader.setControllerFactory(c -> {
            try {
                ViewController controller =
                        (ViewController) c.getDeclaredConstructor(model.getClass(), args.getClass())
                                .newInstance(model, args);
                return controller;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
        return fxmlLoader.load();
    }

    public static void importCarte() throws IOException, SQLException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(scene.getWindow());
        JsonIo jsonIo = new JsonIo(file.getAbsolutePath());
        Carte carte = jsonIo.importFromFile(Carte.class);
        model.create(carte);
    }

    public static void importPile() throws IOException, SQLException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(scene.getWindow());
        JsonIo jsonIo = new JsonIo(file.getAbsolutePath());
        Pile pile = jsonIo.importFromFile(Pile.class);
        model.create(pile);
    }


    public static void main(String[] args) {
        launch();
    }

}
