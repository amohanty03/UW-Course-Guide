package com.cs407.uwcourseguide;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface ScheduleDao {
    @Query("SELECT * FROM ScheduleEntity")
    LiveData<List<ScheduleEntity>> getAll();

    @Insert
    void insert(ScheduleEntity schedule);

    @Delete
    void delete(ScheduleEntity schedule);
}