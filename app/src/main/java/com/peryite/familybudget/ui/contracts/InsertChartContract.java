package com.peryite.familybudget.ui.contracts;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieEntry;
import com.peryite.familybudget.ui.BaseModel;
import com.peryite.familybudget.ui.BasePresenter;
import com.peryite.familybudget.ui.BaseView;

import java.util.List;

public interface InsertChartContract {
    interface Model extends BaseModel{

        void requestChartSetByDateRange(String startDate, String endDate);

        void requestStatisticByCategories();
    }

    interface View extends BaseView{

        void showPieChart(List<PieEntry> set, String centerText);

        void showBarChart(List<BarEntry> set, List<String> labels, String text);
    }

    interface Presenter extends BasePresenter{

        void start();

        void onClickGo(String toString, String toString1);
    }
}
