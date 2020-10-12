package com.peryite.familybudget.ui.models;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieEntry;
import com.peryite.familybudget.api.RestClient;
import com.peryite.familybudget.api.repository.ItemRepository;
import com.peryite.familybudget.entities.CategoryMonthStatistic;
import com.peryite.familybudget.entities.Credential;
import com.peryite.familybudget.entities.ItemStatisticEntity;
import com.peryite.familybudget.entities.MonthEntity;
import com.peryite.familybudget.entities.StatisticEntity;
import com.peryite.familybudget.entities.StatisticMonth;
import com.peryite.familybudget.entities.StatisticPoint;
import com.peryite.familybudget.ui.contracts.InsertChartContract;
import com.peryite.familybudget.ui.listeners.BaseAPIRequestListener;
import com.peryite.familybudget.ui.listeners.InsertChartListener;
import com.peryite.familybudget.utils.ChartType;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsertChartModel implements InsertChartContract.Model {
    private Credential credential;
    private InsertChartListener listener;
    private ItemRepository itemRepository;
    private List<StatisticPoint> statisticPoints;
    private List<CategoryMonthStatistic> categoryMonthStatistics;
    private List<MonthEntity> monthEntities;

    public InsertChartModel(Credential credential) {
        this.credential = credential;
        itemRepository = RestClient.getClient(credential).create(ItemRepository.class);

        final Call<StatisticEntity> statisticEntityCall = itemRepository.getStatistic();
        statisticEntityCall.enqueue(new Callback<StatisticEntity>() {
            @Override
            public void onResponse(Call<StatisticEntity> call, Response<StatisticEntity> response) {
                if (response.isSuccessful() && response.body() != null) {
                    statisticPoints = response.body().getStatisticPoints();

                    listener.statisticPointsIsDownload();
                } else {
                    listener.onResponse();
                }
            }

            @Override
            public void onFailure(Call<StatisticEntity> call, Throwable t) {
                listener.onFailure();
            }
        });

        Call<StatisticMonth> statisticMonthCall = itemRepository.getStatisticMonth();
        statisticMonthCall.enqueue(new Callback<StatisticMonth>() {
            @Override
            public void onResponse(Call<StatisticMonth> call, Response<StatisticMonth> response) {
                if (response.isSuccessful() && response.body() != null) {
                    categoryMonthStatistics = response.body().getCategoryMonthStatistics();
                    monthEntities = createEmptyMonths();
                    for (CategoryMonthStatistic categoryMonth : categoryMonthStatistics) {
                        for (int i =0; i<categoryMonth.getItemStatisticEntities().size(); i++){
                            monthEntities.get(i).addPrice(categoryMonth.getItemStatisticEntities().get(i).getPrice());
                        }
                    }

                    listener.statisticMonthIsDownload();
                } else {
                    listener.onResponse();
                }
            }

            @Override
            public void onFailure(Call<StatisticMonth> call, Throwable t) {

            }
        });
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
        if (statisticPoints == null) {
            final Call<StatisticEntity> statisticEntityCall = itemRepository.getStatistic();
            statisticEntityCall.enqueue(new Callback<StatisticEntity>() {
                @Override
                public void onResponse(Call<StatisticEntity> call, Response<StatisticEntity> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        statisticPoints = response.body().getStatisticPoints();
                        if (statisticPoints.size() > 0) {
                            List<BarEntry> set = new ArrayList<>();
                            List<String> labels = new ArrayList<>();

                            for (int i = 0; i < statisticPoints.size(); i++) {
                                set.add(new BarEntry(i, statisticPoints.get(i).getPrice()));
                                labels.add(statisticPoints.get(i).getBudgetCategory().getName());
                            }

                            listener.setBarChartSet(set, labels, "Categories");
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
        } else {
            if (statisticPoints.size() > 0) {
                List<BarEntry> set = new ArrayList<>();
                List<String> labels = new ArrayList<>();

                for (int i = 0; i < statisticPoints.size(); i++) {
                    set.add(new BarEntry(i, statisticPoints.get(i).getPrice()));
                    labels.add(statisticPoints.get(i).getBudgetCategory().getName());
                }

                listener.setBarChartSet(set, labels, "Categories");
            }
        }
//        final Call<StatisticEntity> statisticEntityCall = itemRepository.getStatistic();
//        statisticEntityCall.enqueue(new Callback<StatisticEntity>() {
//            @Override
//            public void onResponse(Call<StatisticEntity> call, Response<StatisticEntity> response) {
//                if(response.isSuccessful() && response.body()!=null){
//                    List<StatisticPoint> statisticPoints = response.body().getStatisticPoints();
//                    if(statisticPoints.size()>0){
//                        List<BarEntry> set = new ArrayList<>();
//                        List<String> labels = new ArrayList<>();
//
//                        for(int i = 0; i< statisticPoints.size(); i++){
//                            float value = Float.parseFloat(String.format("%.2f", statisticPoints.get(i).getPrice()));
//                            set.add(new BarEntry(i, value));
//                            labels.add(statisticPoints.get(i).getBudgetCategory().getName());
//                        }
//
//                        listener.setBarChartSet(set, labels, "Categories");
////                        List<PieEntry> statistics = getPieEntryFromStatisticPoints(statisticPoints);
////                        listener.setPieChartSet(statistics, "Category statistic");
//
//                    }
//                } else {
//                    listener.onResponse();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<StatisticEntity> call, Throwable t) {
//                listener.onFailure();
//            }
//        });
    }

    @Override
    public void requestCategoryDiagram(ChartType.DiagramType diagramType) {
        if (statisticPoints != null) {
            if (statisticPoints.size() > 0) {
                List<PieEntry> statistics;
                switch (diagramType) {
                    case General:
                        List<BarEntry> set = new ArrayList<>();
                        List<String> labels = new ArrayList<>();

                        for (int i = 0; i < statisticPoints.size(); i++) {
                            set.add(new BarEntry(i, statisticPoints.get(i).getPrice()));
                            labels.add(statisticPoints.get(i).getBudgetCategory().getName());
                        }
                        listener.setBarChartSet(set, labels, "Categories");
                        break;
                    case Income:
                        statistics = getPieEntryFromStatisticPoints(statisticPoints, ChartType.DiagramType.Income);
                        listener.setPieChartSet(statistics, "Income");
                        break;
                    case Spending:
                        statistics = getPieEntryFromStatisticPoints(statisticPoints, ChartType.DiagramType.Spending);
                        listener.setPieChartSet(statistics, "Spending");
                        break;
                    default:
                        break;

                }
            }
        } else {
            listener.onFailure();
        }
    }

    @Override
    public void requestMonthDiagram(ChartType.DiagramType diagramType) {
        if (categoryMonthStatistics != null) {
            if (monthEntities.size() > 0) {
                List<PieEntry> statistics;
                switch (diagramType) {
                    case General:
                        List<BarEntry> set = new ArrayList<>();
                        List<String> labels = new ArrayList<>();

                        for(int i = 0; i<monthEntities.size(); i++){
                            set.add(new BarEntry(i, monthEntities.get(i).getPrice()));
                            labels.add(monthEntities.get(i).getMonthType().name());
                        }
                        listener.setBarChartSet(set, labels, "Months");
                        break;
                    case Income:
                        statistics = getPieEntryFromMonthEntities(monthEntities, ChartType.DiagramType.Income);
                        listener.setPieChartSet(statistics, "Income");
                        break;
                    case Spending:
                        statistics = getPieEntryFromMonthEntities(monthEntities, ChartType.DiagramType.Spending);
                        listener.setPieChartSet(statistics, "Spending");
                        break;
                    default:
                        break;
                }
            }
        } else {
            listener.onFailure();
        }
    }

    private List<PieEntry> getPieEntryFromStatisticPoints(List<StatisticPoint> statisticPoints, ChartType.DiagramType diagramType) {
        List<PieEntry> statistics = new ArrayList<>();
        for (StatisticPoint statisticPoint : statisticPoints) {
            if (diagramType == ChartType.DiagramType.Income) {
                if (statisticPoint.getPrice() > 0) {
                    statistics.add(new PieEntry(statisticPoint.getPrice(), statisticPoint.getBudgetCategory().getName()));
                }
            } else if (diagramType == ChartType.DiagramType.Spending) {
                if (statisticPoint.getPrice() < 0) {
                    statistics.add(new PieEntry(Math.abs(statisticPoint.getPrice()), statisticPoint.getBudgetCategory().getName()));
                }
            }
        }
        return statistics;
    }

    private List<PieEntry> getPieEntryFromMonthEntities(List<MonthEntity> monthEntities, ChartType.DiagramType diagramType){
        List<PieEntry> statistics = new ArrayList<>();
        for(MonthEntity monthEntity: monthEntities){
            if(diagramType == ChartType.DiagramType.Income){
                if(monthEntity.getPrice()>0){
                    statistics.add(new PieEntry(monthEntity.getPrice(), monthEntity.getMonthType().name()));
                }
            } else if(diagramType == ChartType.DiagramType.Spending){
                if(monthEntity.getPrice()<0){
                    statistics.add(new PieEntry(Math.abs(monthEntity.getPrice()), monthEntity.getMonthType().name()));
                }
            }
        }

        return statistics;
    }

    private List<MonthEntity> createEmptyMonths() {
        List<MonthEntity> monthEntities = new ArrayList<>();
        monthEntities.add(new MonthEntity(MonthEntity.MonthType.January));
        monthEntities.add(new MonthEntity(MonthEntity.MonthType.February));
        monthEntities.add(new MonthEntity(MonthEntity.MonthType.March));
        monthEntities.add(new MonthEntity(MonthEntity.MonthType.April));
        monthEntities.add(new MonthEntity(MonthEntity.MonthType.May));
        monthEntities.add(new MonthEntity(MonthEntity.MonthType.June));
        monthEntities.add(new MonthEntity(MonthEntity.MonthType.July));
        monthEntities.add(new MonthEntity(MonthEntity.MonthType.August));
        monthEntities.add(new MonthEntity(MonthEntity.MonthType.September));
        monthEntities.add(new MonthEntity(MonthEntity.MonthType.October));
        monthEntities.add(new MonthEntity(MonthEntity.MonthType.November));
        monthEntities.add(new MonthEntity(MonthEntity.MonthType.December));

        return monthEntities;
    }

}
