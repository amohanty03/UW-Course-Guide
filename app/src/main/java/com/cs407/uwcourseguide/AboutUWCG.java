package com.cs407.uwcourseguide;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AboutUWCG extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_uwcg);
        getSupportActionBar().hide();
    }
}