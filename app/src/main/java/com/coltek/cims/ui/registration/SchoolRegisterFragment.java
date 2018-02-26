package com.coltek.cims.ui.registration;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coltek.cims.R;
import com.coltek.cims.di.modules.Injectable;


/**
 * A simple {@link Fragment} subclass.
 */
public class SchoolRegisterFragment extends Fragment implements Injectable {


    public SchoolRegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.school_register_fragment, container, false);
    }

}
