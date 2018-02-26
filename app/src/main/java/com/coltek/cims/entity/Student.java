package com.coltek.cims.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by BraDev on 11/9/2017.
 */

@Entity
public final class Student {
    @PrimaryKey
    @NonNull
    private String index;

    private String name, department,token, email;

    public Student(@NonNull String index, String name, String department, String token, String email) {
        this.index = index;
        this.name = name;
        this.department = department;
        this.token = token;
        this.email = email;
    }

    public String getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
