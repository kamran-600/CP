package com.example.collegeproject.Assignment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeproject.R;
import com.example.collegeproject.databinding.ActivityStudentAssignmentBinding;

import java.util.ArrayList;
import java.util.List;

public class StudentAssignmentActivity extends AppCompatActivity {
    ActivityStudentAssignmentBinding binding;
    LinearLayoutManager layoutManager;
    List<AssignmentModal> userList;
    AssignmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentAssignmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initData();
        initRecyclerView();

    }

    private void initRecyclerView() {
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerview.setLayoutManager(layoutManager);
        adapter = new AssignmentAdapter(userList);
        binding.recyclerview.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        binding.recyclerview.addItemDecoration(dividerItemDecoration);
        adapter.notifyDataSetChanged();

    }

    private void initData() {
        userList = new ArrayList<>();

        userList.add(new AssignmentModal(R.drawable.cartoon, "Kamran", "CS 1st Year", getString(R.string.teachHead), "12/01/2022", "12:10AM"));
        userList.add(new AssignmentModal(R.drawable.cartoon, "Kamran", "CS 1st Year", getString(R.string.teachHead), "12/01/2022", "12:10AM"));
        userList.add(new AssignmentModal(R.drawable.cartoon, "Kamran", "CS 1st Year", getString(R.string.teachHead), "12/01/2022", "12:10AM"));
        userList.add(new AssignmentModal(R.drawable.cartoon, "Kamran", "CS 1st Year", getString(R.string.teachHead), "12/01/2022", "12:10AM"));
        userList.add(new AssignmentModal(R.drawable.cartoon, "Kamran", "CS 1st Year", getString(R.string.teachHead), "12/01/2022", "12:10AM"));
        userList.add(new AssignmentModal(R.drawable.cartoon, "Kamran", "CS 1st Year", getString(R.string.teachHead), "12/01/2022", "12:10AM"));
        userList.add(new AssignmentModal(R.drawable.cartoon, "Kamran", "CS 1st Year", getString(R.string.teachHead), "12/01/2022", "12:10AM"));
        userList.add(new AssignmentModal(R.drawable.cartoon, "Kamran", "CS 1st Year", getString(R.string.teachHead), "12/01/2022", "12:10AM"));
        userList.add(new AssignmentModal(R.drawable.cartoon, "Kamran", "CS 1st Year", getString(R.string.teachHead), "12/01/2022", "12:10AM"));
        userList.add(new AssignmentModal(R.drawable.cartoon, "Kamran", "CS 1st Year", getString(R.string.teachHead), "12/01/2022", "12:10AM"));
        userList.add(new AssignmentModal(R.drawable.cartoon, "Kamran", "CS 1st Year", getString(R.string.teachHead), "12/01/2022", "12:10AM"));
        userList.add(new AssignmentModal(R.drawable.cartoon, "Kamran", "CS 1st Year", getString(R.string.teachHead), "12/01/2022", "12:10AM"));
        userList.add(new AssignmentModal(R.drawable.cartoon, "Kamran", "CS 1st Year", getString(R.string.teachHead), "12/01/2022", "12:10AM"));
        userList.add(new AssignmentModal(R.drawable.cartoon, "Kamran", "CS 1st Year", getString(R.string.teachHead), "12/01/2022", "12:10AM"));
        userList.add(new AssignmentModal(R.drawable.cartoon, "Kamran", "CS 1st Year", getString(R.string.teachHead), "12/01/2022", "12:10AM"));
        userList.add(new AssignmentModal(R.drawable.cartoon, "Kamran", "CS 1st Year", getString(R.string.teachHead), "12/01/2022", "12:10AM"));
        userList.add(new AssignmentModal(R.drawable.cartoon, "Kamran", "CS 1st Year", getString(R.string.teachHead), "12/01/2022", "12:10AM"));
        userList.add(new AssignmentModal(R.drawable.cartoon, "Kamran", "CS 1st Year", getString(R.string.teachHead), "12/01/2022", "12:10AM"));
        userList.add(new AssignmentModal(R.drawable.cartoon, "Kamran", "CS 1st Year", getString(R.string.teachHead), "12/01/2022", "12:10AM"));
        userList.add(new AssignmentModal(R.drawable.cartoon, "Kamran", "CS 1st Year", getString(R.string.teachHead), "12/01/2022", "12:10AM"));
        userList.add(new AssignmentModal(R.drawable.cartoon, "Kamran", "CS 1st Year", getString(R.string.teachHead), "12/01/2022", "12:10AM"));
        userList.add(new AssignmentModal(R.drawable.cartoon, "Kamran", "CS 1st Year", getString(R.string.teachHead), "12/01/2022", "12:10AM"));
        userList.add(new AssignmentModal(R.drawable.cartoon, "Kamran", "CS 1st Year", getString(R.string.teachHead), "12/01/2022", "12:10AM"));
        userList.add(new AssignmentModal(R.drawable.cartoon, "Kamran", "CS 1st Year", getString(R.string.teachHead), "12/01/2022", "12:10AM"));


    }
}