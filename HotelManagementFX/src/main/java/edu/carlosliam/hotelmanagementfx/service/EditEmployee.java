package edu.carlosliam.hotelmanagementfx.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import edu.carlosliam.hotelmanagementfx.model.data.Employee;
import edu.carlosliam.hotelmanagementfx.model.response.Response;
import edu.carlosliam.hotelmanagementfx.utils.ServiceUtils;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.List;

public class EditEmployee extends Service<Response<Employee>> {
    private final String id;
    private final Employee employee;

    public EditEmployee(String id, Employee employee) {
        this.id = id;
        this.employee = employee;
    }

    @Override
    protected Task<Response<Employee>> createTask() {
        return new Task<>() {
            @Override
            protected Response<Employee> call() {
                Gson gson = new Gson();

                String json = ServiceUtils.getResponse(
                        ServiceUtils.SERVER + "/api/trabajadores/" + id,
                        gson.toJson(employee),
                        "PUT"
                );

                return gson.fromJson(json,
                        new TypeToken<Response<Employee>>(){}.getType());
            }
        };
    }
}
