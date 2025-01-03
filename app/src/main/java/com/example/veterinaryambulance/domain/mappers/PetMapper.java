package com.example.veterinaryambulance.domain.mappers;

import com.example.veterinaryambulance.data.models.Pet;
import com.example.veterinaryambulance.domain.models.PetDTO;

import java.util.List;
import java.util.stream.Collectors;

public class PetMapper {
    public static PetDTO toDTO(Pet pet) {
        return new PetDTO(
                pet.getId(),
                pet.getName(),
                pet.getBreed(),
                pet.getAge(),
                pet.getOwnerName(),
                pet.getOwnerContact()
        );
    }

    public static Pet toModel(PetDTO petDTO) {
        return new Pet(
                petDTO.getId(),
                petDTO.getName(),
                petDTO.getBreed(),
                petDTO.getAge(),
                petDTO.getOwnerName(),
                petDTO.getOwnerContact()
        );
    }

    public static List<PetDTO> toDTO(List<Pet> pets) {
        return pets.stream()
                .map(PetMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static List<Pet> toModel(List<PetDTO> petDTOs) {
        return petDTOs.stream()
                .map(PetMapper::toModel)
                .collect(Collectors.toList());
    }
}