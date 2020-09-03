package com.peryite.familybudget.ui.models;

import android.content.SharedPreferences;

import com.peryite.familybudget.api.RestClient;
import com.peryite.familybudget.api.repository.UserRepository;
import com.peryite.familybudget.entities.Credential;
import com.peryite.familybudget.entities.User;
import com.peryite.familybudget.ui.contracts.LoginContract;
import com.peryite.familybudget.ui.listeners.BaseAPIRequestListener;
import com.peryite.familybudget.ui.listeners.OnAPIUserRequestListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginModel implements LoginContract.Model {
    private SharedPreferences preferencesCredential;
    private SharedPreferences preferencesHasVisited;
    private OnAPIUserRequestListener listener;

    public LoginModel(SharedPreferences preferencesCredential, SharedPreferences preferencesHasVisited) {
        this.preferencesCredential = preferencesCredential;
        this.preferencesHasVisited = preferencesHasVisited;
    }


    @Override
    public boolean isSignInSuccessful(String username, String password) {
        return false;
    }

    @Override
    public void getUser(String login, String password) {

        UserRepository userRepository = RestClient.getClient(new Credential(login, password)).create(UserRepository.class);
        Call<User> userCall = userRepository.getUser();

        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    listener.setUser(response.body());
                } else if(response.code() == 401){
                    listener.setUser(null);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                listener.onFailure();
            }
        });

    }

    @Override
    public void setListener(BaseAPIRequestListener listener) {
        this.listener = (OnAPIUserRequestListener) listener;
    }
}
