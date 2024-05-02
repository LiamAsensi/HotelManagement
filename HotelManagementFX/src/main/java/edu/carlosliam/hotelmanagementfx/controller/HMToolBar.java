package edu.carlosliam.hotelmanagementfx.controller;

import edu.carlosliam.hotelmanagementfx.HotelManagementApplication;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

import java.io.IOException;

public class HMToolBar extends ToolBar {
    // Buttons of navigation
    @FXML
    private Button btnGoHome;
    @FXML
    private Button btnGoToAssignments;
    @FXML
    private Button btnGoToEmployees;
    @FXML
    private Button btnGoToTasks;

    // Buttons to control the window
    @FXML
    private Button btnMinimizeWindow;
    @FXML
    private Button btnMaximizeWindow;
    @FXML
    private Button btnCloseWindow;

    public HMToolBar() {
        FXMLLoader fxmlLoader = new FXMLLoader(HotelManagementApplication.class.getResource("toolbar.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        build();
    }

    // This method adds all the events to the buttons
    private void build() {
        btnCloseWindow.setOnMouseClicked(event -> {
            Platform.exit();
        });
    }
}
