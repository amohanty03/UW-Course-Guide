package com.cs407.uwcourseguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import android.view.LayoutInflater;

import android.util.Log;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.transform.OutputKeys;

public class MainActivity extends AppCompatActivity implements BottomNavigationView
        .OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    String userOrGuest;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        Window window = this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
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
        userOrGuest = intent.getStringExtra("userOrGuest");
        

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
        bottomNavigationView.setItemBackground(customBackground);


        getSupportActionBar().hide();

//        Statement st = null;
//        Connection connection = null;
//        try {
//            connection = DriverManager.getConnection(
//                    "jdbc:mariadb://localhost:3306/database_name",
//                    "root", "saltyhayonwfneals@&13459"
//            );
//            String query = "SELECT * from courses";
//            st = connection.createStatement();
//            ResultSet rs = st.executeQuery(query);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (st != null) {
//                    st.close();
//                }
//                connection.close();
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }
    }

    HomePage firstFragment = new HomePage();
    SchedulePage secondFragment = new SchedulePage();
    ScheduleGuestPage secondGuestFragment = new ScheduleGuestPage();
    LocationPage thirdFragment = new LocationPage();
    SettingsPage fourthFragment = new SettingsPage();
    SettingsPageGuest fourthFragmentGuest = new SettingsPageGuest();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemID = item.getItemId();

        if (itemID == R.id.home) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, firstFragment)
                    .commit();
            return true;
        } else if (itemID == R.id.schedule && userOrGuest.equals("user")) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, secondFragment)
                    .commit();
            return true;
        } else if (itemID == R.id.schedule && userOrGuest.equals("guest")) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, secondGuestFragment)
                    .commit();
            return true;
        }
        else if (itemID == R.id.location) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, thirdFragment)
                    .commit();
            return true;
        } else if (itemID == R.id.settings && userOrGuest.equals("user")) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, fourthFragment)
                    .commit();
            return true;
        } else if (itemID == R.id.settings && userOrGuest.equals("guest")){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, fourthFragmentGuest)
                    .commit();
            return true;
        }
        return false;
    }

    public void goToPersonalInfo() {
        Intent intent = new Intent(this, PersonalInformation.class);
        startActivity(intent);
    }

    public void goToNotifSettings() {
        Intent intent = new Intent(this, NotificationSettings.class);
        startActivity(intent);
    }

    public void goToResetPass() {
        Intent intent = new Intent(this, ForgotActivity.class);
        startActivity(intent);
    }

    public void goToAboutUs() {
        Intent intent = new Intent(this, AboutUWCG.class);
        startActivity(intent);
    }

    public void goToContactUs() {
        Intent intent = new Intent(this, ContactUs.class);
        startActivity(intent);
    }

    public void goToRegisterAccount() {
        Intent intent = new Intent(this, CreateActivity.class);
        startActivity(intent);
    }

    public void goToWelcomePage() {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }
    public void goToLoginPage() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    public void goToCourseFragment(){
        //Intent intent = new Intent(this, )
    }
}