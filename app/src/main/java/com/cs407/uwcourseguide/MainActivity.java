package com.cs407.uwcourseguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView
        .OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        Depends what we want to do with guest:
            If guest data are all gone once app is close, then setToken before logging in or
            creating account.
            If guest data are preserved until they log in or create an account, then setToken in
            MainActivity.
        Most likely will go with the first choice as that's easier and doesn't require us to do more
        work on database.
         */
        //Util.setToken(this);


        // check if user logged in or continued as guest
        Intent intent = getIntent();
        String userOrGuest = intent.getStringExtra("userOrGuest");
        if (userOrGuest == "user"){
            // user
        } else {
            // guest
        }

        /*
        if userOrGuest is guest:
            HomePage:
                Can't add reviews. Everything else can be shown
            SchedulePage:
                Probably can add classes to their schedule but need a warning that it will all be
                lost once they close this app
            LocationPage:
                Everything can be shown
            SettingsPage:
                Only shows:
                    About UW Course Guide
                    Contact Us
                    Register Account - only for guest

          Use AlertDialog if guest try to use any other feature.
          And warning dialogs if guest are about to leave or make an account.
         */

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        //bottomNavigationView = findViewById(R.id.main_navigation);

        bottomNavigationView
                .setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setBackgroundColor(Color.parseColor("#FFFFFF"));

        Drawable customBackground = getResources().getDrawable(R.drawable.custom_background);

        // Set the item background programmatically
        //bottomNavigationView.setItemBackground(customBackground);

        getSupportActionBar().hide();
    }

    HomePage firstFragment = new HomePage();
    SchedulePage secondFragment = new SchedulePage();
    LocationPage thirdFragment = new LocationPage();
    SettingsPage fourthFragment = new SettingsPage();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemID = item.getItemId();

        if (itemID == R.id.home) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, firstFragment)
                    .commit();
            return true;
        } else if (itemID == R.id.schedule) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, secondFragment)
                    .commit();
            return true;
        } else if (itemID == R.id.location) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, thirdFragment)
                    .commit();
            return true;
        } else if (itemID == R.id.settings) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, fourthFragment)
                    .commit();
            return true;
        }
        return false;
    }
}