package com.peryite.familybudget.ui.presenters;

import com.peryite.familybudget.api.RestClient;
import com.peryite.familybudget.api.repository.HealthRepository;
import com.peryite.familybudget.ui.BaseView;
import com.peryite.familybudget.ui.contracts.InitialContract;

import com.peryite.familybudget.ui.views.BudgetActivity;
import com.peryite.familybudget.ui.views.LoginActivity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InitialPresenter implements InitialContract.Presenter {
    private final String TAG = this.getClass().getSimpleName();

    private InitialContract.View view;
    private InitialContract.Model model;

    public InitialPresenter(InitialContract.Model model) {
        this.model = model;
    }

    @Override
    public void attachView(BaseView view) {
        this.view = (InitialContract.View) view;
    }

    public void detachView() {
        view = null;
    }

    @Override
    public void start() {

        view.showProgress();
        HealthRepository healthRepository = RestClient.getClient().create(HealthRepository.class);
        Call<ResponseBody> healthCall = healthRepository.isHealth();

        healthCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (model.isVisitedUser()) {
                        //TODO: created open BudgetActivity
                        view.openActivity(BudgetActivity.class);
                    } else {
                        view.openActivity(LoginActivity.class);
                    }
                } else {
                    view.hideProgress();
                    view.showMessage("Something wrong :( Error code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                view.hideProgress();
                view.showMessage("The server does not respond. Please try restart application");
            }
        });
    }

    public InitialContract.View getView() {
        return view;
    }

    public void setView(InitialContract.View view) {
        this.view = view;
    }

}
