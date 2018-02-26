package com.coltek.cims.ui.profile;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.coltek.cims.R;
import com.coltek.cims.di.modules.Injectable;
import com.coltek.cims.entity.Student;

import javax.inject.Inject;

public class ProfileActivity extends AppCompatActivity implements Injectable,FragCommunicable {

    @Inject
    ViewModelProvider.Factory factory;

    private EditProfileFragment editProfileFragment;
    private ProfileViewModel viewModel;
    private AppCompatTextView nameHolder, idHolder, departmentHolder, emailHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        editProfileFragment = new EditProfileFragment();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        InjectView();

        viewModel = ViewModelProviders.of(this,factory).get(ProfileViewModel.class);

        viewModel.getStudentProfile().observe(ProfileActivity.this, (Student student) -> {
            emailHolder.setText(student.getEmail());
            nameHolder.setText(student.getName());
            idHolder.setText(student.getIndex());
            departmentHolder.setText(student.getDepartment());
        });

    }


    private void InjectView() {
        nameHolder = findViewById(R.id.name_holder);
        idHolder = findViewById(R.id.id_holder);
        departmentHolder = findViewById(R.id.department_holder);
        emailHolder = findViewById(R.id.email_holder);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            if(!editProfileFragment.isVisible()){
                editProfileFragment.setInformation(emailHolder.getText().toString());
                editProfileFragment.show(getFragmentManager(), "EDIT_DIALOG");
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }


    @Override
    public void changeEmail(String email) {
        this.viewModel.updateContact(email);
    }

    @Override
    public void makeText(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }
}
