package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table
public class Ferien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ferienId;

    private String ferienStart;
    private String ferienEnde;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    public Ferien() {
    }

    public Long getFerienId() {
        return ferienId;
    }

    public void setFerienId(Long ferienId) {
        this.ferienId = ferienId;
    }

    public String getFerienStart() {
        return ferienStart;
    }

    public void setFerienStart(String ferienStart) {
        this.ferienStart = ferienStart;
    }

    public String getFerienEnde() {
        return ferienEnde;
    }

    public void setFerienEnde(String ferienEnde) {
        this.ferienEnde = ferienEnde;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
