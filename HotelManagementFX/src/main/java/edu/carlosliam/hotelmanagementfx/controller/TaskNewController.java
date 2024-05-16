package edu.carlosliam.hotelmanagementfx.controller;

import edu.carlosliam.hotelmanagementfx.model.data.Assignment;
import edu.carlosliam.hotelmanagementfx.model.data.Employee;
import edu.carlosliam.hotelmanagementfx.service.EditTask;
import edu.carlosliam.hotelmanagementfx.service.PostTask;
import edu.carlosliam.hotelmanagementfx.service.PostTaskAssigned;
import edu.carlosliam.hotelmanagementfx.utils.MessageUtils;
import edu.carlosliam.hotelmanagementfx.utils.ModalUtils;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class TaskNewController {
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
    private PostTask postTask;

    private EditTask editTask;

    private PostTaskAssigned postTaskAssigned;

    private Assignment taskEdit;

    public void initialize() {

        cbPriority.getItems().addAll(1, 2, 3, 4);

        tfType.getItems().addAll("Electricidad", "Limpieza", "Gestion", "Fontaneria", "Carpinteria");

        cbEmployee.setItems(EmployeeManagerController.employeeObservableList);
    }



    @FXML
    public void close() {
        ModalUtils.modalStage.close();
    }

    @FXML
    public void saveTask() {
        if (taskEdit == null) {
            addTask();
        } else {
            System.out.println("Edit task");
            editTask();
        }
    }

    public void setAssignmentEdit(Assignment task) {
        this.taskEdit = task;

        tfId.setText(task.getCodTask());
        tfDescription.setText(task.getDescription());
        tfType.setValue(task.getType());
        cbEmployee.setValue(task.getEmployee());
        cbPriority.setValue(task.getPriority());
        tfTime.setText(String.valueOf(task.getEstimatedTime()));
        dpSd.setValue(task.getDateStart());
        dpFd.setValue(task.getDateEnd());

        tfId.setDisable(true);
        cbEmployee.setDisable(true);
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
                    tfType.getValue(),
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
                    tfType.getValue(),
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
}
