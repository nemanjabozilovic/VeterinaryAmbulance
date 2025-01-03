package com.example.veterinaryambulance.domain.models;

import android.os.Parcel;
import android.os.Parcelable;

public class AppointmentDTO implements Parcelable {
    private int id;
    private int veterinarianId;
    private int petId;
    private String date;
    private String time;
    private String caseDescription;
    private String veterinarianIdName;
    private String petName;

    public AppointmentDTO() {}

    public AppointmentDTO(int id, int veterinarianId, int petId, String date, String time, String caseDescription) {
        this.id = id;
        this.veterinarianId = veterinarianId;
        this.petId = petId;
        this.date = date;
        this.time = time;
        this.caseDescription = caseDescription;
    }

    protected AppointmentDTO(Parcel in) {
        id = in.readInt();
        veterinarianId = in.readInt();
        petId = in.readInt();
        date = in.readString();
        time = in.readString();
        caseDescription = in.readString();
    }

    public static final Creator<AppointmentDTO> CREATOR = new Creator<AppointmentDTO>() {
        @Override
        public AppointmentDTO createFromParcel(Parcel in) {
            return new AppointmentDTO(in);
        }

        @Override
        public AppointmentDTO[] newArray(int size) {
            return new AppointmentDTO[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(veterinarianId);
        dest.writeInt(petId);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeString(caseDescription);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public String getVeterinarianIdName() {
        return veterinarianIdName;
    }

    public void setVeterinarianIdName(String veterinarianIdName) {
        this.veterinarianIdName = veterinarianIdName;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }
}