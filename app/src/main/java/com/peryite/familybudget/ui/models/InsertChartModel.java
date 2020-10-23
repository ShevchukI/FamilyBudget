package com.peryite.familybudget.ui.models;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.TimingLogger;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieEntry;
import com.peryite.familybudget.api.RestClient;
import com.peryite.familybudget.api.repository.ItemRepository;
import com.peryite.familybudget.dbhelper.DBConverter;
import com.peryite.familybudget.dbhelper.dao.CategoryDAO;
import com.peryite.familybudget.dbhelper.dao.ItemDAO;
import com.peryite.familybudget.entities.BudgetCategory;
import com.peryite.familybudget.entities.CategoryItem;
import com.peryite.familybudget.entities.CategoryMonthStatistic;
import com.peryite.familybudget.entities.Credential;
import com.peryite.familybudget.entities.MonthEntity;
import com.peryite.familybudget.entities.StatisticEntity;
import com.peryite.familybudget.entities.StatisticMonth;
import com.peryite.familybudget.entities.StatisticPoint;
import com.peryite.familybudget.ui.contracts.InsertChartContract;
import com.peryite.familybudget.ui.listeners.BaseAPIRequestListener;
import com.peryite.familybudget.ui.listeners.InsertChartListener;
import com.peryite.familybudget.utils.ChartType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Context context;

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
                        for (int i = 0; i < categoryMonth.getItemStatisticEntities().size(); i++) {
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
    }

    @Override
    public void requestCategoryDiagram(ChartType.DiagramType diagramType) {
//
//        ChartLoader chartLoader = new ChartLoader(diagramType, context, listener);
//        chartLoader.execute();


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


    private static class ChartLoader extends AsyncTask<Void, Void, Void> {
        private ChartType.DiagramType diagramType;
        private Context context;
        private InsertChartListener listener;
        private List<BarEntry> set;
        private List<String> labels;
        private List<PieEntry> statistics;
        private long startTime;

        public ChartLoader(ChartType.DiagramType diagramType, Context context, InsertChartListener listener) {
            this.diagramType = diagramType;
            this.context = context;
            this.listener = listener;

            set = new ArrayList<>();
            labels = new ArrayList<>();

            statistics = new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            startTime = System.currentTimeMillis();


            CategoryDAO categoryDAO = DBConverter.getInstance(context).getCategoryDAO();
            ItemDAO itemDAO = DBConverter.getInstance(context).getItemDAO();

            List<BudgetCategory> budgetCategories = categoryDAO.getAllEntity();
            Map<String, Float> categoryPriceMap = new HashMap<>();

            for (int i = 0; i < budgetCategories.size(); i++) {
                float price = 0;
                List<CategoryItem> categoryItemList = itemDAO.getAllEntityByCategoryId(budgetCategories.get(i).getId());
                for (CategoryItem item : categoryItemList) {
                    price += item.getPrice();
                }
                categoryPriceMap.put(budgetCategories.get(i).getName(), price);
            }
            switch (diagramType) {
                case General:
                    int i = 0;
                    for (Map.Entry<String, Float> item : categoryPriceMap.entrySet()) {
                        set.add(new BarEntry(i, item.getValue()));
                        labels.add(item.getKey());
                        i++;
                    }

                    // listener.setBarChartSet(set, labels, "Categories");
                    break;
                case Income:
                    for (Map.Entry<String, Float> item : categoryPriceMap.entrySet()) {
                        if (item.getValue() >= 0) {
                            statistics.add(new PieEntry(item.getValue(), item.getKey()));
                        }
                    }
                    //listener.setPieChartSet(statistics, "Income");
                    break;
                case Spending:
                    for (Map.Entry<String, Float> item : categoryPriceMap.entrySet()) {
                        if (item.getValue() < 0) {
                            statistics.add(new PieEntry(Math.abs(item.getValue()), item.getKey()));
                        }
                    }
                    // listener.setPieChartSet(statistics, "Spending");
                    break;
                default:
                    break;

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            long endTime = System.currentTimeMillis();
            Log.d("timeTag", "onPostExecute: " + (endTime-startTime));
            if (statistics.isEmpty()) {
                listener.setBarChartSet(set, labels, "Categories");
            } else {
                switch (diagramType) {
                    case Income:
                        listener.setPieChartSet(statistics, "Income");
                        break;
                    case Spending:
                        listener.setPieChartSet(statistics, "Spending");
                        break;
                    default:
                        break;
                }
            }
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

                        for (int i = 0; i < monthEntities.size(); i++) {
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

    private List<PieEntry> getPieEntryFromMonthEntities(List<MonthEntity> monthEntities, ChartType.DiagramType diagramType) {
        List<PieEntry> statistics = new ArrayList<>();
        for (MonthEntity monthEntity : monthEntities) {
            if (diagramType == ChartType.DiagramType.Income) {
                if (monthEntity.getPrice() > 0) {
                    statistics.add(new PieEntry(monthEntity.getPrice(), monthEntity.getMonthType().name()));
                }
            } else if (diagramType == ChartType.DiagramType.Spending) {
                if (monthEntity.getPrice() < 0) {
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

    public void setContext(Context context) {
        this.context = context;
    }
}
