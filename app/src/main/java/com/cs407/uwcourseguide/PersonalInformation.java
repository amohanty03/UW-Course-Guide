package com.cs407.uwcourseguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

public class PersonalInformation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);
        Window window = this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        getSupportActionBar().hide();

        ImageButton collegeYearButton = findViewById(R.id.yearButton);
        collegeYearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openYearDialog();
            }
        });

        // set userName
        TextView username = findViewById(R.id.userName);
        String fullName = Util.getUsername(this);
        username.setText(fullName);

        ImageButton usernameButton = findViewById(R.id.nameButton);
        usernameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNameDialog();
            }
        });
    }

    private void openYearDialog() {
        CollegeYearDialog dialogOptions = new CollegeYearDialog();
        dialogOptions.show(getSupportFragmentManager(), "example dialog");
    }

    private void openNameDialog() {
        PersonalInfoNameDialog dialogOptions = new PersonalInfoNameDialog();
        dialogOptions.show(getSupportFragmentManager(), "example dialog");
    }
}