package edu.carlosliam.hotelmanagementfx.controller;

import edu.carlosliam.hotelmanagementfx.model.data.Employee;
import edu.carlosliam.hotelmanagementfx.utils.ModalUtils;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class AssignNewController {
    @FXML
    private ChoiceBox<Employee> cbEmployees;
    private Employee selectedEmployee;

    public void initialize() {
        cbEmployees.setItems(EmployeeManagerController.employeeObservableList);
    }

    public Employee getEmployeeSelected() {
        return this.selectedEmployee;
    }

    @FXML
    public void close() {
        ModalUtils.modalStage.close();
    }

    @FXML
    private void assignEmployee() {
        selectedEmployee = cbEmployees.getSelectionModel().getSelectedItem();
        close();
    }
}
