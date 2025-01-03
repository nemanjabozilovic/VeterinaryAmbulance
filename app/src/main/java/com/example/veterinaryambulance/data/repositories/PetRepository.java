package com.example.veterinaryambulance.data.repositories;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.veterinaryambulance.data.datasources.databases.DatabaseHelper;
import com.example.veterinaryambulance.data.models.Pet;
import com.example.veterinaryambulance.domain.repositories.IPetRepository;

import java.util.ArrayList;
import java.util.List;

public class PetRepository implements IPetRepository {
    private static final String TABLE_PETS = "pets";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_BREED = "breed";
    private static final String COLUMN_AGE = "age";
    private static final String COLUMN_OWNER_NAME = "owner_name";
    private static final String COLUMN_OWNER_CONTACT = "owner_contact";

    private final DatabaseHelper dbHelper;

    public PetRepository(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public Pet getById(int id) {
        try (Cursor cursor = dbHelper.getReadableDatabase().query(
                TABLE_PETS,
                null,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        )) {
            return cursor != null && cursor.moveToFirst() ? mapCursorToPet(cursor) : null;
        }
    }

    @Override
    public List<Pet> getAll() {
        List<Pet> pets = new ArrayList<>();
        try (Cursor cursor = dbHelper.getReadableDatabase().query(
                TABLE_PETS,
                null,
                null,
                null,
                null,
                null,
                null
        )) {
            while (cursor != null && cursor.moveToNext()) {
                pets.add(mapCursorToPet(cursor));
            }
        }

        return pets;
    }

    @Override
    public Pet insert(Pet pet) {
        long result = dbHelper.getWritableDatabase().insert(TABLE_PETS, null, mapPetToContentValues(pet));
        if (result == -1) { return null; }
        pet.setId((int) result);

        return pet;
    }

    @Override
    public boolean update(Pet pet) {
        return dbHelper.getWritableDatabase().update(
                TABLE_PETS,
                mapPetToContentValues(pet),
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(pet.getId())}
        ) > 0;
    }

    @Override
    public boolean delete(int id) {
        return dbHelper.getWritableDatabase().delete(
                TABLE_PETS,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}
        ) > 0;
    }

    private Pet mapCursorToPet(Cursor cursor) {
        return new Pet(
                cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BREED)),
                cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AGE)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OWNER_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OWNER_CONTACT))
        );
    }

    private ContentValues mapPetToContentValues(Pet pet) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, pet.getName());
        values.put(COLUMN_BREED, pet.getBreed());
        values.put(COLUMN_AGE, pet.getAge());
        values.put(COLUMN_OWNER_NAME, pet.getOwnerName());
        values.put(COLUMN_OWNER_CONTACT, pet.getOwnerContact());
        return values;
    }
}