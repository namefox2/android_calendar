package com.example.calendar;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {listDbItem.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DBdao itemDao();
    private static AppDatabase instance;
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                    .addMigrations(MIGRATION_1_2)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE items ADD COLUMN newColumnName TEXT");
        }
    };
}