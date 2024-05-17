package edu.carlosliam.hotelmanagementfx.controller;

import edu.carlosliam.hotelmanagementfx.HotelManagementApplication;
import edu.carlosliam.hotelmanagementfx.adapter.TaskListViewCell;
import edu.carlosliam.hotelmanagementfx.model.data.Assignment;
import edu.carlosliam.hotelmanagementfx.model.data.Employee;
import edu.carlosliam.hotelmanagementfx.utils.ModalUtils;
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
import java.util.ResourceBundle;

public class AssignmentManagerController implements Initializable {
    @FXML
    private HMToolBar toolbar;

    @FXML
    private ListView<Assignment> lvTasks;

    @FXML
    private ListView<HashMap<Assignment, Employee>> lvAssignmentEmployee;

    public  Employee employeeSelected;

    public static ObservableMap<Assignment, Employee> assignmentEmployee;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HMToolBar.disableButton(toolbar.btnGoToAssignments);
        lvAssignmentEmployee.setItems(assignmentEmployee);

        EmployeeManagerController.updateItems();
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

        ModalUtils.modalStage.setOnCloseRequest(e -> {
            employeeSelected = assignNewController.getEmployeeSelected();
        });

        ModalUtils.openModalParent(parent);

        if (employeeSelected != null) {
            assignmentEmployee.put(assignmentSelected, employeeSelected);
        }
    }
}
