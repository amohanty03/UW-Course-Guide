package com.cs407.uwcourseguide;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private Map<String, Object> courseToDescription;

    private Object courseInfos;

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
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
            //courseList = (ArrayList<String>) getArguments().getSerializable("courseList");
            //courseToDescription = (Map<String, Object>) getArguments().getSerializable("courseList");
            //courseInfos = ((Object) getArguments().getSerializable("courseList"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_courses, container, false);
        //TextView courseText = view.findViewById(R.id.textView12);
        //String firstCourse = courseList.get(0);
        String courseDescription = "";
        courseList = MainActivity.coursesList;

        AutoCompleteTextView courseAutoCompleteTextView = view.findViewById(R.id.courseTextView);

        // Update AutoCompleteTextViews with the fetched data
        if (courseAutoCompleteTextView != null) {
            Log.e("error", "come hereeeeeeeeeeeeeeeeeeeeeeeeeeeee2");
            ArrayAdapter<String> courseAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, courseList);
            courseAutoCompleteTextView.setAdapter(courseAdapter);
        }

//        for (Map.Entry<String, Object> entry: courseToDescription.entrySet()) {
//            String key = entry.getKey();
//            Object value = entry.getValue();

//        Map<String, Object> nestedMap = (Map<String, Object>) courseInfos;
//        String key = ((String) nestedMap.get("number"));
//        String course =  ( (String) nestedMap.get("subject_abbrev") + key);
//        String lastTaught = (String) nestedMap.get("last_taught");
//        String cumulativeGPA = (String) nestedMap.get("cumulative_gpa");
//        String credits = (String) nestedMap.get("credits");
//        String crosslist_subjects = (String) nestedMap.get("crosslist_subjects");
//        String repeatable = (String) nestedMap.get("repeatable");
//        String description = (String) nestedMap.get("description");
//        String title = (String) nestedMap.get("credits");
//        String course_designation = (String) nestedMap.get("course_designation");
//        String requisites = (String) nestedMap.get("requisites");
//        courseDescription =
//                String.format(
//                        "Course: %s\n\nTitle: %s\n\nDescription: %s\n\nCourse description: %s\n\nLast taught: %s\n\nCumulative GPA: %s\n\nCredits: %s\n\nCrosslist subjects: %s\n\nRepeatable: %s\n\nRequisites: %s",
//                        course, title, description, course_designation, lastTaught, cumulativeGPA, credits, crosslist_subjects, repeatable, requisites);
//        }
        //courseText.setText(courseDescription);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button firstToastButton = view.findViewById(R.id.firstToastButton);
        firstToastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasItemsInAutoCompleteTextView()) {
                    Toast.makeText(getActivity(), "AutoCompleteTextView has items!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "AutoCompleteTextView is empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean hasItemsInAutoCompleteTextView() {
        // Assuming you have a reference to your AutoCompleteTextView
        AutoCompleteTextView autoCompleteTextView = getView().findViewById(R.id.courseTextView);

        if (autoCompleteTextView != null) {
            ListAdapter adapter = autoCompleteTextView.getAdapter();
            if (adapter != null) {
                int itemCount = adapter.getCount();
                return itemCount > 0;
            }
        }

        return false;
    }
}