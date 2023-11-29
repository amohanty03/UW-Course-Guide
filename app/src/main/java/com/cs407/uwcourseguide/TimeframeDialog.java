package com.cs407.uwcourseguide;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.content.ContextCompat;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class TimeframeDialog extends AppCompatDialogFragment {
    //private TextView textViewToUpdate;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);

        // Inflate a custom layout for the title
        View customTitleView = LayoutInflater.from(getActivity()).inflate(R.layout.custom_dialog_title, null);
        TextView titleTextView = customTitleView.findViewById(R.id.customDialogTitle);
        titleTextView.setText("Class Reminder Timeframe");

        builder.setCustomTitle(customTitleView)
                .setSingleChoiceItems(R.array.timeframe_options, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        // Handle the selected option
                        // 'which' is the index of the selected item

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        // Handle positive button click

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        // Handle negative button click or dismiss the dialog
                    }
                });
        AlertDialog dialog = builder.create();

        // Set the gravity for the dialog
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(R.drawable.rounded_corners_alertdialog);
            window.setGravity(Gravity.BOTTOM); // Adjust the gravity as needed

            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.y = getResources().getDimensionPixelSize(R.dimen.dialog_padding_bottom);
            window.setAttributes(layoutParams);
        }
        return dialog;
    }

    // Method to set the TextView reference
//    public void setTextViewToUpdate(TextView textViewToUpdate) {
//        this.textViewToUpdate = textViewToUpdate;
//        // Update TextView immediately if a selection has been made before
//        updateTextView();
//    }

    // Method to update the TextView based on the last selected option

}
