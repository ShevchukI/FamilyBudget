package com.peryite.familybudget.ui.listeners;

public interface BudgetListener extends OnAPIUserRequestListener{
    void onResponse(int code);

    void sendAlexaCode(String text);

    void errorMessage(String message);
}
