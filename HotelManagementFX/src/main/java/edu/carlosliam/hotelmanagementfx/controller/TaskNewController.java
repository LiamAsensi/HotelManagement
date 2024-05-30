package edu.carlosliam.hotelmanagementfx.controller;

import edu.carlosliam.hotelmanagementfx.model.data.Assignment;
import edu.carlosliam.hotelmanagementfx.model.data.Employee;
import edu.carlosliam.hotelmanagementfx.model.data.EmployeeWithAssignment;
import edu.carlosliam.hotelmanagementfx.model.data.Room;
import edu.carlosliam.hotelmanagementfx.service.EditTask;
import edu.carlosliam.hotelmanagementfx.service.GetDirtyRoom;
import edu.carlosliam.hotelmanagementfx.service.PostTask;
import edu.carlosliam.hotelmanagementfx.service.PostTaskAssigned;
import edu.carlosliam.hotelmanagementfx.utils.MessageUtils;
import edu.carlosliam.hotelmanagementfx.utils.ModalUtils;
import edu.carlosliam.hotelmanagementfx.utils.NotificationUtils;
import edu.carlosliam.hotelmanagementfx.utils.ValidationUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import java.net.URL;
import java.util.ResourceBundle;

public class TaskNewController implements Initializable {
    @FXML
    private TextField tfId;

    @FXML
    private TextField tfDescription;

    @FXML
    private ChoiceBox<String> tfType;

    @FXML
    private DatePicker dpSd;

    @FXML
    private DatePicker dpFd;

    @FXML
    private ChoiceBox<Integer> cbPriority;

    @FXML
    private TextField tfTime;

    @FXML
    private ChoiceBox<Employee> cbEmployee;

    @FXML
    private ChoiceBox<Room> cbRoom;

    @FXML
    private CheckBox cbCleaning;

    @FXML
    private Button btnSave;

    @FXML
    private HBox btnSaveContainer;

    private PostTask postTask;

    private EditTask editTask;

    private PostTaskAssigned postTaskAssigned;

    private GetDirtyRoom getDirtyRoom;

    private Assignment taskEdit;
    private ValidationSupport validator;

    @FXML
    public void close() {
        ModalUtils.modalStage.close();
    }

    @FXML
    public void saveTask() {
        if (taskEdit == null) {
            addTask();
        } else {
            editTask();
        }
    }

    public void setAssignmentEdit(Assignment task) {
        this.taskEdit = task;

        tfId.setText(task.getCodTask());
        tfDescription.setText(task.getDescription());
        tfType.setValue(EmployeeWithAssignment.professions.get(task.getType()));
        cbEmployee.setValue(task.getEmployee());
        cbPriority.setValue(task.getPriority());
        tfTime.setText(String.valueOf(task.getEstimatedTime()));
        dpSd.setValue(task.getDateStart());
        dpFd.setValue(task.getDateEnd());

        tfId.setDisable(true);
        cbEmployee.setDisable(true);
        cbCleaning.setVisible(false);
        cbEmployee.setVisible(false);
    }

    private void editTask() {
        float time = 0.0f;
        if (tfTime.getText() != null && !tfTime.getText().isEmpty()) {
            time = Float.parseFloat(tfTime.getText());
        }

        if (!isFormEmpty()) {
            Assignment task = new Assignment(
                    taskEdit.getCodTask(),
                    tfDescription.getText(),
                    EmployeeWithAssignment.professionsInverse.get(tfType.getValue()),
                    dpSd.getValue(),
                    dpFd.getValue(),
                    taskEdit.getEmployee(),
                    cbPriority.getValue(),
                    time
            );

            editTask = new EditTask(taskEdit.getCodTask(), task);
            editTask.start();

            editTask.setOnSucceeded(e-> {
                if (!editTask.getValue().isError()) {
                    System.out.println(editTask.getValue().getResult());
                    ModalUtils.modalStage.close();
                } else {
                    MessageUtils.showError("Error editing task", editTask.getValue().getErrorMessage());
                }
            });
        }
    }

