package edu.carlosliam.hotelmanagementfx.controller;

import edu.carlosliam.hotelmanagementfx.model.data.Assignment;
import edu.carlosliam.hotelmanagementfx.service.GetAllTasksBetweenDates;
import edu.carlosliam.hotelmanagementfx.service.GetTasksBetweenDates;
import edu.carlosliam.hotelmanagementfx.utils.*;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EmployeeExpenseReportController {
    @FXML
    private DatePicker dpStartDate;
    @FXML
    private DatePicker dpEndDate;

    @FXML
    public void close() {
        ModalUtils.modalStage.close();
    }

    @FXML
    public void generateReport() {
        if (isFormEmpty()) {
            MessageUtils.showError("Invalid input", "You need to select both dates!");
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MM-yyyy");

            GetAllTasksBetweenDates getAllTasksBetweenDates = new GetAllTasksBetweenDates(
                    formatter.format(dpStartDate.getValue()),
                    formatter.format(dpEndDate.getValue())
            );

            getAllTasksBetweenDates.setOnSucceeded(e -> {
                if (!getAllTasksBetweenDates.getValue().isError()) {
                    ExpenseReportPdfCreator report = new ExpenseReportPdfCreator(
                            getAllTasksBetweenDates.getValue().getResult(),
                            formatter.format(dpStartDate.getValue()),
                            formatter.format(dpEndDate.getValue())
                    );

                    report.createPdf();

                    MessageUtils.showMessage("Report created", "The report has been created successfully!");
                    close();
                } else {
                    MessageUtils.showError("Error getting tasks", getAllTasksBetweenDates.getValue().getErrorMessage());
                }
            });

            getAllTasksBetweenDates.start();
        }
    }

    private boolean isFormEmpty() {
        return dpStartDate.getValue() == null &&
                dpEndDate.getValue() == null;
    }
}
