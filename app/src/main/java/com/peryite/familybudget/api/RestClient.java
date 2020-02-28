package com.peryite.familybudget.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.peryite.familybudget.ui.views.InitialActivity;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

public class RestClient {
    private static RestClient ourInstance;

    private static final String BASE_URL = " https://family-budget-diploma.herokuapp.com";

    private Retrofit retrofit;
    private static Context context;


    public static RestClient getInstance() {
        if (ourInstance == null) {
            ourInstance = new RestClient();
        }
        return ourInstance;
    }

    public static RestClient addContext(Context newContext){
        context = newContext;

        return getInstance();
    }

    private RestClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(interceptor).build();

    }
}
