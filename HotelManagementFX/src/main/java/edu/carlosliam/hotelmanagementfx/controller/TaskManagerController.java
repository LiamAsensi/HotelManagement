package edu.carlosliam.hotelmanagementfx.controller;

import edu.carlosliam.hotelmanagementfx.data.GetTasks;
import com.google.gson.Gson;
import edu.carlosliam.hotelmanagementfx.adapter.TaskListViewCell;
import edu.carlosliam.hotelmanagementfx.model.Employee;
import edu.carlosliam.hotelmanagementfx.model.Task;
import edu.carlosliam.hotelmanagementfx.utils.MessageUtils;
import javafx.collections.FXCollections;
import edu.carlosliam.hotelmanagementfx.utils.ServiceUtils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.Date;

public class TaskManagerController {
    public HMToolBar toolbar;

    @FXML
    private ListView<Task> lvTasks;

    private GetTasks getTasks;
  
    ObservableList<Task> taskObservableList;

    public TaskManagerController() {
        taskObservableList = FXCollections.observableArrayList();

        // Agregamos las tareas a la lista
        taskObservableList.addAll(
            new Task("Reparación de tuberías", "Sin asignar", "Fontanería", new Date(), null, null, 1, 0),
            new Task("Limpieza de oficinas", "Asignado", "Limpieza", new Date(), new Date(), "1", 2, 8),
            new Task("Instalación de luces exteriores", "Sin asignar", "Electricidad", new Date(), null, null, 3, 10),
            new Task("Mantenimiento de calderas", "Finalizado", "Fontanería", new Date(), new Date(), "3", 1, 4),
            new Task("Pintura de paredes", "Sin asignar", "Construcción", new Date(), null, null, 2, 12),
            new Task("Reparación de aire acondicionado", "Asignado", "Electrodomésticos", new Date(), new Date(), "5", 3, 6),
            new Task("Limpieza de conductos de ventilación", "Sin asignar", "Limpieza", new Date(), null, null, 1, 8),
            new Task("Reemplazo de grifos", "Sin asignar", "Fontanería", new Date(), null, null, 2, 4),
            new Task("Reparación de tejado", "Asignado", "Construcción", new Date(), new Date(), "4", 3, 16),
            new Task("Limpieza de alfombras", "Sin asignar", "Limpieza", new Date(), null, null, 1, 6)
        );
    }

    public void initialize() {
        HMToolBar.disableButton(toolbar.btnGoToTasks);

        getTasks = new GetTasks();
        getTasks.start();

        getTasks.setOnSucceeded(e -> {
            if (!getTasks.getValue().isError()) {
                System.out.println(getTasks.getValue().getResult());
                lvTasks.setItems(FXCollections.observableArrayList(getTasks.getValue().getResult()));
            } else {
                MessageUtils.showError("Error getting tasks", getTasks.getValue().getErrorMessage());
            }
        });
      
        getTasks.setOnFailed(e -> {
            MessageUtils.showError("Error", "Error connecting to the server");
        });
      
        lvTasks.setItems(taskObservableList);
        lvTasks.setCellFactory(taskListView -> {
            TaskListViewCell taskListViewCell = new TaskListViewCell();
            taskListViewCell.prefWidthProperty().bind(lvTasks.widthProperty());
            return taskListViewCell;
        });

    }
}