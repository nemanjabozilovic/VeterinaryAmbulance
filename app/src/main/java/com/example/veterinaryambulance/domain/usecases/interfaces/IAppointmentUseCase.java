package com.example.veterinaryambulance.domain.usecases.interfaces;

import com.example.veterinaryambulance.data.models.Appointment;
import com.example.veterinaryambulance.domain.models.AppointmentDTO;

import java.util.List;

public interface IAppointmentUseCase {
    AppointmentDTO getAppointmentById(int appointmentId);
    List<AppointmentDTO> getAllAppointments();
    AppointmentDTO insertAppointment(AppointmentDTO appointmentDTO);
    boolean updateAppointment(AppointmentDTO appointmentDTO);
    boolean deleteAppointment(int appointmentId);
    List<AppointmentDTO> getAppointmentsByPetId(int petId);
    List<AppointmentDTO> getAppointmentsByVeterinarianId(int vetId);
}
