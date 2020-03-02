package com.zapper.assessment.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.zapper.assessment.model.PersonsModel;
import com.zapper.assessment.repository.PersonRepository;

import java.util.List;

public class PersonDatabaseViewModel extends AndroidViewModel {
    private PersonRepository repository;
    private LiveData<List<PersonsModel.Person>> listMutableLiveData;

    public PersonDatabaseViewModel(@NonNull Application application) {
        super(application);
        repository = new PersonRepository(application);
        listMutableLiveData = repository.getAllData();
    }

    public void insert(List<PersonsModel.Person> personList) {
        repository.insert(personList);
    }


    public LiveData<List<PersonsModel.Person>> getAllData() {
        return listMutableLiveData;
    }
}