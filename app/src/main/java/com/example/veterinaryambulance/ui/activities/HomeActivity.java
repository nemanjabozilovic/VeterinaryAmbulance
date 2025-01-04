package com.example.veterinaryambulance.ui.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veterinaryambulance.R;
import com.example.veterinaryambulance.data.datasources.databases.DatabaseHelper;
import com.example.veterinaryambulance.data.repositories.AppointmentRepository;
import com.example.veterinaryambulance.data.repositories.PetRepository;
import com.example.veterinaryambulance.domain.models.AppointmentDTO;
import com.example.veterinaryambulance.domain.models.PetDTO;
import com.example.veterinaryambulance.domain.models.VeterinarianDTO;
import com.example.veterinaryambulance.domain.usecases.implementation.AppointmentUseCase;
import com.example.veterinaryambulance.domain.usecases.implementation.PetUseCase;
import com.example.veterinaryambulance.ui.adapters.AppointmentsAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private TextView tvWelcome, tvDateTime;
    private RecyclerView rvAppointments;
    private Spinner spinnerPet;
    private EditText etProblemDescription, etCancelAppointmentId, etSearchAppointments;
    private Button btnConfirmAppointment, btnResetAppointmentCreation, btnCancelAppointment, btnSortAppointments;

    private VeterinarianDTO currentVeterinarian;
    private AppointmentUseCase appointmentUseCase;
    private PetUseCase petUseCase;
    private AppointmentsAdapter adapter;

    private String selectedDate, selectedTime;
    private List<PetDTO> petList;
    private PetDTO selectedPet;

    private List<AppointmentDTO> appointmentList;
    private List<AppointmentDTO> originalListOfAppointments;

    private boolean isSorted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (!initializeCurrentVeterinarian()) {
            return;
        }

        initializeUIElements();
        initializeDependencies();
        setupUI();
        setupListeners();
        loadAppointments();
        loadPets();
    }

    private boolean initializeCurrentVeterinarian() {
        currentVeterinarian = getIntent().getParcelableExtra("veterinarianDTO");
        if (currentVeterinarian == null) {
            Toast.makeText(this, "No user data found", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void initializeUIElements() {
        tvWelcome = findViewById(R.id.tvWelcome);
        tvDateTime = findViewById(R.id.tvDateTime);
        rvAppointments = findViewById(R.id.rvAppointments);
        spinnerPet = findViewById(R.id.spinnerPet);
        etProblemDescription = findViewById(R.id.etProblemDescription);
        etCancelAppointmentId = findViewById(R.id.etCancelAppointmentId);
        etSearchAppointments = findViewById(R.id.etSearchAppointments);
        btnConfirmAppointment = findViewById(R.id.btnConfirmAppointment);
        btnResetAppointmentCreation = findViewById(R.id.btnResetAppointmentCreation);
        btnCancelAppointment = findViewById(R.id.btnCancelAppointment);
        btnSortAppointments = findViewById(R.id.btnSortAppointments);

        btnCancelAppointment.setEnabled(false);
    }

    private void initializeDependencies() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        appointmentUseCase = new AppointmentUseCase(new AppointmentRepository(dbHelper));
        petUseCase = new PetUseCase(new PetRepository(dbHelper), new AppointmentRepository(dbHelper));
    }

    private void setupUI() {
        displayWelcomeMessage();
        setupRecyclerView();
    }

    private void displayWelcomeMessage() {
        String fullName = String.format("%s %s", currentVeterinarian.getFirstName(), currentVeterinarian.getLastName());
        tvWelcome.setText(getString(R.string.welcome_message, fullName));
    }

    private void setupRecyclerView() {
        rvAppointments.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AppointmentsAdapter(new ArrayList<>(), petUseCase);
        rvAppointments.setAdapter(adapter);
    }

    private void setupListeners() {
        tvDateTime.setOnClickListener(v -> openDateTimePicker());
        btnConfirmAppointment.setOnClickListener(v -> scheduleAppointment());
        btnResetAppointmentCreation.setOnClickListener(v -> resetAppointmentFields());
        btnCancelAppointment.setOnClickListener(v -> cancelAppointment());

        etCancelAppointmentId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnCancelAppointment.setEnabled(!s.toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        etSearchAppointments.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchAppointments(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnSortAppointments.setOnClickListener(v -> sortAppointmentsByDateTime());
    }

    private void openDateTimePicker() {
        Calendar calendar = Calendar.getInstance();

        new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    selectedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
                    new TimePickerDialog(this,
                            (timeView, hourOfDay, minute) -> {
                                selectedTime = String.format("%02d:%02d", hourOfDay, minute);
                                tvDateTime.setText(String.format("%sT%s", selectedDate, selectedTime));
                            },
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            true).show();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void loadAppointments() {
        appointmentList = appointmentUseCase.getAllAppointments();
        if (appointmentList != null && !appointmentList.isEmpty()) {
            originalListOfAppointments = new ArrayList<>(appointmentList);
            adapter.updateAppointments(this, appointmentList);
        }
    }

    private void loadPets() {
        petList = petUseCase.getAllPets();

        List<String> petNames = new ArrayList<>();
        petNames.add("Select a pet");
        if (petList != null && !petList.isEmpty()) {
            for (PetDTO pet : petList) {
                petNames.add(pet.getName());
            }
        } else {
            Toast.makeText(this, "No pets available", Toast.LENGTH_SHORT).show();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, petNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPet.setAdapter(adapter);

        spinnerPet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                if (position == 0) {
                    selectedPet = null;
                } else {
                    selectedPet = petList.get(position - 1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedPet = null;
            }
        });
    }

    private void searchAppointments(String query) {
        if (query == null || query.isEmpty()) {
            adapter.updateAppointments(this, appointmentList);
            return;
        }

        List<AppointmentDTO> filteredAppointments = new ArrayList<>();
        for (AppointmentDTO appointment : appointmentList) {
            if (appointment.getPetName() != null && appointment.getPetName().toLowerCase().contains(query.toLowerCase())) {
                filteredAppointments.add(appointment);
            }
        }

        adapter.updateAppointments(this, filteredAppointments);
    }

    private void sortAppointmentsByDateTime() {
        if (appointmentList == null || appointmentList.isEmpty()) return;

        if (isSorted) {
            appointmentList = new ArrayList<>(originalListOfAppointments);
        } else {
            appointmentList.sort(Comparator.comparing(AppointmentDTO::getDate)
                    .thenComparing(AppointmentDTO::getTime));
            Toast.makeText(this, "Appointments sorted by date and time", Toast.LENGTH_SHORT).show();
        }

        isSorted = !isSorted;
        adapter.updateAppointments(this, appointmentList);
    }

    private void scheduleAppointment() {
        if (selectedPet == null ||
                selectedDate == null ||
                selectedTime == null ||
                etProblemDescription.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please select a pet, set date/time and insert problem description.", Toast.LENGTH_SHORT).show();
            return;
        }

        String problemDescription = etProblemDescription.getText().toString();

        AppointmentDTO appointment = new AppointmentDTO();
        appointment.setVeterinarianId(currentVeterinarian.getId());
        appointment.setPetId(selectedPet.getId());
        appointment.setDate(selectedDate);
        appointment.setTime(selectedTime);
        appointment.setCaseDescription(problemDescription);

        try {
            AppointmentDTO newAppointment = appointmentUseCase.insertAppointment(appointment);

            if (newAppointment != null) {
                Toast.makeText(this, "Appointment created successfully", Toast.LENGTH_SHORT).show();
                resetAppointmentFields();
                loadAppointments();
                isSorted = false;
            } else {
                Toast.makeText(this, "Failed to create the appointment", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void resetAppointmentFields() {
        spinnerPet.setSelection(0);
        tvDateTime.setText("");
        selectedDate = null;
        selectedTime = null;
        etProblemDescription.setText("");
    }

    private void cancelAppointment() {
        String input = etCancelAppointmentId.getText().toString().trim();
        if (input.isEmpty()) {
            Toast.makeText(this, "Please enter an appointment ID", Toast.LENGTH_SHORT).show();
            return;
        }

        int appointmentId;
        try {
            appointmentId = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid appointment ID", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            boolean success = appointmentUseCase.deleteAppointment(appointmentId);
            if (success) {
                Toast.makeText(this, "Appointment canceled successfully", Toast.LENGTH_SHORT).show();
                etCancelAppointmentId.setText("");
                loadAppointments();
            } else {
                Toast.makeText(this, "Failed to cancel the appointment", Toast.LENGTH_SHORT).show();
            }
        }
        catch (IllegalArgumentException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}