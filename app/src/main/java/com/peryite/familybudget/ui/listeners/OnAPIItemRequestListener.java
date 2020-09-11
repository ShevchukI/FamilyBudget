package com.peryite.familybudget.ui.listeners;

import com.peryite.familybudget.entities.Item;

import java.util.List;

public interface OnAPIItemRequestListener extends BaseAPIRequestListener {
    void setItems(List<Item> items);

    void doRefresh();
}
