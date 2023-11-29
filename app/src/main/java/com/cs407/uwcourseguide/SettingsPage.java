package com.cs407.uwcourseguide;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsPage} factory method to
 * create an instance of this fragment.
 */
public class SettingsPage extends Fragment {
    public SettingsPage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_settings_page, container, false);

        // Personal Information Redirect
        ImageButton personalInfo = rootView.findViewById(R.id.personalInfoButton);
        personalInfo.setOnClickListener(view -> ((MainActivity) requireActivity()).goToPersonalInfo());

        // Notification Settings Redirect
        ImageButton notifSettings = rootView.findViewById(R.id.notifSettingsButton);
        notifSettings.setOnClickListener(view -> ((MainActivity) requireActivity()).goToNotifSettings());

        // Reset Password Redirect
        ImageButton resetPass = rootView.findViewById(R.id.resetPassButton);
       // resetPass.setOnClickListener(view -> ((MainActivity) requireActivity()).goToResetPass());

        // About UW Course Guide Redirect
        ImageButton aboutUs = rootView.findViewById(R.id.aboutUsButton);
        aboutUs.setOnClickListener(view -> ((MainActivity) requireActivity()).goToAboutUs());

        // Contact Us Redirect
        ImageButton contactUs = rootView.findViewById(R.id.contactUsButton);
        contactUs.setOnClickListener(view -> ((MainActivity) requireActivity()).goToContactUs());

        // Register Account Redirect
        ImageButton registerAcc = rootView.findViewById(R.id.registerAccButton);
        //registerAcc.setOnClickListener(view -> ((MainActivity) requireActivity()).goToRegisterAccount());

        return rootView;
    }


    // Ensure that onActivityCreated is overridden
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}