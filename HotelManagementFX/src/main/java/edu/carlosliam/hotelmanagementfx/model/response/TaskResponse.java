package edu.carlosliam.hotelmanagementfx.model.response;

import com.google.gson.annotations.SerializedName;
import edu.carlosliam.hotelmanagementfx.model.data.Assignment;

public class TaskResponse extends BaseResponse {
    @SerializedName("result")
    private Assignment assignment;

    public Assignment getTask() {
        return assignment;
    }
}
