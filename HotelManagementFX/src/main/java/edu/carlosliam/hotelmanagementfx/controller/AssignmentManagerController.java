package edu.carlosliam.hotelmanagementfx.controller;

import edu.carlosliam.hotelmanagementfx.HotelManagementApplication;
import edu.carlosliam.hotelmanagementfx.adapter.TaskListViewCell;
import edu.carlosliam.hotelmanagementfx.model.data.Assignment;
import edu.carlosliam.hotelmanagementfx.model.data.Employee;
import edu.carlosliam.hotelmanagementfx.model.data.EmployeeWithAssignment;
import edu.carlosliam.hotelmanagementfx.service.AssignTaskToEmployee;
import edu.carlosliam.hotelmanagementfx.utils.ModalUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class AssignmentManagerController implements Initializable {
    public static ObservableList<EmployeeWithAssignment> employeeWithAssignments =
            FXCollections.observableArrayList();
    public Employee employeeSelected;

    @FXML
    private HMToolBar toolbar;

    @FXML
    private ListView<Assignment> lvTasks;

    @FXML
    private ListView<EmployeeWithAssignment> lvAssignmentEmployee;

    private AssignTaskToEmployee assignTaskToEmployee;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HMToolBar.disableButton(toolbar.btnGoToAssignments);
        lvAssignmentEmployee.setItems(employeeWithAssignments);

        EmployeeManagerController.forceUpdateOfItems();
        TaskManagerController.updateItems();

        lvTasks.setItems(TaskManagerController.assignmentObservableList);

        lvTasks.setCellFactory(taskListView -> {
            TaskListViewCell taskListViewCell = new TaskListViewCell();
            taskListViewCell.prefWidthProperty().bind(lvTasks.widthProperty());
            return taskListViewCell;
        });
    }

    @FXML
    public void assignTask() throws IOException {
        Assignment assignmentSelected = lvTasks.getSelectionModel().getSelectedItem();
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

    @FXML
    public void confirmTasks() throws IOException {
        employeeWithAssignments.forEach(e -> {
            assignTaskToEmployee = new AssignTaskToEmployee(e.getAssignment()
                    .getCodTask(), e.getAssignment(), e.getEmployee().getId());
            assignTaskToEmployee.start();
        });

        employeeWithAssignments.clear();
    }
}
