module com.amplet {
    requires transitive javafx.controls;
    requires javafx.fxml;

    requires ormlite.jdbc;
    requires java.sql;

    opens com.amplet.app to javafx.fxml;
    opens com.amplet.views to javafx.fxml;
    opens com.amplet.db to ormlite.jdbc;

    exports com.amplet.db;
    exports com.amplet.views;
    exports com.amplet.app;
}
