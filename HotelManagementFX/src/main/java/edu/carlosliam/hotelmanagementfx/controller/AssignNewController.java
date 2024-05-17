package edu.carlosliam.hotelmanagementfx.controller;

import edu.carlosliam.hotelmanagementfx.model.data.Employee;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class AssignNewController {
    @FXML
    private ChoiceBox<Employee> cbEmployee;

    public void initialize() {
        cbEmployee.setItems(EmployeeManagerController.employeeObservableList);
    }

    public Employee getEmployeeSelected() {
        return cbEmployee.getSelectionModel().getSelectedItem();
    }
}
