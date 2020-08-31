package com.peryite.familybudget.ui.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.peryite.familybudget.R;
import com.peryite.familybudget.api.repository.UserRepository;
import com.peryite.familybudget.entities.Credential;
import com.peryite.familybudget.utils.GsonUtil;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BudgetActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final String TAG = this.getClass().getSimpleName();


    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

//    @BindView(R.id.fab)
//    FloatingActionButton fab;

    AppCompatTextView navHeaderUsername;

    private SharedPreferences preferencesCredential;
    private UserRepository userRepository;
    private Credential credential;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        preferencesCredential = getSharedPreferences("credential", MODE_PRIVATE);
        String jsonCredential = preferencesCredential.getString("credential", "empty");

        if (jsonCredential.equals("empty")) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        } else {
            credential = (Credential) GsonUtil.fromJson(jsonCredential, Credential.class);
        }

        unbinder = ButterKnife.bind(this);

        navHeaderUsername = navigationView.getHeaderView(0).findViewById(R.id.navigation_header_username);

//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        navHeaderUsername.setText(credential.getUsername());


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.budget, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

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


}
