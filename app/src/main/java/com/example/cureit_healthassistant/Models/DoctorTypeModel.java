package com.example.cureit_healthassistant.Models;

public class DoctorTypeModel {
    String drTypeName,drTypeInitials;

    public DoctorTypeModel(String drTypeName,String drTypeInitials) {
        this.drTypeName = drTypeName;
        this.drTypeInitials = drTypeInitials;
    }

    public String getDrTypeInitials() {
        return drTypeInitials;
    }

    public void setDrTypeInitials(String drTypeInitials) {
        this.drTypeInitials = drTypeInitials;
    }

    public String getDrTypeName() {
        return drTypeName;
    }

    public void setDrTypeName(String drTypeName) {
        this.drTypeName = drTypeName;
    }
}
