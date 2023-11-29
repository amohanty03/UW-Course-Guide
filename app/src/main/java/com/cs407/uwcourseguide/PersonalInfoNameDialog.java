package com.cs407.uwcourseguide;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class PersonalInfoNameDialog extends AppCompatDialogFragment {
    private EditText nameEditText;

    public interface OnNameInputListener {
        void onNameInput(String name);
    }

    private OnNameInputListener onNameInputListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_name_input, null);

        nameEditText = view.findViewById(R.id.editTextName);

        builder.setView(view)
                .setTitle("Enter Name")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String enteredName = nameEditText.getText().toString().trim();
                        if (!enteredName.isEmpty() && onNameInputListener != null) {
                            onNameInputListener.onNameInput(enteredName);
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        PersonalInfoNameDialog.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();
        nameEditText.postDelayed(new Runnable() {
            @Override
            public void run() {
                showKeyboard();
            }
        }, 100);
    }

    private void showKeyboard() {
        if (nameEditText.requestFocus()) {
            InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(nameEditText, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public void setOnNameInputListener(OnNameInputListener listener) {
        this.onNameInputListener = listener;
    }
}
