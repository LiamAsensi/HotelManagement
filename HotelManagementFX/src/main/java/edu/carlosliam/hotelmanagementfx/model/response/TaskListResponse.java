package edu.carlosliam.hotelmanagementfx.model.response;

import edu.carlosliam.hotelmanagementfx.model.data.Assignment;

import java.util.List;

public class TaskListResponse extends BaseResponse{
    private List<Assignment> result;

    public List<Assignment> getResult() {
        return result;
    }
}
