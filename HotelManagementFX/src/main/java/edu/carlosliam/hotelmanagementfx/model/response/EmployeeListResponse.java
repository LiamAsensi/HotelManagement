package edu.carlosliam.hotelmanagementfx.model.response;

import edu.carlosliam.hotelmanagementfx.model.data.Employee;

import java.util.List;

public class EmployeeListResponse extends BaseResponse {
    private List<Employee> result;

    public List<Employee> getResult() {
        return result;
    }
}
