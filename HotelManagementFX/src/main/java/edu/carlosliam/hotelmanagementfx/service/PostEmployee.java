package edu.carlosliam.hotelmanagementfx.service;

import com.google.gson.Gson;
import edu.carlosliam.hotelmanagementfx.model.data.Employee;
import edu.carlosliam.hotelmanagementfx.model.response.EmployeeResponse;
import edu.carlosliam.hotelmanagementfx.utils.ServiceUtils;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class PostEmployee extends Service<EmployeeResponse> {
    private Employee employee;

    public PostEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    protected Task<EmployeeResponse> createTask() {
        return new Task<EmployeeResponse>() {
            @Override
            protected EmployeeResponse call() throws Exception {
                Gson gson = new Gson();

                System.out.println(gson.toJson(employee));

                String json = ServiceUtils.getResponse(
                        ServiceUtils.SERVER + "/api/trabajadores", gson.toJson(employee), "POST");

                return gson.fromJson(json, EmployeeResponse.class);
            }
        };
    }
}
