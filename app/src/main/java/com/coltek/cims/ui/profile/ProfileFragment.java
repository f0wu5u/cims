package com.coltek.cims.ui.profile;


import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coltek.cims.R;
import com.coltek.cims.di.modules.Injectable;
import com.coltek.cims.entity.Student;
import com.coltek.cims.ui.dashboard.FragCommunicable;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class ProfileFragment extends Fragment implements Injectable {

    @Inject
    ViewModelProvider.Factory factory;
    FragCommunicable fragCommunicable;
    ProfileViewModel viewModel;
    private AppCompatTextView nameHolder, idHolder, departmentHolder, emailHolder;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_activity, container, false);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(ProfileFragment.this);

        Activity activity = getActivity();
        if (activity instanceof FragCommunicable) {
            fragCommunicable = (FragCommunicable) activity;
        }
        viewModel = ViewModelProviders.of(this,factory).get(ProfileViewModel.class);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        if (view != null) {
            nameHolder = view.findViewById(R.id.name_holder);
            idHolder = view.findViewById(R.id.id_holder);
            departmentHolder = view.findViewById(R.id.department_holder);
            emailHolder = view.findViewById(R.id.email_holder);

            FloatingActionButton fab = view.findViewById(R.id.fab);
            fab.setOnClickListener(v -> fragCommunicable.showEditProfileFragment(emailHolder.getText().toString()));

            viewModel.getStudentProfile().observe(this, (Student student) -> {
                emailHolder.setText(student.getEmail());
                nameHolder.setText(student.getName());
                idHolder.setText(student.getIndex());
                departmentHolder.setText(student.getDepartment());
            });
        }
    }
}
