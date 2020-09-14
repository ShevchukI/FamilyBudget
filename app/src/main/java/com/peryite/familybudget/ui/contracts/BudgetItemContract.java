package com.peryite.familybudget.ui.contracts;

import com.peryite.familybudget.entities.Item;
import com.peryite.familybudget.ui.BaseModel;
import com.peryite.familybudget.ui.BasePresenter;
import com.peryite.familybudget.ui.BaseView;

import java.util.List;

public interface BudgetItemContract {
    interface Model extends BaseModel{
        void getItems();

        void deleteItem(int id);

        void createItem(Item item);

        void updateItem(Item item);

    }

    interface View extends BaseView{
        void addItemsToAdapter(List<Item> items);

        void initRecycler();

        void updateItems(List<Item> items);

        void showAddItemDialog();

        void showEditItemDialog(Item item);

        void updateBudget();
    }

    interface Presenter extends BasePresenter{

        void start();

        void refresh();

        void onAddItemClick();

        void onDeleteItemClick(int id);

        void confirmCreateItem(Item item);

        void updateItem(Item item);

        void onEditItemClick(Item item);
    }
}
