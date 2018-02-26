package com.coltek.cims.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by BraDev on 11/9/2017.
 */
@Entity
public final class School {

    @PrimaryKey
    @NonNull
    private String index_number;

    @NonNull
    private String name,

    region,
    district,
    town,number;

    public School(@NonNull String region, String district, @NonNull String name, String town,String number) {
        this.region = region;
        this.district = district;
        this.name = name;
        this.town = town;
        this.number = number;
    }

    public String getIndex_number() {
        return index_number;
    }

    public void setIndex_number(String index_number) {
        this.index_number = index_number;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    @NonNull
    public String getNumber() {
        return number;
    }

    public void setNumber(@NonNull String number) {
        this.number = number;
    }
}
