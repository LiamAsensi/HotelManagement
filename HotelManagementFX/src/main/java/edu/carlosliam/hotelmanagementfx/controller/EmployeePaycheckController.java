package edu.carlosliam.hotelmanagementfx.controller;

import edu.carlosliam.hotelmanagementfx.service.GetTasksBetweenDates;
import edu.carlosliam.hotelmanagementfx.utils.MailUtils;
import edu.carlosliam.hotelmanagementfx.utils.MessageUtils;
import edu.carlosliam.hotelmanagementfx.utils.ModalUtils;
import edu.carlosliam.hotelmanagementfx.utils.PaycheckPdfCreator;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;

import java.time.format.DateTimeFormatter;

public class EmployeePaycheckController {
    @FXML
    private DatePicker dpStartDate;
    @FXML
    private DatePicker dpEndDate;

    @FXML
    public void close() {
        ModalUtils.modalStage.close();
    }

    @FXML
    public void generatePaychecks() {
        if (isFormEmpty()) {
            MessageUtils.showError("Invalid input", "You need to select both dates!");
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MM-yyyy");
            MailUtils mailSender = new MailUtils();

            EmployeeManagerController.employeeObservableList.forEach(employee -> {
                GetTasksBetweenDates getTasksBetweenDates = new GetTasksBetweenDates(
                        employee,
                        formatter.format(dpStartDate.getValue()),
                        formatter.format(dpEndDate.getValue())
                );

                getTasksBetweenDates.setOnSucceeded(e -> {
                    if (!getTasksBetweenDates.getValue().isError()) {
                        System.out.println("Generating paycheck for employee with ID: " + employee.getId());

                        PaycheckPdfCreator paycheck = new PaycheckPdfCreator(
                                employee,
                                formatter.format(dpStartDate.getValue()),
                                formatter.format(dpEndDate.getValue()),
                                getTasksBetweenDates.getValue().getResult()
                        );
                        paycheck.createPdf();

                        mailSender.sendEmailWithAttachment(
                                employee,
                                "Paycheck (" + formatter.format(dpStartDate.getValue()) +
                                        " / " + formatter.format(dpEndDate.getValue()) + ")",
                                "Here's the paycheck of your recent work.",
                                paycheck.getDestination()
                        );
                    } else {
                        MessageUtils.showError("Error getting tasks", getTasksBetweenDates.getValue().getErrorMessage());
                    }
                });

                getTasksBetweenDates.start();
            });

            close();
        }
    }

    private boolean isFormEmpty() {
        return dpStartDate.getValue() == null &&
                dpEndDate.getValue() == null;
    }
}
