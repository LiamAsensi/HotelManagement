package edu.carlosliam.hotelmanagementfx.controller;

import com.google.gson.Gson;
import edu.carlosliam.hotelmanagementfx.adapter.TaskListViewCell;
import edu.carlosliam.hotelmanagementfx.model.Employee;
import edu.carlosliam.hotelmanagementfx.model.Task;
import edu.carlosliam.hotelmanagementfx.model.TaskListResponse;
import edu.carlosliam.hotelmanagementfx.utils.MessageUtils;
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

    private final Gson gson = new Gson();
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

        lvTasks.setItems(taskObservableList);
        lvTasks.setCellFactory(taskListView -> {
            TaskListViewCell taskListViewCell = new TaskListViewCell();
            taskListViewCell.prefWidthProperty().bind(lvTasks.widthProperty());
            return taskListViewCell;
        });

        System.out.println("Get tasks");
        getTasks();
        System.out.println("Get tasks 2");

    }

    private void getTasks() {
        String url = ServiceUtils.SERVER + "/tasks";
        ServiceUtils.getResponseAsync(url, null, "GET")
                .thenApply(json -> gson.fromJson(json, TaskListResponse.class))
                .thenAccept(response -> {
                    if (!response.isError()) {
                        Platform.runLater(() -> {
                            //lsTasks.getItems().setAll(response.getTasks());
                            response.getTasks().forEach(System.out::println);
                        });
                    } else {
                        MessageUtils.showError("Error", response.getErrorMessage());
                    }
                })
                .exceptionally(e -> {
                    MessageUtils.showError("Error", e.getMessage());
                    return null;
                });
    }
}