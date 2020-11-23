package com.example.demo.dto;

public class ArbeitszeitDTO {
    private String arbeitsbeginn;
    private String arbeitsende;

    public ArbeitszeitDTO(String arbeitsbeginn, String arbeitsende) {
        this.arbeitsbeginn = arbeitsbeginn;
        this.arbeitsende = arbeitsende;
    }

    public String getArbeitsbeginn() {
        return arbeitsbeginn;
    }

    public void setArbeitsbeginn(String arbeitsbeginn) {
        this.arbeitsbeginn = arbeitsbeginn;
    }

    public String getArbeitsende() {
        return arbeitsende;
    }

    public void setArbeitsende(String arbeitsende) {
        this.arbeitsende = arbeitsende;
    }
}
