package edu.carlosliam.hotelmanagementfx.model;

import java.util.List;

public class EmployeeListResponse extends BaseResponse {
    public List<Employee> result;

    public List<Employee> getResult() {
        return result;
    }
}
