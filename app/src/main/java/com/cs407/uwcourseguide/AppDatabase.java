package com.cs407.uwcourseguide;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ScheduleEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ScheduleDao scheduleDao();
}