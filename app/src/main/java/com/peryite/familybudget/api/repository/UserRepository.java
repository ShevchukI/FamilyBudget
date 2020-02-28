package com.peryite.familybudget.api.repository;

import com.peryite.familybudget.ui.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserRepository {

    @POST("/api/users")
    Call<User> create(@Body User user);

    @GET("/api/users/info")
    Call<User> getInfo(@Query(value = "auth_token",encoded = true) String auth_token);
}
