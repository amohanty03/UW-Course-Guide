package com.cs407.uwcourseguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.view.LayoutInflater;

import android.util.Log;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.FirebaseApp;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.xml.transform.OutputKeys;

public class MainActivity extends AppCompatActivity implements BottomNavigationView
        .OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    String userOrGuest;
    public static List<String> coursesList;
    public static List<String> professorsList;
    public static Map<String, Professor> professorsDescMap;
    public static Map<String, Course> coursesDescMap;

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
        if (userOrGuest == null) {
            userOrGuest = "guest";
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
        bottomNavigationView.setItemBackground(customBackground);


        getSupportActionBar().hide();

        fetchDataFromFirebase();
    }

    HomePage firstFragment = new HomePage();
    SchedulePage secondFragment = new SchedulePage();
    ScheduleGuestPage secondGuestFragment = new ScheduleGuestPage();
    LocationPage thirdFragment = new LocationPage();
    SettingsPage fourthFragment = new SettingsPage();
    SettingsPageGuest fourthFragmentGuest = new SettingsPageGuest();

    private void fetchDataFromFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myCourses = database.getReference("courses");
        DatabaseReference myProfs = database.getReference("professors");

        myCourses.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                coursesList = new ArrayList<>();
                coursesDescMap = new HashMap<>();

                for (DataSnapshot courseSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot courseChildren : courseSnapshot.getChildren()) {
                        coursesList.add(courseChildren.child("subject_abbrev").getValue(String.class) + " " + courseChildren.getKey());

                        String courseTitle = courseChildren.child("subject_abbrev").getValue(String.class) + " " + courseChildren.getKey();
                        String courseDesc = courseChildren.child("description").getValue(String.class);
                        String cGPA = courseChildren.child("cumulative_gpa").getValue(String.class);
                        String credits = courseChildren.child("credits").getValue(String.class);
                        String requisites = courseChildren.child("requisites").getValue(String.class);
                        String courseDesig = courseChildren.child("course_designation").getValue(String.class);
                        String repeatCredit = courseChildren.child("repeatable").getValue(String.class);
                        String lastTaught = courseChildren.child("last_taught").getValue(String.class);
                        String crossList = (Objects.equals(courseChildren.child("crosslist_subjects").getValue(String.class), "N/A")) ?
                                "None" : courseChildren.child("crosslist_subjects").getValue(String.class)+ " " + courseChildren.getKey();
                        String title = courseChildren.child("title").getValue(String.class);

                        // Check and set default value "N/A" for any null values
                        courseTitle = (courseTitle != null) ? courseTitle : "N/A";
                        courseDesc = (courseDesc != null) ? courseDesc : "N/A";
                        cGPA = (cGPA != null) ? cGPA : "N/A";
                        credits = (credits != null) ? credits : "N/A";
                        requisites = (requisites != null) ? requisites : "N/A";
                        courseDesig = (courseDesig != null) ? courseDesig : "N/A";
                        repeatCredit = (repeatCredit != null) ? repeatCredit : "N/A";
                        lastTaught = (lastTaught != null) ? lastTaught : "N/A";
                        crossList = (crossList != null) ? crossList : "N/A";
                        title = (title != null) ? title : "N/A";

                        Course course = new Course(courseTitle, courseDesc, cGPA, credits, requisites, courseDesig, repeatCredit, lastTaught, crossList, title);

                        coursesDescMap.put(courseTitle, course);
                    }
                }

                // Update AutoCompleteTextView on the main thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateCourseCompleteTextView(coursesList);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
            }
        });

        myProfs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                professorsList = new ArrayList<>();
                professorsDescMap = new HashMap<>();

                for (DataSnapshot profSnapshot : dataSnapshot.getChildren()) {
                    professorsList.add(profSnapshot.getKey());
                    String professorKey = profSnapshot.getKey();
                    String department = profSnapshot.child("Department").getValue(String.class);
                    String overallRating = profSnapshot.child("Overall Rating").getValue(String.class);
                    String school = profSnapshot.child("School").getValue(String.class);
                    String wouldTakeAgain = profSnapshot.child("Would take again").getValue(String.class);
                    String totalRatings = profSnapshot.child("Total Ratings").getValue(String.class);

                    // Check and set default value "N/A" for any null values
                    professorKey = (professorKey != null) ? professorKey : "N/A";
                    department = (department != null) ? department : "N/A";
                    overallRating = (overallRating != null) ? overallRating : "N/A";
                    school = (school != null) ? school : "N/A";
                    wouldTakeAgain = (wouldTakeAgain != null) ? wouldTakeAgain : "N/A";
                    totalRatings = (totalRatings != null) ? totalRatings : "N/A";

                    // Create a new Professor object
                    Professor professor = new Professor(professorKey, department, school, overallRating, wouldTakeAgain, totalRatings);

                    // Add the Professor object to the map with professor name as the key
                    professorsDescMap.put(professor.getName(), professor);
                }

                // Update AutoCompleteTextView on the main thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateProfCompleteTextView(professorsList);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
            }
        });
    }

    private void updateProfCompleteTextView(List<String> dataList) {
        // Assuming you have references to your AutoCompleteTextViews
        AutoCompleteTextView profAutoCompleteTextView = findViewById(R.id.profTextView);

        // Update AutoCompleteTextViews with the fetched data
        if (profAutoCompleteTextView != null) {
            ArrayAdapter<String> profAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, dataList);
            profAutoCompleteTextView.setAdapter(profAdapter);
        }
    }

    private void updateCourseCompleteTextView(List<String> dataList) {
        // Assuming you have references to your AutoCompleteTextViews
        AutoCompleteTextView coursesAutoCompleteTextView = findViewById(R.id.courseTextView);

        if (coursesAutoCompleteTextView != null) {
            ArrayAdapter<String> coursesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, dataList);
            coursesAutoCompleteTextView.setAdapter(coursesAdapter);
        }
    }
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