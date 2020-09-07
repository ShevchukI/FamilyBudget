package com.peryite.familybudget.api.repository;

import com.peryite.familybudget.entities.BudgetCategory;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryRepository {
    @GET("/category")
    Call<List<BudgetCategory>> getAllUserCategory();
}
