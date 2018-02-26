package com.coltek.cims.db.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.coltek.cims.entity.Student;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by BraDev ${LOCALE} on 1/10/2018.
 */
@Dao
public interface StudentDao {
    @Insert(onConflict = REPLACE)
    void save(Student student);

    @Query("SELECT * FROM Student LIMIT 1")
    LiveData<Student> getProfile();

    @Query("UPDATE Student SET email = :email")
    void updateContact(String email);
}
