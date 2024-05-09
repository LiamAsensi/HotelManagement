package edu.carlosliam.hotelmanagementfx.service;

import com.google.gson.Gson;
import edu.carlosliam.hotelmanagementfx.model.response.EmployeeListResponse;
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
                        ServiceUtils.SERVER + "/api/trabajadores" + filter, null, "GET");

                System.out.println(json);

                Gson gson = new Gson();

                return gson.fromJson(json, EmployeeListResponse.class);
            }
        };
    }
}
