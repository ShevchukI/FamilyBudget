package com.peryite.familybudget.ui.views.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ProgressBar;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.peryite.familybudget.R;
import com.peryite.familybudget.ui.contracts.InsertChartContract;
import com.peryite.familybudget.ui.models.InsertChartModel;
import com.peryite.familybudget.ui.presenters.InsertChartPresenter;
import com.peryite.familybudget.utils.CustomColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class InsertChartFragment extends BaseFragment implements InsertChartContract.View {

    private InsertChartContract.Presenter presenter;

    // private BarChart barChart;
    private PieChart pieChart;
    private AppCompatTextView tvStartDatePicker;
    private AppCompatTextView tvEndDatePicker;
    private AppCompatButton btnGo;
    private DatePickerDialog.OnDateSetListener startOnDateSetListener;
    private DatePickerDialog.OnDateSetListener endOnDateSetListener;

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
        tvStartDatePicker = view.findViewById(R.id.tv_startDatePicker);
        tvEndDatePicker = view.findViewById(R.id.tv_endDatePicker);
        btnGo = view.findViewById(R.id.btn_goDatePicker);
        pieChart = view.findViewById(R.id.insert_chart_pie_chart);

        tvStartDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_DeviceDefault_Dialog_MinWidth,
                        startOnDateSetListener,
                        year, month, day);

               // datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        startOnDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;

                String date = dayOfMonth+"/"+month+"/"+year;
                tvStartDatePicker.setText(date);
            }
        };

        tvEndDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_DeviceDefault_Dialog_MinWidth,
                        endOnDateSetListener,
                        year, month, day);

                //datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        endOnDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;

                String date = dayOfMonth+"/"+month+"/"+year;
                tvEndDatePicker.setText(date);
            }
        };


        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickGo(tvStartDatePicker.getText().toString(), tvEndDatePicker.getText().toString());
               // showMessage(tvStartDatePicker.getText().toString() + " - " + tvEndDatePicker.getText().toString());


            }
        });
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
        presenter = new InsertChartPresenter(insertChartModel);
        presenter.attachView(this);
    }

    @Override
    public void showChart(List<PieEntry> set, String centerText){
        PieDataSet pieDataSet = new PieDataSet(set, "Visitors");
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

        pieChart.setVisibility(View.VISIBLE);
    }

}
