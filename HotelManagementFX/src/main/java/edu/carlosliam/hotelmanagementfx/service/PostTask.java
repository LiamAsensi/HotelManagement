package edu.carlosliam.hotelmanagementfx.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.carlosliam.hotelmanagementfx.adapter.LocalDateAdapter;
import edu.carlosliam.hotelmanagementfx.model.data.Assignment;
import edu.carlosliam.hotelmanagementfx.model.response.TaskResponse;
import edu.carlosliam.hotelmanagementfx.utils.ServiceUtils;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.time.LocalDate;

public class PostTask extends Service<TaskResponse> {
    private Assignment assignment;

    public PostTask(Assignment assignment) {
        this.assignment = assignment;
    }

    @Override
    protected Task<TaskResponse> createTask() {
        return new Task<TaskResponse>() {
            @Override
            protected TaskResponse call() throws Exception {
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                        .create();

                System.out.println("JSON del trabajo:");
                System.out.println(gson.toJson(assignment));
                System.out.println();

                String json = ServiceUtils.getResponse(
                        ServiceUtils.SERVER + "/api/trabajos", gson.toJson(assignment), "POST");

                return gson.fromJson(json, TaskResponse.class);
            }
        };
    }
}
