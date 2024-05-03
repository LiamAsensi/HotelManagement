module edu.carlosliam.hotelmanagementfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;

    opens edu.carlosliam.hotelmanagementfx to javafx.fxml;
    exports edu.carlosliam.hotelmanagementfx;
    exports edu.carlosliam.hotelmanagementfx.controller;
    opens edu.carlosliam.hotelmanagementfx.controller to javafx.fxml;
}