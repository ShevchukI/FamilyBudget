package com.peryite.familybudget.ui.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.peryite.familybudget.R;
import com.peryite.familybudget.entities.Credential;
import com.peryite.familybudget.entities.User;
import com.peryite.familybudget.ui.contracts.BudgetContract;
import com.peryite.familybudget.ui.models.BudgetModel;
import com.peryite.familybudget.ui.presenters.BudgetPresenter;
import com.peryite.familybudget.ui.views.fragments.BaseFragment;
import com.peryite.familybudget.ui.views.fragments.BudgetCategoryFragment;
import com.peryite.familybudget.ui.views.fragments.FragmentManager;
import com.peryite.familybudget.utils.GsonUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BudgetActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, BudgetContract.View {

    private final String TAG = this.getClass().getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private AppCompatTextView navHeaderUsername;

    private SharedPreferences preferencesCredential;
    private Credential credential;
    private Fragment currentFragment;
    private final static int MAIN_CONTAINER_ID = R.id.fragment_container;
    private BudgetContract.Presenter presenter;
    private int selectedId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);
        setSupportActionBar(toolbar);

        init();

    }

    private void init() {
        preferencesCredential = getSharedPreferences("credential", MODE_PRIVATE);
        String jsonCredential = preferencesCredential.getString("credential", "empty");

        if (jsonCredential.equals("empty")) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else {
            credential = (Credential) GsonUtil.fromJson(jsonCredential, Credential.class);
        }

        unbinder = ButterKnife.bind(this);

        navHeaderUsername = navigationView.getHeaderView(0).findViewById(R.id.navigation_header_username);
        // navHeaderUsername.setText(credential.getUsername());
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        toolbar.inflateMenu(R.menu.budget);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                int id = item.getItemId();

                switch (id) {
                    case R.id.action_refresh:
                        presenter.onClickRefresh();
                        break;
                    case R.id.action_settings:
                        presenter.onClickSetting();
                        break;
                    default:
                        break;
                }

                return false;
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        BudgetModel budgetModel = new BudgetModel(credential);
        presenter = new BudgetPresenter(budgetModel);
        presenter.attachView(this);
        presenter.start();

        //  selectFragment(FragmentManager.FragmentSelect.BudgetCategory);
//        fragment = new BudgetCategoryFragment();
//
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

//        fragment = (FragmentRecycleViewBudgetCategory)getSupportFragmentManager().findFragmentById(R.id.budget_categories);
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fragment_container, fragment)
//                .commit();

//        userRepository = RestClient.getClient().create(UserRepository.class);
//
//        if (!jsonCredential.equals("empty")) {
//            credential = (Credential) GsonUtil.fromJson(jsonCredential, Credential.class);
//            navHeaderUsername.setText(credential.getUsername());
//
//            Call<User> userCall = userRepository.getInfo(credential.getBearerToken());
//            userCall.enqueue(new Callback<User>() {
//                @Override
//                public void onResponse(Call<User> call, Response<User> response) {
//                    if (response.isSuccessful()) {
//                        User user = response.body();
//                        Toast.makeText(getApplicationContext(), user.toString(), Toast.LENGTH_LONG).show();
//
//                        Log.d(TAG, user.toString());
//                    } else {
//                        Log.d(TAG, "onResponse: not successful");
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<User> call, Throwable t) {
//                    Log.d(TAG, "fail response!");
//                    call.cancel();
//                }
//            });
//
//            Log.d(TAG, "");
//        }

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        selectedId = id;

        if (id == R.id.nav_category) {
            presenter.onClickBudgetCategory();
        } else if (id == R.id.nav_gallery) {
            //removeFragment();
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            Log.d(TAG, String.valueOf(Calendar.getInstance().getTime()));
        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        drawer.closeDrawer(GravityCompat.START);


        return true;
    }


    @Override
    public void selectFragment(FragmentManager.FragmentSelect fragmentSelect) {
        currentFragment = FragmentManager.getInstance().getFragment(fragmentSelect);
        FragmentManager.getInstance().setContextOnFragment(fragmentSelect, this);
        FragmentManager.getInstance().setCredentialOnFragment(fragmentSelect, credential);

       getSupportFragmentManager().beginTransaction()
               .replace(MAIN_CONTAINER_ID, currentFragment)
               .commitNow();

        navigationView.setCheckedItem(selectedId);
    }

    @Override
    public void selectNavigation(int id) {
        navigationView.setCheckedItem(id);
    }

    @Override
    public void setBudget(double budget) {
        toolbar.setTitle(String.valueOf(budget));
    }

    @Override
    public void setUserInfo(User user) {
        navHeaderUsername.setText(user.getUsername());
        toolbar.setTitle(String.valueOf(user.getBudget()));
    }

    @Override
    public void refreshFragment(FragmentManager.FragmentSelect fragmentSelect) {
        FragmentManager.getInstance().getFragment(fragmentSelect).refresh();
    }

    private void removeFragment() {
        if(currentFragment!=null) {
            getSupportFragmentManager().beginTransaction().remove(currentFragment).commit();
            navigationView.setCheckedItem(selectedId);
            currentFragment = null;
        }
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }
}
