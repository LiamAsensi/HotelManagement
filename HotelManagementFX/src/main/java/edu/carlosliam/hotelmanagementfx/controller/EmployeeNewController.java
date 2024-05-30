package edu.carlosliam.hotelmanagementfx.controller;

import edu.carlosliam.hotelmanagementfx.model.data.Employee;
import edu.carlosliam.hotelmanagementfx.model.data.EmployeeWithAssignment;
import edu.carlosliam.hotelmanagementfx.service.EditEmployee;
import edu.carlosliam.hotelmanagementfx.service.PostEmployee;
import edu.carlosliam.hotelmanagementfx.utils.MessageUtils;
import edu.carlosliam.hotelmanagementfx.utils.ModalUtils;
import edu.carlosliam.hotelmanagementfx.utils.NotificationUtils;
import edu.carlosliam.hotelmanagementfx.utils.ValidationUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.controlsfx.control.Notifications;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class EmployeeNewController implements Initializable {

    @FXML
    private TextField tfDni;

    @FXML
    private TextField tfId;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfName;

    @FXML
    private ChoiceBox<String> cbProfession;

    @FXML
    private TextField tfSurname;

    @FXML
    private Label title;

    @FXML
    private Button btnSave;

    @FXML
    private HBox btnSaveContainer;

    private PostEmployee postEmployee;

    private Employee employeeEdit;
    private EditEmployee editEmployee;
    private ValidationSupport validator;

    @FXML
    public void close() {
        ModalUtils.modalStage.close();
    }
    @FXML
    public void saveEmployee() {
        if (employeeEdit == null) {
            addEmployee();
        } else {
            System.out.println("Edit employee");
            editEmployee();
        }
    }

    public void setEmployeeEdit(Employee employee) {
        this.employeeEdit = employee;

        title.setText("EDIT EMPLOYEE");

        tfId.setText(employee.getId());
        tfDni.setText(employee.getDni());
        tfName.setText(employee.getName());
        tfSurname.setText(employee.getSurnames());
        cbProfession.setValue(EmployeeWithAssignment.professions.get(employee.getProfession()));
        tfEmail.setText(employee.getEmail());

        tfId.setDisable(true);
        tfDni.setDisable(true);
    }

    private void addEmployee() {
        Employee employee = new Employee(
                tfId.getText(),
                tfDni.getText(),
                tfName.getText(),
                tfSurname.getText(),
                EmployeeWithAssignment.professionsInverse.get(cbProfession.getValue()),
                encryptPassword(tfDni.getText()),
                tfEmail.getText()
        );

        postEmployee = new PostEmployee(employee);
        postEmployee.start();

        postEmployee.setOnSucceeded(e-> {
            if (!postEmployee.getValue().isError()) {
                System.out.println(postEmployee.getValue().getResult());

                Platform.runLater(() ->
                    NotificationUtils.showNotificationSuccess("Employee added",
                            "The employee {name} has been added successfully"
                            .replace("{name}", tfName.getText() + " " + tfSurname.getText()))
                );

                ModalUtils.modalStage.close();
            } else {
                MessageUtils.showError("Error posting employee", postEmployee.getValue().getErrorMessage());
            }
        });
    }

    private void editEmployee() {
        Employee employee = new Employee(
                employeeEdit.getId(),
                employeeEdit.getDni(),
                tfName.getText(),
                tfSurname.getText(),
                EmployeeWithAssignment.professionsInverse.get(cbProfession.getValue()),
                employeeEdit.getPassword(),
                tfEmail.getText()
        );

        editEmployee = new EditEmployee(employeeEdit.getId(), employee);
        editEmployee.start();

        editEmployee.setOnSucceeded(e-> {
            if (!editEmployee.getValue().isError()) {
                System.out.println(editEmployee.getValue().getResult());
                ModalUtils.modalStage.close();
            } else {
                MessageUtils.showError("Error editing employee", editEmployee.getValue().getErrorMessage());
            }
        });
    }

    private String encryptPassword(String password) {
        StringBuilder sb = new StringBuilder();

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());

            for(byte b : hash) {
                sb.append(String.format("%02x", b));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    private void addValidations() {
        // DNI validation
        validator.registerValidator(
                tfDni,
                Validator.createEmptyValidator("DNI is required")
        );
        validator.registerValidator(
                tfDni,
                Validator.createRegexValidator(
                        "DNI is invalid",
                        "^(?:[0-9]{8}[TRWAGMYFPDXBNJZSQVHLCKE]|[XYZ][0-9]{7}[TRWAGMYFPDXBNJZSQVHLCKE])$",
                        null
                )
        );

        // Name validation
        validator.registerValidator(
                tfName,
                Validator.createEmptyValidator("Name is required")
        );
        validator.registerValidator(
                tfName,
                Validator.<String>createPredicateValidator(
                        name -> name.length() >= 2 && name.length() < 100,
                        "Name must be between 2 and 100 characters"
                )
        );

        // Surname validation
        validator.registerValidator(
                tfSurname,
                Validator.createEmptyValidator("Surname is required")
        );
        validator.registerValidator(
                tfSurname,
                Validator.<String>createPredicateValidator(
                        surname -> surname.length() >= 2 && surname.length() < 100,
                        "Surname must be between 2 and 100 characters"
                )
        );

        // Employee ID validation
        validator.registerValidator(
                tfId,
                Validator.createEmptyValidator("Employee ID is required")
        );
        validator.registerValidator(
                tfId,
                Validator.<String>createPredicateValidator(
                        id -> !id.isEmpty() && id.length() < 5,
                        "Employee ID must be between 1 and 5 characters"
                )
        );

        // Email validation
        validator.registerValidator(
                tfEmail,
                Validator.createEmptyValidator("Email is required")
        );
        validator.registerValidator(
                tfEmail,
                Validator.createRegexValidator(
                        "Email is invalid",
                        "^[A-Za-z0-9+_.-]+@(.+)$",
                        null
                )
        );

        // Profession validation
        validator.registerValidator(
                cbProfession,
                Validator.createEmptyValidator("Profession is required")
        );
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        validator = new ValidationSupport();
        validator.setErrorDecorationEnabled(true);
        addValidations();
        validator.revalidate();

        cbProfession.getItems().addAll(EmployeeWithAssignment.professions.values());

        btnSave.disableProperty().bind(validator.invalidProperty());
        ValidationUtils.showErrorsOnNode(validator, btnSave, btnSaveContainer);
    }
}
