package com.coltek.cims.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity()
public final class Mentor {

    @PrimaryKey
    @NonNull
    private String index_number;

    private String full_name,
            qualification,
            status,
            phone,
            bank_name,
            bank_branch,
            bank_acc_number;

    public Mentor(String full_name, String qualification, String status, String phone, String bank_name, String bank_branch, String bank_acc_number) {
        this.full_name = full_name;
        this.qualification = qualification;
        this.status = status;
        this.phone = phone;
        this.bank_name = bank_name;
        this.bank_branch = bank_branch;
        this.bank_acc_number = bank_acc_number;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(@NonNull String phone) {
        this.phone = phone;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(@NonNull String full_name) {
        this.full_name = full_name;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(@NonNull String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_branch() {
        return bank_branch;
    }

    public void setBank_branch(@NonNull String bank_branch) {
        this.bank_branch = bank_branch;
    }

    public String getBank_acc_number() {
        return bank_acc_number;
    }

    public void setBank_acc_number(@NonNull String bank_acc_number) {
        this.bank_acc_number = bank_acc_number;
    }

    @NonNull
    public String getIndex_number() {
        return index_number;
    }

    public void setIndex_number(@NonNull String index_number) {
        this.index_number = index_number;
    }

    @Override
    public String toString() {
        return this.full_name;
    }
}
