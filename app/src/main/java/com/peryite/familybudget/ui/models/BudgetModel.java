package com.peryite.familybudget.ui.models;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.peryite.familybudget.api.RestClient;
import com.peryite.familybudget.api.repository.AlexaRepository;
import com.peryite.familybudget.api.repository.CategoryRepository;
import com.peryite.familybudget.api.repository.ItemRepository;
import com.peryite.familybudget.api.repository.UserRepository;
import com.peryite.familybudget.dbhelper.DBConverter;
import com.peryite.familybudget.dbhelper.dao.CategoryDAO;
import com.peryite.familybudget.dbhelper.dao.ItemDAO;
import com.peryite.familybudget.entities.BudgetCategory;
import com.peryite.familybudget.entities.CategoryItem;
import com.peryite.familybudget.entities.Credential;
import com.peryite.familybudget.entities.Item;
import com.peryite.familybudget.entities.User;
import com.peryite.familybudget.entities.UserOTP;
import com.peryite.familybudget.ui.contracts.BudgetContract;
import com.peryite.familybudget.ui.listeners.BaseAPIRequestListener;
import com.peryite.familybudget.ui.listeners.BudgetListener;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BudgetModel implements BudgetContract.Model {

    private BudgetListener listener;
    private Credential credential;
    private Context context;
    private User user;
    private CategoryDAO categoryDAO;
    private ItemDAO itemDAO;
    private DBConverter dbConverter;
    private CategoryRepository categoryRepository;
    private ItemRepository itemRepository;

    public BudgetModel(Credential credential) {
        this.credential = credential;
    }

    public BudgetModel(Credential credential, Context context) {
        this.credential = credential;
        this.context = context;

        dbConverter = DBConverter.getInstance(context);
        categoryDAO = dbConverter.getCategoryDAO();
        itemDAO = dbConverter.getItemDAO();
    }

    @Override
    public void setListener(BaseAPIRequestListener listener) {
        this.listener = (BudgetListener) listener;
    }

    @Override
    public void getUser() {
        UserRepository userRepository = RestClient.getClient(credential).create(UserRepository.class);
        Call<User> userCall = userRepository.getUser();
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    listener.setUser(response.body());
                    setUser(response.body());
                } else {
                    listener.onResponse(response.code());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                listener.onFailure();
            }
        });
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void getAlexaCode() {
        AlexaRepository alexaRepository = RestClient.getClient(credential).create(AlexaRepository.class);
        Call<UserOTP> alexaCall = alexaRepository.getUserOTP();
        alexaCall.enqueue(new Callback<UserOTP>() {
            @Override
            public void onResponse(Call<UserOTP> call, Response<UserOTP> response) {
                if (response.code() == 200) {
                    listener.sendAlexaCode(response.body().getOtp());
                } else {
                    listener.errorMessage("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<UserOTP> call, Throwable t) {
                listener.onFailure();

                call.cancel();
            }
        });
    }

    @Override
    public void addBudget(Item budget) {
        ItemRepository itemRepository = RestClient.getClient(credential).create(ItemRepository.class);
        Call<Item> itemCall = itemRepository.addBudget(budget);
        itemCall.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        listener.updateUser();
                    }
                } else {
                    listener.onResponse();
                }
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                listener.onFailure();
            }
        });
        //TODO: addBudget request!
    }

    @Override
    public void reloadDB() {
//        DBLoader dbLoader = new DBLoader();
//        dbLoader.execute();
    }
}
