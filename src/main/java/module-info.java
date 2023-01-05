module com.amplet {
    requires transitive javafx.controls;
    requires javafx.fxml;

    requires ormlite.jdbc;
    requires java.sql;
    requires com.google.gson;

    opens com.amplet.app to javafx.fxml, com.google.gson;
    opens com.amplet.views to javafx.fxml;
    opens com.amplet.db to ormlite.jdbc;
    opens com.amplet.io to com.google.gson;

    exports com.amplet.db;
    exports com.amplet.views;
    exports com.amplet.app;
}
