package edu.carlosliam.hotelmanagementfx.controller;

import edu.carlosliam.hotelmanagementfx.adapter.EmployeeListViewCell;
import edu.carlosliam.hotelmanagementfx.model.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    private ObservableList<Employee> employeeObservableList;

    public EmployeeManagerController() {
        employeeObservableList = FXCollections.observableArrayList();

        employeeObservableList.addAll(
            new Employee("1", "12345678A", "Manolo", "García", "Plumber", "1", "manolo@gmail.com"),
            new Employee("2", "12345678B", "Paco", "McNelly", "Cleaner", "123", "paco@gmail.com"),
            new Employee("3", "12345678C", "Dani", "Almazán", "Electrician", "111", "dani@gmail.com"),
            new Employee("4", "12345678D", "Miguel", "Collado", "Cleaner", "1432", "mike@gmail.com"),
            new Employee("5", "12345678E", "Liam", "Alejo", "Plumber", "112", "liam@gmail.com"),
            new Employee("6", "12345678F", "Carlos", "García", "Electrician", "142", "carlos@gmail.com")
        );

        employeeObservableList.forEach(e -> System.out.println(e.getName()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HMToolBar.disableButton(toolbar.btnGoToEmployees);

        lvEmployees.setItems(employeeObservableList);
        lvEmployees.setCellFactory(employeeListView -> {
            EmployeeListViewCell employeeListViewCell = new EmployeeListViewCell();
            employeeListViewCell.prefWidthProperty().bind(lvEmployees.widthProperty());
            return employeeListViewCell;
        });
    }
}
