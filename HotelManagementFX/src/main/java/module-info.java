module edu.carlosliam.hotelmanagementfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.slf4j;
    requires kernel;
    requires layout;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;

    opens edu.carlosliam.hotelmanagementfx to javafx.fxml;
    exports edu.carlosliam.hotelmanagementfx;

    exports edu.carlosliam.hotelmanagementfx.controller;
    opens edu.carlosliam.hotelmanagementfx.controller to javafx.fxml;

    opens edu.carlosliam.hotelmanagementfx.adapter to javafx.fxml;
    exports edu.carlosliam.hotelmanagementfx.adapter;

    opens edu.carlosliam.hotelmanagementfx.service to javafx.fxml;
    exports edu.carlosliam.hotelmanagementfx.service;

    opens edu.carlosliam.hotelmanagementfx.utils to javafx.fxml;
    exports edu.carlosliam.hotelmanagementfx.utils;

    opens edu.carlosliam.hotelmanagementfx.model.data to javafx.base, com.google.gson;
    exports edu.carlosliam.hotelmanagementfx.model.data;

    opens edu.carlosliam.hotelmanagementfx.model.response to javafx.base, com.google.gson;
    exports edu.carlosliam.hotelmanagementfx.model.response;
}