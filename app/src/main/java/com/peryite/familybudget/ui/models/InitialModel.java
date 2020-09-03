package com.peryite.familybudget.ui.models;

import com.peryite.familybudget.ui.contracts.InitialContract;
import com.peryite.familybudget.ui.listeners.BaseAPIRequestListener;

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

    @Override
    public void setListener(BaseAPIRequestListener listener) {

    }
}
