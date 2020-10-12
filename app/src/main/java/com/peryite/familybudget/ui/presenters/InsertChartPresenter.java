package com.peryite.familybudget.ui.presenters;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieEntry;
import com.peryite.familybudget.ui.BaseView;
import com.peryite.familybudget.ui.contracts.InsertChartContract;
import com.peryite.familybudget.ui.listeners.InsertChartListener;
import com.peryite.familybudget.utils.ChartType;

import java.util.List;

public class InsertChartPresenter implements InsertChartContract.Presenter {
    private InsertChartContract.Model model;
    private InsertChartContract.View view;

    public InsertChartPresenter(InsertChartContract.Model model){
        model.setListener(new InsertChartListener() {
            @Override
            public void setPieChartSet(List<PieEntry> set, String centerText) {
                view.showPieChart(set, centerText);
            }

            @Override
            public void setBarChartSet(List<BarEntry> set, List<String> labels, String text) {
                view.showBarChart(set, labels, text);
            }

            @Override
            public void statisticPointsIsDownload() {
                view.showStatisticButton();
            }

            @Override
            public void statisticMonthIsDownload() {
                view.showMonthButton();
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
        model.requestStatisticByCategories();
    }

    @Override
    public void onClickGo(String startDate, String endDate) {
       // model.requestChartSetByDateRange(startDate, endDate);
        model.requestStatisticByCategories();
    }

    @Override
    public void selectStatisticDiagram(ChartType.StatisticType statisticType, ChartType.DiagramType diagramType) {
        switch (statisticType){
            case Category:
                model.requestCategoryDiagram(diagramType);
                break;
            case Month:
                model.requestMonthDiagram(diagramType);
                break;
            default:
                break;
        }
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
