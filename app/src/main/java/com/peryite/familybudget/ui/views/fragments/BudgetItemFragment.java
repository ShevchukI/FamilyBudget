package com.peryite.familybudget.ui.views.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.peryite.familybudget.R;
import com.peryite.familybudget.entities.BudgetCategory;
import com.peryite.familybudget.entities.Item;
import com.peryite.familybudget.ui.adapters.BudgetItemRecyclerAdapter;
import com.peryite.familybudget.ui.contracts.BudgetItemContract;
import com.peryite.familybudget.ui.listeners.OnBudgetItemClick;
import com.peryite.familybudget.ui.models.BudgetItemModel;
import com.peryite.familybudget.ui.presenters.BudgetItemPresenter;

import java.util.List;

public class BudgetItemFragment extends BaseFragment implements BudgetItemContract.View {

    private AppCompatTextView tvTitle;
    private RecyclerView recyclerView;
    private AppCompatButton btnAddItem;
    private BudgetItemContract.Presenter presenter;
    private BudgetItemRecyclerAdapter adapter;

    private BudgetCategory budgetCategory;

    public BudgetItemFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_crud_list, container, false);
        // Inflate the layout for this fragment
        btnAddItem = view.findViewById(R.id.btn_list_add);
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // presenter.onAddCategoryClick();
            }
        });

        init();

        presenter.start();

        return view;
    }

    @SuppressLint("SetTextI18n")
    private void init(){
        tvTitle = view.findViewById(R.id.tv_list_title);
        tvTitle.setText("Category/"+budgetCategory.getName());

        btnAddItem = view.findViewById(R.id.btn_list_add);
        btnAddItem.setText(R.string.add_item);
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onAddItemClick();
            }
        });

        BudgetItemModel budgetItemModel = new BudgetItemModel(credential, budgetCategory);
        presenter = new BudgetItemPresenter(budgetItemModel);
        presenter.attachView(this);
    }

    @Override
    public void showProgress(ProgressBar progressBar) {

    }

    @Override
    public void hideProgress(ProgressBar progressBar) {

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
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

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void addItemsToAdapter(List<Item> items) {
        adapter = new BudgetItemRecyclerAdapter(items, getActivity());
        adapter.setListener(new OnBudgetItemClick() {

            @Override
            public void onEditItemClick(int id) {
                presenter.onEditItemClick(adapter.getItems().get(id));
            }

            @Override
            public void onDeleteItemClick(final int id) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("Delete item");
                alertDialog.setMessage("Do you want to delete item " + adapter.getItems().get(id).getName() + "?");
                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.onDeleteItemClick(adapter.getItems().get(id).getId());
                    }
                });

                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                alertDialog.show();
            }

        });
    }

    @Override
    public void initRecycler() {
        recyclerView = view.findViewById(R.id.rv_list_body);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void updateItems(List<Item> items) {
        adapter.setItems(items);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showAddItemDialog() {
        //createEditCategoryDialog(new BudgetCategory(), false);
    }

    @Override
    public void showEditItemDialog(Item item) {

    }

    public BudgetCategory getBudgetCategory() {
        return budgetCategory;
    }

    public void setBudgetCategory(BudgetCategory budgetCategory) {
        this.budgetCategory = budgetCategory;
    }
}
