package com.peryite.familybudget.ui.models;


import android.support.v7.widget.RecyclerView;

import com.peryite.familybudget.api.RestClient;
import com.peryite.familybudget.api.repository.CategoryRepository;
import com.peryite.familybudget.api.repository.ItemRepository;
import com.peryite.familybudget.entities.BudgetCategory;
import com.peryite.familybudget.entities.Credential;
import com.peryite.familybudget.entities.Item;
import com.peryite.familybudget.ui.contracts.BudgetCategoryContract;
import com.peryite.familybudget.ui.listeners.BaseAPIRequestListener;
import com.peryite.familybudget.ui.listeners.OnAPICategoryRequestListener;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BudgetCategoryModel implements BudgetCategoryContract.Model {
    private OnAPICategoryRequestListener listener;
    private Credential credential;
    private CategoryRepository categoryRepository;

    public BudgetCategoryModel(Credential credential) {
        this.credential = credential;
        categoryRepository = RestClient.getClient(credential).create(CategoryRepository.class);
    }

    @Override
    public void setListener(BaseAPIRequestListener listener) {
        this.listener = (OnAPICategoryRequestListener) listener;
    }

    @Override
    public void getCategories() {
        Call<List<BudgetCategory>> categoryCall = categoryRepository.getAllUserCategory();
        categoryCall.enqueue(new Callback<List<BudgetCategory>>() {
            @Override
            public void onResponse(Call<List<BudgetCategory>> call, Response<List<BudgetCategory>> response) {
                if (response.code() == 200) {
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

    @Override
    public void deleteCategory(int id) {
        Call<ResponseBody> categoryCall = categoryRepository.deleteUserCategory(id);
        categoryCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    listener.doRefresh();
                } else {
                    listener.onResponse();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                listener.onFailure();
            }
        });
    }

    @Override
    public void createCategory(BudgetCategory budgetCategory) {
        Call<BudgetCategory> categoryCall = categoryRepository.createUserCategory(budgetCategory);
        categoryCall.enqueue(new Callback<BudgetCategory>() {
            @Override
            public void onResponse(Call<BudgetCategory> call, Response<BudgetCategory> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        listener.doRefresh();
                    }
                } else {
                    listener.onResponse();
                }
            }

            @Override
            public void onFailure(Call<BudgetCategory> call, Throwable t) {
                listener.onFailure();
            }
        });
    }

    @Override
    public void updateCategory(BudgetCategory budgetCategory) {
        Call<BudgetCategory> categoryCall = categoryRepository.updateUserCategory(budgetCategory.getId(), budgetCategory);
        categoryCall.enqueue(new Callback<BudgetCategory>() {
            @Override
            public void onResponse(Call<BudgetCategory> call, Response<BudgetCategory> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        listener.doRefresh();
                    }
                } else {
                    listener.onResponse();
                }
            }

            @Override
            public void onFailure(Call<BudgetCategory> call, Throwable t) {
                listener.onFailure();
            }
        });
    }

    @Override
    public void addItemToCategory(Item item) {
        ItemRepository itemRepository = RestClient.getClient(credential).create(ItemRepository.class);
        Call<Item> itemCall = itemRepository.createItem(item.getId(), item);
        itemCall.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                if(response.code() == 200){
                    listener.doRefresh();
                } else {
                    listener.onResponse();
                }
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                listener.onFailure();
            }
        });
    }
}
