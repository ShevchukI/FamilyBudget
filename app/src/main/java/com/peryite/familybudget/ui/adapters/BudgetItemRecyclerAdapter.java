package com.peryite.familybudget.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.peryite.familybudget.R;
import com.peryite.familybudget.entities.Item;
import com.peryite.familybudget.ui.listeners.OnBudgetItemClick;

import java.util.List;

public class BudgetItemRecyclerAdapter extends RecyclerView.Adapter<BudgetItemRecyclerAdapter.ViewHolder> {
    private final String TAG = this.getClass().getSimpleName();

    private List<Item> items;
    private OnBudgetItemClick listener;
    private Context context;

    public BudgetItemRecyclerAdapter(List<Item> budgetCategoryList, FragmentActivity activity) {
        this.items = budgetCategoryList;
        this.context = activity;
    }

    @NonNull
    @Override
    public BudgetItemRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_category_item, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        final Item item = items.get(i);

        viewHolder.bind(item);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current state of the item
                boolean expanded = item.isExpanded();
                // Change the state
                item.setExpanded(!expanded);
                // Notify the adapter that item has changed
                BudgetItemRecyclerAdapter.this.notifyItemChanged(i);
            }
        });

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
        private AppCompatTextView itemName;
        private AppCompatTextView itemPrice;
        private AppCompatTextView itemDate;
        private AppCompatTextView itemDescription;
        private LinearLayout itemSubItem;

        private View divider;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Log.d(TAG, "ViewHolder: finding views!");

            itemView.setOnCreateContextMenuListener(this);

            itemName = itemView.findViewById(R.id.category_item_name);
            itemPrice = itemView.findViewById(R.id.category_item_price);
            itemDate = itemView.findViewById(R.id.category_item_added_date);
            itemDescription = itemView.findViewById(R.id.category_item_description);

            itemSubItem = itemView.findViewById(R.id.category_item_sub_item);

            divider = itemView.findViewById(R.id.divider);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select The Action");

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
                        listener.onEditItemClick(getAdapterPosition());
                        break;

                    case 2:
                        listener.onDeleteItemClick(getAdapterPosition());
                        break;
                    default:
                        break;
                }
                return true;
            }
        };

        private void bind(Item item) {
            boolean expanded = item.isExpanded();

            itemSubItem.setVisibility(expanded ? View.VISIBLE : View.GONE);

            itemName.setText(item.getName());
            itemPrice.setText(String.valueOf(item.getPrice()));
            itemDescription.setText(item.getDescription());
            itemDate.setText(String.valueOf(item.getDate()));

        }
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setListener(OnBudgetItemClick listener) {
        this.listener = listener;
    }
}
