package com.cs407.uwcourseguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Main Activity will be the login page, unless user already logged. Similar to Notes lab
        SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.uwcourseguide", Context.MODE_PRIVATE);

        if (sharedPreferences.getString("username", "") != "") {
            // go to another screen, probably Home page
        } else {
            // stay in login page and add functionalities to the buttons
        }
    }
}