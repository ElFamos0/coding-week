module com.amplet {
    requires javafx.controls;
    requires javafx.fxml;

    requires ormlite.jdbc;
    requires java.sql;

    opens com.amplet.app to javafx.fxml;
    opens com.amplet.views to javafx.fxml;

    exports com.amplet.views;
    exports com.amplet.app;
}
