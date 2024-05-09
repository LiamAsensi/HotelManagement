package edu.carlosliam.hotelmanagementfx.model;

import java.util.List;

public class TaskListResponse extends BaseResponse{
    public List<Task> result;

    public List<Task> getResult() {
        return result;
    }
}
