package edu.carlosliam.hotelmanagementfx.adapter;

import edu.carlosliam.hotelmanagementfx.HotelManagementApplication;
import edu.carlosliam.hotelmanagementfx.model.data.Employee;
import edu.carlosliam.hotelmanagementfx.model.data.EmployeeWithAssignment;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

import java.io.IOException;


public class EmployeeListViewCell extends ListCell<Employee> {
    @FXML
    private HBox hbMain;

    @FXML
    private HBox hbContainer;

    @FXML
    private Label lblDni;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblName;

    @FXML
    private Label lblProfession;

    @FXML
    private Label lblStatus;

    private FXMLLoader fxmlLoader;

    @Override
    protected void updateItem(Employee employee, boolean empty) {
        super.updateItem(employee, empty);

        if (empty || employee == null) {
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        } else {
            if (fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(
                        HotelManagementApplication.class.getResource("layout/employee-item.fxml")
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
            bind(employee);
        }
    }

    private void bind(Employee employee) {
        lblName.setText(employee.getName() + " " + employee.getSurnames());
        lblProfession.setText(EmployeeWithAssignment.professions.get(employee.getProfession()));
        lblEmail.setText(employee.getEmail());
        lblStatus.setText("Available");
        lblDni.setText(employee.getDni());

        setText(null);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }
}

