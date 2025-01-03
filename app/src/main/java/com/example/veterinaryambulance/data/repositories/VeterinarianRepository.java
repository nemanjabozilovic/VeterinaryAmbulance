package com.example.veterinaryambulance.data.repositories;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.veterinaryambulance.data.datasources.databases.DatabaseHelper;
import com.example.veterinaryambulance.data.models.Veterinarian;
import com.example.veterinaryambulance.domain.repositories.IVeterinarianRepository;

import java.util.ArrayList;
import java.util.List;

public class VeterinarianRepository implements IVeterinarianRepository {
    private static final String TABLE_VETERINARIANS = "veterinarians";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_POSITION = "position";
    private static final String COLUMN_CONTACT = "contact";

    private final DatabaseHelper dbHelper;

    public VeterinarianRepository(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public Veterinarian getById(int id) {
        try (Cursor cursor = dbHelper.getReadableDatabase().query(
                TABLE_VETERINARIANS,
                null,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        )) {
            return cursor.moveToFirst() ? mapCursorToVeterinarian(cursor) : null;
        }
    }

    @Override
    public List<Veterinarian> getAll() {
        List<Veterinarian> veterinarians = new ArrayList<>();
        try (Cursor cursor = dbHelper.getReadableDatabase().query(
                TABLE_VETERINARIANS,
                null,
                null,
                null,
                null,
                null,
                null
        )) {
            while (cursor != null && cursor.moveToNext()) {
                veterinarians.add(mapCursorToVeterinarian(cursor));
            }
        }

        return veterinarians;
    }

    @Override
    public Veterinarian insert(Veterinarian veterinarian) {
        long result = dbHelper.getWritableDatabase().insert(TABLE_VETERINARIANS, null, mapVeterinarianToContentValues(veterinarian));
        if (result == -1) {
            return null;
        }

        veterinarian.setId((int) result);

        return veterinarian;
    }

    @Override
    public boolean update(Veterinarian veterinarian) {
        return dbHelper.getWritableDatabase().update(
                TABLE_VETERINARIANS,
                mapVeterinarianToContentValues(veterinarian),
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(veterinarian.getId())}
        ) > 0;
    }

    @Override
    public boolean delete(int id) {
        return dbHelper.getWritableDatabase().delete(
                TABLE_VETERINARIANS,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}
        ) > 0;
    }

    @Override
    public Veterinarian login(String username, String password) {
        try (Cursor cursor = dbHelper.getReadableDatabase().query(
                TABLE_VETERINARIANS,
                null,
                COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?",
                new String[]{username, password},
                null,
                null,
                null
        )) {
            return cursor.moveToFirst() ? mapCursorToVeterinarian(cursor) : null;
        }
    }

    private Veterinarian mapCursorToVeterinarian(Cursor cursor) {
        return new Veterinarian(
                cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRST_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LAST_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_POSITION)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTACT))
        );
    }

    private ContentValues mapVeterinarianToContentValues(Veterinarian veterinarian) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRST_NAME, veterinarian.getFirstName());
        values.put(COLUMN_LAST_NAME, veterinarian.getLastName());
        values.put(COLUMN_USERNAME, veterinarian.getUsername());
        values.put(COLUMN_PASSWORD, veterinarian.getPassword());
        values.put(COLUMN_POSITION, veterinarian.getPosition());
        values.put(COLUMN_CONTACT, veterinarian.getContact());

        return values;
    }
}