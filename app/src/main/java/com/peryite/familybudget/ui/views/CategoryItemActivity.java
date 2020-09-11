package com.peryite.familybudget.ui.views;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;

import com.peryite.familybudget.R;
import com.peryite.familybudget.entities.CategoryItem;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryItemActivity extends BaseActivity {

//    @BindView(R.id.category_item_spend_progressBar)
//    ProgressBar progressBar;
//    @BindView(R.id.category_item_spend_progressBar_progress)
//    AppCompatTextView progress;

    @BindView(R.id.category_item_recycler)
    RecyclerView recyclerView;

    private int limit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_item);

        unbinder = ButterKnife.bind(this);

        limit = 500;

//        List<CategoryItem> categoryItems = new ArrayList<>();
//        categoryItems.add(new CategoryItem(23L, "itemOne", 24.04, "desct", new Timestamp(1)));
//        categoryItems.add(new CategoryItem(22L, "itetwo", 23.04, "des4ct", new Timestamp(10)));
//        categoryItems.add(new CategoryItem(24L, "item3e", 23.04, "des325ct", new Timestamp(12)));
//        categoryItems.add(new CategoryItem(26L, "item4e", 20.04, "deseqct", new Timestamp(13)));
//        categoryItems.add(new CategoryItem(278L, "itemO6e", 23.04, "de67sct", new Timestamp(14)));
//        categoryItems.add(new CategoryItem(287L, "item7e", 123.04, "des665ct", new Timestamp(15)));
//        categoryItems.add(new CategoryItem(213L, "itemO8ne", 223.56, "de54sct", new Timestamp(16)));
//        categoryItems.add(new CategoryItem(123L, "item989ne", 23.32, "des65ct", new Timestamp(17)));
//
//        CategoryItemAdapter categoryItemAdapter = new CategoryItemAdapter(categoryItems);
//        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(categoryItemAdapter);
//        recyclerView.setHasFixedSize(true);
//
//        double allPrice = 0;
//        for (CategoryItem categoryItem : categoryItems) {
//            allPrice += categoryItem.getPrice();
//        }

//        setProgressValue((int) allPrice);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

//
//    private void setProgressValue(int progressValue) {
//        progressBar.setProgress(((100 * progressValue) / limit));
//        progress.setText(progressBar.getProgress() + "%");
//
//    }
}
