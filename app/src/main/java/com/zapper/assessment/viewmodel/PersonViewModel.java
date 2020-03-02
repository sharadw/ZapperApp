package com.zapper.assessment.viewmodel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.zapper.assessment.model.PersonsModel;
import com.zapper.assessment.repository.APIClient;
import com.zapper.assessment.repository.PersonAPIRepo;
import com.zapper.assessment.repository.PersonRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("unchecked")
public class PersonViewModel extends ViewModel {

    public String personId;
    public String personName;
    private Application mContext;
    public MutableLiveData<ArrayList<PersonViewModel>> mutableLiveData = new MutableLiveData<>();
    public MutableLiveData<List<PersonsModel.Person>> listMutableLiveData = new MutableLiveData<>();
    private ArrayList<PersonViewModel> arrayList;
    private PersonRepository mPersonDBRepository;


    public PersonViewModel(Application mContext, PersonsModel.Person personsModel) {
        this.mContext = mContext;
        this.personId = ""+personsModel.id;
        this.personName = personsModel.name;
        mPersonDBRepository = new PersonRepository(mContext);
    }

    public MutableLiveData<ArrayList<PersonViewModel>> makeRequest() {
        arrayList = new ArrayList<>();

            PersonAPIRepo mApiClient = APIClient.getInstance().getAPIService();
            Call<PersonsModel> personsModel = mApiClient.getPersonData();

            personsModel.enqueue(new Callback<PersonsModel>() {
                @Override
                public void onResponse(Call<PersonsModel> call, Response<PersonsModel> response) {
                    if (response.isSuccessful()) {
                        PersonsModel model = response.body();
                        List<PersonsModel.Person> models = model.getPersons();
                        if (model.getPersons().size() > 0) {

                            for (PersonsModel.Person mPerson : models) {
                                arrayList.add(new PersonViewModel(mContext, mPerson));
                                mutableLiveData.setValue(arrayList);

                            }
                            //insert in to Database
                            mPersonDBRepository.insert(models);
                        }

                    }
                }

                @Override
                public void onFailure(Call<PersonsModel> call, Throwable t) {
                    Log.e("ZAPPER", "" + t.getMessage());
                }
            });


        return mutableLiveData;

    }
}
