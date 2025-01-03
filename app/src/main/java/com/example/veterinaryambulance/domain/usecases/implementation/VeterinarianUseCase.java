package com.example.veterinaryambulance.domain.usecases.implementation;

import com.example.veterinaryambulance.data.models.Veterinarian;
import com.example.veterinaryambulance.domain.mappers.VeterinarianMapper;
import com.example.veterinaryambulance.domain.models.VeterinarianDTO;
import com.example.veterinaryambulance.domain.repositories.IVeterinarianRepository;
import com.example.veterinaryambulance.domain.usecases.interfaces.IVeterinarianUseCase;

import java.util.List;

public class VeterinarianUseCase implements IVeterinarianUseCase {
    private final IVeterinarianRepository veterinarianRepository;

    public VeterinarianUseCase(IVeterinarianRepository veterinarianRepository) {
        this.veterinarianRepository = veterinarianRepository;
    }

    @Override
    public VeterinarianDTO getVeterinarianById(int veterinarianId) {
        Veterinarian veterinarian = veterinarianRepository.getById(veterinarianId);
        return veterinarian != null ? VeterinarianMapper.toDTO(veterinarian) : null;
    }

    @Override
    public List<VeterinarianDTO> getAllVeterinarians() {
        List<Veterinarian> veterinarians = veterinarianRepository.getAll();
        return VeterinarianMapper.toDTO(veterinarians);
    }

    @Override
    public boolean insertVeterinarian(VeterinarianDTO veterinarianDTO) {
        Veterinarian veterinarian = VeterinarianMapper.toModel(veterinarianDTO);
        Veterinarian insertedVeterinarian = veterinarianRepository.insert(veterinarian);
        return insertedVeterinarian != null;
    }

    @Override
    public boolean updateVeterinarian(VeterinarianDTO veterinarianDTO) {
        Veterinarian veterinarian = VeterinarianMapper.toModel(veterinarianDTO);
        return veterinarianRepository.update(veterinarian);
    }

    @Override
    public boolean deleteVeterinarian(int veterinarianId) {
        return veterinarianRepository.delete(veterinarianId);
    }

    @Override
    public VeterinarianDTO login(String username, String password) {
        Veterinarian veterinarian = veterinarianRepository.login(username, password);
        return veterinarian != null ? VeterinarianMapper.toDTO(veterinarian) : null;
    }
}