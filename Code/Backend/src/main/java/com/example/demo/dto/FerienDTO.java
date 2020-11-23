package com.example.demo.dto;

public class FerienDTO {
    private Long id;
    private String ferienStart;
    private String ferienEnde;

    public FerienDTO(Long id, String ferienStart, String ferienEnde) {
        this.id = id;
        this.ferienStart = ferienStart;
        this.ferienEnde = ferienEnde;
    }

    public FerienDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
