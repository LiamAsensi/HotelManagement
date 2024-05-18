package edu.carlosliam.hotelmanagementfx.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import edu.carlosliam.hotelmanagementfx.model.data.Employee;
import edu.carlosliam.hotelmanagementfx.model.response.Response;
import edu.carlosliam.hotelmanagementfx.utils.ServiceUtils;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.List;

public class GetEmployees extends Service<Response<List<Employee>>> {
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
