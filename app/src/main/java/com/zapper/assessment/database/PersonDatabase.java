package com.zapper.assessment.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.zapper.assessment.model.PersonsModel;
import com.zapper.assessment.utils.AppConstants;

@Database(entities = {PersonsModel.Person.class}, version = 1)
public abstract class PersonDatabase extends RoomDatabase {

    private static PersonDatabase instance;

    public abstract PersonDao personDao();

    public static synchronized PersonDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                     PersonDatabase.class, AppConstants.TABLE_PERSON)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
