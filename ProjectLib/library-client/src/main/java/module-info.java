module com.library.libraryclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;

    opens com.library.libraryclient to javafx.fxml;
    opens com.library.libraryclient.controller to javafx.fxml;
    opens com.library.libraryclient.model to javafx.base, com.fasterxml.jackson.databind;

    exports com.library.libraryclient;
    exports com.library.libraryclient.controller;
}
