package com.cs407.uwcourseguide;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;
import java.util.List;

public class ScheduleDisplayFragment extends Fragment {

    private TextView scheduleTextView;
    private ScheduleViewModel scheduleViewModel;

    public ScheduleDisplayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule_display, container, false);
        scheduleTextView = view.findViewById(R.id.scheduleTextView);
        Button btnClearSchedule = view.findViewById(R.id.btnClearSchedule);


        // Initialize Room Database and ViewModel
        AppDatabase db = Room.databaseBuilder(getContext().getApplicationContext(),
                AppDatabase.class, "schedule-database").build();
        ScheduleDao scheduleDao = db.scheduleDao();
        ScheduleViewModelFactory viewModelFactory = new ScheduleViewModelFactory(scheduleDao);
        scheduleViewModel = new ViewModelProvider(this, viewModelFactory).get(ScheduleViewModel.class);

        // Observe the LiveData from ViewModel
        scheduleViewModel.getSchedules().observe(getViewLifecycleOwner(), schedules -> {
            displaySchedules(schedules);
        });
        btnClearSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSchedule();
            }
        });

        return view;
    }

    private void displaySchedules(List<ScheduleEntity> schedules) {

        StringBuilder scheduleText = new StringBuilder();
        for (ScheduleEntity schedule : schedules) {
            scheduleText.append("Class: ").append(schedule.getClassName()).append("\n");
            scheduleText.append("Professor: ").append(schedule.getProfessor()).append("\n");
            scheduleText.append("Location: ").append(schedule.getLocation()).append("\n");
            scheduleText.append("Room Number: ").append(schedule.getRoomNumber()).append("\n\n");
        }
        scheduleTextView.setText(scheduleText.toString());
    }

    private void clearSchedule() {
        // Call the method in ViewModel to clear the schedule
        scheduleViewModel.clearAllSchedules();
    }
}

