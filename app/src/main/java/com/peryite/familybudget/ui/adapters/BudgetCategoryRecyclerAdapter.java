package com.peryite.familybudget.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
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
        view.findViewById(R.id.category_add_item_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAddCategoryItemClick(viewHolder.getAdapterPosition());
            }
        });
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (listener != null) {
//                    Log.d(LOG_TAG, "onClick: ");
//                    listener.onItemClick(view, viewHolder.getAdapterPosition());
//                } else {
//                    Log.d(LOG_TAG, "onClick: listener is null");
//                }
//            }
//        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BudgetCategoryRecyclerAdapter.ViewHolder viewHolder, final int i) {
//        int heroIconId = -1;
//        int heroSpecialtyIconId = -1;
//
//        try {
//            heroIconId = R.drawable.class.getField("ic_hero_" + items.get(i).getHeroName().replace(" ", "_").toLowerCase()).getInt(null);
//        } catch (IllegalAccessException e) {
//            Log.e(LOG_TAG, "onBindViewHolder: " + e.getMessage());
//        } catch (NoSuchFieldException e) {
//            Log.e(LOG_TAG, "onBindViewHolder: " + e.getMessage());
//        }
//        if (heroIconId != -1) {
//            viewHolder.heroIcon.setImageDrawable(ContextCompat.getDrawable(context, heroIconId));
//        } else {
//            viewHolder.heroIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_random));
//        }

        viewHolder.categoryName.setText(items.get(i).getName());
        viewHolder.categorySpendPrice.setText(items.get(i).getDescription());
//        final int id = items.get(i).getId();
//        viewHolder.categoryOpen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.onOpenCategoryClick(id);
//            }
//        });
//
//        viewHolder.categoryAddItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.onAddCategoryItemClick(id);
//            }
//        });

//        switch (items.get(i).getHeroGender()) {
//            case FEMALE:
//                viewHolder.heroGender.setText("Female");
//                break;
//            case MALE:
//                viewHolder.heroGender.setText("Male");
//                break;
//        }

//        viewHolder.heroRace.setText(items.get(i).getHeroRace());
//
//        try {
//            heroSpecialtyIconId = R.drawable.class.getField("ic_specialty_" + items.get(i).getHeroSpeciality().getSpecialtyName().replace(" ", "_").toLowerCase()).getInt(null);
//        } catch (IllegalAccessException e) {
//            Log.e(LOG_TAG, "onBindViewHolder: " + e.getMessage());
//        } catch (NoSuchFieldException e) {
//            Log.e(LOG_TAG, "onBindViewHolder: " + e.getMessage());
//        }
//        if (heroSpecialtyIconId != -1) {
//            viewHolder.heroSpecialtyIcon.setImageDrawable(ContextCompat.getDrawable(context, heroSpecialtyIconId));
//        } else {
//            viewHolder.heroSpecialtyIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_random));
//        }
//
//        viewHolder.heroSpecialtyName.setText(items.get(i).getHeroSpeciality().getSpecialtyName());

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

    public class ViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView categoryName;
        private AppCompatTextView categorySpendPrice;
        private AppCompatImageView categoryOpen;
        private AppCompatImageView categoryAddItem;

        private View divider;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(LOG_TAG, "ViewHolder: finding views!");


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
