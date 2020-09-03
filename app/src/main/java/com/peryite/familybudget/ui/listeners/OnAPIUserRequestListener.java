package com.peryite.familybudget.ui.listeners;

import com.peryite.familybudget.entities.User;

import java.util.List;

public interface OnAPIUserRequestListener extends BaseAPIRequestListener{
    void setUser(User user);

    void setUsers(List<User> users);
}
