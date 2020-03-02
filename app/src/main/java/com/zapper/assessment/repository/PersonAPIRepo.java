package com.zapper.assessment.repository;

import com.zapper.assessment.model.PersonsModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PersonAPIRepo {

    @GET("/persons")
    Call<PersonsModel> getPersonData();
}
