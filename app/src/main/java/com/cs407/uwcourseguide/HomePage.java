package com.cs407.uwcourseguide;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.Firebase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HomePage extends Fragment implements ValueEventListener {
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/uw_course_guide";
    private static final String DB_USER = "root@localhost";
    private static final String DB_PASSWORD = "saltyhayonwfneals@&13459";

    public HomePage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home_page, container, false);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("courses");

        AutoCompleteTextView autoCompleteTextView = rootView.findViewById(R.id.autoCompleteTextView);
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                List<String> coursesList = new ArrayList<>();
                for (DataSnapshot courseSnapshot : dataSnapshot.getChildren()) {
                    // Iterate through each course under the "courses" node
                    // Get course number and name
                    for (DataSnapshot courseChildren : courseSnapshot.getChildren()) {
                       coursesList.add(courseChildren.child("subject_abbrev").getValue(String.class) + " " + courseChildren.getKey());
                    }
                }
//                for (String courses : coursesList) {
//                    Log.d("CourseInfo", "Course Number: " + courses);
//                }
                populateAutoCompleteTextView(coursesList, autoCompleteTextView);
                //populateSpinner(coursesList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
        return rootView;
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }

//    private void populateSpinner(List<String> titles) {
//        Spinner spinner = getView().findViewById(R.id.a);
//
//        // Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, titles);
//
//        // Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        // Apply the adapter to the spinner
//        spinner.setAdapter(adapter);
//    }

    private void populateAutoCompleteTextView(List<String> titles, AutoCompleteTextView autoCompleteTextView) {
        // Create an ArrayAdapter using the string array and a default layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, titles);

        // Apply the adapter to the AutoCompleteTextView
        autoCompleteTextView.setAdapter(adapter);
    }
}
