package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table
public class Arbeitstage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long arbeitstageId;

    private boolean mo;
    private boolean di;
    private boolean mi;
    private boolean don;
    private boolean fr;
    private boolean sa;
    private boolean so;
    @OneToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;


    public Long getArbeitstageId() {
        return arbeitstageId;
    }

    public void setArbeitstageId(Long arbeitstageId) {
        this.arbeitstageId = arbeitstageId;
    }

    public boolean isMo() {
        return mo;
    }

    public void setMo(boolean mo) {
        this.mo = mo;
    }

    public boolean isDi() {
        return di;
    }

    public void setDi(boolean di) {
        this.di = di;
    }

    public boolean isMi() {
        return mi;
    }

    public void setMi(boolean mi) {
        this.mi = mi;
    }

    public boolean isDon() {
        return don;
    }

    public void setDon(boolean don) {
        this.don = don;
    }

    public boolean isFr() {
        return fr;
    }

    public void setFr(boolean fr) {
        this.fr = fr;
    }

    public boolean isSa() {
        return sa;
    }

    public void setSa(boolean sa) {
        this.sa = sa;
    }

    public boolean isSo() {
        return so;
    }

    public void setSo(boolean so) {
        this.so = so;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
