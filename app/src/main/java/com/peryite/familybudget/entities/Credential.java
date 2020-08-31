package com.peryite.familybudget.entities;

import com.google.gson.annotations.SerializedName;

public class Credential {
    @SerializedName("username")
    private String username;
    @SerializedName("token")
    private String token;

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }

    public String getBearerToken() {
        return "Bearer " + token;
    }

    public String getBasicAuth(){
        return username + ":" + token;
    }
}