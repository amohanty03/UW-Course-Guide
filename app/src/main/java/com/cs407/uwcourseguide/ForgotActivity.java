package com.cs407.uwcourseguide;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        Button reset = findViewById(R.id.reset);
        EditText email = findViewById(R.id.username3);
        EditText phone = findViewById(R.id.phone);

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check which fields user inputted
                if (email.getText() != null) {
                    // make sure not both fields inputted
                    if (phone.getText() != null) {
                        String emailInput = email.getText().toString().trim();

                        // check if email valid
                        if (emailInput.matches(emailPattern)) {
                            Intent intent = new Intent(ForgotActivity.this, ResetActivity.class);
                            intent.putExtra("email", emailInput);
                            startActivity(intent);
                        } else {
                            Toast.makeText(ForgotActivity.this, "Email invalid. Must be in this format: username@example.edu", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(ForgotActivity.this, "Both email and phone inputted. Only input one.", Toast.LENGTH_LONG).show();
                    }
                } else { // phone inputted
                    String phoneInput = phone.getText().toString().trim();
                    String cleanedPhone = phoneInput.replaceAll("[^0-9]", "");
                    boolean validPhone = Patterns.PHONE.matcher(cleanedPhone).matches();

                    if (validPhone) {
                        Intent intent = new Intent(ForgotActivity.this, ResetActivity.class);
                        intent.putExtra("phone", cleanedPhone);
                        startActivity(intent);
                    } else {
                        Toast.makeText(ForgotActivity.this, "Invalid phone number", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
