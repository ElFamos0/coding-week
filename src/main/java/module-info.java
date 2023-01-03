module com.amplet {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.amplet to javafx.fxml;
    exports com.amplet;
}
