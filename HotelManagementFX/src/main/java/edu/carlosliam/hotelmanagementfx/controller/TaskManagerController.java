package edu.carlosliam.hotelmanagementfx.controller;

import edu.carlosliam.hotelmanagementfx.HotelManagementApplication;
import edu.carlosliam.hotelmanagementfx.model.data.Assignment;
import edu.carlosliam.hotelmanagementfx.model.data.Employee;
import edu.carlosliam.hotelmanagementfx.service.DeleteTask;
import edu.carlosliam.hotelmanagementfx.service.GetTasks;
import edu.carlosliam.hotelmanagementfx.adapter.TaskListViewCell;
import edu.carlosliam.hotelmanagementfx.service.GetTasksScheduled;
import edu.carlosliam.hotelmanagementfx.utils.MessageUtils;
import edu.carlosliam.hotelmanagementfx.utils.ModalUtils;
import edu.carlosliam.hotelmanagementfx.utils.NotificationUtils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;

import edu.carlosliam.hotelmanagementfx.controller.EmployeeManagerController.ProfessionFilter;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class TaskManagerController implements Initializable {
    public HMToolBar toolbar;

    @FXML
    private ListView<Assignment> lvTasks;

    @FXML
    private Button btnEditTask;

    @FXML
    private Button btnDeleteTask;

    @FXML
    private ChoiceBox<String> cbType;

    private static DeleteTask deleteTask;
    private static GetTasks getTasks;
    private static GetTasksScheduled getTasksScheduled;
    private static ProfessionFilter currentFilter = ProfessionFilter.ALL;
    public static ObservableList<Assignment> assignmentObservableList;

    public TaskManagerController() {
        assignmentObservableList = FXCollections.observableArrayList();
    }

    private void editTask() throws IOException {
        Assignment task = lvTasks.getSelectionModel().getSelectedItem();
        if(task != null && task.getEmployee() == null && task.getDateEnd() == null) {
            FXMLLoader fxmlLoader = new FXMLLoader(HotelManagementApplication.class.getResource("layout/task-new-view.fxml"));
            Parent parent = fxmlLoader.load();

            TaskNewController taskNewController = fxmlLoader.getController();
            taskNewController.setAssignmentEdit(task);

            ModalUtils.openModalParent(parent);
            forceUpdateItems();
            lvTasks.refresh();
            disableButtons(true);
        }
    }

    @FXML
    public void addTask() throws IOException {
        ModalUtils.openModal("layout/task-new-view.fxml"); // Cambiar plantilla
        forceUpdateItems();
        lvTasks.refresh();
        disableButtons(true);
    }

    // Update the list of tasks
    public static void forceUpdateItems() {
        if (assignmentObservableList == null) {
            assignmentObservableList = FXCollections.observableArrayList();
        }
        assignmentObservableList.clear();
        getTasks = new GetTasks();
        getTasks.start();

        getTasks.setOnSucceeded(e -> {
            if (getTasks.getValue() != null && !getTasks.getValue().isError()) {
                filterTasks(getTasks.getValue().getResult());
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
                    forceUpdateItems();
                    lvTasks.refresh();
                    disableButtons(true);
                    Platform.runLater(() ->
                            NotificationUtils.showNotificationSuccess("Task removed",
                                    "The task has been removed successfully"
                            )
                    );
                } else {
                    MessageUtils.showError("Error deleting task", deleteTask.getValue().getErrorMessage());
                }
            });
        }
    }

    private static void filterTasks(List<Assignment> tasks) {
        assignmentObservableList.clear();

        if (currentFilter == ProfessionFilter.ALL) {
            tasks.stream()
                    .sorted(Comparator.comparing(Assignment::getDateEnd, Comparator.nullsFirst(Comparator.naturalOrder())))
                    .forEach(assignmentObservableList::add);
        } else {
            tasks.stream()
                    .filter(task ->
                            task.getType().equals(
                                    Employee.professionsEnum.get(currentFilter)
                            )
                    )
                    .sorted(Comparator.comparing(Assignment::getDateEnd, Comparator.nullsFirst(Comparator.naturalOrder())))
                    .forEach(assignmentObservableList::add);
        }
    }

    private void loadScheduledService() {
        getTasksScheduled = new GetTasksScheduled();
        getTasksScheduled.setPeriod(Duration.seconds(10));
        getTasksScheduled.setOnSucceeded(e -> {
            Assignment selectedItem = lvTasks.getSelectionModel().getSelectedItem();

            if (getTasksScheduled.getValue() != null
                    && !getTasksScheduled.getValue().isError()) {

                filterTasks(getTasksScheduled.getValue().getResult());
                lvTasks.refresh();

                if (selectedItem != null && assignmentObservableList.contains(selectedItem)) {
                    lvTasks.getSelectionModel().select(selectedItem);
                }
            }
        });
        getTasksScheduled.setOnFailed(e -> {
            System.out.println("Error getting tasks");
            lvTasks.refresh();
        });
        getTasksScheduled.start();
    }

    // Block the buttons when no task is selected
    private void disableButtons(boolean disabled) {
        btnEditTask.setDisable(disabled);
        btnDeleteTask.setDisable(disabled);
    }

    private void loadProfessionsCheckbox() {
        cbType.getItems().add("All");
        cbType.getItems().addAll(Employee.professions.values());
        cbType.getSelectionModel().selectFirst();

        cbType.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                            currentFilter = ProfessionFilter.valueOf(newValue.toUpperCase());
                            forceUpdateItems();
                            lvTasks.refresh();
                        }
                );
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HMToolBar.disableButton(toolbar.btnGoToTasks);
        lvTasks.setItems(assignmentObservableList);

        // Disable buttons when no task is selected
        disableButtons(true);

        // Update the list of employees
        EmployeeManagerController.forceUpdateOfItems();

        // Update the list of tasks
        loadScheduledService();

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
                        (observable, oldValue, newValue) -> disableButtons(
                                newValue != null &&  newValue.getDateEnd() != null)
                );

        // Edit the task on double click
        lvTasks.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                try {
                    editTask();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

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

        loadProfessionsCheckbox();
    }
}