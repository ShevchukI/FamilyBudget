package com.peryite.familybudget.ui.models;


import com.peryite.familybudget.api.RestClient;
import com.peryite.familybudget.api.repository.CategoryRepository;
import com.peryite.familybudget.entities.BudgetCategory;
import com.peryite.familybudget.entities.Credential;
import com.peryite.familybudget.ui.contracts.BudgetCategoryContract;
import com.peryite.familybudget.ui.listeners.BaseAPIRequestListener;
import com.peryite.familybudget.ui.listeners.OnAPICategoryRequestListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BudgetCategoryModel implements BudgetCategoryContract.Model {
    private OnAPICategoryRequestListener listener;
    private Credential credential;

    public BudgetCategoryModel(Credential credential){
        this.credential = credential;
    }

    @Override
    public void setListener(BaseAPIRequestListener listener) {
        this.listener = (OnAPICategoryRequestListener) listener;
    }

    @Override
    public void getCategories() {
        CategoryRepository categoryRepository = RestClient.getClient(credential).create(CategoryRepository.class);
        Call<List<BudgetCategory>> categoryCall = categoryRepository.getAllUserCategory();
        categoryCall.enqueue(new Callback<List<BudgetCategory>>() {
            @Override
            public void onResponse(Call<List<BudgetCategory>> call, Response<List<BudgetCategory>> response) {
                if(response.code() ==200){
                    listener.setCategories(response.body());
                } else {
                    listener.onResponse();
                }
            }

            @Override
            public void onFailure(Call<List<BudgetCategory>> call, Throwable t) {
                listener.onFailure();
            }
        });

    }
}
