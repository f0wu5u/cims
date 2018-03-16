package com.coltek.cims.ui.notice;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.coltek.cims.R;

/**
 * Created by BraDev ${LOCALE} on 3/15/2018.
 */

class ViewHolder extends RecyclerView.ViewHolder {

    TextView title, description, date;

    ViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.message_title);
        description = itemView.findViewById(R.id.message_desc);
        date = itemView.findViewById(R.id.message_time);
    }
}
