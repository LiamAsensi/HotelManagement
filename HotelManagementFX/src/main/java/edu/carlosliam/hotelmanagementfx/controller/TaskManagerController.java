package edu.carlosliam.hotelmanagementfx.controller;

import edu.carlosliam.hotelmanagementfx.data.GetTasks;
import com.google.gson.Gson;
import edu.carlosliam.hotelmanagementfx.adapter.TaskListViewCell;
import edu.carlosliam.hotelmanagementfx.model.Employee;
import edu.carlosliam.hotelmanagementfx.model.Task;
import edu.carlosliam.hotelmanagementfx.utils.MessageUtils;
import javafx.collections.FXCollections;
import edu.carlosliam.hotelmanagementfx.utils.ServiceUtils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.Date;

public class TaskManagerController {
    public HMToolBar toolbar;

    @FXML
    private ListView<Task> lvTasks;

    private GetTasks getTasks;

    ObservableList<Task> taskObservableList;

    public TaskManagerController() {
        taskObservableList = FXCollections.observableArrayList();
    }

    public void initialize() {
        HMToolBar.disableButton(toolbar.btnGoToTasks);
        lvTasks.setItems(taskObservableList);

        getTasks = new GetTasks();
        getTasks.start();

        getTasks.setOnSucceeded(e -> {
            if (!getTasks.getValue().isError()) {
                taskObservableList.addAll((getTasks.getValue().getResult()));
            } else {
                MessageUtils.showError("Error getting tasks", getTasks.getValue().getErrorMessage());
            }
        });
      
        getTasks.setOnFailed(e -> {
            MessageUtils.showError("Error", "Error connecting to the server");
        });

        lvTasks.setCellFactory(taskListView -> {
            TaskListViewCell taskListViewCell = new TaskListViewCell();
            taskListViewCell.prefWidthProperty().bind(lvTasks.widthProperty());
            return taskListViewCell;
        });

    }
}