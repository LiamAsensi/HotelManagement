package edu.carlosliam.hotelmanagementfx.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import edu.carlosliam.hotelmanagementfx.adapter.LocalDateAdapter;
import edu.carlosliam.hotelmanagementfx.model.data.Assignment;
import edu.carlosliam.hotelmanagementfx.model.data.Employee;
import edu.carlosliam.hotelmanagementfx.model.response.Response;
import edu.carlosliam.hotelmanagementfx.utils.ServiceUtils;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.time.LocalDate;

public class AssignTaskToEmployee extends Service<Response<Assignment>> {
    private final String taskId;

    private final Assignment assignment;

    private final String employeeId;

    public AssignTaskToEmployee(String taskId, Assignment assignment, String employeeId) {
        this.taskId = taskId;
        this.assignment = assignment;
        this.employeeId = employeeId;
    }

    @Override
    protected Task<Response<Assignment>> createTask() {
        return new Task<Response<Assignment>>() {
            @Override
            protected Response<Assignment> call() {
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                        .create();

                String json = ServiceUtils.getResponse(
                        ServiceUtils.SERVER + "/api/trabajos/" + taskId + "/asignar/" + employeeId,
                        gson.toJson(assignment),
                        "PUT");

                return gson.fromJson(json, new TypeToken<Response<Assignment>>(){}.getType());
            }
        };
    }
}
