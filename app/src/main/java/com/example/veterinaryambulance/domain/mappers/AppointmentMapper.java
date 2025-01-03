package com.example.veterinaryambulance.domain.mappers;

import com.example.veterinaryambulance.data.models.Appointment;
import com.example.veterinaryambulance.domain.models.AppointmentDTO;

import java.util.List;
import java.util.stream.Collectors;

public class AppointmentMapper {
    public static AppointmentDTO toDTO(Appointment appointment) {
        return new AppointmentDTO(
                appointment.getId(),
                appointment.getVeterinarianId(),
                appointment.getPetId(),
                appointment.getDate(),
                appointment.getTime(),
                appointment.getCaseDescription()
        );
    }

    public static Appointment toModel(AppointmentDTO appointmentDTO) {
        return new Appointment(
                appointmentDTO.getId(),
                appointmentDTO.getVeterinarianId(),
                appointmentDTO.getPetId(),
                appointmentDTO.getDate(),
                appointmentDTO.getTime(),
                appointmentDTO.getCaseDescription()
        );
    }

    public static List<AppointmentDTO> toDTO(List<Appointment> appointments) {
        return appointments.stream()
                .map(AppointmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static List<Appointment> toModel(List<AppointmentDTO> appointmentDTOs) {
        return appointmentDTOs.stream()
                .map(AppointmentMapper::toModel)
                .collect(Collectors.toList());
    }
}