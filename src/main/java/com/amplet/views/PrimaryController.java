package com.amplet.views;

import java.io.IOException;

import com.amplet.app.App;

import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
