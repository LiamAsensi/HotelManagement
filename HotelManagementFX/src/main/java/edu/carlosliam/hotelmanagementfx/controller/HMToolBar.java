package edu.carlosliam.hotelmanagementfx.controller;

import edu.carlosliam.hotelmanagementfx.HotelManagementApplication;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class HMToolBar extends ToolBar {
    // Buttons of navigation
    @FXML
    private Button btnGoToHome;
    @FXML
    public Button btnGoToAssignments;
    @FXML
    public Button btnGoToEmployees;
    @FXML
    public Button btnGoToTasks;

    // Buttons to control the window
    @FXML
    private Button btnMinimizeWindow;
    @FXML
    private Button btnMaximizeWindow;
    @FXML
    private Button btnCloseWindow;

    // Main container of the ToolBar (used for collision detection with the cursor)
    @FXML
    private HBox hbMain;

    private double xOffset = 0;
    private double yOffset = 0;

    public HMToolBar() {
        FXMLLoader fxmlLoader = new FXMLLoader(
                HotelManagementApplication.class.getResource("hb-toolbar-view.fxml"));
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
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Are you sure you want to exit?");
            alert.setContentText("(All the unsaved changes will be lost)");
            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> Platform.exit());
        });

        btnMinimizeWindow.setOnMouseClicked(event ->
            HotelManagementApplication.primaryStage.setIconified(true));

        btnMaximizeWindow.setOnMouseClicked(event -> maximizeWindow());

        hbMain.setOnMouseClicked(event -> {
            if (event.getClickCount() >= 2) {
                maximizeWindow();
            }
        });

        btnGoToEmployees.setOnMouseClicked(event ->
        {
            try {
                changeScene("employee-manager-view.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        btnGoToTasks.setOnMouseClicked(event -> {
            try {
                changeScene("task-view.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        btnGoToAssignments.setOnMouseClicked(event -> {
            try {
                changeScene("assignment-manager-view.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        btnGoToHome.setOnMouseClicked(event -> {
            try {
                changeScene("home-view.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        moveWindowEvents();
    }

    private void maximizeWindow() {
        HotelManagementApplication.primaryStage.setMaximized(
                !HotelManagementApplication.primaryStage.isMaximized()
        );
    }

    // Method that allows the window to be moved by dragging the cursor
    private void moveWindowEvents() {
        hbMain.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        hbMain.setOnMouseDragged(event -> {
            HotelManagementApplication.primaryStage.setX(event.getScreenX() - xOffset);
            HotelManagementApplication.primaryStage.setY(event.getScreenY() - yOffset);
        });
    }

    public static void disableButton(Button btn) {
        btn.getStyleClass().add("active");
    }

    private void changeScene(String sceneName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                HotelManagementApplication.class.getResource(sceneName));
        Scene scene = new Scene(fxmlLoader.load());

        HotelManagementApplication.primaryStage.setScene(scene);
    }
}
