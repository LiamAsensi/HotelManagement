package edu.carlosliam.hotelmanagementfx.model;

public class Employee {
    public String id;
    public String dni;
    public String name;
    public String surnames;
    public String profession;
    public String password;
    public String email;

    public Employee(String id, String dni, String name, String surnames, String profession, String password, String email) {
        this.id = id;
        this.dni = dni;
        this.name = name;
        this.surnames = surnames;
        this.profession = profession;
        this.password = password;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurnames() {
        return surnames;
    }

    public void setSurnames(String surnames) {
        this.surnames = surnames;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "dni='" + dni + '\'' +
                ", name='" + name + '\'' +
                ", surnames='" + surnames + '\'' +
                ", profession='" + profession + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
