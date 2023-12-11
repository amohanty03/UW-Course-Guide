package com.cs407.uwcourseguide;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CoursesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CoursesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private List<String> courseList = new ArrayList<>();
    AutoCompleteTextView courseAutoCompleteTextView;
    ArrayAdapter<String> courseAdapter;
    TextView courseTitle;
    TextView courseDesc;
    TextView cGPATitle;
    TextView cGPA;
    TextView creditsTitle;
    TextView credits;
    TextView requisitesTitle;
    TextView requisites;
    TextView courseDesigTitle;
    TextView courseDesig;
    TextView repeatCreditTitle;
    TextView repeatCredit;
    TextView lastTaughtTitle;
    TextView lastTaught;
    TextView crossListTitle;
    TextView crossList;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CoursesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CoursesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CoursesFragment newInstance(String param1, String param2) {
        CoursesFragment fragment = new CoursesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_courses, container, false);
        courseList = MainActivity.coursesList;
        courseAutoCompleteTextView = view.findViewById(R.id.courseTextView);

        // Update AutoCompleteTextViews with the fetched data
        if (courseAutoCompleteTextView != null) {
            courseAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, courseList);
            courseAutoCompleteTextView.setAdapter(courseAdapter);

            courseTitle = view.findViewById(R.id.courseTitle);
            courseDesc = view.findViewById(R.id.courseDesc);
            cGPATitle = view.findViewById(R.id.gpaTitle);
            cGPA = view.findViewById(R.id.cgpa);
            creditsTitle = view.findViewById(R.id.creditsTitle);
            credits = view.findViewById(R.id.credits);
            requisitesTitle = view.findViewById(R.id.reqsTitle);
            requisites = view.findViewById(R.id.reqs);
            courseDesigTitle = view.findViewById(R.id.coursedesigTitle);
            courseDesig = view.findViewById(R.id.coursedesig);
            repeatCreditTitle = view.findViewById(R.id.repeatTitle);
            repeatCredit = view.findViewById(R.id.repeat);
            lastTaughtTitle = view.findViewById(R.id.lastTaughtTitle);
            lastTaught = view.findViewById(R.id.lastTaught);
            crossListTitle = view.findViewById(R.id.crossListTitle);
            crossList = view.findViewById(R.id.crossList);
            courseAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Handle the item click here
                    String selectedCourse = (String) parent.getItemAtPosition(position);
                    Course targetCourse = MainActivity.coursesDescMap.get(selectedCourse);
                    String error = "Something went wrong!";
                    if (targetCourse != null) {
                        courseTitle.setText(targetCourse.getCourseTitle() + ": " + targetCourse.getTitle());
                        courseDesc.setText(targetCourse.getCourseDescription());
                        cGPATitle.setText("Cumulative GPA");
                        cGPA.setText(targetCourse.getcGPA());
                        creditsTitle.setText("Credits");
                        credits.setText(targetCourse.getCredits());
                        requisitesTitle.setText("Requisities");
                        requisites.setText(targetCourse.getRequisites());
                        courseDesigTitle.setText("Course Designation");
                        courseDesig.setText(targetCourse.getCourseDesignation());
                        repeatCreditTitle.setText("Repeatable for Credit");
                        repeatCredit.setText(targetCourse.getRepeatCredit());
                        lastTaughtTitle.setText("Last Taught");
                        lastTaught.setText(targetCourse.getLastTaught());
                        crossListTitle.setText("Cross-listed Subjects");
                        crossList.setText(targetCourse.getCrossList());
                    } else {
                        courseTitle.setText(error);
                    }
                }
            });
        }
        return view;
    }
}