package com.example.veterinaryambulance.domain.usecases.implementation;

import com.example.veterinaryambulance.data.models.Appointment;
import com.example.veterinaryambulance.domain.mappers.AppointmentMapper;
import com.example.veterinaryambulance.domain.models.AppointmentDTO;
import com.example.veterinaryambulance.domain.repositories.IAppointmentRepository;
import com.example.veterinaryambulance.domain.usecases.interfaces.IAppointmentUseCase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class AppointmentUseCase implements IAppointmentUseCase {
    private final IAppointmentRepository appointmentRepository;

    public AppointmentUseCase(IAppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public AppointmentDTO getAppointmentById(int appointmentId) {
        Appointment appointment = appointmentRepository.getById(appointmentId);
        return appointment != null ? AppointmentMapper.toDTO(appointment) : null;
    }

    @Override
    public List<AppointmentDTO> getAllAppointments() {
        List<Appointment> appointments = appointmentRepository.getAll();

        List<Appointment> futureAppointments = appointments.stream()
                .filter(appointment -> {
                    String dateTimeString = appointment.getDate() + "T" + appointment.getTime();
                    try {
                        LocalDateTime appointmentDateTime = LocalDateTime.parse(dateTimeString);
                        return appointmentDateTime.isAfter(LocalDateTime.now());
                    } catch (DateTimeParseException e) {
                        e.printStackTrace();
                        return false;
                    }
                })
                .toList();

        return AppointmentMapper.toDTO(futureAppointments);
    }

    @Override
    public AppointmentDTO insertAppointment(AppointmentDTO appointmentDTO) {
        Appointment appointment = AppointmentMapper.toModel(appointmentDTO);
        validateAppointment(appointment, true);

        Appointment insertedAppointment = appointmentRepository.insert(appointment);

        return AppointmentMapper.toDTO(insertedAppointment);
    }

    @Override
    public boolean updateAppointment(AppointmentDTO appointmentDTO) {
        Appointment appointment = AppointmentMapper.toModel(appointmentDTO);
        validateAppointment(appointment, false);

        return appointmentRepository.update(appointment);
    }

    @Override
    public boolean deleteAppointment(int appointmentId) {
        Appointment appointment = appointmentRepository.getById(appointmentId);
        if (appointment == null) {
            throw new IllegalArgumentException("Appointment does not exist.");
        }

        LocalDateTime appointmentDateTime = parseDateTime(appointment.getDate(), appointment.getTime());
        LocalDateTime now = LocalDateTime.now();

        if (appointmentDateTime.isBefore(now) ||
                (appointmentDateTime.toLocalDate().equals(now.toLocalDate()) &&
                        appointmentDateTime.getHour() == now.getHour())) {
            throw new IllegalArgumentException("Appointments cannot be canceled during the current hour.");
        }

        return appointmentRepository.delete(appointmentId);
    }

    private void validateAppointment(Appointment appointment, boolean isInsert) {
        LocalDateTime appointmentDateTime = parseDateTime(appointment.getDate(), appointment.getTime());

        if (appointmentDateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Appointment must be scheduled in the future.");
        }

        List<Appointment> existingAppointments = appointmentRepository.getAll();
        for (Appointment existing : existingAppointments) {
            if (!isInsert && existing.getId() == appointment.getId()) {
                continue;
            }

            if (existing.getVeterinarianId() == appointment.getVeterinarianId()) {
                LocalDateTime existingDateTime = parseDateTime(existing.getDate(), existing.getTime());

                if (existingDateTime.equals(appointmentDateTime)) {
                    throw new IllegalArgumentException("An appointment already exists for this veterinarian at the specified date and time.");
                }

                if (appointmentDateTime.isBefore(existingDateTime.plusHours(1)) &&
                        appointmentDateTime.isAfter(existingDateTime.minusHours(1))) {
                    throw new IllegalArgumentException("Appointments must be scheduled at least 1 hour apart.");
                }
            }
        }
    }

    private LocalDateTime parseDateTime(String date, String time) {
        return LocalDateTime.parse(date + "T" + time, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    @Override
    public List<AppointmentDTO> getAppointmentsByPetId(int petId) {
        return AppointmentMapper.toDTO(appointmentRepository.getAppointmentsByPetId(petId));
    }

    @Override
    public List<AppointmentDTO> getAppointmentsByVeterinarianId(int vetId) {
        return AppointmentMapper.toDTO(appointmentRepository.getAppointmentsByVeterinarianId(vetId));
    }
}