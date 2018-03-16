package com.coltek.cims.ui.notice;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coltek.cims.R;
import com.coltek.cims.entity.Notice;

import java.util.ArrayList;

/**
 * Created by BraDev ${LOCALE} on 3/15/2018.
 */

class NoticeRecycleViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private final ArrayList<Notice> notices;

    NoticeRecycleViewAdapter(ArrayList<Notice> notices) {
        this.notices = notices;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater newsListInflater = LayoutInflater.from(parent.getContext());
        View customView = newsListInflater.inflate(R.layout.notice_component, parent, false);
        return new ViewHolder(customView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Notice notice = this.notices.get(position);
        try {
            holder.title.setText(notice.getTitle());
            holder.description.setText(notice.getDescription());
            holder.date.setText(notice.getDate());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return notices.size();
    }
}
