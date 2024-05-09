package edu.carlosliam.hotelmanagementfx.model;

import com.google.gson.annotations.SerializedName;

import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Task {

    @SerializedName("code")
    public String codTask;

    @SerializedName("description")
    public String description;

    @SerializedName("category")
    public String type;

    @SerializedName("start_date")
    public Date dateStart;

    @SerializedName("end_date")
    public Date dateEnd;

    @SerializedName("employee_id")
    public String employeeId;

    public int priority;

    @SerializedName("time")
    public int estimatedTime;

    public Task(String description, String status, String type, Date dateStart, Date dateEnd, String employeeId, int priority, int estimatedTime) {
        this.description = description;
        this.type = type;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.employeeId = employeeId;
        this.priority = priority;
        this.estimatedTime = estimatedTime;
    }

    public String getCodTask() {
        return codTask;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public int getPriority() {
        return priority;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public void setDescription(String description) {
        this.description = description;
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
