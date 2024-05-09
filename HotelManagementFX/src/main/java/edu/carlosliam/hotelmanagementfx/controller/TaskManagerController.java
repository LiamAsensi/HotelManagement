package edu.carlosliam.hotelmanagementfx.controller;

import edu.carlosliam.hotelmanagementfx.data.GetTasks;
import edu.carlosliam.hotelmanagementfx.model.Task;
import edu.carlosliam.hotelmanagementfx.utils.MessageUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class TaskManagerController {
    public HMToolBar toolbar;

    @FXML
    private ListView<Task> lsTasks;

    private GetTasks getTasks;

    public void initialize() {
        HMToolBar.disableButton(toolbar.btnGoToTasks);

        getTasks = new GetTasks();
        getTasks.start();

        getTasks.setOnSucceeded(e -> {
            if (!getTasks.getValue().isError()) {
                System.out.println(getTasks.getValue().getResult());
                lsTasks.setItems(FXCollections.observableArrayList(getTasks.getValue().getResult()));
            } else {
                MessageUtils.showError("Error getting tasks", getTasks.getValue().getErrorMessage());
            }
        });

        getTasks.setOnFailed(e -> {
            MessageUtils.showError("Error", "Error connecting to the server");
        });
    }
}