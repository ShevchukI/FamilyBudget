package com.peryite.familybudget.ui.models;

import com.peryite.familybudget.entities.Credential;
import com.peryite.familybudget.entities.User;
import com.peryite.familybudget.ui.contracts.InitialContract;
import com.peryite.familybudget.ui.views.BudgetActivity;
import com.peryite.familybudget.ui.views.LoginActivity;
import com.peryite.familybudget.utils.GsonUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InitialModel implements InitialContract.Model {
    private boolean hasVisited;
    private String credential;

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
}
