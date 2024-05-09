package edu.carlosliam.hotelmanagementfx.data;

import com.google.gson.Gson;
import edu.carlosliam.hotelmanagementfx.model.EmployeeListResponse;
import edu.carlosliam.hotelmanagementfx.model.TaskListResponse;
import edu.carlosliam.hotelmanagementfx.utils.ServiceUtils;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class GetEmployees extends Service<EmployeeListResponse> {
    private final String filter;

    public GetEmployees() {
        filter = "";
    }

    @Override
    protected Task<EmployeeListResponse> createTask() {
        return new Task<EmployeeListResponse>() {
            @Override
            protected EmployeeListResponse call() throws Exception {
                String json = ServiceUtils.getResponse(
                        ServiceUtils.SERVER + "/employees" + filter, null, "GET");

                Gson gson = new Gson();

                return gson.fromJson(json, EmployeeListResponse.class);
            }
        };
    }
}
