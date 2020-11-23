package com.example.demo.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
public class Termin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long TerminId;

    private String vorname;
    private String nachname;
    private String email;
    private LocalDate datum;

    private int hoursStart;
    private int minutesStart;
    private int hoursEnd;
    private int minutesEnd;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Long getTerminId() {
        return TerminId;
    }

    public void setTerminId(Long terminId) {
        TerminId = terminId;
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

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public int getHoursStart() {
        return hoursStart;
    }

    public void setHoursStart(int hoursStart) {
        this.hoursStart = hoursStart;
    }

    public int getMinutesStart() {
        return minutesStart;
    }

    public void setMinutesStart(int minutesStart) {
        this.minutesStart = minutesStart;
    }

    public int getHoursEnd() {
        return hoursEnd;
    }

    public void setHoursEnd(int hoursEnd) {
        this.hoursEnd = hoursEnd;
    }

    public int getMinutesEnd() {
        return minutesEnd;
    }

    public void setMinutesEnd(int minutesEnd) {
        this.minutesEnd = minutesEnd;
    }
}
