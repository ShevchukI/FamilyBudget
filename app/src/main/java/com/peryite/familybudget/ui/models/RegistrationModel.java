package com.peryite.familybudget.ui.models;

import android.util.Log;

import com.peryite.familybudget.api.RestClient;
import com.peryite.familybudget.api.repository.AlexaRepository;
import com.peryite.familybudget.api.repository.UserRepository;
import com.peryite.familybudget.entities.Credential;
import com.peryite.familybudget.entities.Login;
import com.peryite.familybudget.entities.UserOTP;
import com.peryite.familybudget.ui.contracts.RegistrationContract;
import com.peryite.familybudget.ui.listeners.BaseAPIRequestListener;
import com.peryite.familybudget.ui.listeners.OnAPIRegistrationRequestListener;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationModel implements RegistrationContract.Model {
    private final String TAG = this.getClass().getSimpleName();

    private OnAPIRegistrationRequestListener listener;

    public RegistrationModel(){

    }

    @Override
    public void setListener(BaseAPIRequestListener listener) {
        this.listener = (OnAPIRegistrationRequestListener) listener;
    }

    @Override
    public void registerNewUser(final Login login) {
        UserRepository userRepository = RestClient.getClient().create(UserRepository.class);
        Call<ResponseBody> loginCall = userRepository.registration(login);
        loginCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200) {
                    listener.onResponse();
                } else if(response.code() == 400){
                    listener.errorMessage("User already exist!");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
               listener.onFailure();

               call.cancel();
            }
        });
       
    }

    @Override
    public void requestAlexaCode(Login login) {
        AlexaRepository alexaRepository = RestClient.getClient(new Credential(login.getUsername(), login.getPassword())).create(AlexaRepository.class);
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