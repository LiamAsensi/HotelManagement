package edu.carlosliam.hotelmanagementfx.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import edu.carlosliam.hotelmanagementfx.model.data.Room;
import edu.carlosliam.hotelmanagementfx.utils.ServiceUtils;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.lang.reflect.Type;
import java.util.List;

public class GetDirtyRoom extends Service<List<Room>> {
    @Override
    protected Task<List<Room>> createTask() {
        return new Task<>() {
            @Override
            protected List<Room> call() throws Exception {
                String json = ServiceUtils.getResponse(
                        ServiceUtils.SERVER_NEST + "/limpieza/sucias", null, "GET");

                Type roomListType = new TypeToken<List<Room>>(){}.getType();
                System.out.println(json);

                Gson gson = new Gson();

                return gson.fromJson(json, roomListType);
            }
        };
    }
}
