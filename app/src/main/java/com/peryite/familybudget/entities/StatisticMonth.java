package com.peryite.familybudget.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StatisticMonth {
    @SerializedName("time")
    private int time;
    @SerializedName("categories")
    private List<CategoryMonthStatistic> categoryMonthStatistics;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public List<CategoryMonthStatistic> getCategoryMonthStatistics() {
        return categoryMonthStatistics;
    }

    public void setCategoryMonthStatistics(List<CategoryMonthStatistic> categoryMonthStatistics) {
        this.categoryMonthStatistics = categoryMonthStatistics;
    }
}
