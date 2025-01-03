package com.example.veterinaryambulance.data.repositories;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.veterinaryambulance.data.datasources.databases.DatabaseHelper;
import com.example.veterinaryambulance.data.models.Appointment;
import com.example.veterinaryambulance.domain.repositories.IAppointmentRepository;

import java.util.ArrayList;
import java.util.List;

public class AppointmentRepository implements IAppointmentRepository {
    private static final String TABLE_APPOINTMENTS = "appointments";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_VETERINARIAN_ID = "veterinarian_id";
    private static final String COLUMN_PET_ID = "pet_id";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_CASE_DESCRIPTION = "case_description";

    private final DatabaseHelper dbHelper;

    public AppointmentRepository(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public Appointment getById(int id) {
        try (Cursor cursor = dbHelper.getReadableDatabase().query(
                TABLE_APPOINTMENTS,
                null,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        )) {
            return cursor.moveToFirst() ? mapCursorToAppointment(cursor) : null;
        }
    }

    @Override
    public List<Appointment> getAll() {
        List<Appointment> appointments = new ArrayList<>();
        try (Cursor cursor = dbHelper.getReadableDatabase().query(
                TABLE_APPOINTMENTS,
                null,
                null,
                null,
                null,
                null,
                null
        )) {
            while (cursor.moveToNext()) {
                appointments.add(mapCursorToAppointment(cursor));
            }
        }

        return appointments;
    }

    @Override
    public Appointment insert(Appointment appointment) {
        long result = dbHelper.getWritableDatabase().insert(TABLE_APPOINTMENTS, null, mapAppointmentToContentValues(appointment));
        if (result == -1) { return null; }

        appointment.setId((int) result);

        return appointment;
    }

    @Override
    public boolean update(Appointment appointment) {
        return dbHelper.getWritableDatabase().update(
                TABLE_APPOINTMENTS,
                mapAppointmentToContentValues(appointment),
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(appointment.getId())}
        ) > 0;
    }

    @Override
    public boolean delete(int id) {
        return dbHelper.getWritableDatabase().delete(
                TABLE_APPOINTMENTS,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}
        ) > 0;
    }

    @Override
    public List<Appointment> getAppointmentsByPetId(int petId) {
        List<Appointment> appointments = new ArrayList<>();
        try (Cursor cursor = dbHelper.getReadableDatabase().query(
                TABLE_APPOINTMENTS,
                null,
                COLUMN_PET_ID + " = ?",
                new String[]{String.valueOf(petId)},
                null,
                null,
                null
        )) {
            while (cursor.moveToNext()) {
                appointments.add(mapCursorToAppointment(cursor));
            }
        }
        return appointments;
    }

    @Override
    public List<Appointment> getAppointmentsByVeterinarianId(int vetId) {
        List<Appointment> appointments = new ArrayList<>();
        try (Cursor cursor = dbHelper.getReadableDatabase().query(
                TABLE_APPOINTMENTS,
                null,
                COLUMN_VETERINARIAN_ID + " = ?",
                new String[]{String.valueOf(vetId)},
                null,
                null,
                null
        )) {
            while (cursor.moveToNext()) {
                appointments.add(mapCursorToAppointment(cursor));
            }
        }
        return appointments;
    }

    private Appointment mapCursorToAppointment(Cursor cursor) {
        return new Appointment(
                cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_VETERINARIAN_ID)),
                cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PET_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CASE_DESCRIPTION))
        );
    }

    private ContentValues mapAppointmentToContentValues(Appointment appointment) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_VETERINARIAN_ID, appointment.getVeterinarianId());
        values.put(COLUMN_PET_ID, appointment.getPetId());
        values.put(COLUMN_DATE, appointment.getDate());
        values.put(COLUMN_TIME, appointment.getTime());
        values.put(COLUMN_CASE_DESCRIPTION, appointment.getCaseDescription());
        return values;
    }
}