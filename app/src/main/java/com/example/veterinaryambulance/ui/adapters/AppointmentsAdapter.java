package com.example.veterinaryambulance.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterinaryambulance.R;
import com.example.veterinaryambulance.domain.models.AppointmentDTO;
import com.example.veterinaryambulance.domain.usecases.implementation.PetUseCase;

import java.util.List;

public class AppointmentsAdapter extends RecyclerView.Adapter<AppointmentsAdapter.AppointmentViewHolder> {

    private List<AppointmentDTO> appointments;
    private final PetUseCase petUseCase;

    public AppointmentsAdapter(List<AppointmentDTO> appointments, PetUseCase petUseCase) {
        this.appointments = appointments;
        this.petUseCase = petUseCase;
    }

    @NonNull
    @Override
    public AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appointment, parent, false);
        return new AppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentViewHolder holder, int position) {
        AppointmentDTO appointment = appointments.get(position);

        if (appointment.getPetName() == null || appointment.getPetName().isEmpty()) {
            String petName = petUseCase.getPetById(appointment.getPetId()).getName();
            appointment.setPetName(petName);
        }

        String details = String.format(
                "ID: %s\nPet: %s\nDate: %s\nTime: %s\nIssue: %s",
                appointment.getId(),
                appointment.getPetName() != null ? appointment.getPetName() : "Unknown",
                appointment.getDate(),
                appointment.getTime(),
                appointment.getCaseDescription()
        );
        holder.bind(details);
    }

    @Override
    public int getItemCount() {
        return appointments != null ? appointments.size() : 0;
    }

    public void updateAppointments(Context context, List<AppointmentDTO> updatedAppointments) {
        this.appointments = updatedAppointments;
        notifyDataSetChanged();
    }

    static class AppointmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvAppointmentDetails;

        public AppointmentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAppointmentDetails = itemView.findViewById(R.id.tvAppointmentDetails);
        }

        public void bind(String details) {
            tvAppointmentDetails.setText(details);
        }
    }
}