    public void addTask() {
        float time = 0.0f;
        if (tfTime.getText() != null && !tfTime.getText().isEmpty()) {
            time = Float.parseFloat(tfTime.getText());
        }

        if (!isFormEmpty()) {
            Assignment assignment = new Assignment(
                    tfId.getText(),
                    tfDescription.getText(),
                    EmployeeWithAssignment.professionsInverse.get(tfType.getValue()),
                    dpSd.getValue(),
                    dpFd.getValue(),
                    null,
                    cbPriority.getValue(),
                    time
            );

            if (cbEmployee.getValue() == null) {
                postTask = new PostTask(assignment);
                postTask.start();

                postTask.setOnSucceeded(e-> {
                    if (!postTask.getValue().isError()) {
                        System.out.println(postTask.getValue().getResult());

                        Platform.runLater(() ->
                            NotificationUtils.showNotificationSuccess("Task added",
                                "The task {name} has been added successfully"
                                .replace("{name}", tfDescription.getText()))
                        );

                        ModalUtils.modalStage.close();
                    } else {
                        MessageUtils.showError("Error posting task", postTask.getValue().getErrorMessage());
                    }
                });
            } else {
                postTaskAssigned = new PostTaskAssigned(assignment, cbEmployee.getValue().getId());
                postTaskAssigned.start();

                postTaskAssigned.setOnSucceeded(e-> {
                    if (!postTaskAssigned.getValue().isError()) {
                        System.out.println(postTaskAssigned.getValue().getResult());

                        Platform.runLater(() ->
                            NotificationUtils.showNotificationSuccess("Task added",
                                "The task {name} has been added successfully with the employee {employee}"
                                .replace("{name}", tfDescription.getText())
                                .replace("{employee}", cbEmployee.getValue().getName()))
                        );

                        ModalUtils.modalStage.close();
                    } else {
                        MessageUtils.showError("Error posting task", postTaskAssigned.getValue().getErrorMessage());
                    }
                });
            }
        }
    }

    private boolean isFormEmpty() {
        return tfType.getValue().isBlank() ||
                cbPriority.getValue().toString().isBlank() ||
                tfId.getText().isBlank() ||
                tfDescription.getText().isBlank() ||
                dpSd.getValue().toString().isBlank();
    }

    private void initializeDirtyRooms() {
        getDirtyRoom = new GetDirtyRoom();
        getDirtyRoom.start();

        getDirtyRoom.setOnSucceeded(e-> {
            cbRoom.getItems().addAll(getDirtyRoom.getValue());
        });
    }

    private void addValidations() {
        // Description validation
        validator.registerValidator(
                tfDescription,
                Validator.createEmptyValidator("Description is required")
        );
        validator.registerValidator(
                tfDescription,
                Validator.<String>createPredicateValidator(
                        name -> name.length() >= 2 && name.length() < 500,
                        "Name must be between 2 and 500 characters"
                )
        );

        // Task ID validation
        validator.registerValidator(
                tfId,
                Validator.createEmptyValidator("Task ID is required")
        );
        validator.registerValidator(
                tfId,
                Validator.<String>createPredicateValidator(
                        id -> !id.isEmpty() && id.length() < 5,
                        "Employee ID must be between 1 and 5 characters"
                )
        );

        // Estimated time validation
        validator.registerValidator(
                tfTime,
                Validator.createRegexValidator(
                        "Time is invalid",
                        "^$|^[0-9]+(\\.[0-9]+)?$",
                        null
                )
        );

        // Priority validation
        validator.registerValidator(
                cbPriority,
                Validator.createEmptyValidator("Priority is required")
        );

        // Type validation
        validator.registerValidator(
                tfType,
                Validator.createEmptyValidator("Type is required")
        );

        // Start date validation
        validator.registerValidator(
                dpSd,
                Validator.createEmptyValidator("Start date is required")
        );
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbPriority.getItems().addAll(1, 2, 3, 4);
        validator = new ValidationSupport();
        validator.setErrorDecorationEnabled(true);

        tfType.getItems().addAll(EmployeeWithAssignment.professions.values());
        tfType.getItems().remove("Cleaning");

        cbEmployee.setItems(EmployeeManagerController.employeeObservableList);

        initializeDirtyRooms();
        cbRoom.setVisible(false);

        cbCleaning.selectedProperty().addListener((observable, oldValue, newValue) -> {
            cbRoom.setVisible(newValue);
            tfDescription.setDisable(newValue);
            tfType.setDisable(newValue);

            if (newValue) {
                tfType.setValue("Cleaning");
            } else {
                tfType.setValue(null);
            }
        });

        cbRoom.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            tfDescription.setText("Cleaning room " + newValue.getNumber());
        });

        addValidations();
        btnSave.disableProperty().bind(validator.invalidProperty());
        ValidationUtils.showErrorsOnNode(validator, btnSave, btnSaveContainer);
    }
}
