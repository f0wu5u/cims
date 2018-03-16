package com.coltek.cims.ui.school_registration;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.coltek.cims.entity.School;
import com.coltek.cims.ui.mentor_registration.MentorRegistration;

import javax.inject.Inject;

import static com.coltek.cims.helper.Status.ERROR;
import static com.coltek.cims.util.Validation.isEmpty;
import static com.coltek.cims.util.Validation.isValidNumber;


/**
 * A simple {@link Fragment} subclass.
 */
public class SchoolRegistration extends AppCompatActivity implements Injectable {
    EditText nameF, townF, districtF, phoneF;
    Spinner regionS;
    Button toMentor, done;
    String region = "Ashanti Region";

    String id = "0";

    @Inject
    ViewModelProvider.Factory factory;


    private SchoolRegistrationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.school_register_activity);
        InjectView();

        viewModel = ViewModelProviders.of(this, factory).get(SchoolRegistrationViewModel.class);
        viewModel.getSchool().observe(this, this::setSchool);
        viewModel.getResource().observe(this, stringResource -> {
            if ((stringResource != null ? stringResource.status : null) == ERROR) {
                makeToast(stringResource.message);
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    void InjectView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameF = findViewById(R.id.sch_name);
        townF = findViewById(R.id.sch_town);
        regionS = findViewById(R.id.sch_region);
        districtF = findViewById(R.id.sch_district);
        phoneF = findViewById(R.id.std_number);

        done = findViewById(R.id.sch_done);
        toMentor = findViewById(R.id.to_mentor);

        done.setOnClickListener(v -> validateForm());
        toMentor.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), MentorRegistration.class);
            startActivity(intent);
            finish();
        });

        regionS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                region = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    void setSchool(School school) {
        if (school != null) {
            nameF.setText(school.getName());
            districtF.setText(school.getDistrict());
            townF.setText(school.getTown());
            phoneF.setText(school.getNumber());
            String s = school.getRegion() == null ? region : school.getRegion();
            regionS.setSelection(((ArrayAdapter) regionS.getAdapter()).getPosition(s));

            id = school.getIndex_number();
        }
    }

    protected void validateForm() {
        View view = null;
        boolean cancelProcess = false;
        String name = this.nameF.getText().toString().trim(), district = this.districtF.getText().toString().trim(), town = this.townF.getText().toString().trim(), phone = this.phoneF.getText().toString().trim(), msg = null;

        if (isEmpty(name)) {
            cancelProcess = true;
            view = this.nameF;
            msg = "Name can't be empty";
        } else if (!isValidNumber(phone)) {
            cancelProcess = true;
            view = this.phoneF;
            msg = "Provide a valid number";
        } else if (isEmpty(town)) {
            cancelProcess = true;
            view = this.townF;
            msg = "Town can't be empty";
        } else if (isEmpty(district)) {
            cancelProcess = true;
            view = this.districtF;
            msg = "District can't be empty";
        }
        if (cancelProcess) {
            view.requestFocus();
            makeToast(msg);
        } else {
            viewModel.modifySchool(new School(region, district, name, town, phone, id));
        }
    }

    void makeToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
