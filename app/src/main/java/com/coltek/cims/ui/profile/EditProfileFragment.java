package com.coltek.cims.ui.profile;


import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.coltek.cims.R;

import static com.coltek.cims.util.Validation.isEmail;
import static com.coltek.cims.util.Validation.isEmpty;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends DialogFragment {

    private TextInputEditText emailEditText;
    private String email;
    private FragCommunicable communicable;

    public EditProfileFragment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        Activity activity = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        View view = activity.getLayoutInflater().inflate(R.layout.edit_profile_fragment,null);

        emailEditText = view.findViewById(R.id.email_text);
        emailEditText.setText(email);

        setStyle(STYLE_NO_TITLE, 0);

        return builder
                .setView(view)
                .setPositiveButton("Ok", (dialog, which) -> {
                    String email = emailEditText.getText().toString();
                    if (email.equalsIgnoreCase(this.email)) {
                        dismiss();
                    }
                    else{
                        if (!isEmpty(email) && !isEmail(email)) {
                            communicable.makeText("Email must be valid email or empty");
                            return;
                        }
                        communicable.changeEmail(email);
                        dismiss();
                    }
                })
                .setNegativeButton("Cancel",((dialog, which) -> this.dismiss()))
                .create();
    }

    public void setInformation(String email) {
        this.email = email;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof FragCommunicable) {
            communicable = (FragCommunicable) activity;
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
