package com.coltek.cims.ui.mentor_registration;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.coltek.cims.R;
import com.coltek.cims.di.modules.Injectable;
import com.coltek.cims.entity.Mentor;
import com.coltek.cims.ui.school_registration.SchoolRegistration;

import javax.inject.Inject;

import static com.coltek.cims.helper.Status.ERROR;
import static com.coltek.cims.util.Validation.isEmpty;
import static com.coltek.cims.util.Validation.isValidNumber;

public class MentorRegistration extends AppCompatActivity implements Injectable {

    @Inject
    ViewModelProvider.Factory factory;
    String sta = "Trained";
    String id = "0";
    private EditText name, phone, bank_name, bank_branch, bank_account, address, qualification;
    private Spinner status;
    private Button toSchool, done;
    private MentorRegistrationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.mentor_register_activity);
        InjectView();

        viewModel = ViewModelProviders.of(this, factory).get(MentorRegistrationViewModel.class);
        viewModel.getMentor().observe(this, this::setMentor);
        viewModel.getResource().observe(this, stringResource -> {
            if ((stringResource != null ? stringResource.status : null) == ERROR) {
                makeToast(stringResource.message);
            }
        });

    }


    void InjectView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = findViewById(R.id.men_name);
        phone = findViewById(R.id.men_phone);
        address = findViewById(R.id.men_address);
        qualification = findViewById(R.id.men_qua);

        status = findViewById(R.id.men_status);
        bank_name = findViewById(R.id.men_bank_name);
        bank_branch = findViewById(R.id.men_bank_branch);
        bank_account = findViewById(R.id.men_ac_number);

        done = findViewById(R.id.mentor_done);
        toSchool = findViewById(R.id.to_school);

        done.setOnClickListener(v -> validateForm());
        toSchool.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), SchoolRegistration.class);
            startActivity(intent);
            finish();
        });

        status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sta = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                sta = parent.getSelectedItem().toString();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }


    void setMentor(Mentor mentor) {
        if (mentor != null) {
            name.setText(mentor.getFull_name());
            phone.setText(mentor.getPhone());
            bank_account.setText(mentor.getBank_acc_number());
            bank_branch.setText(mentor.getBank_branch());
            bank_name.setText(mentor.getBank_name());
            address.setText(mentor.getAddress());
            qualification.setText(mentor.getQualification());

            id = mentor.getIndex_number();
            String s = mentor.getStatus() == null ? sta : mentor.getStatus();
            status.setSelection(((ArrayAdapter) status.getAdapter()).getPosition(s));

        }
    }

    void makeToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    protected void validateForm() {
        View view = null;
        boolean cancelProcess = false;
        String name = this.name.getText().toString().trim(), phone = this.phone.getText().toString().trim(), bank_name = this.bank_name.getText().toString().trim(), bank_branch = this.bank_branch.getText().toString().trim(), bank_acc = this.bank_account.getText().toString().trim(), address = this.address.getText().toString().trim(), qua = this.qualification.getText().toString(), msg = null;

        if (isEmpty(name)) {
            cancelProcess = true;
            view = this.name;
            msg = "Name can't be empty";
        } else if (!isValidNumber(phone)) {
            cancelProcess = true;
            view = this.phone;
            msg = "Provide a valid number";
        } else if (isEmpty(bank_name)) {
            cancelProcess = true;
            view = this.bank_name;
            msg = "Provide a valid bank name";
        } else if (isEmpty(bank_branch)) {
            cancelProcess = true;
            view = this.bank_branch;
            msg = "Provide a valid bank branch";
        } else if (isEmpty(bank_acc)) {
            cancelProcess = true;
            view = this.bank_account;
            msg = "Provide a valid bank account number";
        } else if (isEmpty(address)) {
            cancelProcess = true;
            view = this.address;
            msg = "Provide a valid address";
        }
        if (cancelProcess) {
            view.requestFocus();
            makeToast(msg);
        } else {
            viewModel.modifyMentor(new Mentor(name, qua, address, sta, phone, bank_name, bank_branch, bank_acc, id));
        }
    }
}
