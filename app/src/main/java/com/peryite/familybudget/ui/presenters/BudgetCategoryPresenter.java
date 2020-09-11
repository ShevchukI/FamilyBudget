package com.peryite.familybudget.ui.presenters;

import com.peryite.familybudget.entities.BudgetCategory;
import com.peryite.familybudget.entities.Item;
import com.peryite.familybudget.ui.BaseView;
import com.peryite.familybudget.ui.contracts.BudgetCategoryContract;
import com.peryite.familybudget.ui.listeners.OnAPICategoryRequestListener;

import java.util.List;

public class BudgetCategoryPresenter implements BudgetCategoryContract.Presenter {
    private BudgetCategoryContract.View view;
    private BudgetCategoryContract.Model model;

    private boolean isRefresh;

    public BudgetCategoryPresenter(final BudgetCategoryContract.Model model) {
        model.setListener(new OnAPICategoryRequestListener() {
            @Override
            public void setCategories(List<BudgetCategory> budgetCategories) {
                if (isRefresh) {
                    view.updateCategories(budgetCategories);
                    isRefresh = false;
                    view.updateBudget();
                } else {
                    view.addCategoriesToAdapter(budgetCategories);
                    view.initRecycler();
                }
            }

            @Override
            public void doRefresh() {
                refresh();
            }

            @Override
            public void onResponse() {
                view.showMessage("Response: something wrong");
            }

            @Override
            public void onFailure() {
                view.showMessage("Fail");
            }
        });
        this.model = model;
    }


    @Override
    public void attachView(BaseView view) {
        this.view = (BudgetCategoryContract.View) view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void start() {
        model.getCategories();
    }

    @Override
    public void onOpenCategoryClick(BudgetCategory budgetCategory) {
        view.openCategory(budgetCategory);
       // view.showMessage("Open category: " + categoryId);
    }

    @Override
    public void onAddItemToCategoryClick(int categoryId) {
        view.showAddItemDialog(categoryId);
        //view.showMessage("Add item to category: " + categoryId);
    }

    @Override
    public void refresh() {

        isRefresh = true;
        model.getCategories();
    }

    @Override
    public void onAddCategoryClick() {
        view.showAddCategoryDialog();
    }

    @Override
    public void onDeleteCategoryClick(int id) {
        model.deleteCategory(id);
    }

    @Override
    public void confirmCreateCategory(BudgetCategory budgetCategory) {
        model.createCategory(budgetCategory);
    }

    @Override
    public void updateCategory(BudgetCategory budgetCategory) {
        model.updateCategory(budgetCategory);
    }

    @Override
    public void onEditCategoryClick(BudgetCategory budgetCategory) {
        view.showEditCategoryDialog(budgetCategory);
    }

    @Override
    public void confirmCreateItem(Item item) {
        model.addItemToCategory(item);
    }
}
