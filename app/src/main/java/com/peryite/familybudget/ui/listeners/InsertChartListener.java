package com.peryite.familybudget.ui.listeners;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieEntry;

import java.util.List;

public interface InsertChartListener extends BaseAPIRequestListener{
    void setPieChartSet(List<PieEntry> set, String centerText);

    void setBarChartSet(List<BarEntry> set, List<String> labels, String text);

    void statisticPointsIsDownload();

    void statisticMonthIsDownload();
}
