package com.cs407.uwcourseguide;

import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.Firebase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HomePage extends Fragment implements ValueEventListener {
    private final Executor executor = Executors.newSingleThreadExecutor();
    private boolean alreadyRetrievedData = false;
    private List<String> courseDescription = new ArrayList<>();
    Map<String, Object> courseToDescription = new HashMap<>();

    public HomePage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home_page, container, false);

        AutoCompleteTextView autoCompleteTextView = rootView.findViewById(R.id.autoCompleteTextView);

        // run data retrieval in background thread
        if (!alreadyRetrievedData){
            executor.execute(() -> {
                fetchDataFromDatabase(autoCompleteTextView);
            });
        }

        Button courseButton = rootView.findViewById(R.id.btnCourses);
        //courseButton.setOnClickListener(view -> ((MainActivity) requireActivity()).goToLoginPage());
        courseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CoursesFragment coursesFragment = new CoursesFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, CoursesFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("showing Course fragment")
                        .commit();
            }
        });
        Button profButton = rootView.findViewById(R.id.btnProfessor);
        profButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CoursesFragment coursesFragment = new CoursesFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, ProfessorsFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("showing Course fragment")
                        .commit();
            }
        });

        return rootView;
    }

    // DATASNAPSHOT:DataSnapshot { key = 1, value = {last_taught=Spring 2015, number=1, cumulative_gpa=null, credits=1 credit., crosslist_subjects=null, repeatable=Yes, unlimited number of completions, description=Full-time off-campus work experience which combines classroom theory with practical knowledge of operations to provide students with a background upon which to base a professional career. Students receive credit only for the term in which they are actively enrolled and working. The same work experience may not count towards credit in A A E 399. Students must have a declared major in Agricultural and Applied Economics or Agricultural Business Management and will require consent of the supervising instructor and academic advisor. , subject_abbrev=A A E, title=COOPERATIVE EDUCATION/CO-OP IN AGRICULTURAL & APPLIED ECONOMICS, course_designation=Workplace - Workplace Experience Course, full_subject_name=Agricultural and Applied Economics, requisites=Consent of instructor} }
    public void fetchDataFromDatabase(AutoCompleteTextView autoCompleteTextView){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("courses");
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
//                        Log.d("DataSnapshot", "~~~~~~~~~~~~~~~~~~~~~~~DATASNAPSHOT:" + courseChildren+ "~~~~~~~~~~~~~~~~~~~~");
//                        System.exit(1);
                        courseToDescription.put(courseChildren.getKey(), courseChildren.getValue());
                        coursesList.add(courseChildren.child("subject_abbrev").getValue(String.class) + " " + courseChildren.getKey());
                    }
                }

                alreadyRetrievedData = true;

                Log.d("CourseInfo", "~~~~~~~~~~~~~~~~~~~~~~~COURSES RETRIEVED~~~~~~~~~~~~~~~~~~~~");

                getActivity().runOnUiThread(() -> {
                    populateAutoCompleteTextView(coursesList, autoCompleteTextView);
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }

    private void populateAutoCompleteTextView(List<String> titles, AutoCompleteTextView autoCompleteTextView) {
        // Create an ArrayAdapter using the string array and a default layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, titles);

        // Apply the adapter to the AutoCompleteTextView
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedCourse = autoCompleteTextView.getText().toString();
                Log.d("CourseInfo", "~~~~~~~~~~~~~~~~~~~~~~~COURSES SELECTED:" + selectedCourse + "~~~~~~~~~~~");
                //courseDescription.add(selectedCourse);
                Object value = courseToDescription.get(selectedCourse);
                CoursesFragment coursesFragment = new CoursesFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("courseList", (Serializable) value);
                coursesFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, coursesFragment)
                        .setReorderingAllowed(true)
                        .addToBackStack("showing Course fragment")
                        .commit();
            }
        });
    }
}
