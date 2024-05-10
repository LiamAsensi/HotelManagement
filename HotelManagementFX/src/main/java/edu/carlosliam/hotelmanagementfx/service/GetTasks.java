package edu.carlosliam.hotelmanagementfx.service;

import com.google.gson.Gson;
import edu.carlosliam.hotelmanagementfx.model.response.TaskListResponse;
import edu.carlosliam.hotelmanagementfx.utils.ServiceUtils;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class GetTasks extends Service<TaskListResponse> {
    String filter;

    public GetTasks()
    {
        filter="";

    }
    @Override
    protected Task<TaskListResponse> createTask() {
        return new Task<TaskListResponse>() {
            @Override
            protected TaskListResponse call() throws Exception {
                String json = ServiceUtils.getResponse(
                        ServiceUtils.SERVER + "/api/trabajos" + filter, null, "GET");

                System.out.println(json);

                Gson gson = new Gson();

                return gson.fromJson(json, TaskListResponse.class);
            }
        };
    }
}
