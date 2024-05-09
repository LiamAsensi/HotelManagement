package edu.carlosliam.hotelmanagementfx.model;

import com.google.gson.annotations.SerializedName;

import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Task {

    @SerializedName("code")
    private String codTask;

    @SerializedName("description")
    private String description;
    private String status;

    @SerializedName("category")
    private String type;

    @SerializedName("start_date")
    private Date dateStart;

    @SerializedName("end_date")
    private Date dateEnd;

    @SerializedName("employee_id")
    private String employeeId;

    private int priority;

    @SerializedName("time")
    private int estimatedTime;

    private Employee employee;

    public Task(String description, String status, String type, Date dateStart, Date dateEnd, String employeeId, int priority, int estimatedTime) {
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

    public Date getDate() {
        return dateStart;
    }

    public Date getDateEnd() {
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

    public void setDate(Date dateStart) {
        this.dateStart = dateStart;
    }

    public void setDateEnd(Date dateEnd) {
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
