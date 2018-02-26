package com.coltek.cims.db.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.coltek.cims.entity.School;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by BraDev ${LOCALE} on 1/10/2018.
 */
@Dao
public interface SchoolDao {
    @Update(onConflict = REPLACE)
    void updateSchool(School school);

    @Insert(onConflict = REPLACE)
    void save(School school);

    @Query("SELECT * FROM School LIMIT 1")
    LiveData<School> getSchool();
}
