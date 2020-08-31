package com.peryite.familybudget.api.repository;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface HealthRepository {
    @GET("/health")
    Call<ResponseBody> isHealth();
}
