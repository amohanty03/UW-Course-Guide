package com.cs407.uwcourseguide;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class CollegeYearDialog extends AppCompatDialogFragment {
    private int lastSelectedOption = -1; // Default to -1 when no option is selected
    private static final String KEY_SELECTED_OPTION = "selected_option";
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            lastSelectedOption = savedInstanceState.getInt(KEY_SELECTED_OPTION, -1);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);

        // Inflate a custom layout for the title
        View customTitleView = LayoutInflater.from(getActivity()).inflate(R.layout.custom_dialog_title, null);
        TextView titleTextView = customTitleView.findViewById(R.id.customDialogTitle);
        titleTextView.setText("Select your college year");

        builder.setCustomTitle(customTitleView)
                .setSingleChoiceItems(R.array.collegeyear_options, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        // Handle the selected option
                        // 'which' is the index of the selected item
                        lastSelectedOption = which;
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        // Handle positive button click
                        updateTextView();
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SELECTED_OPTION, lastSelectedOption);
    }

    private void updateTextView() {
        TextView collegeYear = getActivity().findViewById(R.id.Year);
        if (collegeYear != null && lastSelectedOption >= 0) {
            Log.i("info", "does it come here");
            String[] options = getResources().getStringArray(R.array.collegeyear_options);
            if (lastSelectedOption < options.length) {
                String selectedText = options[lastSelectedOption];
                //textViewToUpdate.setText("Selected Timeframe: " + selectedText);
                collegeYear.setText(selectedText);
            }
        }
    }
}
