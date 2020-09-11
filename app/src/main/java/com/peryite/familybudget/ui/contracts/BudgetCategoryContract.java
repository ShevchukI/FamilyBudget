package com.peryite.familybudget.ui.contracts;

import com.peryite.familybudget.entities.BudgetCategory;
import com.peryite.familybudget.entities.Item;
import com.peryite.familybudget.ui.BaseModel;
import com.peryite.familybudget.ui.BasePresenter;
import com.peryite.familybudget.ui.BaseView;

import java.util.List;

public interface BudgetCategoryContract {
    interface Model extends BaseModel {

        void getCategories();

        void deleteCategory(int id);

        void createCategory(BudgetCategory budgetCategory);

        void updateCategory(BudgetCategory budgetCategory);

        void addItemToCategory(Item item);
    }

    interface View extends BaseView {
        void showMessage(String text);

        void addCategoriesToAdapter(List<BudgetCategory> budgetCategories);

        void initRecycler();

        void updateCategories(List<BudgetCategory> budgetCategories);

        void showAddCategoryDialog();

        void showEditCategoryDialog(BudgetCategory budgetCategory);

        void openCategory(BudgetCategory budgetCategory);

        void showAddItemDialog(int categoryId);

        void updateBudget();
    }

    interface Presenter extends BasePresenter {
        void start();

        void onOpenCategoryClick(BudgetCategory budgetCategory);

        void onAddItemToCategoryClick(int categoryId);

        void refresh();

        void onAddCategoryClick();

        void onDeleteCategoryClick(int id);

        void confirmCreateCategory(BudgetCategory budgetCategory);

        void updateCategory(BudgetCategory budgetCategory);

        void onEditCategoryClick(BudgetCategory budgetCategory);

        void confirmCreateItem(Item item);
    }
}
