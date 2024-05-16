package edu.carlosliam.hotelmanagementfx.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import edu.carlosliam.hotelmanagementfx.adapter.LocalDateAdapter;
import edu.carlosliam.hotelmanagementfx.model.data.Assignment;
import edu.carlosliam.hotelmanagementfx.model.data.Employee;
import edu.carlosliam.hotelmanagementfx.model.response.Response;
import edu.carlosliam.hotelmanagementfx.utils.ServiceUtils;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.time.LocalDate;
import java.util.List;

public class GetTasksBetweenDates extends Service<Response<List<Assignment>>> {
    private final Employee employee;
    private final String startDate;
    private final String endDate;

    public GetTasksBetweenDates(Employee employee, String startDate, String endDate) {
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    protected Task<Response<List<Assignment>>> createTask() {
        return new Task<>() {
            @Override
            protected Response<List<Assignment>> call() {
                String json = ServiceUtils.getResponse(
                        ServiceUtils.SERVER + "/api/trabajos/" +
                        employee.getId() + "/finalizados/" +
                        startDate + "/" + endDate,
                        null,
                        "GET"
                );

                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                        .create();

                return gson.fromJson(json,
                        new TypeToken<Response<List<Assignment>>>(){}.getType());
            }
        };
    }
}
