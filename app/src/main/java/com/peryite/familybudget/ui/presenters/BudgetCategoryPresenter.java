package com.peryite.familybudget.ui.presenters;

import com.peryite.familybudget.entities.BudgetCategory;
import com.peryite.familybudget.entities.CategoryItem;
import com.peryite.familybudget.ui.BaseView;
import com.peryite.familybudget.ui.contracts.BudgetCategoryContract;
import com.peryite.familybudget.ui.listeners.OnAPICategoryRequestListener;
import com.peryite.familybudget.ui.models.BudgetCategoryModel;

import java.util.List;

public class BudgetCategoryPresenter implements BudgetCategoryContract.Presenter {
    private BudgetCategoryContract.View view;
    private BudgetCategoryContract.Model model;

    public BudgetCategoryPresenter(BudgetCategoryContract.Model model) {
        model.setListener(new OnAPICategoryRequestListener() {
            @Override
            public void setCategories(List<BudgetCategory> budgetCategories) {
                view.addCategoriesToAdapter(budgetCategories);
                view.initRecycler();
//                StringBuilder stringBuilder = new StringBuilder();
//                for(BudgetCategory budgetCategory:budgetCategories){
//                    stringBuilder.append(budgetCategory.toString());
//                }
//
//                view.showMessage(stringBuilder.toString());
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
}
