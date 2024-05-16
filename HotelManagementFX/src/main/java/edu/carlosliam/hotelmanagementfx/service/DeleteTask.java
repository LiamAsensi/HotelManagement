package edu.carlosliam.hotelmanagementfx.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import edu.carlosliam.hotelmanagementfx.model.response.Response;
import edu.carlosliam.hotelmanagementfx.utils.ServiceUtils;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class DeleteTask extends Service<Response<String>> {

    private final String id;

    public DeleteTask(String id) {
        this.id = id;
    }

    @Override
    protected Task<Response<String>> createTask() {
        return new Task<>() {
            @Override
            protected Response<String> call() {
                String json = ServiceUtils.getResponse(
                        ServiceUtils.SERVER + "/api/trabajos/" + id, null, "DELETE");

                Gson gson = new Gson();

                return gson.fromJson(json,
                        new TypeToken<Response<String>>(){}.getType());
            }
        };
    }
}
