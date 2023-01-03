package com.amplet.views;

import com.amplet.app.Observer;
import javafx.fxml.Initializable;

public class Index implements Observer, Initializable {

    public void update() {
        System.out.println("Index updated");
    }

    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {}
}
