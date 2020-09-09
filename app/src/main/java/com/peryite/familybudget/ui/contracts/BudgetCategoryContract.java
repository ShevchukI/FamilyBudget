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

        void deleteCategory(int id);

        void createCategory(BudgetCategory budgetCategory);
    }

    interface View  extends BaseView{
        void showMessage(String text);
        void addCategoriesToAdapter(List<BudgetCategory> budgetCategories);
        void initRecycler();

        void updateCategories(List<BudgetCategory> budgetCategories);

        void showAddCategoryDialog();
    }

    interface Presenter extends BasePresenter {
        void start();

        void onOpenCategoryClick(int categoryId);

        void onAddItemToCategoryClick(int categoryId);

        void refresh();

        void onAddCategoryClick();

        void onDeleteCategoryClick(int id);

        void confirmCreateCategory(BudgetCategory budgetCategory);
    }
}
