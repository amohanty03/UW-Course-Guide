package com.cs407.uwcourseguide;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SchedulePage extends Fragment {

    private EditText courseName, professorName, courseTime, courseDays, courseLocation;
    private Button submitButton, viewScheduleButton;

    // Temporary storage for the schedule data
    private ArrayList<Map<String, String>> scheduleList = new ArrayList<>();

    public SchedulePage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule_page, container, false);

        // Initializing the input fields and buttons
        courseName = view.findViewById(R.id.courseName);
        professorName = view.findViewById(R.id.professorName);
        courseTime = view.findViewById(R.id.courseTime);
        courseDays = view.findViewById(R.id.courseDays);
        courseLocation = view.findViewById(R.id.courseLocation);
        submitButton = view.findViewById(R.id.submitSchedule);
        viewScheduleButton = view.findViewById(R.id.viewSchedule);

        // Set up listeners for the buttons
        setupButtonListeners();

        return view;
    }

    private void setupButtonListeners() {
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addScheduleEntry();
            }
        });

        viewScheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScheduleDisplayFragment displayFragment = new ScheduleDisplayFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("scheduleList", scheduleList);
                displayFragment.setArguments(bundle);

                // Use the ID 'flFragment' for fragment transactions
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.flFragment, displayFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void addScheduleEntry() {
        String course = courseName.getText().toString();
        String professor = professorName.getText().toString();
        String time = courseTime.getText().toString();
        String days = courseDays.getText().toString();
        String location = courseLocation.getText().toString();

        // Validate inputs
        if (course.isEmpty() || professor.isEmpty() || time.isEmpty() || days.isEmpty() || location.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Store the data
        Map<String, String> scheduleEntry = new HashMap<>();
        scheduleEntry.put("course", course);
        scheduleEntry.put("professor", professor);
        scheduleEntry.put("time", time);
        scheduleEntry.put("days", days);
        scheduleEntry.put("location", location);
        scheduleList.add(scheduleEntry);

        // Clear the input fields after adding
        courseName.setText("");
        professorName.setText("");
        courseTime.setText("");
        courseDays.setText("");
        courseLocation.setText("");

        Toast.makeText(getActivity(), "Schedule Added", Toast.LENGTH_SHORT).show();
    }

    private void navigateToScheduleDisplay() {
        ScheduleDisplayFragment displayFragment = new ScheduleDisplayFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("scheduleList", scheduleList);
        displayFragment.setArguments(bundle);

        // Replace this with your actual code to navigate to the new fragment
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.flFragment, displayFragment)
                .addToBackStack(null)
                .commit();
    }
}
