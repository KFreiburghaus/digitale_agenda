package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table
public class Arbeitszeit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long arbeitszeitId;

    private String arbeitsbeginn;
    private String arbeitsende;

    @OneToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

    public Arbeitszeit() {
    }

    public Long getArbeitszeitId() {
        return arbeitszeitId;
    }

    public void setArbeitszeitId(Long arbeitszeitId) {
        this.arbeitszeitId = arbeitszeitId;
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

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
