package com.example.veterinaryambulance.domain.models;

import android.os.Parcel;
import android.os.Parcelable;

public class VeterinarianDTO implements Parcelable {
    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String position;
    private String contact;

    public VeterinarianDTO() {}

    public VeterinarianDTO(int id, String firstName, String lastName, String username, String password, String position, String contact) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.position = position;
        this.contact = contact;
    }

    protected VeterinarianDTO(Parcel in) {
        id = in.readInt();
        firstName = in.readString();
        lastName = in.readString();
        username = in.readString();
        password = in.readString();
        position = in.readString();
        contact = in.readString();
    }

    public static final Creator<VeterinarianDTO> CREATOR = new Creator<VeterinarianDTO>() {
        @Override
        public VeterinarianDTO createFromParcel(Parcel in) {
            return new VeterinarianDTO(in);
        }

        @Override
        public VeterinarianDTO[] newArray(int size) {
            return new VeterinarianDTO[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(position);
        dest.writeString(contact);
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}