package edu.carlosliam.hotelmanagementfx.controller;

import edu.carlosliam.hotelmanagementfx.HotelManagementApplication;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class HomeController {
    @FXML
    void goToAssignments() throws IOException {
        changeScene("layout/assignment-manager-view.fxml");
    }

    @FXML
    void goToEmployees() throws IOException {
        changeScene("layout/employee-manager-view.fxml");
    }

    @FXML
    void goToTasks() throws IOException {
        changeScene("layout/task-manager-view.fxml");
    }

    @FXML
    void openInfo(ActionEvent event) {

    }

    @FXML
    void openSettings(ActionEvent event) {

    }

    @FXML
    void close() {
        Platform.exit();
    }

    private void changeScene(String sceneName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                HotelManagementApplication.class.getResource(sceneName));
        Scene scene = new Scene(fxmlLoader.load());

        HotelManagementApplication.primaryStage.setScene(scene);
    }
}
