package edu.carlosliam.hotelmanagementfx.controller;

import com.google.gson.Gson;
import edu.carlosliam.hotelmanagementfx.model.Task;
import edu.carlosliam.hotelmanagementfx.model.TaskListResponse;
import edu.carlosliam.hotelmanagementfx.utils.MessageUtils;
import edu.carlosliam.hotelmanagementfx.utils.ServiceUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class TaskManagerController {
    public HMToolBar toolbar;

    @FXML
    private ListView<Task> lsTasks;

    private final Gson gson = new Gson();

    public void initialize() {
        HMToolBar.disableButton(toolbar.btnGoToTasks);

        System.out.println("Get tasks");
        getTasks();
        System.out.println("Get tasks 2");
    }

    private void getTasks() {
        String url = ServiceUtils.SERVER + "/tasks";
        ServiceUtils.getResponseAsync(url, null, "GET")
                .thenApply(json -> gson.fromJson(json, TaskListResponse.class))
                .thenAccept(response -> {
                    if (!response.isError()) {
                        Platform.runLater(() -> {
                            //lsTasks.getItems().setAll(response.getTasks());
                            response.getTasks().forEach(System.out::println);
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