package com.example.veterinaryambulance.domain.repositories;

import com.example.veterinaryambulance.data.models.Pet;

import java.util.List;

public interface IPetRepository {
    Pet getById(int id);
    List<Pet> getAll();
    Pet insert(Pet pet);
    boolean update(Pet pet);
    boolean delete(int id);
}