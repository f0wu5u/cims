package com.coltek.cims.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by BraDev on 11/9/2017.
 */
@Entity
public class School {

    @PrimaryKey
    @NonNull
    private String index_number;

    @NonNull
    private String name,
    region,
    district,
    town,number;

    public School(@NonNull String region, String district, @NonNull String name, String town, String number, String index_number) {
        this.region = region;
        this.district = district;
        this.name = name;
        this.town = town;
        this.number = number;
        this.index_number = index_number;
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


    public String getDistrict() {
        return district;
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

    @NonNull
    public String getNumber() {
        return number;
    }

    public void setNumber(@NonNull String number) {
        this.number = number;
    }
}
