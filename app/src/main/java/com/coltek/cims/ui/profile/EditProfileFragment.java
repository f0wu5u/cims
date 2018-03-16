package com.coltek.cims.ui.profile;


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.coltek.cims.R;
import com.coltek.cims.ui.dashboard.FragCommunicable;

import static com.coltek.cims.util.Validation.isEmail;
import static com.coltek.cims.util.Validation.isEmpty;


public class EditProfileFragment extends DialogFragment {
    private FragCommunicable communicable;

    public EditProfileFragment() {
    }

    public static EditProfileFragment newInstance(String email) {
        EditProfileFragment fragment = new EditProfileFragment();
        Bundle args = new Bundle();
        args.putString("email", email);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        Activity activity = getActivity();

        if (activity instanceof FragCommunicable) {
            communicable = (FragCommunicable) activity;
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            final String mEmail = getArguments().getString("email");
            View view = activity.getLayoutInflater().inflate(R.layout.edit_profile_fragment, null);
            TextInputEditText emailEditText = view.findViewById(R.id.email_text);
            emailEditText.setText(mEmail);

            setStyle(STYLE_NO_TITLE, 0);

            return builder.setView(view).setPositiveButton("Ok", (dialog, which) -> {
                String email = emailEditText.getText().toString();
                if (email.equalsIgnoreCase(mEmail)) {
                    dismiss();
                } else {
                    if (!isEmpty(email) && !isEmail(email)) {
                        communicable.makeText("Email must be valid email or empty");
                        return;
                    }
                    communicable.changeEmail(email);
                    dismiss();
                }
            }).setNegativeButton("Cancel", ((dialog, which) -> this.dismiss())).create();
        } else {
            throw new RuntimeException(activity.toString() + " must implement " + FragCommunicable.class.getSimpleName());
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        communicable = null;
    }
}
