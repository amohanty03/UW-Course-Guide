package com.cs407.uwcourseguide;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class SettingsPageGuest extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_settings_guest_page, container, false);

        // About UW Course Guide Redirect
        ImageButton aboutUs = rootView.findViewById(R.id.aboutUsButton);
        aboutUs.setOnClickListener(view -> ((MainActivity) requireActivity()).goToAboutUs());

        // Contact Us Redirect
        ImageButton contactUs = rootView.findViewById(R.id.contactUsButton);
        contactUs.setOnClickListener(view -> ((MainActivity) requireActivity()).goToContactUs());

        // Register Account Redirect
        ImageButton registerAcc = rootView.findViewById(R.id.registerAccButton);
        registerAcc.setOnClickListener(view -> ((MainActivity) requireActivity()).goToRegisterAccount());

        // Set userNameSettings to the full name
        TextView username = rootView.findViewById(R.id.userNameSettings);
        String fullName = "Guest";
        username.setText("Hi " + fullName +"!");

        return rootView;
    }
}
