package com.peryite.familybudget.api.users;

public class RestClient {
    private static final RestClient ourInstance = new RestClient();

    private static final String API_URL = " https://family-budget-diploma.herokuapp.com";


    public static RestClient getInstance() {
        return ourInstance;
    }

    private RestClient() {
    }
}
