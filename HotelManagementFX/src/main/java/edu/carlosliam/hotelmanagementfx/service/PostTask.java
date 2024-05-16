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

public class PostTask extends Service<Response<Assignment>> {
    private Assignment assignment;

    public PostTask(Assignment assignment) {
        this.assignment = assignment;
    }

    @Override
    protected Task<Response<Assignment>> createTask() {
        return new Task<Response<Assignment>>() {
            @Override
            protected Response<Assignment> call() throws Exception {
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                        .create();

                System.out.println("JSON del trabajo:");
                System.out.println(gson.toJson(assignment));
                System.out.println();

                String json = ServiceUtils.getResponse(
                        ServiceUtils.SERVER + "/api/trabajos", gson.toJson(assignment), "POST");

                return gson.fromJson(json, new TypeToken<Response<Assignment>>(){}.getType());
            }
        };
    }
}
