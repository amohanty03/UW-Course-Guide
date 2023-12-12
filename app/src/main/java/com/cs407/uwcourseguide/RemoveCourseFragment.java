package com.cs407.uwcourseguide;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import java.util.List;
import java.util.stream.Collectors;

public class RemoveCourseFragment extends Fragment {

    private Spinner spinnerCourses;
    private ScheduleViewModel scheduleViewModel;
    private List<ScheduleEntity> courseList;
    private ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_remove_course, container, false);

        spinnerCourses = view.findViewById(R.id.spinnerCourses);
        Button btnRemoveCourse = view.findViewById(R.id.btnRemoveCourse);

        // Initialize Room Database
        AppDatabase db = Room.databaseBuilder(getContext().getApplicationContext(),
                AppDatabase.class, "schedule-database").build();

        // Initialize ViewModel with Factory
        ScheduleDao scheduleDao = db.scheduleDao();
        ScheduleViewModelFactory viewModelFactory = new ScheduleViewModelFactory(scheduleDao);
        scheduleViewModel = new ViewModelProvider(this, viewModelFactory).get(ScheduleViewModel.class);

        // Observe the LiveData from ViewModel
        scheduleViewModel.getSchedules().observe(getViewLifecycleOwner(), this::updateSpinner);

        btnRemoveCourse.setOnClickListener(v -> removeSelectedCourse());

        return view;
    }


    private void updateSpinner(List<ScheduleEntity> schedules) {
        courseList = schedules;
        List<String> courseNames = schedules.stream()
                .map(ScheduleEntity::getClassName)
                .collect(Collectors.toList());

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, courseNames);
        spinnerCourses.setAdapter(adapter);
    }

    private void removeSelectedCourse() {
        int position = spinnerCourses.getSelectedItemPosition();
        if (position >= 0 && position < courseList.size()) {
            ScheduleEntity selectedCourse = courseList.get(position);
            scheduleViewModel.deleteSchedule(selectedCourse);
            // Optionally, update spinner
        }
    }
}