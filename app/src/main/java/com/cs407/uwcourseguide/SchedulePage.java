package com.cs407.uwcourseguide;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SchedulePage extends Fragment {
    private AppDatabase db;
    private ScheduleViewModel scheduleViewModel;
    private AutoCompleteTextView autoCompleteLocation;
    private EditText editTextClassName, editTextProfessor, editTextRoomNumber;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule_page, container, false);

        db = Room.databaseBuilder(getContext().getApplicationContext(), AppDatabase.class, "schedule-database").build();
        scheduleViewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);

        autoCompleteLocation = view.findViewById(R.id.autoCompleteLocation);
        editTextClassName = view.findViewById(R.id.courseName);
        editTextProfessor = view.findViewById(R.id.professorName);
        editTextRoomNumber = view.findViewById(R.id.editTextRoomNumber);

        // Load locations into the AutoCompleteTextView from JSON file
        List<String> locations = loadLocationsFromJsonFile();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, locations);
        autoCompleteLocation.setAdapter(adapter);
        autoCompleteLocation.setThreshold(1); // Start filtering from one character

        // Handle save button click
        view.findViewById(R.id.submitSchedule).setOnClickListener(v -> saveSchedule());

        return view;
    }

    private List<String> loadLocationsFromJsonFile() {
        List<String> locations = new ArrayList<>();
        try {
            InputStream is = getContext().getAssets().open("locations.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
            reader.close();
            JSONArray jsonArray = new JSONArray(json.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                locations.add(jsonArray.getString(i));
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return locations;
    }

    private void saveSchedule() {
        String className = editTextClassName.getText().toString();
        String professor = editTextProfessor.getText().toString();
        String location = autoCompleteLocation.getText().toString();
        String roomNumber = editTextRoomNumber.getText().toString();

        ScheduleEntity schedule = new ScheduleEntity();
        schedule.className = className;
        schedule.professor = professor;
        schedule.location = location;
        schedule.roomNumber = roomNumber;

        new Thread(() -> db.scheduleDao().insert(schedule)).start();
    }
}