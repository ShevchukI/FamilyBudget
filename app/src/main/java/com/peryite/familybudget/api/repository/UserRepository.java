package com.peryite.familybudget.api.repository;

import com.peryite.familybudget.entities.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserRepository {

    @POST("/api/users")
    Call<User> create(@Body User user);

    @GET("/api/users/info")
    Call<User> getInfo(@Header("Authorization") String token);
}
