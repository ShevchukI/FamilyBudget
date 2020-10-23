package com.peryite.familybudget.ui.views.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.audiofx.AudioEffect;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.peryite.familybudget.R;
import com.peryite.familybudget.ui.contracts.InsertChartContract;
import com.peryite.familybudget.ui.models.InsertChartModel;
import com.peryite.familybudget.ui.presenters.InsertChartPresenter;
import com.peryite.familybudget.utils.ChartType;
import com.peryite.familybudget.utils.CustomColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class InsertChartFragment extends BaseFragment implements InsertChartContract.View {

    private InsertChartContract.Presenter presenter;

    private BarChart barChart;
    private PieChart pieChart;
    private RadioGroup rgStatisticType;
    private RadioGroup rgDiagramType;
//    private RadioButton rbCategory;
//    private RadioButton rbMonth;
//    private RadioButton rbGeneral;
//    private RadioButton rbGeneral;
//    private AppCompatTextView tvStartDatePicker;
//    private AppCompatTextView tvEndDatePicker;
//    private AppCompatButton btnGo;
//    private DatePickerDialog.OnDateSetListener startOnDateSetListener;
//    private DatePickerDialog.OnDateSetListener endOnDateSetListener;

    public InsertChartFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_insert_chart, container, false);
        // Inflate the layout for this fragment

        init();

        presenter.start();

        return view;
    }

    private void init() {
//        tvStartDatePicker = view.findViewById(R.id.tv_startDatePicker);
//        tvEndDatePicker = view.findViewById(R.id.tv_endDatePicker);
//        btnGo = view.findViewById(R.id.btn_goDatePicker);
        pieChart = view.findViewById(R.id.insert_chart_pie_chart);
        barChart = view.findViewById(R.id.insert_chart_bar_chart);

        rgStatisticType = view.findViewById(R.id.rg_statistic_type);
        rgStatisticType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                showDiagramByStatistic(checkedId);
//                switch (checkedId){
//                    case R.id.rb_statistic_categories:
//                       showDiagramByStatistic(checkedId);
//                        break;
//                    case R.id.rb_statistic_month:
//                        showDiagramByStatistic(checkedId);
//                        break;
//                    default:
//                        break;
//                }
            }
        });

        rgDiagramType = view.findViewById(R.id.rg_diagram_type);
        rgDiagramType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                showStatisticByDiagram(checkedId);
            }
        });

//        tvStartDatePicker.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Calendar calendar = Calendar.getInstance();
//                int year = calendar.get(Calendar.YEAR);
//                int month = calendar.get(Calendar.MONTH);
//                int day = calendar.get(Calendar.DAY_OF_MONTH);
//
//                DatePickerDialog datePickerDialog = new DatePickerDialog(
//                        getActivity(),
//                        android.R.style.Theme_DeviceDefault_Dialog_MinWidth,
//                        startOnDateSetListener,
//                        year, month, day);
//
//                // datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                datePickerDialog.show();
//            }
//        });
//
//        startOnDateSetListener = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                month = month + 1;
//
//                String date = dayOfMonth + "/" + month + "/" + year;
//                tvStartDatePicker.setText(date);
//            }
//        };
//
//        tvEndDatePicker.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Calendar calendar = Calendar.getInstance();
//                int year = calendar.get(Calendar.YEAR);
//                int month = calendar.get(Calendar.MONTH);
//                int day = calendar.get(Calendar.DAY_OF_MONTH);
//
//                DatePickerDialog datePickerDialog = new DatePickerDialog(
//                        getActivity(),
//                        android.R.style.Theme_DeviceDefault_Dialog_MinWidth,
//                        endOnDateSetListener,
//                        year, month, day);
//
//                //datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                datePickerDialog.show();
//            }
//        });
//
//        endOnDateSetListener = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                month = month + 1;
//
//                String date = dayOfMonth + "/" + month + "/" + year;
//                tvEndDatePicker.setText(date);
//            }
//        };
//
//
//        btnGo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                barChart.setVisibility(View.GONE);
//                pieChart.setVisibility(View.GONE);
//                presenter.onClickGo(tvStartDatePicker.getText().toString(), tvEndDatePicker.getText().toString());
//                // showMessage(tvStartDatePicker.getText().toString() + " - " + tvEndDatePicker.getText().toString());
//
//
//            }
//        });
//        barChart = view.findViewById(R.id.insert_chart_bar_chart);
//
//        ArrayList<BarEntry> visitors = new ArrayList<>();
//        visitors.add(new BarEntry(2014, 100));
//        visitors.add(new BarEntry(2015, 230));
//        visitors.add(new BarEntry(2016, 140));
//        visitors.add(new BarEntry(2017, 700));
//        visitors.add(new BarEntry(2018, 340));
//
//        BarDataSet barDataSet = new BarDataSet(visitors, "Visitors");
//        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
//        barDataSet.setValueTextColor(Color.BLACK);
//        barDataSet.setValueTextSize(16f);
//
//        BarData barData = new BarData(barDataSet);
//        barChart.setFitBars(true);
//        barChart.setData(barData);
//        barChart.getDescription().setText("Bar chart Example");
//        barChart.animateY(2000);


        InsertChartModel insertChartModel = new InsertChartModel(credential);
       insertChartModel.setContext(context);
        presenter = new InsertChartPresenter(insertChartModel);
        presenter.attachView(this);
    }



    @Override
    public void showPieChart(List<PieEntry> set, String centerText) {
        PieDataSet pieDataSet = new PieDataSet(set, "");
        pieDataSet.setColors(CustomColorTemplate.FIFTEEN_COLORS);
//        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                showMessage("I clicked on " + ((PieEntry) e).getLabel() + ":" + e.getY());
            }

            @Override
            public void onNothingSelected() {

            }
        });
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText(centerText);
        pieChart.animate();

        barChart.setVisibility(View.GONE);
        pieChart.setVisibility(View.GONE);
        pieChart.setVisibility(View.VISIBLE);
    }

    @Override
    public void showBarChart(List<BarEntry> set, List<String> labels, String text) {
        BarDataSet barDataSet = new BarDataSet(set, text);
        barDataSet.setColors(CustomColorTemplate.FIFTEEN_COLORS);
        Description description = new Description();
        description.setText("");

        barChart.setDescription(description);
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);

        barChart.getData().setValueTextSize(15);
        barChart.getXAxis().setTextSize(15);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        xAxis.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
       // xAxis.setGranularity(1f);
        xAxis.setLabelCount(labels.size());
        xAxis.setLabelRotationAngle(270);

