package com.cs407.uwcourseguide;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class ScheduleGuestPage extends Fragment {
    private Button login, signup;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule_guest_display, container, false);
        login = view.findViewById(R.id.loginScheduleGuest);
        signup = view.findViewById(R.id.loginScheduleGuest);
        buttonListeners();
        return view;
    }

    private void buttonListeners() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go back to welcome page
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go back to create page
            }
        });
    }
}
