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

    opens edu.carlosliam.hotelmanagementfx.adapter to javafx.fxml;
    exports edu.carlosliam.hotelmanagementfx.adapter;

    opens edu.carlosliam.hotelmanagementfx.model to javafx.fxml;
    exports edu.carlosliam.hotelmanagementfx.model;

    opens edu.carlosliam.hotelmanagementfx.data to javafx.fxml;
    exports edu.carlosliam.hotelmanagementfx.data;

    opens edu.carlosliam.hotelmanagementfx.utils to javafx.fxml;
    exports edu.carlosliam.hotelmanagementfx.utils;
}