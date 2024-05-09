package edu.carlosliam.hotelmanagementfx.model.response;

import com.google.gson.annotations.SerializedName;
import edu.carlosliam.hotelmanagementfx.model.data.Employee;

public class EmployeeResponse extends BaseResponse {
    @SerializedName("result")
    private Employee employee;

    public Employee getEmployee() {
        return employee;
    }
}
