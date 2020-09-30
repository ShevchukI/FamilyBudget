package com.peryite.familybudget.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StatisticEntity {
    @SerializedName("time")
    private int time;
    @SerializedName("statisticPoints")
    private List<StatisticPoint> statisticPoints;

    public StatisticEntity() {
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public List<StatisticPoint> getStatisticPoints() {
        return statisticPoints;
    }

    public void setStatisticPoints(List<StatisticPoint> statisticPoints) {
        this.statisticPoints = statisticPoints;
    }
}
