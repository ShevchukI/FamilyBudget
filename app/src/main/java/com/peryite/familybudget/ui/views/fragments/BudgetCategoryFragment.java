package com.peryite.familybudget.ui.views.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.peryite.familybudget.R;
import com.peryite.familybudget.entities.BudgetCategory;
import com.peryite.familybudget.entities.CategoryItem;
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
    private AppCompatButton btnAddCategory;
    private BudgetCategoryContract.Presenter presenter;
    private BudgetCategoryRecyclerAdapter adapter;

    public BudgetCategoryFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_budget_category, container, false);
        // Inflate the layout for this fragment
        btnAddCategory = view.findViewById(R.id.btn_add_category);
        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onAddCategoryClick();
            }
        });
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
                presenter.onOpenCategoryClick(adapter.getItems().get(id).getId());
            }

            @Override
            public void onAddCategoryItemClick(int id) {
                presenter.onAddItemToCategoryClick(adapter.getItems().get(id).getId());
            }

            @Override
            public void onEditCategoryClick(int id) {
                //    presenter.onEditCategoryClick()
            }

            @Override
            public void onDeleteCategoryClick(final int id) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("Delete category");
                alertDialog.setMessage("Do you want to delete category " + adapter.getItems().get(id).getName() + " with all category items?");
                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.onDeleteCategoryClick(adapter.getItems().get(id).getId());
                    }
                });

                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                alertDialog.show();

                //presenter.onDeleteCategoryClick(adapter.getItems().get(id).getId());
            }

            @Override
            public void showListenerMessage(String text) {
                showMessage(text);
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
    public void updateCategories(List<BudgetCategory> budgetCategories) {
        adapter.setItems(budgetCategories);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showAddCategoryDialog() {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(getActivity()).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.edit_dialog_budget_category, null);

        final AppCompatEditText etCategoryName = dialogView.findViewById(R.id.et_edit_category_name);
        final AppCompatEditText etCategoryDescription = dialogView.findViewById(R.id.et_edit_category_description);
        AppCompatButton btnOk = dialogView.findViewById(R.id.btn_edit_category_ok);
        AppCompatButton btnCancel = dialogView.findViewById(R.id.btn_edit_category_cancel);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etCategoryName.getText().toString();
                String description = etCategoryDescription.getText().toString();
                if (!name.equals("")) {
                    presenter.confirmCreateCategory(new BudgetCategory(0, name, description));
                    dialogBuilder.dismiss();
                } else {
                    showMessage("Empty name!");
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder.dismiss();
            }
        });
//        Button button1 = (Button) dialogView.findViewById(R.id.buttonSubmit);
//        Button button2 = (Button) dialogView.findViewById(R.id.buttonCancel);

//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialogBuilder.dismiss();
//            }
//        });
//        button1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // DO SOMETHINGS
//                dialogBuilder.dismiss();
//            }
//        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

    @Override
    public void refresh() {
        super.refresh();

        presenter.refresh();
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

}
