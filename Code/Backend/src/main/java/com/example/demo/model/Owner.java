package com.example.demo.model;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

@Entity
@Table
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long OwnerId;

    private String vorname;
    private String nachname;
    private String firma;
    private String email;
    private String password;
    private Instant created;
    private boolean enabled;

    @OneToMany(mappedBy = "owner")
    private Set<Termin> termine;

    @OneToOne(mappedBy = "owner")
    private Arbeitstage arbeitstage;

    @OneToMany(mappedBy = "owner")
    private Set<Ferien> ferien;

    @OneToOne(mappedBy = "owner")
    private Arbeitszeit arbeitszeit;

    public Set<Termin> getTermine() {
        return termine;
    }

    public void setTermine(Set<Termin> termine) {
        this.termine = termine;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Long getOwnerId() {
        return OwnerId;
    }

    public void setOwnerId(Long ownerId) {
        OwnerId = ownerId;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public Arbeitstage getArbeitstage() {
        return arbeitstage;
    }

    public void setArbeitstage(Arbeitstage arbeitstage) {
        this.arbeitstage = arbeitstage;
    }

    public Set<Ferien> getFerien() {
        return ferien;
    }

    public void setFerien(Set<Ferien> ferien) {
        this.ferien = ferien;
    }

    public Arbeitszeit getArbeitszeit() {
        return arbeitszeit;
    }

    public void setArbeitszeit(Arbeitszeit arbeitszeit) {
        this.arbeitszeit = arbeitszeit;
    }
}
