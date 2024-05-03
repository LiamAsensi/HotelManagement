package edu.carlosliam.hotelmanagementfx.model;

import com.google.gson.annotations.SerializedName;

import java.time.format.DateTimeFormatter;

public class Task {

    @SerializedName("_code")
    private String codTask;

    @SerializedName("_description")
    private String description;
    private String status;

    @SerializedName("_category")
    private String type;

    @SerializedName("_start_date")
    private DateTimeFormatter dateStart;

    @SerializedName("_end_date")
    private DateTimeFormatter dateEnd;

    @SerializedName("_employee_id")
    private String employeeId;

    private int priority;

    @SerializedName("_time")
    private int estimatedTime;

    private Employee employee;

    public Task(String description, String status, String type, DateTimeFormatter dateStart, DateTimeFormatter dateEnd, String employeeId, int priority, int estimatedTime) {
        this.description = description;
        this.status = status;
        this.type = type;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.employeeId = employeeId;
        this.priority = priority;
        this.estimatedTime = estimatedTime;
    }


    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public DateTimeFormatter getDate() {
        return dateStart;
    }

    public DateTimeFormatter getDateEnd() {
        return dateEnd;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(DateTimeFormatter dateStart) {
        this.dateStart = dateStart;
    }

    public void setDateEnd(DateTimeFormatter dateEnd) {
        this.dateEnd = dateEnd;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "Task{" +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                ", priority=" + priority +
                '}';
    }
}
