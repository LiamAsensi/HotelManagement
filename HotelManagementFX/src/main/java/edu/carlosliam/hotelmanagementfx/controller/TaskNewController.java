package edu.carlosliam.hotelmanagementfx.controller;

import edu.carlosliam.hotelmanagementfx.model.data.Assignment;
import edu.carlosliam.hotelmanagementfx.model.data.Employee;
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
    private TextField cbTime;

    @FXML
    private ChoiceBox<Employee> cbEmployee;
    private PostTask postTask;

    private PostTaskAssigned postTaskAssigned;

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
        if (!isFormEmpty()) {
            Assignment assignment = new Assignment(
                    tfId.getText(),
                    tfDescription.getText(),
                    tfType.getValue(),
                    dpSd.getValue(),
                    dpFd.getValue(),
                    null,
                    cbPriority.getValue(),
                    0
            );

            if (cbEmployee.getValue() == null) {
                postTask = new PostTask(assignment);
                postTask.start();

                postTask.setOnSucceeded(e-> {
                    if (!postTask.getValue().isError()) {
                        System.out.println(postTask.getValue().getTask());
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
                        System.out.println(postTaskAssigned.getValue().getTask());
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
