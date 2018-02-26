package com.coltek.cims.ui.login;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.coltek.cims.R;
import com.coltek.cims.di.modules.Injectable;
import com.coltek.cims.entity.Credentials;
import com.coltek.cims.ui.dashboard.DashboardActivity;
import com.coltek.cims.ui.resetpassword.ResetPasswordActivity;

import javax.inject.Inject;

import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;
import static android.view.inputmethod.EditorInfo.IME_NULL;
import static com.coltek.cims.util.Validation.isEmpty;
import static com.coltek.cims.util.Validation.isValidIndex;
import static com.coltek.cims.util.Validation.isValidPassword;


public class LoginActivity extends AppCompatActivity implements Injectable {


    @Inject
    ViewModelProvider.Factory viewModelFactory;


    private LoginViewModel loginViewModel;

    private TextInputEditText usernameInput, passwordInput;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        InjectView();

        loginViewModel = ViewModelProviders.of(this,viewModelFactory).get(LoginViewModel.class);

        loginViewModel.getResponse().observe(this, message ->{
            switch (message.status){
                case ERROR:
                    loginFailedWith(message.message);
                    break;
                case LOADING:
                    lockForm();
                    break;
                case SUCCESS:
                    goHome();
                    break;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
//        viewModel.setCredential(String.valueOf(usernameInput.getText()),String.valueOf(passwordInput.getText()));
    }

    protected void InjectView(){
        usernameInput = findViewById(R.id.mStudentID);
        passwordInput = findViewById(R.id.mPassword);

        TextView mResetLink = findViewById(R.id.fgt_pass);
        mResetLink.setOnClickListener(view -> {
            Intent reset = new Intent(getApplicationContext(), ResetPasswordActivity.class);
            startActivity(reset.putExtra("Email","SDss"));
//            finish();
        });

        passwordInput.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == IME_ACTION_DONE || id == IME_NULL) {
                authenticate();
                return true;
            }
            return false;
        });

        loginBtn = findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(view -> authenticate());
    }

    protected void authenticate(){
        String Password = passwordInput.getText().toString(),
                StudentID = usernameInput.getText().toString();

        usernameInput.setError(null);
        passwordInput.setError(null);

        boolean cancelProcess = false;
        View focusView = null;


        if (isEmpty(StudentID)) {
            usernameInput.setError(getString(R.string.error_field_required));
            focusView = usernameInput;
            cancelProcess = true;
        } else if (!isValidIndex(StudentID)) {
            usernameInput.setError(getString(R.string.error_invalid_index));
            focusView = usernameInput;
            cancelProcess = true;
        } else if (!isValidPassword(Password)) {
            passwordInput.setError(getString(R.string.error_field_required));
            focusView = passwordInput;
            cancelProcess = true;
        }

        if (cancelProcess) {
            focusView.requestFocus();
        } else {
            loginViewModel.attemptLogin(new Credentials(StudentID,Password));
        }


    }

    private void lockForm() {
        passwordInput.setEnabled(false);
        loginBtn.setEnabled(false);
        loginBtn.setText(R.string.please_wait);
    }

    private void resetForm() {
        loginBtn.setEnabled(true);
        loginBtn.setText(R.string.login_btn_text);
        passwordInput.setText(null);
        passwordInput.setEnabled(true);
    }

    private void goHome() {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), DashboardActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void loginFailedWith(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
        resetForm();
    }
}
