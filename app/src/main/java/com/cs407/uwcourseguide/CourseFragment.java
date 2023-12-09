package com.cs407.uwcourseguide;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class CourseFragment extends Fragment {

    private EditText editTextSearchCourses;
    //private DatabaseHelper databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.courses_fragment, container, false);

        editTextSearchCourses = view.findViewById(R.id.courseSearch);
        //databaseHelper = new DatabaseHelper(getContext());

        editTextSearchCourses.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Implement search logic for courses
                searchCourses(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return view;
    }

    private void searchCourses(String query) {
        // Retrieve data from database that matches the query
        //List<Course> courses = databaseHelper.searchCourses(query);
    }
}