package edu.carlosliam.hotelmanagementfx.controller;

import edu.carlosliam.hotelmanagementfx.model.data.Employee;
import edu.carlosliam.hotelmanagementfx.service.EditEmployee;
import edu.carlosliam.hotelmanagementfx.service.PostEmployee;
import edu.carlosliam.hotelmanagementfx.utils.MessageUtils;
import edu.carlosliam.hotelmanagementfx.utils.ModalUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EmployeeNewController {

    @FXML
    private TextField tfDni;

    @FXML
    private TextField tfId;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfProfession;

    @FXML
    private TextField tfSurname;

    @FXML
    private Label title;

    private PostEmployee postEmployee;

    private Employee employeeEdit;
    private EditEmployee editEmployee;

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
        tfProfession.setText(employee.getProfession());
        tfEmail.setText(employee.getEmail());

        tfId.setDisable(true);
        tfDni.setDisable(true);
    }

    private void addEmployee() {
        if (!isFormEmpty()) {
            Employee employee = new Employee(
                    tfId.getText(),
                    tfDni.getText(),
                    tfName.getText(),
                    tfSurname.getText(),
                    tfProfession.getText(),
                    encryptPassword(tfDni.getText()),
                    tfEmail.getText()
            );

            postEmployee = new PostEmployee(employee);
            postEmployee.start();

            postEmployee.setOnSucceeded(e-> {
                if (!postEmployee.getValue().isError()) {
                    System.out.println(postEmployee.getValue().getResult());
                    ModalUtils.modalStage.close();
                } else {
                    MessageUtils.showError("Error posting employee", postEmployee.getValue().getErrorMessage());
                }
            });
        }
    }

    private void editEmployee() {
        if (!isFormEmpty()) {
            Employee employee = new Employee(
                    employeeEdit.getId(),
                    employeeEdit.getDni(),
                    tfName.getText(),
                    tfSurname.getText(),
                    tfProfession.getText(),
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
    }

    private boolean isFormEmpty() {
        return tfDni.getText().isBlank() ||
                tfId.getText().isBlank() ||
                tfEmail.getText().isBlank() ||
                tfName.getText().isBlank() ||
                tfProfession.getText().isBlank() ||
                tfSurname.getText().isBlank();
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
}
