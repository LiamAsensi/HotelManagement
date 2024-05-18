package edu.carlosliam.hotelmanagementfx.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import edu.carlosliam.hotelmanagementfx.model.data.Employee;
import edu.carlosliam.hotelmanagementfx.model.response.Response;
import edu.carlosliam.hotelmanagementfx.utils.ServiceUtils;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

import java.util.List;

public class GetEmployeesScheduled extends ScheduledService<Response<List<Employee>>> {
    @Override
    protected Task<Response<List<Employee>>> createTask() {
        return new Task<>() {
            @Override
            protected Response<List<Employee>> call() {
                String json = ServiceUtils.getResponse(
                        ServiceUtils.SERVER + "/api/trabajadores",
                        null,
                        "GET"
                );

                Gson gson = new Gson();

                return gson.fromJson(json,
                        new TypeToken<Response<List<Employee>>>(){}.getType());
            }
        };
    }
}
