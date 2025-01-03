package com.example.veterinaryambulance.domain.mappers;

import com.example.veterinaryambulance.data.models.Veterinarian;
import com.example.veterinaryambulance.domain.models.VeterinarianDTO;

import java.util.List;
import java.util.stream.Collectors;

public class VeterinarianMapper {
    public static VeterinarianDTO toDTO(Veterinarian veterinarian) {
        return new VeterinarianDTO(
                veterinarian.getId(),
                veterinarian.getFirstName(),
                veterinarian.getLastName(),
                veterinarian.getUsername(),
                veterinarian.getPassword(),
                veterinarian.getPosition(),
                veterinarian.getContact()
        );
    }

    public static Veterinarian toModel(VeterinarianDTO veterinarianDTO) {
        return new Veterinarian(
                veterinarianDTO.getId(),
                veterinarianDTO.getFirstName(),
                veterinarianDTO.getLastName(),
                veterinarianDTO.getUsername(),
                veterinarianDTO.getPassword(),
                veterinarianDTO.getPosition(),
                veterinarianDTO.getContact()
        );
    }

    public static List<VeterinarianDTO> toDTO(List<Veterinarian> veterinarians) {
        return veterinarians.stream()
                .map(VeterinarianMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static List<Veterinarian> toModel(List<VeterinarianDTO> veterinarianDTOs) {
        return veterinarianDTOs.stream()
                .map(VeterinarianMapper::toModel)
                .collect(Collectors.toList());
    }
}