package edu.carlosliam.hotelmanagementfx.controller;

import com.google.gson.Gson;
import edu.carlosliam.hotelmanagementfx.model.Task;
import edu.carlosliam.hotelmanagementfx.model.TaskListResponse;
import edu.carlosliam.hotelmanagementfx.utils.MessageUtils;
import edu.carlosliam.hotelmanagementfx.utils.ServiceUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class HelloController {
    public HMToolBar toolbar;

    @FXML
    private ListView<Task> lsTasks;

    private final Gson gson = new Gson();

    public void initialize() {
        HMToolBar.disableButton(toolbar.btnGoToAssignments);

        getTasks();
    }

    private void getTasks() {
        String url = ServiceUtils.SERVER + "/limpiezas";
        ServiceUtils.getResponseAsync(url, null, "GET")
                .thenApply(json -> gson.fromJson(json, TaskListResponse.class))
                .thenAccept(response -> {
                    if (!response.isError()) {
                        Platform.runLater(() -> {
                            lsTasks.getItems().setAll(response.getTasks());
                        });
                    } else {
                        MessageUtils.showError("Error", response.getErrorMessage());
                    }
                })
                .exceptionally(e -> {
                    MessageUtils.showError("Error", e.getMessage());
                    return null;
                });
    }
}