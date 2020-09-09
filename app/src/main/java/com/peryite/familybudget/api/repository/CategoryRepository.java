package com.peryite.familybudget.api.repository;

import com.peryite.familybudget.entities.BudgetCategory;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CategoryRepository {
    @GET("/category")
    Call<List<BudgetCategory>> getAllUserCategory();

    @DELETE("/category/{id}")
    Call<ResponseBody> deleteUserCategory(@Path("id") int id);

    @POST("/category")
    Call<BudgetCategory> createUserCategory(@Body BudgetCategory budgetCategory);

    @PUT("category/{id}")
    Call<BudgetCategory> updateUserCategory(@Path("id") int id, @Body BudgetCategory budgetCategory);
}
