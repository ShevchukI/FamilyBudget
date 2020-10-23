package com.peryite.familybudget.ui.views;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.peryite.familybudget.R;
import com.peryite.familybudget.api.RestClient;
import com.peryite.familybudget.api.repository.CategoryRepository;
import com.peryite.familybudget.api.repository.ItemRepository;
import com.peryite.familybudget.dbhelper.DBConverter;
import com.peryite.familybudget.dbhelper.dao.CategoryDAO;
import com.peryite.familybudget.dbhelper.dao.ItemDAO;
import com.peryite.familybudget.entities.BudgetCategory;
import com.peryite.familybudget.entities.CategoryItem;
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

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private static Credential credential;
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

        if (currentFragment != null) {
            Log.d(TAG, "onCreate: notNull");
        }

        init();
        if (savedInstanceState != null) {
            switch (savedInstanceState.getInt("currentFragment")) {
                case 0:
                    selectFragment(FragmentManager.FragmentSelect.BudgetCategory);
                    break;
                case 1:
                    selectFragment(FragmentManager.FragmentSelect.BudgetItem);
                    break;
                case 2:
                    selectFragment(FragmentManager.FragmentSelect.InsertChart);
                    break;
            }
        }


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
                    case R.id.action_reload_local_database:
                        reloadDB();
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

        BudgetModel budgetModel = new BudgetModel(credential, this);
        presenter = new BudgetPresenter(budgetModel);
        presenter.attachView(this);
        presenter.start();

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (currentFragment == FragmentManager.getInstance().getFragment(FragmentManager.FragmentSelect.BudgetItem)) {
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
        } else if (id == R.id.nav_insert_chart) {
            presenter.onClickInsertChart();
        }

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
        FragmentManager.getInstance().setListenerOnFragment(fragmentSelect, listener);
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
                if (!etItemPrice.getText().toString().equals("")) {
                    Double price = Double.parseDouble(etItemPrice.getText().toString());
                    if (price > 0) {
                        Item item = new Item.Builder()
                                .asEarned(price)
                                .withDescription(etItemDescription.getText().toString())
                                .build();
                        presenter.onClickAddBudget(item);

                        dialogBuilder.dismiss();
                    } else {
                        showMessage("Budget cannot be negative!");
                    }
                } else {
                    showMessage("Price is empty!");
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
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        switch (currentFragmentType) {
            case BudgetCategory:
                outState.putInt("currentFragment", 0);
                break;
            case BudgetItem:
                outState.putInt("currentFragment", 1);
                break;
            case InsertChart:
                outState.putInt("currentFragment", 2);
                break;
        }
    }

    private void reloadDB() {
        DBLoader dbLoader = new DBLoader(this);
        dbLoader.execute();
    }

    private static class DBLoader extends AsyncTask<Void, Void, Void> {

        public Context context;

        private List<BudgetCategory> budgetCategories;

        public DBLoader(Context context) {
            this.context = context;

        }

        public List<BudgetCategory> getBudgetCategories() {
            return budgetCategories;
        }

        public void setBudgetCategories(List<BudgetCategory> budgetCategories) {
            this.budgetCategories = budgetCategories;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            DBConverter dbConverter = DBConverter.getInstance(context);
            final CategoryDAO categoryDAO = dbConverter.getCategoryDAO();
            if (!dbConverter.isEmptyDataBase()) {
                dbConverter.clearDataBase();
                Log.d("db", "reloadDB: db is not empty! " + categoryDAO.getAllEntity().size());

            }
            CategoryRepository categoryRepository = RestClient.getClient(credential).create(CategoryRepository.class);


            Call<List<BudgetCategory>> categoryCall = categoryRepository.getAllUserCategory();
            categoryCall.enqueue(new Callback<List<BudgetCategory>>() {
                @Override
                public void onResponse(Call<List<BudgetCategory>> call, Response<List<BudgetCategory>> response) {
                    if (response.code() == 200) {
                        setBudgetCategories(response.body());
                        AddCategoryLoader addCategoryLoader = new AddCategoryLoader(budgetCategories, context);
                        addCategoryLoader.execute();
                    } else {
                        Log.d("db", "onResponse: ");
                    }
                }

                @Override
                public void onFailure(Call<List<BudgetCategory>> call, Throwable t) {
                    Log.d("db", "onFailure: ");
                }
            });

            return null;
        }


    }

    private static class AddCategoryLoader extends AsyncTask<Void, Void, Void> {

        private Context context;
        private List<BudgetCategory> budgetCategories;

        public AddCategoryLoader(List<BudgetCategory> budgetCategories, Context context) {
            this.budgetCategories = budgetCategories;
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            DBConverter dbConverter = DBConverter.getInstance(context);
            final CategoryDAO categoryDAO = dbConverter.getCategoryDAO();
            categoryDAO.insert(budgetCategories);
            budgetCategories = categoryDAO.getAllEntity();

            addItemsToCategory(budgetCategories);
            return null;
        }

        private void addItemsToCategory(final List<BudgetCategory> budgetCategories) {
            ItemRepository itemRepository = RestClient.getClient(credential).create(ItemRepository.class);


            Call<List<Item>> itemCall = itemRepository.getAllItem();
            itemCall.enqueue(new Callback<List<Item>>() {
                @Override
                public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                    if (response.code() == 200) {
                        AddItemLoader addItemLoader = new AddItemLoader(response.body(), budgetCategories, context);
                        addItemLoader.execute();

                    } else {
                    }
                }

                @Override
                public void onFailure(Call<List<Item>> call, Throwable t) {

                }
            });
        }
    }

    private static class AddItemLoader extends AsyncTask<Void, Void, Void> {

        private List<Item> items;
        private List<BudgetCategory> budgetCategories;
        private Context context;

        public AddItemLoader(List<Item> items, List<BudgetCategory> budgetCategories, Context context) {
            this.items = items;
            this.budgetCategories = budgetCategories;
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            final ItemDAO itemDAO = DBConverter.getInstance(context).getItemDAO();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");

            for (Item item : items) {
                Timestamp date = new Timestamp(0);

                try {
                    date = new Timestamp(formatter.parse(item.getDate()).getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                itemDAO.insert(new CategoryItem(item.getId(), item.getName(), item.getPrice(), item.getDescription(), date, item.getBudgetCategory().getId()));
            }

            for (BudgetCategory category : budgetCategories) {
                Log.d("db", "category: " + category.getName());
                List<CategoryItem> categoryItemList = itemDAO.getAllEntityByCategoryId(category.getId());
                for (CategoryItem item : categoryItemList) {
                    Log.d("db", "items: " + item.getName() + " price: " + item.getPrice());
                }
            }
            return null;
        }
    }
}
