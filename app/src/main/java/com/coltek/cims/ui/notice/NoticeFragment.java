package com.coltek.cims.ui.notice;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coltek.cims.R;
import com.coltek.cims.entity.Notice;

import java.util.ArrayList;
import java.util.Date;


public class NoticeFragment extends Fragment {

    ArrayList<Notice> mNotices;

    public NoticeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mNotices = new ArrayList<>();
        String date = new Date().toString();
        mNotices.add(new Notice("Hello world", getString(R.string.large_text), date));
        mNotices.add(new Notice("Hello world 2", getString(R.string.large_text), date));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notice, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        if (view != null) {
            RecyclerView recyclerView = view.findViewById(R.id.notice_recycleview);
            recyclerView.setAdapter(new NoticeRecycleViewAdapter(mNotices));
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        }
    }
}
