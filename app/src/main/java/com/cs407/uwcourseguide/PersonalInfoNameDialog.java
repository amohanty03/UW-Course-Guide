package com.cs407.uwcourseguide;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class PersonalInfoNameDialog extends AppCompatDialogFragment {
    private EditText nameEditText;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);

        // Inflate a custom layout for the title
        View customTitleView = LayoutInflater.from(getActivity()).inflate(R.layout.custom_dialog_title, null);
        TextView titleTextView = customTitleView.findViewById(R.id.customDialogTitle);
        titleTextView.setText("Update your name");
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_name_input, null);

        nameEditText = view.findViewById(R.id.editTextName);

        TextView userName = getActivity().findViewById(R.id.userName);

        builder.setView(view)
                .setCustomTitle(customTitleView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String enteredName = nameEditText.getText().toString().trim();
                        //if (!enteredName.isEmpty() && onNameInputListener != null) {
                            //onNameInputListener.onNameInput(enteredName);
                            Log.e("ERROR", enteredName);
                            userName.setText(enteredName);
                       // }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        PersonalInfoNameDialog.this.getDialog().cancel();
                    }
                });

        AlertDialog dialog = builder.create();

        // Set the gravity for the dialog
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(R.drawable.rounded_corners_alertdialog);
        }
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        nameEditText.postDelayed(new Runnable() {
            @Override
            public void run() {
                showKeyboard();
            }
        }, 100);
    }

    private void showKeyboard() {
        if (nameEditText.requestFocus()) {
            getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
