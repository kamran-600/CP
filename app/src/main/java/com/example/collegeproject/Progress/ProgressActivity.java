package com.example.collegeproject.Progress;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.example.collegeproject.databinding.ActivityProgressBinding;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ProgressActivity extends AppCompatActivity {

    ActivityProgressBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProgressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(2010,100));
        entries.add(new BarEntry(2012,200));
        entries.add(new BarEntry(2014,140));
        entries.add(new BarEntry(2015,130));
        entries.add(new BarEntry(2017,170));
        entries.add(new BarEntry(2020,120));
        entries.add(new BarEntry(2013,180)); entries.add(new BarEntry(2022,300));
        entries.add(new BarEntry(2025,200));
        entries.add(new BarEntry(2011,100));


        BarDataSet dataSet = new BarDataSet(entries, "Attendance");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(20f);

        BarData barData = new BarData(dataSet);


        binding.barChart.setFitBars(true);
        binding.barChart.setData(barData);
        binding.barChart.getDescription().setText("Attendance Tracking");
        binding.barChart.animateY(2000);


    }
}