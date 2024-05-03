package edu.carlosliam.hotelmanagementfx.controller;

import edu.carlosliam.hotelmanagementfx.model.Employee;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HMToolBar.disableButton(toolbar.btnGoToEmployees);
    }
}
