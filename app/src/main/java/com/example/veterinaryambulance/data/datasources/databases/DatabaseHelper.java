package com.example.veterinaryambulance.data.datasources.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Name and Version
    private static final String DATABASE_NAME = "veterinary_ambulance.db";
    private static final int DATABASE_VERSION = 3;

    // Table Names
    private static final String TABLE_VETERINARIANS = "veterinarians";
    private static final String TABLE_PETS = "pets";
    private static final String TABLE_APPOINTMENTS = "appointments";

    // Common Column Names
    private static final String COLUMN_ID = "id";

    // Veterinarians Table Columns
    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_POSITION = "position";
    private static final String COLUMN_CONTACT = "contact";

    // Pets Table Columns
    private static final String COLUMN_PET_NAME = "name";
    private static final String COLUMN_BREED = "breed";
    private static final String COLUMN_AGE = "age";
    private static final String COLUMN_OWNER_NAME = "owner_name";
    private static final String COLUMN_OWNER_CONTACT = "owner_contact";

    // Appointments Table Columns
    private static final String COLUMN_VETERINARIAN_ID = "veterinarian_id";
    private static final String COLUMN_PET_ID = "pet_id";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_CASE_DESCRIPTION = "case_description";

    // Table Creation Statements
    private static final String CREATE_TABLE_VETERINARIANS = "CREATE TABLE " + TABLE_VETERINARIANS + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_FIRST_NAME + " TEXT NOT NULL, " +
            COLUMN_LAST_NAME + " TEXT NOT NULL, " +
            COLUMN_USERNAME + " TEXT NOT NULL, " +
            COLUMN_PASSWORD + " TEXT NOT NULL, " +
            COLUMN_POSITION + " TEXT NOT NULL, " +
            COLUMN_CONTACT + " TEXT NOT NULL);";

    private static final String CREATE_TABLE_PETS = "CREATE TABLE " + TABLE_PETS + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_PET_NAME + " TEXT NOT NULL, " +
            COLUMN_BREED + " TEXT NOT NULL, " +
            COLUMN_AGE + " INTEGER NOT NULL, " +
            COLUMN_OWNER_NAME + " TEXT NOT NULL, " +
            COLUMN_OWNER_CONTACT + " TEXT NOT NULL);";

    private static final String CREATE_TABLE_APPOINTMENTS = "CREATE TABLE " + TABLE_APPOINTMENTS + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_VETERINARIAN_ID + " INTEGER NOT NULL, " +
            COLUMN_PET_ID + " INTEGER NOT NULL, " +
            COLUMN_DATE + " TEXT NOT NULL, " +
            COLUMN_TIME + " TEXT NOT NULL, " +
            COLUMN_CASE_DESCRIPTION + " TEXT NOT NULL, " +
            "FOREIGN KEY (" + COLUMN_VETERINARIAN_ID + ") REFERENCES " + TABLE_VETERINARIANS + "(" + COLUMN_ID + "), " +
            "FOREIGN KEY (" + COLUMN_PET_ID + ") REFERENCES " + TABLE_PETS + "(" + COLUMN_ID + "));";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_VETERINARIANS);
        db.execSQL(CREATE_TABLE_PETS);
        db.execSQL(CREATE_TABLE_APPOINTMENTS);

        // Insert Default Veterinarian
        String defaultVeterinarians = "INSERT INTO " + TABLE_VETERINARIANS + " (" +
                COLUMN_FIRST_NAME + ", " +
                COLUMN_LAST_NAME + ", " +
                COLUMN_USERNAME + ", " +
                COLUMN_PASSWORD + ", " +
                COLUMN_POSITION + ", " +
                COLUMN_CONTACT + ") VALUES " +
                "('dr Nemanja', 'Bozilovic', 'drnemanja', 'password123', 'CEO - spec. dr vet.', '+38166321123'), " +
                "('James', 'Smith', 'jamess', 'password456', 'Senior Veterinarian', '+18005551235'), " +
                "('Sophia', 'Williams', 'sophiaw', 'password789', 'Veterinarian', '+18005551236'), " +
                "('Michael', 'Brown', 'michaelb', 'password321', 'Junior Veterinarian', '+18005551237'), " +
                "('Olivia', 'Davis', 'oliviad', 'password654', 'Emergency Specialist', '+18005551238');";
        db.execSQL(defaultVeterinarians);

        // Insert Default Pets
        String defaultPets = "INSERT INTO " + TABLE_PETS + " (" +
                COLUMN_PET_NAME + ", " +
                COLUMN_BREED + ", " +
                COLUMN_AGE + ", " +
                COLUMN_OWNER_NAME + ", " +
                COLUMN_OWNER_CONTACT + ") VALUES " +
                "('Bella', 'Golden Retriever', 3, 'John Doe', '+18005551345'), " +
                "('Charlie', 'Labrador Retriever', 5, 'Jane Smith', '+18005551346'), " +
                "('Max', 'Beagle', 2, 'Robert Brown', '+18005551347'), " +
                "('Luna', 'Siberian Husky', 4, 'Emily Davis', '+18005551348'), " +
                "('Rocky', 'German Shepherd', 6, 'Michael Johnson', '+18005551349');";
        db.execSQL(defaultPets);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPOINTMENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PETS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VETERINARIANS);
        onCreate(db);
    }
}