package com.peryite.familybudget.ui.contracts;

import com.github.mikephil.charting.data.PieEntry;
import com.peryite.familybudget.ui.BaseModel;
import com.peryite.familybudget.ui.BasePresenter;
import com.peryite.familybudget.ui.BaseView;

import java.util.List;

public interface InsertChartContract {
    interface Model extends BaseModel{

        void requestChartSetByDateRange(String startDate, String endDate);
    }

    interface View extends BaseView{

        void showChart(List<PieEntry> set, String centerText);
    }

    interface Presenter extends BasePresenter{

        void start();

        void onClickGo(String toString, String toString1);
    }
}
