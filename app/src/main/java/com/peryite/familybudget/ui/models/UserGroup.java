package com.peryite.familybudget.ui.models;

import com.google.gson.annotations.SerializedName;

public class UserGroup {
    @SerializedName("id")
    private Long id;
    @SerializedName("isActive")
    private boolean isActive;
    @SerializedName("isAdmin")
    private boolean isAdmin;
    @SerializedName("name")
    private String name;

    public UserGroup() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
