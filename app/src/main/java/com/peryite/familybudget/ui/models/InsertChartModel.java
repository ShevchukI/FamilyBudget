package com.peryite.familybudget.ui.models;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieEntry;
import com.peryite.familybudget.api.RestClient;
import com.peryite.familybudget.api.repository.ItemRepository;
import com.peryite.familybudget.entities.Credential;
import com.peryite.familybudget.entities.StatisticEntity;
import com.peryite.familybudget.entities.StatisticPoint;
import com.peryite.familybudget.ui.contracts.InsertChartContract;
import com.peryite.familybudget.ui.listeners.BaseAPIRequestListener;
import com.peryite.familybudget.ui.listeners.InsertChartListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsertChartModel implements InsertChartContract.Model {
    private Credential credential;
    private InsertChartListener listener;
    private ItemRepository itemRepository;

    public InsertChartModel(Credential credential){
        this.credential = credential;
        itemRepository = RestClient.getClient(credential).create(ItemRepository.class);
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
        visitors.add(new PieEntry(100, "July"));
        visitors.add(new PieEntry(520, "September"));
        visitors.add(new PieEntry(342, "October"));
        visitors.add(new PieEntry(128, "November"));

        listener.setPieChartSet(visitors, "Visitors");
    }

    @Override
    public void requestStatisticByCategories() {
        final Call<StatisticEntity> statisticEntityCall = itemRepository.getStatistic();
        statisticEntityCall.enqueue(new Callback<StatisticEntity>() {
            @Override
            public void onResponse(Call<StatisticEntity> call, Response<StatisticEntity> response) {
                if(response.isSuccessful() && response.body()!=null){
                    List<StatisticPoint> statisticPoints = response.body().getStatisticPoints();
                    if(statisticPoints.size()>0){
                        List<BarEntry> set = new ArrayList<>();
                        List<String> labels = new ArrayList<>();

                        for(int i = 0; i< statisticPoints.size(); i++){
                            float value = Float.parseFloat(String.format("%.2f", statisticPoints.get(i).getPrice()));
                            set.add(new BarEntry(i, value));
                            labels.add(statisticPoints.get(i).getBudgetCategory().getName());
                        }

                        listener.setBarChartSet(set, labels, "Categories");
//                        List<PieEntry> statistics = getPieEntryFromStatisticPoints(statisticPoints);
//                        listener.setPieChartSet(statistics, "Category statistic");

                    }
                } else {
                    listener.onResponse();
                }
            }

            @Override
            public void onFailure(Call<StatisticEntity> call, Throwable t) {
                listener.onFailure();
            }
        });
    }

    private List<PieEntry> getPieEntryFromStatisticPoints(List<StatisticPoint> statisticPoints){
        List<PieEntry> statistics = new ArrayList<>();
        for (StatisticPoint statisticPoint: statisticPoints) {
            if(statisticPoint.getPrice()<0) {
                statistics.add(new PieEntry(statisticPoint.getPrice(), statisticPoint.getBudgetCategory().getName()));
            }
        }
        return statistics;
    }


}
