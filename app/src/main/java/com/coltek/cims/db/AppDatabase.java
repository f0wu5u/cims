package com.coltek.cims.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.coltek.cims.db.daos.MentorDao;
import com.coltek.cims.db.daos.SchoolDao;
import com.coltek.cims.db.daos.StudentDao;
import com.coltek.cims.entity.Mentor;
import com.coltek.cims.entity.School;
import com.coltek.cims.entity.Student;

/**
 * Created by BraDev ${LOCALE} on 1/10/2018.
 */
@Database(entities = {Student.class, School.class, Mentor.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract StudentDao studentDao();
    public abstract SchoolDao schoolDao();
    public abstract MentorDao mentorDao();

}
