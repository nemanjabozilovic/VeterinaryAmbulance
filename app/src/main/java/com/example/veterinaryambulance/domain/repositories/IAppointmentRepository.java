package com.example.veterinaryambulance.domain.repositories;

import com.example.veterinaryambulance.data.models.Appointment;

import java.util.List;

public interface IAppointmentRepository {
    Appointment getById(int id);
    List<Appointment> getAll();
    Appointment insert(Appointment appointment);
    boolean update(Appointment appointment);
    boolean delete(int id);
    List<Appointment> getAppointmentsByPetId(int petId);
    List<Appointment> getAppointmentsByVeterinarianId(int vetId);
}