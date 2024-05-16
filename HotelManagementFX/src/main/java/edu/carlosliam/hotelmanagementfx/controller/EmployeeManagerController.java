package edu.carlosliam.hotelmanagementfx.controller;

import edu.carlosliam.hotelmanagementfx.HotelManagementApplication;
import edu.carlosliam.hotelmanagementfx.adapter.EmployeeListViewCell;
import edu.carlosliam.hotelmanagementfx.model.data.Assignment;
import edu.carlosliam.hotelmanagementfx.service.DeleteEmployee;
import edu.carlosliam.hotelmanagementfx.service.GetEmployees;
import edu.carlosliam.hotelmanagementfx.model.data.Employee;
import edu.carlosliam.hotelmanagementfx.service.GetEmployeesScheduled;
import edu.carlosliam.hotelmanagementfx.utils.MessageUtils;
import edu.carlosliam.hotelmanagementfx.utils.ModalUtils;
import edu.carlosliam.hotelmanagementfx.utils.PdfCreator;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeeManagerController implements Initializable {
    @FXML
    private HMToolBar toolbar;
    @FXML
    private Button btnEditEmployee;
    @FXML
    private Button btnFire;
    @FXML
    private Button btnPaycheck;
    @FXML
    private ListView<Employee> lvEmployees;

    public static ObservableList<Employee> employeeObservableList;
    public static GetEmployees getEmployees;
    private static DeleteEmployee deleteEmployee;
    private static GetEmployeesScheduled getEmployeesScheduled;

    public EmployeeManagerController() {
        employeeObservableList = FXCollections.observableArrayList();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HMToolBar.disableButton(toolbar.btnGoToEmployees);
        lvEmployees.setItems(employeeObservableList);

        getEmployeesScheduled = new GetEmployeesScheduled();
        getEmployeesScheduled.setPeriod(Duration.seconds(5));
        getEmployeesScheduled.setOnSucceeded(e -> {
            if (getEmployeesScheduled.getValue() != employeeObservableList && getEmployeesScheduled.getValue() != null) {
                employeeObservableList.clear();
                employeeObservableList.addAll(getEmployeesScheduled.getValue());
            }
        });
        getEmployeesScheduled.start();

        disableButtons(true);

        lvEmployees.setCellFactory(employeeListView -> {
            EmployeeListViewCell employeeListViewCell = new EmployeeListViewCell();
            employeeListViewCell.prefWidthProperty().bind(lvEmployees.widthProperty());
            return employeeListViewCell;
        });

        lvEmployees.getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (observable, oldValue, newValue) -> disableButtons(false)
                );

        btnFire.setOnAction(e -> fireEmployee());

        btnEditEmployee.setOnAction(e -> {
            try {
                editEmployee();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        btnPaycheck.setOnAction(e -> {
            try {
                generatePaycheck();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    @FXML
    public void addEmployee() throws IOException {
        ModalUtils.openModal("layout/employee-new-view.fxml");
        updateItems();
    }

    public static void updateItems() {
        if (employeeObservableList == null) {
            employeeObservableList = FXCollections.observableArrayList();
        }
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

    private static ScheduledService<List<Employee>> createScheduledService() {
        return new ScheduledService<>() {
            @Override
            protected Task<List<Employee>> createTask() {
                return new Task<>() {
                    @Override
                    protected List<Employee> call() {
                        List<Employee> employees = new ArrayList<>();
                        System.out.println("Servicio creado...");

                        getEmployees = new GetEmployees();
                        getEmployees.start();

                        getEmployees.setOnSucceeded(e -> Platform.runLater(() -> {
                            if (!getEmployees.getValue().isError()) {
                                System.out.println("Empleados obtenidos");
                                employees.addAll(getEmployees.getValue().getResult());
                            } else {
                                MessageUtils.showError("Error getting tasks", getEmployees.getValue().getErrorMessage());
                            }
                        }));

                        getEmployees.setOnFailed(e -> Platform.runLater(() -> {
                            MessageUtils.showError("Error", "Error connecting to the server");
                        }));

                        System.out.println("Empleados obtenidos: " + employees.size());

                        return employees;
                    }
                };
            }
        };
    }

    private void fireEmployee() {
        Employee employee = lvEmployees.getSelectionModel().getSelectedItem();
        if (employee != null) {
            deleteEmployee = new DeleteEmployee(employee.getId());
            deleteEmployee.start();

            deleteEmployee.setOnSucceeded(e -> {
                System.out.println("a");
                if (!deleteEmployee.getValue().isError()) {
                    System.out.println("Socorrooo");
                    updateItems();
                    disableButtons(true);
                } else {
                    MessageUtils.showError("Error firing employee", deleteEmployee.getValue().getErrorMessage());
                }
            });
        }
    }

    private void editEmployee() throws IOException {
        Employee employee = lvEmployees.getSelectionModel().getSelectedItem();
        if (employee != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(HotelManagementApplication.class.getResource("layout/employee-new-view.fxml"));
            Parent parent = fxmlLoader.load();

            EmployeeNewController controller = fxmlLoader.getController();
            controller.setEmployeeEdit(employee);

            ModalUtils.openModalParent(parent);
            updateItems();
        }
    }

    private void generatePaycheck() throws IOException {
        ModalUtils.openModal("layout/employee-paycheck-view.fxml");
    }

    private void disableButtons(boolean disabled) {
        btnEditEmployee.setDisable(disabled);
        btnFire.setDisable(disabled);
    }
}
