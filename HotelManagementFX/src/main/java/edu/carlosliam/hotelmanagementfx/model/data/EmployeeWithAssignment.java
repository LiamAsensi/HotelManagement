package edu.carlosliam.hotelmanagementfx.model.data;

public class EmployeeWithAssignment {
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
