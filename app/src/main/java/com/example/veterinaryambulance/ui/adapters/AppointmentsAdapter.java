package com.example.veterinaryambulance.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterinaryambulance.R;
import com.example.veterinaryambulance.domain.models.AppointmentDTO;
import com.example.veterinaryambulance.domain.usecases.interfaces.IPetUseCase;

import java.util.ArrayList;
import java.util.List;

public class AppointmentsAdapter extends RecyclerView.Adapter<AppointmentsAdapter.ViewHolder> {
    private final List<AppointmentDTO> appointments;
    private final IPetUseCase petUseCase;

    public AppointmentsAdapter(List<AppointmentDTO> appointments, IPetUseCase petUseCase) {
        this.appointments = appointments != null ? appointments : new ArrayList<>();
        this.petUseCase = petUseCase;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_appointment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppointmentDTO appointment = appointments.get(position);

        String dateTime = "Date & Time: " + appointment.getDate() + " " + appointment.getTime();
        appointment.setPetName(petUseCase.getPetById(appointment.getPetId()).getName());

        String details = "ID: " + appointment.getId() +
                "\nPet Name: " + appointment.getPetName() +
                "\n" + dateTime +
                "\nCase: " + appointment.getCaseDescription();

        holder.tvDetails.setText(details);
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    public void updateAppointments(Context context, List<AppointmentDTO> newAppointments) {
        appointments.clear();
        if (newAppointments != null) {
            appointments.addAll(newAppointments);
        }
        notifyDataSetChanged();

        Toast.makeText(context, "Appointments updated: " + appointments.size() + " items.", Toast.LENGTH_SHORT).show();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDetails;

        ViewHolder(View itemView) {
            super(itemView);
            tvDetails = itemView.findViewById(R.id.tvAppointmentDetails);
        }
    }
}