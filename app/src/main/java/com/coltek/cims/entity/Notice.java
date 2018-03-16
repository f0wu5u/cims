package com.coltek.cims.entity;

/**
 * Created by BraDev ${LOCALE} on 3/15/2018.
 */

public class Notice {
    private String title, description, date;

    public Notice(String title, String description, String date) {
        this.date = date;
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }
}
