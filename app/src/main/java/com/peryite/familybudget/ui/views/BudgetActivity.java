package com.peryite.familybudget.ui.views;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.peryite.familybudget.R;
import com.peryite.familybudget.entities.Credential;
import com.peryite.familybudget.entities.Item;
import com.peryite.familybudget.entities.User;
import com.peryite.familybudget.ui.contracts.BudgetContract;
import com.peryite.familybudget.ui.listeners.BudgetFragmentListener;
import com.peryite.familybudget.ui.listeners.OnBudgetCategoryListener;
import com.peryite.familybudget.ui.models.BudgetModel;
import com.peryite.familybudget.ui.presenters.BudgetPresenter;
import com.peryite.familybudget.ui.views.fragments.FragmentManager;
import com.peryite.familybudget.utils.GsonUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BudgetActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, BudgetContract.View {

    private final String TAG = this.getClass().getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.btn_add_budget)
    AppCompatButton btnAddBudget;

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
    private FragmentManager.FragmentSelect currentFragmentType;
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
                    case R.id.action_get_alexa_code:
                        showDialogAlexaRequest();
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
            if(currentFragment == FragmentManager.getInstance().getFragment(FragmentManager.FragmentSelect.BudgetItem)){
                selectFragment(FragmentManager.FragmentSelect.BudgetCategory);
            } else {
                super.onBackPressed();
            }
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
        }
//        else if(id == R.id.nav_alexa_code){
//            presenter.onClickAlexaCode();
//        }
//        else if (id == R.id.nav_gallery) {
//            //removeFragment();
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_tools) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//            Log.d(TAG, String.valueOf(Calendar.getInstance().getTime()));
//        }
        else if (id == R.id.nav_logout) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        drawer.closeDrawer(GravityCompat.START);


        return true;
    }


    @Override
    public void selectFragment(FragmentManager.FragmentSelect fragmentSelect) {
        currentFragment = FragmentManager.getInstance().getFragment(fragmentSelect);
        currentFragmentType = fragmentSelect;
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

    @SuppressLint("DefaultLocale")
    @Override
    public void setUserInfo(User user) {
        navHeaderUsername.setText(user.getUsername());
        toolbar.setTitle(String.format("%.2f", user.getBudget()));
    }

    @Override
    public void refreshFragment(FragmentManager.FragmentSelect fragmentSelect) {
        FragmentManager.getInstance().getFragment(fragmentSelect).refresh();
    }

    @Override
    public void refreshCurrentFragment() {
        FragmentManager.getInstance().getFragment(currentFragmentType).refresh();

    }

    @Override
    public void showDialog(String title, String text) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(title);
        alertDialog.setMessage(text);
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        alertDialog.show();
    }

    @Override
    public void setListenerOnFragment(FragmentManager.FragmentSelect fragmentSelect, BudgetFragmentListener listener) {
        FragmentManager.getInstance().getFragment(fragmentSelect).setListener(listener);
    }

    private void removeFragment() {
        if (currentFragment != null) {
            getSupportFragmentManager().beginTransaction().remove(currentFragment).commit();
            navigationView.setCheckedItem(selectedId);
            currentFragment = null;
        }
    }

    public void showDialogAlexaRequest() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Get Alexa Code");
        alertDialog.setMessage("Do you want to get new Alexa code?");
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.onClickAlexaCode();
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.show();
    }

    @OnClick(R.id.btn_add_budget)
    public void onClickAddBudget() {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.edit_dialog_budget_item, null);

        final AppCompatEditText etItemPrice = dialogView.findViewById(R.id.et_edit_item_price);
        final LinearLayout linLayName = dialogView.findViewById(R.id.linLay_edit_item_name);
        final AppCompatEditText etItemDescription = dialogView.findViewById(R.id.et_edit_item_description);

        linLayName.setVisibility(View.GONE);

        AppCompatButton btnOk = dialogView.findViewById(R.id.btn_edit_item_ok);
        AppCompatButton btnCancel = dialogView.findViewById(R.id.btn_edit_item_cancel);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double price = Double.parseDouble(etItemPrice.getText().toString());
                if (price > 0) {
                    Item item = new Item.Builder()
                            .asEarned(price)
                            .withDescription(etItemDescription.getText().toString())
                            .build();
                  //  presenter.onClickAddBudget(item);

                    dialogBuilder.dismiss();
                } else {
                    showMessage("Budget cannot be negative!");
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder.dismiss();
            }
        });
        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
//        alertDialog.setTitle("Budget");
//        alertDialog.setMessage("Add budget value");
//
//        final EditText inputBudget = new EditText(this);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.MATCH_PARENT);
//        inputBudget.setLayoutParams(lp);
//
//
//        final EditText inputDescription = new EditText(this);
//        inputDescription.setLayoutParams(lp);
//
//        int type = InputType.TYPE_CLASS_NUMBER |  InputType.TYPE_NUMBER_FLAG_DECIMAL;
//
//        inputBudget.setInputType(type);
//        alertDialog.setView(inputBudget);
//
//        alertDialog.setPositiveButton("OK",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        try {
//                            double budget = Double.parseDouble(inputBudget.getText().toString());
//                            if (budget > 0) {
//                                Item item = new Item.Builder()
//                                        .asEarned(budget)
//                                        .
//                                presenter.onClickAddBudget(item);
//                            } else {
//                                showMessage("Budget cannot be negative!");
//                            }
//                        } catch (NumberFormatException ex){
//                            showMessage("Please input double value");
//                        }
//                    }
//                });
//
//        alertDialog.setNegativeButton("Cancel",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//
//        alertDialog.show();
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
