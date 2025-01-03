package com.example.veterinaryambulance.data.models;

public class Appointment {
    private int id;
    private int veterinarianId;
    private int petId;
    private String date;
    private String time;
    private String caseDescription;

    public Appointment() {
    }

    public Appointment(int id, int veterinarianId, int petId, String date, String time, String caseDescription) {
        this.id = id;
        this.veterinarianId = veterinarianId;
        this.petId = petId;
        this.date = date;
        this.time = time;
        this.caseDescription = caseDescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVeterinarianId() {
        return veterinarianId;
    }

    public void setVeterinarianId(int veterinarianId) {
        this.veterinarianId = veterinarianId;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCaseDescription() {
        return caseDescription;
    }

    public void setCaseDescription(String caseDescription) {
        this.caseDescription = caseDescription;
    }
}