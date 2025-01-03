package com.example.veterinaryambulance.domain.models;

import android.os.Parcel;
import android.os.Parcelable;

public class PetDTO implements Parcelable {
    private int id;
    private String name;
    private String breed;
    private int age;
    private String ownerName;
    private String ownerContact;

    public PetDTO() {}

    public PetDTO(int id, String name, String breed, int age, String ownerName, String ownerContact) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.ownerName = ownerName;
        this.ownerContact = ownerContact;
    }

    protected PetDTO(Parcel in) {
        id = in.readInt();
        name = in.readString();
        breed = in.readString();
        age = in.readInt();
        ownerName = in.readString();
        ownerContact = in.readString();
    }

    public static final Creator<PetDTO> CREATOR = new Creator<PetDTO>() {
        @Override
        public PetDTO createFromParcel(Parcel in) {
            return new PetDTO(in);
        }

        @Override
        public PetDTO[] newArray(int size) {
            return new PetDTO[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(breed);
        dest.writeInt(age);
        dest.writeString(ownerName);
        dest.writeString(ownerContact);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerContact() {
        return ownerContact;
    }

    public void setOwnerContact(String ownerContact) {
        this.ownerContact = ownerContact;
    }
}