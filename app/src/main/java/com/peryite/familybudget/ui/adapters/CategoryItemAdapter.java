package com.peryite.familybudget.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peryite.familybudget.R;
import com.peryite.familybudget.ui.models.CategoryItem;

import java.util.List;

public class CategoryItemAdapter extends RecyclerView.Adapter<CategoryItemAdapter.CategoryItemViewHolder>{

    private List<CategoryItem> categoryItems;

    public CategoryItemAdapter(List<CategoryItem> categoryItems) {
        this.categoryItems = categoryItems;
    }

    @NonNull
    @Override
    public CategoryItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.item_category_item, viewGroup, false);
        return new CategoryItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryItemViewHolder categoryItemViewHolder, final int position) {
        final CategoryItem categoryItem = categoryItems.get(position);

        categoryItemViewHolder.bind(categoryItem);

        categoryItemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean expanded = categoryItem.isExpanded();
                categoryItem.setExpanded(!expanded);
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryItems == null ? 0 : categoryItems.size();
    }


    public class CategoryItemViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView number;
        private AppCompatTextView name;
        private AppCompatTextView price;
        private AppCompatTextView date;
        private AppCompatTextView description;
        private View subItem;

        public CategoryItemViewHolder(@NonNull View itemView) {
            super(itemView);

            number = itemView.findViewById(R.id.category_item_number);
            name = itemView.findViewById(R.id.category_item_name);
            price = itemView.findViewById(R.id.category_item_price);
            date = itemView.findViewById(R.id.category_item_added_date);
            description = itemView.findViewById(R.id.category_item_description);
            subItem = itemView.findViewById(R.id.category_item_sub_item);
        }

        private void bind(CategoryItem categoryItem) {
            boolean expanded = categoryItem.isExpanded();

            subItem.setVisibility(expanded ? View.VISIBLE : View.GONE);

//            number.setText(getAdapterPosition() + 1);
            name.setText(categoryItem.getName());
            price.setText(String.valueOf(categoryItem.getPrice()));
            date.setText(String.valueOf(categoryItem.getDate()));
            description.setText(categoryItem.getDescription());
        }
    }
}
