package com.peryite.familybudget.ui.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.peryite.familybudget.ui.BaseView;

import java.util.List;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    private final String TAG = this.getClass().getSimpleName();

    protected List<View> elements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void showProgress(ProgressBar progressBar) {
        Log.d(TAG, "showProgress: ");
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress(ProgressBar progressBar) {
        Log.d(TAG, "hideProgress: ");
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {
        Log.d(TAG, "showMessage: ");
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void disableElements(List<View> elements) {
        for (View element : elements) {
            element.setEnabled(false);
        }
    }

    @Override
    public void disableElements() {

    }

    @Override
    public void enableElements(List<View> elements) {
        for (View element : elements) {
            element.setEnabled(true);
        }
    }

    @Override
    public void enableElements() {

    }

    @Override
    public void enableElements(boolean enabled) {

    }

    @Override
    public void startActivity(Context packageContext, Class cls) {
        Intent intent = new Intent(packageContext, cls);
        startActivity(intent);
    }

    @Override
    public void closeActivity() {
        this.finish();
    }

}
