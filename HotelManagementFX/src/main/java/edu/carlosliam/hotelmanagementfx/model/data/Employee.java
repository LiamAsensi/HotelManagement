package edu.carlosliam.hotelmanagementfx.model.data;

import com.google.gson.annotations.SerializedName;
import edu.carlosliam.hotelmanagementfx.controller.EmployeeManagerController.ProfessionFilter;

import java.util.HashMap;

public class Employee {
    @SerializedName("idTrabajador")
    private final String id;
    @SerializedName("dni")
    private String dni;
    @SerializedName("nombre")
    private String name;
    @SerializedName("apellidos")
    private String surnames;
    @SerializedName("especialidad")
    private String profession;
    @SerializedName("contraseña")
    private String password;
    private String email;

    public static HashMap<String, String> professions = new HashMap<>() {{
        put("limpieza", "Cleaner");
        put("gestion", "Manager");
        put("electricidad", "Electrician");
        put("fontaneria", "Plumber");
        put("carpinteria", "Carpenter");
    }};

    public static HashMap<ProfessionFilter, String> professionsEnum = new HashMap<>() {{
        put(ProfessionFilter.CLEANER, "limpieza");
        put(ProfessionFilter.MANAGER, "gestion");
        put(ProfessionFilter.ELECTRICIAN, "electricidad");
        put(ProfessionFilter.PLUMBER, "fontaneria");
        put(ProfessionFilter.CARPENTER, "carpinteria");
    }};

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
        return String.format("%s %s - %s - %s - %s", name, surnames, dni, profession, email);
    }

    @Override
    public int hashCode() {
        return super.hashCode() + id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Employee && id.equals(((Employee) obj).id);
    }
}
