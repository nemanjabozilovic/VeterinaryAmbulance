package com.example.veterinaryambulance.domain.usecases.implementation;

import com.example.veterinaryambulance.data.models.Appointment;
import com.example.veterinaryambulance.data.models.Pet;
import com.example.veterinaryambulance.domain.mappers.PetMapper;
import com.example.veterinaryambulance.domain.models.PetDTO;
import com.example.veterinaryambulance.domain.repositories.IAppointmentRepository;
import com.example.veterinaryambulance.domain.repositories.IPetRepository;
import com.example.veterinaryambulance.domain.usecases.interfaces.IPetUseCase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PetUseCase implements IPetUseCase {
    private final IPetRepository petRepository;
    private final IAppointmentRepository appointmentRepository;

    public PetUseCase(IPetRepository petRepository, IAppointmentRepository appointmentRepository) {
        this.petRepository = petRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public PetDTO getPetById(int petId) {
        Pet pet = petRepository.getById(petId);
        return pet != null ? PetMapper.toDTO(pet) : null;
    }

    @Override
    public List<PetDTO> getAllPets() {
        List<Pet> pets = petRepository.getAll();
        return PetMapper.toDTO(pets);
    }

    @Override
    public PetDTO insertPet(PetDTO petDTO) {
        Pet pet = PetMapper.toModel(petDTO);
        Pet insertedPet = petRepository.insert(pet);

        return PetMapper.toDTO(insertedPet);
    }

    @Override
    public boolean updatePet(PetDTO petDTO) {
        Pet pet = PetMapper.toModel(petDTO);
        return petRepository.update(pet);
    }

    @Override
    public boolean deletePet(int petId) {
        List<Appointment> appointments = appointmentRepository.getAppointmentsByPetId(petId);

        for (Appointment appointment : appointments) {
            LocalDateTime appointmentDateTime = LocalDateTime.parse(
                    appointment.getDate() + "T" + appointment.getTime(),
                    DateTimeFormatter.ISO_LOCAL_DATE_TIME
            );

            if (appointmentDateTime.isAfter(LocalDateTime.now())) {
                throw new IllegalStateException("Cannot delete pet with scheduled future appointments.");
            }
        }

        for (Appointment appointment : appointments) {
            LocalDateTime appointmentDateTime = LocalDateTime.parse(
                    appointment.getDate() + "T" + appointment.getTime(),
                    DateTimeFormatter.ISO_LOCAL_DATE_TIME
            );

            if (appointmentDateTime.isBefore(LocalDateTime.now())) {
                appointmentRepository.delete(appointment.getId());
            }
        }

        return petRepository.delete(petId);
    }
}