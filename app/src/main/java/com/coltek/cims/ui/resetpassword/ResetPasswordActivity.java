package com.coltek.cims.ui.resetpassword;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.coltek.cims.R;
import com.coltek.cims.di.modules.Injectable;

import javax.inject.Inject;

import static com.coltek.cims.util.Validation.isEmail;
import static com.coltek.cims.util.Validation.isEmpty;


public class ResetPasswordActivity extends AppCompatActivity implements Injectable {

    @Inject
    ViewModelProvider.Factory factory;

    private Button resetBtn;
    private TextInputEditText emailTxt;
    private ResetViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password_activity);

        viewModel = ViewModelProviders.of(this,factory).get(ResetViewModel.class);

        viewModel.getResponse().observe(this,response->{
            switch (response.status){
                case ERROR:
                    makeResponse(response.message);
                    break;
                case SUCCESS:
                    makeResponse(response.data);
                    break;
            }
            toggleForm();
        });

        Init();
    }

    protected void Init(){
        resetBtn = findViewById(R.id.reset_btn);
        emailTxt = findViewById(R.id.email_text);
        resetBtn.setOnClickListener(v->attemptReset());
        findViewById(R.id.back).setOnClickListener(V->onBackPressed());
    }

    private void attemptReset() {
        String email = emailTxt.getText().toString();
        if (isEmpty(email)) {
            emailTxt.setError(getString(R.string.error_field_required));
            emailTxt.requestFocus();
        } else if (!isEmail(email)) {
            emailTxt.setError(getString(R.string.error_invalid_email));
        } else {
            toggleForm();
        }
    }

    private void toggleForm() {
        if (emailTxt.isEnabled()) {
            emailTxt.setEnabled(false);
            resetBtn.setEnabled(false);
            resetBtn.setText(R.string.please_wait);
        } else {
            emailTxt.setEnabled(true);
            resetBtn.setEnabled(true);
            resetBtn.setText(R.string.reset);
        }
    }

    private void makeResponse(String message){
        new AlertDialog.Builder(getApplicationContext())
                .setMessage(message)
                .setCancelable(true)
                .setNegativeButton("OK", (dialog, which) -> dialog.dismiss());
    }

}
