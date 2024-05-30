package edu.carlosliam.hotelmanagementfx.controller;

import edu.carlosliam.hotelmanagementfx.HotelManagementApplication;
import edu.carlosliam.hotelmanagementfx.adapter.EmployeeListViewCell;
import edu.carlosliam.hotelmanagementfx.service.DeleteEmployee;
import edu.carlosliam.hotelmanagementfx.service.GetEmployees;
import edu.carlosliam.hotelmanagementfx.model.data.Employee;
import edu.carlosliam.hotelmanagementfx.service.GetEmployeesScheduled;
import edu.carlosliam.hotelmanagementfx.utils.EmployeeReportPdfCreator;
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
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeeManagerController implements Initializable {
    public static ObservableList<Employee> employeeObservableList;
    public static GetEmployees getEmployees;
    private static DeleteEmployee deleteEmployee;
    private static GetEmployeesScheduled getEmployeesScheduled;
    private static ProfessionFilter currentFilter = ProfessionFilter.ALL;

    @FXML
    private HMToolBar toolbar;
    @FXML
    private BorderPane root;
    @FXML
    private Button btnEditEmployee;
    @FXML
    private Button btnFire;
    @FXML
    private ChoiceBox<String> cbProfession;
    @FXML
    private ListView<Employee> lvEmployees;

    public EmployeeManagerController() {
        employeeObservableList = FXCollections.observableArrayList();
    }

    // This method is used to filter the employees by profession
    private static void filterEmployees(List<Employee> employees) {
        if (currentFilter == ProfessionFilter.ALL) {
            employeeObservableList.addAll(employees);
        } else {
            employees.stream()
                    .filter(employee ->
                            employee.getProfession().equals(
                                    Employee.professionsEnum.get(currentFilter)
                            )
                    )
                    .forEach(employeeObservableList::add);
        }
    }

    /*
    * This method is used to force the update of the items in the list view,
    * without waiting for the scheduled service to update the list.
    */
    public static void forceUpdateOfItems() {
        if (employeeObservableList == null) {
            employeeObservableList = FXCollections.observableArrayList();
        }
        employeeObservableList.clear();
        getEmployees = new GetEmployees();
        getEmployees.start();

        getEmployees.setOnSucceeded(e -> {
            if (!getEmployees.getValue().isError()) {
                employeeObservableList.clear();
                filterEmployees(getEmployees.getValue().getResult());
            } else {
                MessageUtils.showError("Error getting tasks",
                        getEmployees.getValue().getErrorMessage());
            }
        });

        getEmployees.setOnFailed(e ->
                MessageUtils.showError("Error", "Error connecting to the server"));
    }

    // This method is used to disable/enable the context buttons (edit and fire)
    private void disableContextButtons(boolean disabled) {
        btnEditEmployee.setDisable(disabled);
        btnFire.setDisable(disabled);
    }

    // This method is used to load the professions checkbox and its event listener
    private void loadProfessionsCheckbox() {
        cbProfession.getItems().add("All");
        cbProfession.getItems().addAll(Employee.professions.values());
        cbProfession.getSelectionModel().selectFirst();

        cbProfession.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                            currentFilter = ProfessionFilter.valueOf(newValue.toUpperCase());
                            forceUpdateOfItems();
                        }
                );
    }

    // This method is used to load the scheduled service
    private void loadScheduledService() {
        getEmployeesScheduled = new GetEmployeesScheduled();
        getEmployeesScheduled.setPeriod(Duration.minutes(1));
        getEmployeesScheduled.setOnSucceeded(e -> {
            Employee selectedItem = lvEmployees.getSelectionModel().getSelectedItem();

            if (getEmployeesScheduled.getValue() != null
                    && !getEmployeesScheduled.getValue().isError()
                    && getEmployeesScheduled.getValue() != employeeObservableList) {
                employeeObservableList.clear();

                filterEmployees(getEmployeesScheduled.getValue().getResult());

                if (selectedItem != null && employeeObservableList.contains(selectedItem)) {
                    lvEmployees.getSelectionModel().select(selectedItem);
                }
            }
        });
        getEmployeesScheduled.start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HMToolBar.disableButton(toolbar.btnGoToEmployees);
        lvEmployees.setItems(employeeObservableList);

        disableContextButtons(true);
        loadScheduledService();

        lvEmployees.setCellFactory(employeeListView -> {
            EmployeeListViewCell employeeListViewCell = new EmployeeListViewCell();
            employeeListViewCell.prefWidthProperty().bind(lvEmployees.widthProperty());
            return employeeListViewCell;
        });

        lvEmployees.getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (observable, oldValue, newValue) -> disableContextButtons(newValue == null)
                );

        lvEmployees.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                try {
                    editEmployee();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        root.setOnMouseClicked(e -> lvEmployees.getSelectionModel().clearSelection());

        loadProfessionsCheckbox();
    }

    @FXML
    public void addEmployee() throws IOException {
        ModalUtils.openModal("layout/employee-new-view.fxml");
        forceUpdateOfItems();
        lvEmployees.getSelectionModel().clearSelection();
    }

    @FXML
    private void fireEmployee() {
        Employee employee = lvEmployees.getSelectionModel().getSelectedItem();
        if (employee != null) {
            lvEmployees.getSelectionModel().clearSelection();
            deleteEmployee = new DeleteEmployee(employee.getId());
            deleteEmployee.start();

            deleteEmployee.setOnSucceeded(e -> {
                if (!deleteEmployee.getValue().isError()) {
                    forceUpdateOfItems();
                    disableContextButtons(true);
                    Platform.runLater(() ->
                        NotificationUtils.showNotificationSuccess("Employee fired",
                            "The employee has been fired successfully"
                        )
                    );
                } else {
                    MessageUtils.showError("Error firing employee",
                            deleteEmployee.getValue().getErrorMessage());
                }
            });
        }
    }

    @FXML
    private void editEmployee() throws IOException {
        Employee employee = lvEmployees.getSelectionModel().getSelectedItem();
        if (employee != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(HotelManagementApplication.class.getResource("layout/employee-new-view.fxml"));
            Parent parent = fxmlLoader.load();

            EmployeeNewController controller = fxmlLoader.getController();
            controller.setEmployeeEdit(employee);

            ModalUtils.openModalParent(parent);
            forceUpdateOfItems();
            lvEmployees.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void generatePaycheck() throws IOException {
        ModalUtils.openModal("layout/employee-paycheck-view.fxml");
        lvEmployees.getSelectionModel().clearSelection();
    }

    @FXML
    private void generateEmployeeReport() {
        EmployeeReportPdfCreator report = new EmployeeReportPdfCreator(
                EmployeeReportPdfCreator.ReportType.ALL_EMPLOYEES,
                new ArrayList<>(employeeObservableList)
        );
        report.createPdf();
        MessageUtils.showMessage("Report created", "The report has been created successfully");

        lvEmployees.getSelectionModel().clearSelection();
    }

    @FXML
    private void generateExpenseReport() throws IOException {
        ModalUtils.openModal("layout/employee-expense-report-view.fxml");
        lvEmployees.getSelectionModel().clearSelection();
    }

    // This enum is used to store the state of the filter of the employees by profession
    public enum ProfessionFilter {
        ALL,
        CLEANER,
        MANAGER,
        ELECTRICIAN,
        PLUMBER,
        CARPENTER,
        PAINTER,
        CONSTRUCTOR
    }
}
