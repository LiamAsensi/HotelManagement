package edu.carlosliam.hotelmanagementfx.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import edu.carlosliam.hotelmanagementfx.model.data.Employee;
import edu.carlosliam.hotelmanagementfx.model.response.Response;
import edu.carlosliam.hotelmanagementfx.utils.ServiceUtils;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

import java.util.List;

public class GetEmployeesScheduled extends ScheduledService<List<Employee>> {
    @Override
    protected Task<List<Employee>> createTask() {
        return new Task<>() {
            @Override
            protected List<Employee> call() {
                String json = ServiceUtils.getResponse(
                        ServiceUtils.SERVER + "/api/trabajadores",
                        null,
                        "GET"
                );

                Gson gson = new Gson();

                Response<List<Employee>> response = gson.fromJson(json,
                        new TypeToken<Response<List<Employee>>>(){}.getType());

                return response.getResult();
            }
        };
    }
}
