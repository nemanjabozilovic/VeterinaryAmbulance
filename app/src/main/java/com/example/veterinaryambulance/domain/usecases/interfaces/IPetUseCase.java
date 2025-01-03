package com.example.veterinaryambulance.domain.usecases.interfaces;

import com.example.veterinaryambulance.domain.models.PetDTO;

import java.util.List;

public interface IPetUseCase {
    PetDTO getPetById(int petId);
    List<PetDTO> getAllPets();
    PetDTO insertPet(PetDTO petDTO);
    boolean updatePet(PetDTO petDTO);
    boolean deletePet(int petId);
}