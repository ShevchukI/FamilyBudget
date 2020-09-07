package com.peryite.familybudget.ui.contracts;

import com.peryite.familybudget.entities.BudgetCategory;
import com.peryite.familybudget.entities.CategoryItem;
import com.peryite.familybudget.ui.BaseModel;
import com.peryite.familybudget.ui.BasePresenter;
import com.peryite.familybudget.ui.BaseView;
import com.peryite.familybudget.ui.adapters.BudgetCategoryRecyclerAdapter;

import java.util.List;

public interface BudgetCategoryContract {
    interface Model extends BaseModel {

       void getCategories();
    }

    interface View  extends BaseView{
        void showMessage(String text);
        void addCategoriesToAdapter(List<BudgetCategory> budgetCategories);
        void initRecycler();
    }

    interface Presenter extends BasePresenter {
        void start();
    }
}
