package edu.carlosliam.hotelmanagementfx.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import edu.carlosliam.hotelmanagementfx.model.data.Employee;
import edu.carlosliam.hotelmanagementfx.model.response.Response;
import edu.carlosliam.hotelmanagementfx.utils.ServiceUtils;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class PostEmployee extends Service<Response<Employee>> {
    private final Employee employee;

    public PostEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    protected Task<Response<Employee>> createTask() {
        return new Task<>() {
            @Override
            protected Response<Employee> call() throws Exception {
                Gson gson = new Gson();

                String json = ServiceUtils.getResponse(
                        ServiceUtils.SERVER + "/api/trabajadores", gson.toJson(employee), "POST");

                return gson.fromJson(json,
                        new TypeToken<Response<Employee>>(){}.getType());
            }
        };
    }
}
