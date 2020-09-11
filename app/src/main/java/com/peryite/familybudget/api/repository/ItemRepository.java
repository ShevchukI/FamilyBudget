package com.peryite.familybudget.api.repository;

import com.peryite.familybudget.entities.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ItemRepository {
    @GET("/item/{categoryId}")
    Call<List<Item>> getCategoryItems(@Path("categoryId") int categoryId);
}
