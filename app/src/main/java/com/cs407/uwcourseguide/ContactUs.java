package com.cs407.uwcourseguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class ContactUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        getSupportActionBar().hide();
    }

    public void sendEmail(View view) {
        String recipientEmail = "mohanty5@wisc.edu";  // replace with your email address

        EditText subjectView = findViewById(R.id.name);
        EditText bodyView = findViewById(R.id.MessageText);
        String subject = subjectView.getText().toString();
        String body = bodyView.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");  // Use "message/rfc822" MIME type for emails

        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{recipientEmail});
        intent.putExtra(Intent.EXTRA_SUBJECT, "UW Course Guide Contact Request: " + subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(intent, "Choose an email app"));
        } else {
            Log.e("Error", "No app can handle this intent");
        }
    }
}