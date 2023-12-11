package com.cs407.uwcourseguide;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class ScheduleViewModel extends ViewModel {
    private LiveData<List<ScheduleEntity>> schedules;
    private final ScheduleDao scheduleDao;

    public ScheduleViewModel(ScheduleDao scheduleDao) {
        this.scheduleDao = scheduleDao;
        schedules = scheduleDao.getAll();
    }

    public LiveData<List<ScheduleEntity>> getSchedules() {
        return schedules;
    }

    public void insertSchedule(ScheduleEntity schedule) {
        new Thread(() -> scheduleDao.insert(schedule)).start();
    }

    public void deleteSchedule(ScheduleEntity schedule) {
        new Thread(() -> scheduleDao.delete(schedule)).start();
    }
}