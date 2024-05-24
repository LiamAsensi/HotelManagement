package edu.carlosliam.hotelmanagementfx.model.data;

import java.util.HashMap;

public class EmployeeWithAssignment {
    public static HashMap<String, String> professions;
    static {
        professions = new HashMap<>();
        professions.put("electricidad", "Electricity");
        professions.put("limpieza", "Cleaning");
        professions.put("gestion", "Management");
        professions.put("fontaneria", "Plumber");
        professions.put("carpinteria", "Carpentry");
        professions.put("pintura", "Painting");
        professions.put("construccion", "Construction");
    }
    private Assignment assignment;
    private Employee employee;

    public EmployeeWithAssignment(Assignment assignment, Employee employee) {
        this.assignment = assignment;
        this.employee = employee;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "Assignment: " + assignment.getCodTask() +
                "- Employee: " + employee.getName() + " " +
                employee.getSurnames() + " (" + employee.getId() + ")";
    }
}
