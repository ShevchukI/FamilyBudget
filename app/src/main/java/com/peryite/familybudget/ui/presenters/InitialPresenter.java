package com.peryite.familybudget.ui.presenters;

import android.util.Log;

import com.peryite.familybudget.api.RestClient;
import com.peryite.familybudget.api.repository.UserRepository;
import com.peryite.familybudget.ui.contracts.InitialContract;
import com.peryite.familybudget.ui.models.Credential;
import com.peryite.familybudget.ui.models.User;
import com.peryite.familybudget.ui.views.BudgetActivity;
import com.peryite.familybudget.ui.views.LoginActivity;
import com.peryite.familybudget.utils.GsonUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InitialPresenter implements InitialContract.Presenter {
    private final String TAG = this.getClass().getSimpleName();

    private InitialContract.View view;
    private boolean hasVisited;
    private String jsonCredential;

    private UserRepository userRepository;

    public InitialPresenter() {
        userRepository = RestClient.getClient().create(UserRepository.class);
    }

    public InitialPresenter(InitialContract.View view, boolean hasVisited) {
        Log.d(TAG, "InitialPresenter: created with view = " + view + " and hasVisited = " + hasVisited);
        this.view = view;
        this.hasVisited = hasVisited;
    }

    @Override
    public void start() {
        Log.d(TAG, "start: hasVisited = " + hasVisited);
        view.showProgress();

        if (!hasVisited) {
            view.openActivity(LoginActivity.class);
        } else {
            //TODO authorization
            if (!jsonCredential.equals("empty")) {
                Credential credential = (Credential) GsonUtil.fromJson(jsonCredential, Credential.class);
                Call<User> call = userRepository.getInfo(credential.getBearerToken());
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful()){
                            User user = response.body();
                            if(user!=null){
                                view.openActivity(BudgetActivity.class);
                            }
                        } else {
                            view.openActivity(LoginActivity.class);
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        view.openActivity(LoginActivity.class);
                        call.cancel();
                    }
                });
            } else {
                view.openActivity(LoginActivity.class);
            }
        }
    }

    public static class Builder {
        private InitialContract.View view;
        private boolean hasVisited;
        private String jsonCredential;

        public Builder withView(InitialContract.View view) {
            this.view = view;

            return this;
        }

        public Builder withVisited(boolean hasVisited) {
            this.hasVisited = hasVisited;

            return this;
        }

        public Builder withCredential(String jsonCredential) {
            this.jsonCredential = jsonCredential;

            return this;
        }

        public InitialPresenter build() {
            InitialPresenter initialPresenter = new InitialPresenter();
            initialPresenter.setView(this.view);
            initialPresenter.setHasVisited(this.hasVisited);
            initialPresenter.setJsonCredential(this.jsonCredential);

            return initialPresenter;
        }
    }

    public InitialContract.View getView() {
        return view;
    }

    public void setView(InitialContract.View view) {
        this.view = view;
    }

    public boolean isHasVisited() {
        return hasVisited;
    }

    public void setHasVisited(boolean hasVisited) {
        this.hasVisited = hasVisited;
    }

    public String getJsonCredential() {
        return jsonCredential;
    }

    public void setJsonCredential(String jsonCredential) {
        this.jsonCredential = jsonCredential;
    }
}
