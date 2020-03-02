package com.zapper.assessment.database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.zapper.assessment.model.PersonsModel;
import com.zapper.assessment.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface PersonDao {
    @Insert
    void insert(List<PersonsModel.Person> listMutableLiveData);

    @Update
    void update(PersonsModel.Person person);

    @Delete
    void delete(PersonsModel.Person person);

    @Query("DELETE FROM "+ AppConstants.TABLE_PERSON)
    void deleteAllNotes();

    @Query("SELECT * FROM "+AppConstants.TABLE_PERSON)
    LiveData<List<PersonsModel.Person>> getAllData();

}
