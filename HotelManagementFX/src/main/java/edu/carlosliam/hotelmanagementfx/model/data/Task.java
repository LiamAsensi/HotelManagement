package edu.carlosliam.hotelmanagementfx.model.data;

import com.google.gson.annotations.SerializedName;

import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Task {

    @SerializedName("codTrab")
    private String codTask;

    @SerializedName("descripcion")
    private String description;

    @SerializedName("categoria")
    private String type;

    @SerializedName("fecIni")
    private Date dateStart;

    @SerializedName("fecFin")
    private Date dateEnd;

    @SerializedName("trabajador")
    private Employee employee;

    @SerializedName("prioridad")
    private int priority;

    @SerializedName("tiempo")
    private int estimatedTime;
  
    public Task(String description, String status, String type, Date dateStart, Date dateEnd, Employee employee, int priority, int estimatedTime) {
        this.description = description;
        this.type = type;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.employee = employee;
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

    public Employee getEmployee() {
        return employee;
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
