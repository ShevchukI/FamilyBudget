package com.peryite.familybudget.api.repository;

import com.peryite.familybudget.ui.models.Credential;
import com.peryite.familybudget.ui.models.Login;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthorizationRepository {

    @POST("/api/auth/login")
    Call<Credential> login(@Body Login login);
}
