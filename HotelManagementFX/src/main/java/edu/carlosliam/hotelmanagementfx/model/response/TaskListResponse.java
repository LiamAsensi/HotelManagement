package edu.carlosliam.hotelmanagementfx.model.response;

import edu.carlosliam.hotelmanagementfx.model.data.Task;

import java.util.List;

public class TaskListResponse extends BaseResponse{
    private List<Task> result;

    public List<Task> getResult() {
        return result;
    }
}
