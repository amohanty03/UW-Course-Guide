package com.cs407.uwcourseguide;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomePage extends AppCompatActivity { //extends Fragment {

    public HomePage() {
        // Required empty public constructor
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home_page);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, new HomePageFragment())
                    .commit();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();

        Button firstFragmentButton = findViewById(R.id.btnCourses);
        firstFragmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, CourseFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("showing Courses")
                        .commit();
            }
        });

        Button secondFragmentButton = findViewById(R.id.btnProfessor);
        secondFragmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, ProfessorFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("showing Professors")
                        .commit();
            }
        });

        Button addButton = findViewById(R.id.btnAdd);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add new review
            }
        });
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_page, container, false);
    }
}