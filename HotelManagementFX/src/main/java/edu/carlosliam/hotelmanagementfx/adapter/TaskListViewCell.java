package edu.carlosliam.hotelmanagementfx.adapter;

import edu.carlosliam.hotelmanagementfx.HotelManagementApplication;
import edu.carlosliam.hotelmanagementfx.model.Employee;
import edu.carlosliam.hotelmanagementfx.model.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

import java.io.IOException;


public class TaskListViewCell extends ListCell<Task> {
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
    private Label lblStatus;

    private FXMLLoader fxmlLoader;

    @Override
    protected void updateItem(Task task, boolean empty) {
        super.updateItem(task, empty);

        if (empty || task == null) {
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
            bind(task);
        }
    }

    private void bind(Task task) {
        lblName.setText(task.getDescription());
        lblType.setText(task.getType());
        lblStatus.setText("Available");
        lblEmployeeName.setText("Unassigned");
        lblDate.setText(task.getDateStart().toString());

        setText(null);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }
}
