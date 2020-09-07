package com.peryite.familybudget.ui.views.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.peryite.familybudget.R;
import com.peryite.familybudget.entities.BudgetCategory;
import com.peryite.familybudget.entities.Credential;
import com.peryite.familybudget.ui.adapters.BudgetCategoryRecyclerAdapter;
import com.peryite.familybudget.ui.contracts.BudgetCategoryContract;
import com.peryite.familybudget.ui.listeners.OnBudgetCategoryItemClick;
import com.peryite.familybudget.ui.models.BudgetCategoryModel;
import com.peryite.familybudget.ui.presenters.BudgetCategoryPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class BudgetCategoryFragment extends BaseFragment implements BudgetCategoryContract.View {

    private RecyclerView recyclerView;
    private FloatingActionButton fabAddItem;
    private BudgetCategoryContract.Presenter presenter;
    private BudgetCategoryRecyclerAdapter adapter;

    public BudgetCategoryFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_budget_category, container, false);
        // Inflate the layout for this fragment
        BudgetCategoryModel budgetCategoryModel = new BudgetCategoryModel(credential);
        presenter = new BudgetCategoryPresenter(budgetCategoryModel);
        presenter.attachView(this);
        presenter.start();
//        fabAddItem = view.findViewById(R.id.add_item);
//        fabAddItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showMessage("Add item click!");
//            }
//        });
        return view;
    }

    @Override
    public void showProgress(ProgressBar progressBar) {

    }

    @Override
    public void hideProgress(ProgressBar progressBar) {

    }

    @Override
    public void showMessage(String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void addCategoriesToAdapter(List<BudgetCategory> budgetCategories) {
        adapter = new BudgetCategoryRecyclerAdapter(budgetCategories, getActivity());
        adapter.setListener(new OnBudgetCategoryItemClick() {
            @Override
            public void onOpenCategoryClick(int id) {
                showMessage("Open category: " + id);
            }

            @Override
            public void onAddCategoryItemClick(int id) {
                showMessage("Add item to category: " + id);
            }
        });
    }

    @Override
    public void initRecycler() {
        recyclerView = view.findViewById(R.id.rv_budget_categories);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

    }


    @Override
    public void disableElements(List<View> elements) {

    }

    @Override
    public void disableElements() {

    }

    @Override
    public void enableElements(List<View> elements) {

    }

    @Override
    public void enableElements() {

    }

    @Override
    public void enableElements(boolean enabled) {

    }

    @Override
    public void startActivity(Context packageContext, Class cls) {

    }

    @Override
    public void closeActivity() {

    }

}
