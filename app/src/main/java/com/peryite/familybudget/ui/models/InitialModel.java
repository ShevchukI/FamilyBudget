package com.peryite.familybudget.ui.models;

import com.peryite.familybudget.api.RestClient;
import com.peryite.familybudget.api.repository.HealthRepository;
import com.peryite.familybudget.ui.contracts.InitialContract;
import com.peryite.familybudget.ui.listeners.BaseAPIRequestListener;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InitialModel implements InitialContract.Model {
    private boolean hasVisited;
    private String credential;
    private BaseAPIRequestListener listener;

    public InitialModel(boolean hasVisited, String credential) {
        this.hasVisited = hasVisited;
        this.credential = credential;
    }


    @Override
    public boolean isVisitedUser() {
        if (!hasVisited) {
            return false;
        } else {
            //TODO authorization
            if (credential.equals("empty")) {
                return false;
            } else {
                return true;
            }
        }
    }

    @Override
    public void checkHealth() {
        HealthRepository healthRepository = RestClient.getClient().create(HealthRepository.class);
        Call<ResponseBody> healthCall = healthRepository.isHealth();

        healthCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
               listener.onResponse();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                listener.onFailure();
            }
        });
    }

    @Override
    public void setListener(BaseAPIRequestListener listener) {
        this.listener = listener;
    }
}
