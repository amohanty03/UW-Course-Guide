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
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfessorsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfessorsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<Professor> professors;
    AutoCompleteTextView profAutoCompleteTextView;
    ArrayAdapter<String> profAdapter;
    Map<String, Professor> professorMap = new HashMap<>();

    TextView profName;
    TextView profDept;
    TextView profSchool;
    TextView profRatings;
    TextView profLikeliness;
    TextView profTotalRatings;
    RatingBar profRating;

    public ProfessorsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfessorsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfessorsFragment newInstance(String param1, String param2) {
        ProfessorsFragment fragment = new ProfessorsFragment();
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
        View view = inflater.inflate(R.layout.fragment_professors, container, false);
        List<String> profList = MainActivity.professorsList;
        profAutoCompleteTextView = view.findViewById(R.id.profTextView);

//        professors = MainActivity.professorsDesc;
//        Log.e("error", String.valueOf(professors.size()));
//        for (Professor professor : professors) {
//            professorMap.put(professor.getName(), professor);
//        }

        // Update AutoCompleteTextViews with the fetched data
        if (profAutoCompleteTextView != null) {
            profAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, profList);
            profAutoCompleteTextView.setAdapter(profAdapter);

            profName = view.findViewById(R.id.profTitle);
            profDept = view.findViewById(R.id.profDept);
            profSchool = view.findViewById(R.id.profSchool);
            profRatings = view.findViewById(R.id.ratings);
            profLikeliness = view.findViewById(R.id.likeliness);
            profTotalRatings = view.findViewById(R.id.totalRatings);
            profRating = view.findViewById(R.id.ratingBar);
            profAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Handle the item click here
                    String selectedProfessor = (String) parent.getItemAtPosition(position);
                    Professor targetProfessor = MainActivity.professorsDescMap.get(selectedProfessor);
                    String error = "Something went wrong!";
                    if (targetProfessor != null) {
                        profName.setText(selectedProfessor);
                        profDept.setText("Professor in the " + targetProfessor.getDepartment());
                        profSchool.setText(targetProfessor.getSchool());
                        try {
                            float val = Float.parseFloat(targetProfessor.getRatings());
                            profRating.setVisibility(View.VISIBLE);
                            profRating.setRating(val);
                            profRatings.setText("Rating: " + val + "/5.0");
                        } catch (NumberFormatException e) {
                            profRating.setVisibility(View.VISIBLE);
                            profRating.setRating(0.0F);
                            profRatings.setText("Rating: " + "0.0/5.0");
                        }
                        profLikeliness.setText("Would take again: " + targetProfessor.getLikeliness());

                        String underlinedText = "Based on " + targetProfessor.getTotalRatings() + " reviews";
                        SpannableString underlinedContent = new SpannableString(underlinedText);
                        underlinedContent.setSpan(new UnderlineSpan(), 0, underlinedContent.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        profTotalRatings.setText(underlinedContent);
                    } else {
                        profName.setText(error);
                    }
                    // Do something with the selected professor
                    Log.e("SelectedProfessor", selectedProfessor);
                    // For example, you can open a new fragment or perform some other action
                }
            });
        }
        return view;
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
////        Button secondToastButton = view.findViewById(R.id.secondToastButton);
////        secondToastButton.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                if (hasItemsInAutoCompleteTextView()) {
////                    Toast.makeText(getActivity(), "AutoCompleteTextView has items!", Toast.LENGTH_SHORT).show();
////                } else {
////                    Toast.makeText(getActivity(), "AutoCompleteTextView is empty!", Toast.LENGTH_SHORT).show();
////                }
////            }
////        })
//    }

    private boolean hasItemsInAutoCompleteTextView() {
        // Assuming you have a reference to your AutoCompleteTextView
        AutoCompleteTextView autoCompleteTextView = getView().findViewById(R.id.profTextView);

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