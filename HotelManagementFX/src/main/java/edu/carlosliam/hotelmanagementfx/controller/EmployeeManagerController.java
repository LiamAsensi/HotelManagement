package edu.carlosliam.hotelmanagementfx.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeManagerController implements Initializable {
    @FXML
    private HMToolBar toolbar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HMToolBar.disableButton(toolbar.btnGoToEmployees);
    }
}