//        YAxis yAxis = barChart.getAxis(YAxis.AxisDependency.LEFT);
//        yAxis.setStartAtZero(false);

        barChart.animateY(2000);
        barChart.invalidate();

        pieChart.setVisibility(View.GONE);
        barChart.setVisibility(View.VISIBLE);
    }

    @Override
    public void showStatisticButton() {
        rgStatisticType.findViewById(R.id.rb_statistic_categories).setVisibility(View.VISIBLE);
    }

    @Override
    public void showMonthButton() {
        rgStatisticType.findViewById(R.id.rb_statistic_month).setVisibility(View.VISIBLE);
    }

    private void showDiagramByStatistic(int checkedStatisticId){
        ChartType.StatisticType statisticType = ChartType.StatisticType.Category;
        ChartType.DiagramType diagramType = ChartType.DiagramType.General;

        switch (checkedStatisticId){
            case R.id.rb_statistic_categories:
                statisticType = ChartType.StatisticType.Category;
                //showMessage("Selected diagram " + diagramName + " and statistic Categories");
                break;
            case R.id.rb_statistic_month:
                statisticType = ChartType.StatisticType.Month;
               // showMessage("Selected diagram " + diagramName + " and statistic Month");
                break;
            default:
                break;
        }

        String statName = ((RadioButton)view.findViewById(checkedStatisticId)).getText().toString();
        switch (rgDiagramType.getCheckedRadioButtonId()){
            case R.id.rb_diagram_general:
                diagramType = ChartType.DiagramType.General;
                showMessage("Selected statistic " + statName + " and diagram General");
                break;
            case R.id.rb_diagram_income:
                diagramType = ChartType.DiagramType.Income;
                showMessage("Selected statistic " + statName + " and diagram Income");
                break;
            case R.id.rb_diagram_spending:
                diagramType = ChartType.DiagramType.Spending;
                showMessage("Selected statistic " + statName + " and diagram Spending");
                break;
            default:
                break;
        }

        presenter.selectStatisticDiagram(statisticType, diagramType);

    }

    private void showStatisticByDiagram(int checkedDiagramId) {
        ChartType.StatisticType statisticType = ChartType.StatisticType.Category;
        ChartType.DiagramType diagramType = ChartType.DiagramType.General;
        switch (checkedDiagramId){
            case R.id.rb_diagram_general:
                diagramType = ChartType.DiagramType.General;
                break;
            case R.id.rb_diagram_income:
                diagramType = ChartType.DiagramType.Income;
                break;
            case R.id.rb_diagram_spending:
                diagramType = ChartType.DiagramType.Spending;
                break;
            default:
                break;
        }
        String diagramName = ((RadioButton)view.findViewById(checkedDiagramId)).getText().toString();
        switch (rgStatisticType.getCheckedRadioButtonId()){
            case R.id.rb_statistic_categories:
                statisticType = ChartType.StatisticType.Category;

                showMessage("Selected diagram " + diagramName + " and statistic Categories");
                break;
            case R.id.rb_statistic_month:
                statisticType = ChartType.StatisticType.Month;
                showMessage("Selected diagram " + diagramName + " and statistic Month");
                break;
            default:
                break;
        }

        presenter.selectStatisticDiagram(statisticType, diagramType);
    }
}
