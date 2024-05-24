package edu.carlosliam.hotelmanagementfx.controller;

import edu.carlosliam.hotelmanagementfx.HotelManagementApplication;
import edu.carlosliam.hotelmanagementfx.model.data.Assignment;
import edu.carlosliam.hotelmanagementfx.service.DeleteTask;
import edu.carlosliam.hotelmanagementfx.service.GetTasks;
import edu.carlosliam.hotelmanagementfx.adapter.TaskListViewCell;
import edu.carlosliam.hotelmanagementfx.utils.MessageUtils;
import edu.carlosliam.hotelmanagementfx.utils.ModalUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.IOException;

public class TaskManagerController {
    public HMToolBar toolbar;

    @FXML
    private ListView<Assignment> lvTasks;

    @FXML
    private Button btnEditTask;

    @FXML
    private Button btnDeleteTask;

    private static DeleteTask deleteTask;

    private static GetTasks getTasks;

    public static ObservableList<Assignment> assignmentObservableList;

    public TaskManagerController() {
        assignmentObservableList = FXCollections.observableArrayList();
    }

    public void initialize() {
        HMToolBar.disableButton(toolbar.btnGoToTasks);
        lvTasks.setItems(assignmentObservableList);

        // Disable buttons when no task is selected
        disableButtons(true);

        // Update the list of employees
        EmployeeManagerController.forceUpdateOfItems();

        // Update the list of tasks
        updateItems();

        // Set the cell factory for the list view
        lvTasks.setCellFactory(taskListView -> {
            TaskListViewCell taskListViewCell = new TaskListViewCell();
            taskListViewCell.prefWidthProperty().bind(lvTasks.widthProperty());
            return taskListViewCell;
        });

        // Enable buttons when a task is selected
        lvTasks.getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (observable, oldValue, newValue) -> disableButtons(false)
                );

        // Define the action for the delete button
        btnDeleteTask.setOnAction(e -> deleteTask());

        // Define the action for the edit button
        btnEditTask.setOnAction(e -> {
            try {
                editTask();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    private void editTask() throws IOException {
        Assignment task = lvTasks.getSelectionModel().getSelectedItem();
        if(task != null && task.getEmployee() == null) {
            FXMLLoader fxmlLoader = new FXMLLoader(HotelManagementApplication.class.getResource("layout/task-new-view.fxml"));
            Parent parent = fxmlLoader.load();

            TaskNewController taskNewController = fxmlLoader.getController();
            taskNewController.setAssignmentEdit(task);

            ModalUtils.openModalParent(parent);
            updateItems();
            disableButtons(true);
        }
    }

    @FXML
    public void addTask() throws IOException {
        ModalUtils.openModal("layout/task-new-view.fxml"); // Cambiar plantilla
        updateItems();
        disableButtons(true);
    }

    // Update the list of tasks
    public static void updateItems() {
        if (assignmentObservableList == null) {
            assignmentObservableList = FXCollections.observableArrayList();
        }
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


    // Delete task selected
    private void deleteTask() {
        Assignment assignment = lvTasks.getSelectionModel().getSelectedItem();
        if(assignment != null && assignment.getEmployee() == null) {
            deleteTask = new DeleteTask(assignment.getCodTask());
            deleteTask.start();

            deleteTask.setOnSucceeded(e -> {
                if (!deleteTask.getValue().isError()) {
                    updateItems();
                    disableButtons(true);
                } else {
                    MessageUtils.showError("Error deleting task", deleteTask.getValue().getErrorMessage());
                }
            });
        }
    }

    // Block the buttons when no task is selected
    private void disableButtons(boolean disabled) {
        btnEditTask.setDisable(disabled);
        btnDeleteTask.setDisable(disabled);
    }
}