package com.peryite.familybudget.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.peryite.familybudget.R;
import com.peryite.familybudget.entities.BudgetCategory;
import com.peryite.familybudget.ui.listeners.OnBudgetCategoryItemClick;

import java.util.List;

public class BudgetCategoryRecyclerAdapter extends RecyclerView.Adapter<BudgetCategoryRecyclerAdapter.ViewHolder> {

    private final static String LOG_TAG = BudgetCategoryRecyclerAdapter.class.getSimpleName();

    private List<BudgetCategory> items;
    private OnBudgetCategoryItemClick listener;
    private Context context;

    public BudgetCategoryRecyclerAdapter(List<BudgetCategory> budgetCategoryList, FragmentActivity activity) {
        this.items = budgetCategoryList;
        this.context = activity;
    }

    @NonNull
    @Override
    public BudgetCategoryRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_category, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.categoryOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onOpenCategoryClick(viewHolder.getAdapterPosition());
            }
        });
        viewHolder.categoryAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAddCategoryItemClick(viewHolder.getAdapterPosition());
            }
        });


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BudgetCategoryRecyclerAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.categoryName.setText(items.get(i).getName());
        viewHolder.categorySpendPrice.setText(items.get(i).getDescription());

        if (i == items.size() - 1) {
            viewHolder.divider.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.divider.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        private AppCompatTextView categoryName;
        private AppCompatTextView categorySpendPrice;
        private AppCompatImageView categoryOpen;
        private AppCompatImageView categoryAddItem;

        private View divider;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(LOG_TAG, "ViewHolder: finding views!");

            itemView.setOnCreateContextMenuListener(this);

            categoryName = itemView.findViewById(R.id.category_item_name);
            categorySpendPrice = itemView.findViewById(R.id.category_description);
            categoryOpen = itemView.findViewById(R.id.category_open_category_item);
            categoryAddItem = itemView.findViewById(R.id.category_add_item_button);


////            heroGender = itemView.findViewById(R.id.tv_hero_gender);
//            heroRace = itemView.findViewById(R.id.tv_hero_race);
//            heroSpecialtyIcon = itemView.findViewById(R.id.iv_hero_specialty);
//            heroSpecialtyName = itemView.findViewById(R.id.tv_specialty_name);
            divider = itemView.findViewById(R.id.divider);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select The Action");
//            menu.add(0, v.getId(), 0, "Call");//groupId, itemId, order, title
//            menu.add(0, v.getId(), 0, "SMS");

            MenuItem edit = menu.add(Menu.NONE, 1, 1, "Edit");
            MenuItem delete = menu.add(Menu.NONE, 2, 2, "Delete");

            edit.setOnMenuItemClickListener(onEditMenu);
            delete.setOnMenuItemClickListener(onEditMenu);

        }

        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case 1:
                        listener.onEditCategoryClick(getAdapterPosition());
                        //listener.showListenerMessage("edit? Item id: " + item.getItemId() + " adapterPosition: " + getAdapterPosition() + " itemId: " + getItems().get(getAdapterPosition()).getId());
                        //Do stuff
                        break;

                    case 2:
                        //Do stuff
                        listener.onDeleteCategoryClick(getAdapterPosition());
                        //listener.showListenerMessage("delete? Item id: " + item.getItemId()+ " adapterPosition: " + getAdapterPosition() + " itemId: " + getItems().get(getAdapterPosition()).getId());
                        break;
                    default:
                        break;
                }
                return true;
            }
        };

    }

    public List<BudgetCategory> getItems() {
        return items;
    }

    public void setItems(List<BudgetCategory> items) {
        this.items = items;
    }

//    public OnHeroRecyclerItemClickListener getListener() {
//        return listener;
//    }
//
    public void setListener(OnBudgetCategoryItemClick listener) {
        this.listener = listener;
    }
}
