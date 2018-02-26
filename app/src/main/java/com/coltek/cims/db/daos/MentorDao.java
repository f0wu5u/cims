package com.coltek.cims.db.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.coltek.cims.entity.Mentor;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;


@Dao
public interface MentorDao {
    @Insert(onConflict = REPLACE)
    void save(Mentor mentor);

    @Query("SELECT * FROM Mentor LIMIT 1")
    LiveData<Mentor> getProfile();
}
