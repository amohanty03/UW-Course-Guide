package com.cs407.uwcourseguide;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getSupportActionBar().hide();
        Window window = this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.red_topbar));
        Button login = findViewById(R.id.login2);
        TextView forgot = findViewById(R.id.forgot);
        TextView continueAsGuest = findViewById(R.id.continueasguest1);
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        // TODO: check if given email and password is correct
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check if correct email and password
                String usernameInput = username.getText().toString().trim();
                String passwordInput = password.getText().toString().trim();
                if (usernameInput.matches(emailPattern)){ // valid email
                    // check if correct username and password
                    if (true) { // correct password
                        // set token as logged in
                        Util.setToken(WelcomeActivity.this);

                        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                        intent.putExtra("userOrGuest", "user");
                        startActivity(intent);
                    } else { // incorrect password
                        Toast.makeText(WelcomeActivity.this, "Given username or password does not match from database", Toast.LENGTH_LONG).show();
                    }
                } else{ // invalid email
                    Toast.makeText(WelcomeActivity.this, "Username invalid. Must be in this format: username@example.edu", Toast.LENGTH_LONG).show();
                }
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, ForgotActivity.class);
                startActivity(intent);
            }
        });

        continueAsGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                intent.putExtra("userOrGuest", "guest");
                startActivity(intent);
            }
        });
    }
}
