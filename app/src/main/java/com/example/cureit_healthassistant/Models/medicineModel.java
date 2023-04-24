package com.example.cureit_healthassistant.Models;

public class medicineModel {
    String doctor,name,description;

    public medicineModel(){}
    public medicineModel(String doctor, String name, String description) {
        this.doctor = doctor;
        this.name = name;
        this.description = description;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
