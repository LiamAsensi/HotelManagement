package edu.carlosliam.hotelmanagementfx.adapter;

import edu.carlosliam.hotelmanagementfx.HotelManagementApplication;
import edu.carlosliam.hotelmanagementfx.model.data.Assignment;
import edu.carlosliam.hotelmanagementfx.model.data.EmployeeWithAssignment;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;

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
    private FontIcon fiPriority;

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
        lblType.setText(EmployeeWithAssignment.professions.get(assignment.getType()));

        if (assignment.getEmployee() != null) {
            lblEmployeeName.setText(assignment.getEmployee().getName() + " " + assignment.getEmployee().getSurnames());
        } else {
            lblEmployeeName.setText("Unassigned");
        }

        lblDate.setText(assignment.getDateStart().toString());

        switch (assignment.getPriority()) {
            case 1:
                fiPriority.setIconColor(Color.GRAY);
                break;
            case 2:
                fiPriority.setIconColor(Color.GREEN);
                break;
            case 3:
                fiPriority.setIconColor(Color.ORANGE);
                break;
            case 4:
                fiPriority.setIconColor(Color.RED);
                break;
        }

        setText(null);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }
}

