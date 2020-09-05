package com.peryite.familybudget.api.repository;

import com.peryite.familybudget.entities.UserOTP;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AlexaRepository {
    @GET("/otp")
    Call<UserOTP> getUserOTP();
}
