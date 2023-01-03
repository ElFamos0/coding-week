package com.amplet.views;

import java.io.IOException;

import com.amplet.app.App;

import javafx.fxml.FXML;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}