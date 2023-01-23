package com.example.collegeproject.fee;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeproject.R;
import com.example.collegeproject.databinding.ActivityFeeSummaryBinding;

import java.util.ArrayList;
import java.util.List;

public class FeeSummaryActivity extends AppCompatActivity {

    private ActivityFeeSummaryBinding binding;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<FeeSummaryModel> userList;
    FeeSummaryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeeSummaryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initData();
        initRecyclerView();
    }

    /* *****************************************
               set data to adapter
     ***************************************** */
    private void initRecyclerView() {
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerview.setLayoutManager(layoutManager);
        adapter = new FeeSummaryAdapter(userList);
        binding.recyclerview.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        binding.recyclerview.addItemDecoration(dividerItemDecoration);
        adapter.notifyDataSetChanged();
    }

    /* *****************************************
           initialize the data for adapter
     ***************************************** */
    private void initData() {
        userList = new ArrayList<>();

        userList.add(new FeeSummaryModel(R.drawable.a0, "Ram", "1904310100075", "85000",
                "50000"));
        userList.add(new FeeSummaryModel(R.drawable.a1, "Sita", " 19043101000759", "90000",
                "56000"));
        userList.add(new FeeSummaryModel(R.drawable.a5, "Mohan", "1904310100065", "89000",
                "50000"));
        userList.add(new FeeSummaryModel(R.drawable.a1, "Kamran", " 19043101000706", "98000",
                "55000"));
        userList.add(new FeeSummaryModel(R.drawable.a4, "Tanveer", "1904310100075", "85000",
                "50000"));
        userList.add(new FeeSummaryModel(R.drawable.a1, "Mark", " 19043101000756", "90000",
                "55000"));
        userList.add(new FeeSummaryModel(R.drawable.a5, "Pinku", "1904310100075", "85000",
                "50000"));
        userList.add(new FeeSummaryModel(R.drawable.a2, "Tony", " 19043101000756", "90000",
                "55000"));
    }
}