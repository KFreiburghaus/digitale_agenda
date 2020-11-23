package com.example.demo.dto;

public class CalendarDto {
    private int year;
    private int month;
    private int day;
    private int hoursStart;
    private int minutesStart;
    private int hoursEnd;
    private int minutesEnd;
    private String vorname;
    private String nachname;
    private String email;
    private Long Id;


    public CalendarDto() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
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
}
