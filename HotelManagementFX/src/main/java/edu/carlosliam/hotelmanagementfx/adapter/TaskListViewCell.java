package edu.carlosliam.hotelmanagementfx.adapter;

import edu.carlosliam.hotelmanagementfx.HotelManagementApplication;
import edu.carlosliam.hotelmanagementfx.model.data.Assignment;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.text.SimpleDateFormat;


public class TaskListViewCell extends ListCell<Assignment> {
    @FXML
    private HBox hbMain;

    @FXML
    private HBox hbContainer;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblEmployeeName;

    @FXML
    private Label lblName;

    @FXML
    private Label lblType;

    @FXML
    private Label lblDateEnd;

    @FXML
    private Label lblPriority;

    @FXML
    private Label lblStatus;

    private FXMLLoader fxmlLoader;

    @Override
    protected void updateItem(Assignment assignment, boolean empty) {
        super.updateItem(assignment, empty);

        if (empty || assignment == null) {
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        } else {
            if (fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(
                        HotelManagementApplication.class.getResource("layout/task-item.fxml")
                );
                fxmlLoader.setController(this);
                fxmlLoader.setRoot(this);

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    System.err.println("Error loading the FXML file: " + e.getMessage());
                }
            }
            hbMain.setMaxWidth(Double.MAX_VALUE);
            hbContainer.setMaxWidth(Double.MAX_VALUE);
            bind(assignment);
        }
    }

    private void bind(Assignment assignment) {
        lblName.setText(assignment.getDescription());
        lblType.setText(assignment.getType());
        if (assignment.getEmployee() != null && assignment.getDateEnd() != null) {
            lblStatus.setText("Finished");
            lblEmployeeName.setText(assignment.getEmployee().getName() + " " + assignment.getEmployee().getSurnames());
        } else if (assignment.getEmployee() != null && assignment.getDateEnd() == null) {
            lblStatus.setText("Assigned");
            lblEmployeeName.setText(assignment.getEmployee().getName() + " " + assignment.getEmployee().getSurnames());
        } else {
            lblStatus.setText("Unassigned");
        }

        if (assignment.getDateEnd() != null) {
            lblDate.setText(assignment.getDateStart().toString());
        } else {
            lblDate.setText("Not started");
        }

        lblPriority.setText(assignment.getPriority() + "");

        setText(null);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }
}

