package com.peryite.familybudget.ui.models;

import com.github.mikephil.charting.data.PieEntry;
import com.peryite.familybudget.entities.Credential;
import com.peryite.familybudget.ui.contracts.InsertChartContract;
import com.peryite.familybudget.ui.listeners.BaseAPIRequestListener;
import com.peryite.familybudget.ui.listeners.InsertChartListener;

import java.util.ArrayList;

public class InsertChartModel implements InsertChartContract.Model {
    private Credential credential;
    private InsertChartListener listener;

    public InsertChartModel(Credential credential){
        this.credential = credential;
    }

    @Override
    public void setListener(BaseAPIRequestListener listener) {
        this.listener = (InsertChartListener) listener;
    }

    @Override
    public void requestChartSetByDateRange(String startDate, String endDate) {
        //TODO: request on server

        ArrayList<PieEntry> visitors = new ArrayList<>();
        visitors.add(new PieEntry(480, "December"));
        visitors.add(new PieEntry(120, "May"));
        visitors.add(new PieEntry(230, "April"));
        visitors.add(new PieEntry(730, "June"));
        visitors.add(new PieEntry(520, "September"));
        visitors.add(new PieEntry(342, "October"));
        visitors.add(new PieEntry(128, "November"));

        listener.setChartSet(visitors, "Visitors");
    }
}
