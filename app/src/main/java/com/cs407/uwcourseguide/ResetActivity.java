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

public class ResetActivity extends AppCompatActivity {
    // TODO: check if email or phone exists
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        TextView resetLink = findViewById(R.id.resetLink);
        Button reset = findViewById(R.id.back);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        String phone = intent.getStringExtra("phone");

        getSupportActionBar().hide();
        Window window = this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.red_topbar));

        // check which was inputted
        if (email != null) {
            // check if email exist. If not, do not send email.
            if (true) {
                resetLink.setText("If the given email exists, a reset link has been send to " + email);

                // send email
            }
        } else {
            // check if phone exist. If not, do not send text
            if (true) {
                resetLink.setText("If the given phone number exists, a reset link has been send to " + phone);

                // send text
            }
        }

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResetActivity.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
