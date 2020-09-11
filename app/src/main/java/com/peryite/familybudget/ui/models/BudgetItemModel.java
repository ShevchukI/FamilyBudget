package com.peryite.familybudget.ui.models;

import com.peryite.familybudget.api.RestClient;
import com.peryite.familybudget.api.repository.ItemRepository;
import com.peryite.familybudget.entities.BudgetCategory;
import com.peryite.familybudget.entities.Credential;
import com.peryite.familybudget.entities.Item;
import com.peryite.familybudget.ui.contracts.BudgetItemContract;
import com.peryite.familybudget.ui.listeners.BaseAPIRequestListener;
import com.peryite.familybudget.ui.listeners.OnAPIItemRequestListener;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BudgetItemModel implements BudgetItemContract.Model {

    private OnAPIItemRequestListener listener;
    private Credential credential;
    private ItemRepository itemRepository;
    private BudgetCategory budgetCategory;

    public BudgetItemModel(Credential credential, BudgetCategory budgetCategory) {
        this.credential = credential;
        this.budgetCategory = budgetCategory;
        itemRepository = RestClient.getClient(credential).create(ItemRepository.class);
    }

    @Override
    public void setListener(BaseAPIRequestListener listener) {
        this.listener = (OnAPIItemRequestListener) listener;
    }

    @Override
    public void getItems() {
        Call<List<Item>> itemCall = itemRepository.getCategoryItems(budgetCategory.getId());
        itemCall.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.code() == 200) {
                    listener.setItems(response.body());
                } else {
                    listener.onResponse();
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                listener.onFailure();
            }
        });
    }

    @Override
    public void deleteItem(int id) {
        Call<ResponseBody> itemCall = itemRepository.deleteItem(id);
        itemCall.enqueue(new Callback<ResponseBody>() {
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
    public void createItem(Item item) {
        Call<Item> itemCall = itemRepository.createItem(item.getBudgetCategory().getId(), item);
        itemCall.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                if (response.code() == 200) {
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

    @Override
    public void updateItem(Item item) {
        Call<Item> itemCall = itemRepository.updateItem(item.getId(), item);
        itemCall.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                if(response.body()!=null){
                    listener.doRefresh();
                }
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                listener.onFailure();
            }
        });
    }
}
