package com.cs407.uwcourseguide;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ScheduleViewModelFactory implements ViewModelProvider.Factory {
    private final ScheduleDao scheduleDao;

    public ScheduleViewModelFactory(ScheduleDao scheduleDao) {
        this.scheduleDao = scheduleDao;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ScheduleViewModel.class)) {
            return (T) new ScheduleViewModel(scheduleDao);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
