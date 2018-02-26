package com.coltek.cims.ui.registration;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.coltek.cims.R;
import com.coltek.cims.di.modules.Injectable;
import com.coltek.cims.entity.Mentor;

import javax.inject.Inject;

import static com.coltek.cims.helper.Status.ERROR;
import static com.coltek.cims.util.Validation.isEmpty;
import static com.coltek.cims.util.Validation.isValidNumber;


/**
 * A simple {@link Fragment} subclass.
 */
public class MentorRegisterFragment extends Fragment implements Injectable,View.OnClickListener  {

    private EditText name,phone,bank_name,bank_branch,bank_account;
    private Spinner qualification,status;
    private Button toSchool;
    private ImageButton done;

    private String qua = "Bsc/Ba Degree",
            sta="Unprofessional";

    private MentorRegistrationViewModel viewModel;

    @Inject
    RegistrationNavigator navigator;

    @Inject
    ViewModelProvider.Factory factory;

    public MentorRegisterFragment() {
        // Required empty public constructor
    }

    private void setMentor(Mentor mentor) {
        if (mentor != null) {
            name.setText(mentor.getFull_name());
            phone.setText(mentor.getPhone());
            bank_account.setText(mentor.getBank_acc_number());
            bank_branch.setText(mentor.getBank_branch());
            bank_name.setText(mentor.getBank_name());

            String s = mentor.getStatus() == null ? sta : mentor.getStatus(),
                    q = mentor.getQualification() == null ? qua : mentor.getQualification();

            status.setSelection(((ArrayAdapter) status.getAdapter()).getPosition(s));
            qualification.setSelection(((ArrayAdapter) qualification.getAdapter()).getPosition(q));
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        qualification.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                qua = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                qua = parent.getSelectedItem().toString();
            }
        });

        status.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sta = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sta = parent.getSelectedItem().toString();
            }
        });

        done.setOnClickListener(this);
        toSchool.setOnClickListener(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view  = inflater.inflate(R.layout.mentor_register_fragment, container, false);

        name = view.findViewById(R.id.men_name);
        phone = view.findViewById(R.id.men_phone);
        qualification = view.findViewById(R.id.men_qua);

        status = view.findViewById(R.id.men_status);
        bank_name = view.findViewById(R.id.men_bank_name);
        bank_branch = view.findViewById(R.id.men_bank_branch);
        bank_account = view.findViewById(R.id.men_ac_number);
        done = view.findViewById(R.id.mentor_done);
        toSchool = view.findViewById(R.id.to_school);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(this,factory).get(MentorRegistrationViewModel.class);

        viewModel.getMentor().observe(this, this::setMentor);
        viewModel.getResource().observe(this,stringResource -> {
            if ((stringResource != null ? stringResource.status : null) == ERROR){
                makeToast(stringResource.message);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.to_school:
                navigator.navigateSchool();
                break;
            case R.id.mentor_done:
                validateForm();
        }
    }

    protected void validateForm(){
        View view = null;
        boolean cancelProcess = false;
        String name = this.name.getText().toString().trim(),
                phone = this.phone.getText().toString().trim(),
                bank_name = this.bank_name.getText().toString().trim(),
                bank_branch = this.bank_branch.getText().toString().trim(),
                bank_acc = this.bank_account.getText().toString().trim(),
                msg = null;

        if(isEmpty(name)){
            cancelProcess = true;
            view = this.name;
            msg = "Name can't be empty";
        }
        else if (!isValidNumber(phone)){
            cancelProcess = true;
            view = this.phone;
            msg ="Provide a valid number";

        }
        else if(isEmpty(bank_name)){
            cancelProcess = true;
            view = this.bank_name;
            msg = "Provide a valid bank name";
        }
        else if(isEmpty(bank_branch)){
            cancelProcess = true;
            view = this.bank_branch;
            msg="Provide a valid bank branch";
        }
        else if(isEmpty(bank_acc)){
            cancelProcess = true;
            view = this.bank_account;
            msg = "Provide a valid bank account number";
        }

        if (cancelProcess){
            view.requestFocus();
            makeToast(msg);
        }else{
            viewModel.modifyMentor(new Mentor(name,qua,sta,phone,bank_name,bank_branch,bank_acc));
        }
    }

    public void makeToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }
}
