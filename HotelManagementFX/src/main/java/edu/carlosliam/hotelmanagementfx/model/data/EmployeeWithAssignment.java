package edu.carlosliam.hotelmanagementfx.model.data;

import java.util.HashMap;

public class EmployeeWithAssignment {
    public static HashMap<String, String> professions;
    public static HashMap<String, String> professionsInverse;
    static {
        professions = new HashMap<>();
        professions.put("electricidad", "Electricity");
        professions.put("limpieza", "Cleaning");
        professions.put("gestion", "Management");
        professions.put("fontaneria", "Plumber");
        professions.put("carpinteria", "Carpentry");
        professions.put("pintura", "Painting");
        professions.put("construccion", "Construction");

        professionsInverse = new HashMap<>();
        professionsInverse.put("Electricity", "electricidad");
        professionsInverse.put("Cleaning", "limpieza");
        professionsInverse.put("Management", "gestion");
        professionsInverse.put("Plumber", "fontaneria");
        professionsInverse.put("Carpentry", "carpinteria");
        professionsInverse.put("Painting", "pintura");
        professionsInverse.put("Construction", "construccion");
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
