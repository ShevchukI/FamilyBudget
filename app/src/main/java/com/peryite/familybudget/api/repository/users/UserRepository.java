package com.peryite.familybudget.api.repository.users;

import com.peryite.familybudget.api.ApiService;
import com.peryite.familybudget.ui.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserRepository extends ApiService {

    @POST("/api/users")
    public Call<User> regisration(@Body User user);
}
