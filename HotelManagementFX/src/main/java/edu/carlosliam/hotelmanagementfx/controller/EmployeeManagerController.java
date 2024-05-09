package edu.carlosliam.hotelmanagementfx.controller;

import edu.carlosliam.hotelmanagementfx.adapter.EmployeeListViewCell;
import edu.carlosliam.hotelmanagementfx.service.GetEmployees;
import edu.carlosliam.hotelmanagementfx.model.data.Employee;
import edu.carlosliam.hotelmanagementfx.utils.MessageUtils;
import edu.carlosliam.hotelmanagementfx.utils.ModalUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeManagerController implements Initializable {
    @FXML
    private HMToolBar toolbar;
    @FXML
    private Button btnEditEmployee;
    @FXML
    private Button btnFire;
    @FXML
    private ListView<Employee> lvEmployees;
    public static ObservableList<Employee> employeeObservableList;
    private GetEmployees getEmployees;

    public EmployeeManagerController() {
        employeeObservableList = FXCollections.observableArrayList();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HMToolBar.disableButton(toolbar.btnGoToEmployees);
        lvEmployees.setItems(employeeObservableList);

        updateItems();

        lvEmployees.setCellFactory(employeeListView -> {
            EmployeeListViewCell employeeListViewCell = new EmployeeListViewCell();
            employeeListViewCell.prefWidthProperty().bind(lvEmployees.widthProperty());
            return employeeListViewCell;
        });
    }

    @FXML
    public void addEmployee() throws IOException {
        ModalUtils.openModal("layout/employee-new-view.fxml");
        updateItems();
    }

    private void updateItems() {
        employeeObservableList.clear();
        getEmployees = new GetEmployees();
        getEmployees.start();

        getEmployees.setOnSucceeded(e -> {
            if (!getEmployees.getValue().isError()) {
                employeeObservableList.addAll(getEmployees.getValue().getResult());
            } else {
                MessageUtils.showError("Error getting tasks", getEmployees.getValue().getErrorMessage());
            }
        });

        getEmployees.setOnFailed(e -> {
            MessageUtils.showError("Error", "Error connecting to the server");
        });
    }
}
