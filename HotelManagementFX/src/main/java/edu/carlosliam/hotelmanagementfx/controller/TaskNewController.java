package edu.carlosliam.hotelmanagementfx.controller;

import edu.carlosliam.hotelmanagementfx.model.data.Assignment;
import edu.carlosliam.hotelmanagementfx.model.data.Employee;
import edu.carlosliam.hotelmanagementfx.service.PostTask;
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
    private TextField tfType;

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

    public void initialize() {

        cbPriority.getItems().addAll(1, 2, 3, 4, 5);

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
                    tfType.getText(),
                    dpSd.getValue(),
                    dpFd.getValue(),
                    cbEmployee.getValue(),
                    cbPriority.getValue(),
                    0
            );

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
        }
    }

    private boolean isFormEmpty() {
        return tfType.getText().isBlank() ||
                cbEmployee.getValue().toString().isBlank() ||
                cbPriority.getValue().toString().isBlank() ||
                tfId.getText().isBlank() ||
                tfDescription.getText().isBlank() ||
                dpSd.getValue().toString().isBlank();
    }
}
