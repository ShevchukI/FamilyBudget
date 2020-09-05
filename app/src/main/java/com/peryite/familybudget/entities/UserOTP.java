package com.peryite.familybudget.entities;

import com.google.gson.annotations.SerializedName;

public class UserOTP {
    @SerializedName("username")
    private String username;
    @SerializedName("otp")
    private String otp;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    @Override
    public String toString() {
        return "UserOTP{" +
                "username='" + username + '\'' +
                ", otp='" + otp + '\'' +
                '}';
    }
}
