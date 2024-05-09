package edu.carlosliam.hotelmanagementfx.controller;

import edu.carlosliam.hotelmanagementfx.model.data.Employee;
import edu.carlosliam.hotelmanagementfx.service.PostEmployee;
import edu.carlosliam.hotelmanagementfx.utils.MessageUtils;
import edu.carlosliam.hotelmanagementfx.utils.ModalUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeNewController {
    @FXML
    private PasswordField pfPassword;

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

    private PostEmployee postEmployee;

    @FXML
    public void close() {
        ModalUtils.modalStage.close();
    }

    @FXML
    public void saveEmployee() {
        if (!isFormEmpty()) {
            Employee employee = new Employee(
                    tfId.getText(),
                    tfDni.getText(),
                    tfName.getText(),
                    tfSurname.getText(),
                    tfProfession.getText(),
                    pfPassword.getText(),
                    tfEmail.getText()
            );

            postEmployee = new PostEmployee(employee);
            postEmployee.start();

            postEmployee.setOnSucceeded(e-> {
                if (!postEmployee.getValue().isError()) {
                    System.out.println(postEmployee.getValue().getEmployee());
                } else {
                    MessageUtils.showError("Error posting employee", postEmployee.getValue().getErrorMessage());
                }
            });

            ModalUtils.modalStage.close();
        }
    }

    private boolean isFormEmpty() {
        return pfPassword.getText().isBlank() ||
                tfDni.getText().isBlank() ||
                tfId.getText().isBlank() ||
                tfEmail.getText().isBlank() ||
                tfName.getText().isBlank() ||
                tfProfession.getText().isBlank() ||
                tfSurname.getText().isBlank();
    }
}
