package edu.carlosliam.hotelmanagementfx.services;

import com.google.gson.Gson;
import edu.carlosliam.hotelmanagementfx.model.TaskListResponse;
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
                        ServiceUtils.SERVER + "/tasks" + filter, null, "GET");
                Gson gson = new Gson();
                TaskListResponse response = gson.fromJson(json, TaskListResponse.class);

                return response;
            }
        };
    }
}
