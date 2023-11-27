package com.cs407.uwcourseguide;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        Button signUp = findViewById(R.id.signup1);
        TextView continueAsGuest = findViewById(R.id.continueasguest2);
        EditText name = findViewById(R.id.name);
        EditText username = findViewById(R.id.username2);
        EditText password = findViewById(R.id.password2);

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String passwordPattern = "^(?=.*[A-Z]).{6,}$";

        // TODO: check if name and email already exists
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validate all user inputs
                String nameInput = name.getText().toString().trim();
                String usernameInput = username.getText().toString().trim();
                String passwordInput = password.getText().toString().trim();

                // check if name does not already exist
                if (true) {
                    // valid email
                    if (usernameInput.matches(emailPattern)) {
                        // check if email does not already exist
                        if (true) {
                            // valid password
                            if (passwordInput.matches(passwordPattern)) {
                                Intent intent = new Intent(CreateActivity.this, MainActivity.class);
                                intent.putExtra("userOrGuest", "user");
                                startActivity(intent);
                            } else {
                                Toast.makeText(CreateActivity.this, "Password must have at least 1 capital letter and 6+ in length", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(CreateActivity.this, "Given email already exist", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(CreateActivity.this, "Given name already exist", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(CreateActivity.this, "Email invalid. Must be in this format: username@example.edu", Toast.LENGTH_LONG).show();
                }
            }
        });

        continueAsGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
