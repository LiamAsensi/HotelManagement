package edu.carlosliam.hotelmanagementfx.controller;

import edu.carlosliam.hotelmanagementfx.model.data.Assignment;
import edu.carlosliam.hotelmanagementfx.service.GetTasks;
import edu.carlosliam.hotelmanagementfx.adapter.TaskListViewCell;
import edu.carlosliam.hotelmanagementfx.utils.MessageUtils;
import edu.carlosliam.hotelmanagementfx.utils.ModalUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.IOException;

public class TaskManagerController {
    public HMToolBar toolbar;

    @FXML
    private ListView<Assignment> lvTasks;

    private GetTasks getTasks;

    public static ObservableList<Assignment> assignmentObservableList;

    public TaskManagerController() {
        assignmentObservableList = FXCollections.observableArrayList();
    }

    public void initialize() {
        HMToolBar.disableButton(toolbar.btnGoToTasks);
        lvTasks.setItems(assignmentObservableList);

        EmployeeManagerController.updateItems();
        updateItems();

        lvTasks.setCellFactory(taskListView -> {
            TaskListViewCell taskListViewCell = new TaskListViewCell();
            taskListViewCell.prefWidthProperty().bind(lvTasks.widthProperty());
            return taskListViewCell;
        });
    }

    @FXML
    public void addTask() throws IOException {
        ModalUtils.openModal("layout/task-new-view.fxml"); // Cambiar plantilla
        updateItems();
    }

    private void updateItems() {
        assignmentObservableList.clear();
        getTasks = new GetTasks();
        getTasks.start();

        getTasks.setOnSucceeded(e -> {
            if (!getTasks.getValue().isError()) {
                assignmentObservableList.addAll(getTasks.getValue().getResult());
            } else {
                MessageUtils.showError("Error getting tasks", getTasks.getValue().getErrorMessage());
            }
        });

        getTasks.setOnFailed(e -> {
            MessageUtils.showError("Error", "Error connecting to the server");
        });
    }
}