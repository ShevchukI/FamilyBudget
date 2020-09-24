package com.peryite.familybudget.ui.listeners;

import com.github.mikephil.charting.data.PieEntry;

import java.util.List;

public interface InsertChartListener extends BaseAPIRequestListener{
    void setChartSet(List<PieEntry> set, String centerText);
}
