package edu.carlosliam.hotelmanagementfx.model.data;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;

public class Assignment {

    @SerializedName("codTrab")
    private String codTask;

    @SerializedName("descripcion")
    private String description;

    @SerializedName("categoria")
    private String type;

    @SerializedName("fecIni")
    private LocalDate dateStart;

    @SerializedName("fecFin")
    private LocalDate dateEnd;

    @SerializedName("trabajador")
    private Employee employee;

    @SerializedName("prioridad")
    private int priority;

    @SerializedName("tiempo")
    private float estimatedTime;
  
    public Assignment(String codTask, String description, String type, LocalDate dateStart, LocalDate dateEnd, Employee employee, int priority, float estimatedTime) {
        this.codTask = codTask;
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

    public LocalDate getDateStart() {
        return dateStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public Employee getEmployee() {
        return employee;
    }

    public int getPriority() {
        return priority;
    }

    public float getEstimatedTime() {
        return estimatedTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    @Override
    public String toString() {
        return "Task{" +
                "description='" + description + '\'' +
                ", dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                ", employee=" + (employee == null ? "unassigned" : employee.getName()) +
                '}';
    }

    @Override
    public int hashCode() {
        return super.hashCode() + codTask.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Assignment && codTask.equals(((Assignment) obj).codTask);
    }
}
