package com.peryite.familybudget.ui.presenters;

import com.peryite.familybudget.entities.Item;
import com.peryite.familybudget.ui.BaseView;
import com.peryite.familybudget.ui.contracts.BudgetItemContract;
import com.peryite.familybudget.ui.listeners.OnAPIItemRequestListener;

import java.util.List;

public class BudgetItemPresenter implements BudgetItemContract.Presenter {
    private BudgetItemContract.View view;
    private BudgetItemContract.Model model;

    private boolean isRefresh;

    public BudgetItemPresenter(BudgetItemContract.Model model) {
        model.setListener(new OnAPIItemRequestListener() {

            @Override
            public void setItems(List<Item> items) {
                if (isRefresh) {
                    view.updateItems(items);
                    isRefresh = false;
                } else {
                    view.addItemsToAdapter(items);
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
        this.view = (BudgetItemContract.View) view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void start() {
        model.getItems();
    }

    @Override
    public void refresh() {
        isRefresh = true;
        model.getItems();
    }

    @Override
    public void onAddItemClick() {
        view.showMessage("Add item click");
    }

    @Override
    public void onDeleteItemClick(Long id) {
        view.showMessage("Delete item click");
    }

    @Override
    public void confirmCreateItem(Item item) {

    }

    @Override
    public void updateItem(Item item) {

    }

    @Override
    public void onEditItemClick(Item item) {
        view.showMessage("Edit item click");
    }
}