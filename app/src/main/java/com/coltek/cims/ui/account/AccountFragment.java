package com.coltek.cims.ui.account;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.coltek.cims.R;
import com.coltek.cims.di.modules.Injectable;
import com.coltek.cims.factory.ViewModelFactory;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

import static com.coltek.cims.util.Validation.isEmpty;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment implements Injectable {

    @Inject
    ViewModelFactory factory;

    private AccountViewModel viewModel;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidSupportInjection.inject(AccountFragment.this);

        viewModel = ViewModelProviders.of(AccountFragment.this, factory).get(AccountViewModel.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        if (view != null) {
            EditText old_pass = view.findViewById(R.id.old_password), new_pass = view.findViewById(R.id.new_password), confirm_pass = view.findViewById(R.id.confirm_password);

            Button change_pass = view.findViewById(R.id.password_change_btn);
//                    change_address = view.findViewById(R.id.update_address);

//            change_address.setOnClickListener(v->{
            //TODO: GET GEOLOCATION AND SAVE TO SERVER
//            });

            change_pass.setOnClickListener(v -> {
                String oldPassword = old_pass.getText().toString().trim(), newPassword = new_pass.getText().toString().trim(), confirmPassword = confirm_pass.getText().toString().trim();

                String errorMessage = null;
                boolean cancelProcess = false;
                View errorField = null;

                if (isEmpty(oldPassword)) {
                    errorMessage = "Old password field is required";
                    cancelProcess = true;
                    errorField = old_pass;
                } else if (isEmpty(newPassword)) {
                    errorField = new_pass;
                    cancelProcess = true;
                    errorMessage = "New password field is required";
                } else if (!newPassword.equals(confirmPassword)) {
                    errorField = confirm_pass;
                    cancelProcess = true;
                    errorMessage = "Couldn't confirm new password";
                } else if (newPassword.equals(oldPassword)) {
                    errorField = new_pass;
                    cancelProcess = true;
                    errorMessage = "New password must be different from old password";
                }

                if (cancelProcess) {
                    errorField.requestFocus();
                    makeToast(errorMessage);
                } else {
                    viewModel.changePassword(oldPassword, newPassword);
                }

            });
        }

    }

    private void makeToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
