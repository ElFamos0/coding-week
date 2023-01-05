package com.amplet.views;

import java.io.IOException;
import java.util.ArrayList;
import com.amplet.app.App;
import com.amplet.app.Carte;
import com.amplet.app.Model;
import com.amplet.app.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ApprFinEntrainement extends ViewController {

    public ApprFinEntrainement(Model model) {
        super(model);
        model.addObserver(this);
    }


    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

    @FXML
    public void switchToIndex() throws IOException {
        App.setRoot("index");
    }


    @FXML
    public void initialize() {


    }

}
