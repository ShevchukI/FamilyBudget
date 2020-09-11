package com.peryite.familybudget.api.repository;

import com.peryite.familybudget.entities.Item;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ItemRepository {
    @GET("/item/{categoryId}")
    Call<List<Item>> getCategoryItems(@Path("categoryId") int categoryId);

    @POST("/item/{categoryId}")
    Call<Item> createItem(@Path("categoryId") int categoryId, @Body Item item);

    @DELETE("/item/{itemId}")
    Call<ResponseBody> deleteItem(@Path("itemId") int id);

    @PUT("/item/{itemId}")
    Call<Item> updateItem(@Path("itemId") int id, @Body Item item);
}
