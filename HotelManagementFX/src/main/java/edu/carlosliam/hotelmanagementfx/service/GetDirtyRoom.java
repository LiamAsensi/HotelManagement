package edu.carlosliam.hotelmanagementfx.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import edu.carlosliam.hotelmanagementfx.adapter.LocalDateAdapter;
import edu.carlosliam.hotelmanagementfx.model.data.Assignment;
import edu.carlosliam.hotelmanagementfx.model.data.Room;
import edu.carlosliam.hotelmanagementfx.model.response.Response;
import edu.carlosliam.hotelmanagementfx.utils.ServiceUtils;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.time.LocalDate;
import java.util.List;

public class GetDirtyRoom extends Service<Response<List<Room>>> {
    private final String filter;

    public GetDirtyRoom()
    {
        filter="";
    }
    @Override
    protected Task<Response<List<Room>>> createTask() {
        return new Task<Response<List<Room>>>() {
            @Override
            protected Response<List<Room>> call() throws Exception {
                String json = ServiceUtils.getResponse(
                        ServiceUtils.SERVER_NEST + "/limpieza/sucias" + filter, null, "GET");

                System.out.println(json);

                Gson gson = new Gson();

                return gson.fromJson(json, new TypeToken<Response<List<Room>>>(){}.getType());
            }
        };
    }
}
