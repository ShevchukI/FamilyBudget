package com.peryite.familybudget.entities;

import com.google.gson.annotations.SerializedName;

public class User {
//    @SerializedName("id")
//    private Long id;
//    @SerializedName("firstName")
//    private String firstName;
//    @SerializedName("lastName")
//    private String lastName;
    @SerializedName("username")
    private String username;
//    @SerializedName("email")
//    private String email;
    @SerializedName("budget")
    private Double budget;
    @SerializedName("alexaAuth")
    private boolean alexaAuth;

    public User(){

    }
//
//    public User() {
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//


    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public boolean isAlexaAuth() {
        return alexaAuth;
    }

    public void setAlexaAuth(boolean alexaAuth) {
        this.alexaAuth = alexaAuth;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", budget=" + budget +
                ", alexaAuth=" + alexaAuth +
                '}';
    }
}
