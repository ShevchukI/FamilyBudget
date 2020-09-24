package com.peryite.familybudget.ui.presenters;

import com.github.mikephil.charting.data.PieEntry;
import com.peryite.familybudget.ui.BaseView;
import com.peryite.familybudget.ui.contracts.InsertChartContract;
import com.peryite.familybudget.ui.listeners.InsertChartListener;

import java.util.List;

public class InsertChartPresenter implements InsertChartContract.Presenter {
    private InsertChartContract.Model model;
    private InsertChartContract.View view;

    public InsertChartPresenter(InsertChartContract.Model model){
        model.setListener(new InsertChartListener() {
            @Override
            public void setChartSet(List<PieEntry> set, String centerText) {
                view.showChart(set, centerText);
            }

            @Override
            public void onResponse() {

            }

            @Override
            public void onFailure() {

            }
        });

        this.model = model;
    }

    @Override
    public void start() {

    }

    @Override
    public void onClickGo(String startDate, String endDate) {
        model.requestChartSetByDateRange(startDate, endDate);
    }

    @Override
    public void attachView(BaseView view) {
        this.view = (InsertChartContract.View) view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }
}
