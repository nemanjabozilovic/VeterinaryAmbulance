package com.example.veterinaryambulance.domain.usecases.interfaces;

import com.example.veterinaryambulance.domain.models.VeterinarianDTO;

import java.util.List;

public interface IVeterinarianUseCase {
    VeterinarianDTO getVeterinarianById(int veterinarianId);

    List<VeterinarianDTO> getAllVeterinarians();

    boolean insertVeterinarian(VeterinarianDTO veterinarianDTO);

    boolean updateVeterinarian(VeterinarianDTO veterinarianDTO);

    boolean deleteVeterinarian(int veterinarianId);

    VeterinarianDTO login(String username, String password);
}