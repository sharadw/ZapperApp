package com.zapper.assessment.repository;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.zapper.assessment.database.PersonDao;
import com.zapper.assessment.database.PersonDatabase;
import com.zapper.assessment.model.PersonsModel;

import java.util.ArrayList;
import java.util.List;

public class PersonRepository  {

    private PersonDao personDao;
    private LiveData<List<PersonsModel.Person>> listMutableLiveData;

    public PersonRepository(Application application){
        PersonDatabase database = PersonDatabase.getInstance(application);
        personDao = database.personDao();
        listMutableLiveData = personDao.getAllData();
    }

    public void insert(List<PersonsModel.Person> listMutableLiveData){

        new InsertDataAsyncTask(personDao).execute(listMutableLiveData);

    }

    public LiveData<List<PersonsModel.Person>> getAllData(){

        return listMutableLiveData;

    }


    private static class InsertDataAsyncTask extends AsyncTask<List<PersonsModel.Person>,Void,Void>{

        private PersonDao mPersonDao;

        private InsertDataAsyncTask(PersonDao mPersonDao){
            this.mPersonDao = mPersonDao;
        }



        @Override
        protected Void doInBackground(List<PersonsModel.Person>... mutableLiveData) {
            mPersonDao.insert(mutableLiveData[0]);
            return null;
        }
    }


}
