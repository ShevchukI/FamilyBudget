package com.peryite.familybudget.ui.models;

import com.peryite.familybudget.api.RestClient;
import com.peryite.familybudget.api.repository.AlexaRepository;
import com.peryite.familybudget.api.repository.UserRepository;
import com.peryite.familybudget.entities.Credential;
import com.peryite.familybudget.entities.User;
import com.peryite.familybudget.entities.UserOTP;
import com.peryite.familybudget.ui.contracts.BudgetContract;
import com.peryite.familybudget.ui.listeners.BaseAPIRequestListener;
import com.peryite.familybudget.ui.listeners.BudgetListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BudgetModel implements BudgetContract.Model {

    private BudgetListener listener;
    private Credential credential;
    private User user;

    public BudgetModel(Credential credential){
        this.credential = credential;
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
                if(response.code() == 200){
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
                if(response.code()==200){
                    listener.sendAlexaCode(response.body().getOtp());
                } else {
                    listener.errorMessage("Error: "+response.code());
                }
            }

            @Override
            public void onFailure(Call<UserOTP> call, Throwable t) {
                listener.onFailure();

                call.cancel();
            }
        });
    }
}
