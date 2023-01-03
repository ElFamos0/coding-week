module com.amplet {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.amplet.app to javafx.fxml;
    opens com.amplet.views to javafx.fxml;
    exports com.amplet.views;
    exports com.amplet.app;
}
