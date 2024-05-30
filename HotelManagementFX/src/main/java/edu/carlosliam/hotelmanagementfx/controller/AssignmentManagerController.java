package edu.carlosliam.hotelmanagementfx.controller;

import edu.carlosliam.hotelmanagementfx.HotelManagementApplication;
import edu.carlosliam.hotelmanagementfx.adapter.TaskListViewCell;
import edu.carlosliam.hotelmanagementfx.model.data.Assignment;
import edu.carlosliam.hotelmanagementfx.model.data.Employee;
import edu.carlosliam.hotelmanagementfx.model.data.EmployeeWithAssignment;
import edu.carlosliam.hotelmanagementfx.service.AssignTaskToEmployee;
import edu.carlosliam.hotelmanagementfx.service.GetTasks;
import edu.carlosliam.hotelmanagementfx.utils.MailUtils;
import edu.carlosliam.hotelmanagementfx.utils.MessageUtils;
import edu.carlosliam.hotelmanagementfx.utils.ModalUtils;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AssignmentManagerController implements Initializable {
    public static ObservableList<EmployeeWithAssignment> employeeWithAssignments =
            FXCollections.observableArrayList();

    private static ObservableList<Assignment> availableAssignments = FXCollections.observableArrayList();

    private GetTasks getTasks;

    public Employee employeeSelected;

    @FXML
    private HMToolBar toolbar;

    @FXML
    private ListView<Assignment> lvTasks;

    @FXML
    private ListView<EmployeeWithAssignment> lvAssignmentEmployee;

    @FXML
    private Button btAssign;

    @FXML
    private Button btConfirm;

    @FXML
    private Button btUnassign;

    private AssignTaskToEmployee assignTaskToEmployee;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HMToolBar.disableButton(toolbar.btnGoToAssignments);
        lvAssignmentEmployee.setItems(employeeWithAssignments);

        EmployeeManagerController.forceUpdateOfItems();
        TaskManagerController.forceUpdateItems();
        getTasks();

        lvTasks.setItems(availableAssignments);

        lvTasks.setCellFactory(taskListView -> {
            TaskListViewCell taskListViewCell = new TaskListViewCell();
            taskListViewCell.prefWidthProperty().bind(lvTasks.widthProperty());
            return taskListViewCell;
        });



        // Double click to open the assing task modal
        lvTasks.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                try {
                    assignTask();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        btConfirm.disableProperty().bind(Bindings.isEmpty(employeeWithAssignments));
        btAssign.disableProperty().bind(lvTasks.getSelectionModel().selectedItemProperty().isNull());
        btUnassign.disableProperty().bind(lvAssignmentEmployee.getSelectionModel().selectedItemProperty().isNull());
    }

    private void getTasks() {
        availableAssignments.clear();
        getTasks = new GetTasks();
        getTasks.start();

        getTasks.setOnSucceeded(e -> {
            if (getTasks.getValue() != null && !getTasks.getValue().isError()) {
                List<Assignment> tasks = getTasks.getValue().getResult()
                        .stream()
                        .filter(t -> t.getEmployee() == null)
                        .toList();

                availableAssignments.addAll(tasks);
            } else {
                MessageUtils.showError("Error getting tasks", getTasks.getValue().getErrorMessage());
            }
        });

        getTasks.setOnFailed(e -> {
            MessageUtils.showError("Error", "Error connecting to the server");
        });
    }

    @FXML
    public void assignTask() throws IOException {
        Assignment assignmentSelected = lvTasks.getSelectionModel().getSelectedItem();

        if (assignmentSelected != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(HotelManagementApplication.class.getResource("layout/assign-new-view.fxml"));
            Parent parent = fxmlLoader.load();

            AssignNewController assignNewController = fxmlLoader.getController();

            ModalUtils.openModalParent(parent);

            employeeSelected = assignNewController.getEmployeeSelected();

            if (employeeSelected != null) {
                EmployeeWithAssignment ewa = new EmployeeWithAssignment(assignmentSelected, employeeSelected);
                employeeWithAssignments.add(ewa);
            }
        }
    }

    @FXML
    public void unassignTask() {
        EmployeeWithAssignment ewa = lvAssignmentEmployee.getSelectionModel().getSelectedItem();
        if (ewa != null) {
            employeeWithAssignments.remove(ewa);
        }
    }

    @FXML
    public void confirmTasks() throws IOException {
        MailUtils mailUtils = new MailUtils();

        employeeWithAssignments.forEach(e -> {
            assignTaskToEmployee = new AssignTaskToEmployee(e.getAssignment()
                    .getCodTask(), e.getAssignment(), e.getEmployee().getId());
            assignTaskToEmployee.start();
        });

        employeeWithAssignments.stream()
            .map(EmployeeWithAssignment::getEmployee)
            .forEach(e -> mailUtils.sendBasicEmail(e, "New tasks assigned", "You have been assigned new tasks! Check the phone app for more information."));

        employeeWithAssignments.clear();
    }
}
