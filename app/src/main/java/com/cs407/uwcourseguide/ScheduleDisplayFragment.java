package com.cs407.uwcourseguide;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Map;

public class ScheduleDisplayFragment extends Fragment {

    private ArrayList<Map<String, String>> scheduleList;

    public ScheduleDisplayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            scheduleList = (ArrayList<Map<String, String>>) getArguments().getSerializable("scheduleList");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule_display, container, false);
        TextView scheduleTextView = view.findViewById(R.id.scheduleTextView);

        StringBuilder scheduleText = new StringBuilder();
        for (Map<String, String> entry : scheduleList) {
            scheduleText.append("Class: ").append(entry.get("course")).append("\n");
            scheduleText.append("Professor: ").append(entry.get("professor")).append("\n");
            scheduleText.append("Time: ").append(entry.get("time")).append("\n");
            scheduleText.append("Days: ").append(entry.get("days")).append("\n");
            scheduleText.append("Location: ").append(entry.get("location")).append("\n\n");
        }

        scheduleTextView.setText(scheduleText.toString());
        return view;
    }
}
