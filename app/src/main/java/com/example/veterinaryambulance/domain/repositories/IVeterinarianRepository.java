package com.example.veterinaryambulance.domain.repositories;

import com.example.veterinaryambulance.data.models.Veterinarian;

import java.util.List;

public interface IVeterinarianRepository {
    Veterinarian getById(int id);
    List<Veterinarian> getAll();
    Veterinarian insert(Veterinarian veterinarian);
    boolean update(Veterinarian veterinarian);
    boolean delete(int id);
    Veterinarian login(String username, String password);
}