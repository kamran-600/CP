package com.example.collegeproject.studentData.Remark;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeproject.R;
import com.example.collegeproject.attendance.AttendanceModel;
import com.example.collegeproject.databinding.ActivityRemarkBinding;

import java.util.ArrayList;
import java.util.List;

public class RemarkActivity extends AppCompatActivity {

    LinearLayoutManager layoutManager;
    List<AttendanceModel> userList;
    RemarkAdapter adapter;
    private ActivityRemarkBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRemarkBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initData();
        initRecyclerView();
    }

    /* *****************************************
        passing data to adapter using arraylist
       ***************************************** */
    private void initData() {

        userList = new ArrayList<>();

        userList.add(new AttendanceModel(R.drawable.cse, "Kamran", "31"));
        userList.add(new AttendanceModel(R.drawable.c, "James Anderson", "32"));
        userList.add(new AttendanceModel(R.drawable.cs2, "David Warner", "33"));
        userList.add(new AttendanceModel(R.drawable.cse, "Virat Kohli", "34"));
        userList.add(new AttendanceModel(R.drawable.c, "Rohit", "35"));
        userList.add(new AttendanceModel(R.drawable.cs2, "Mohammad Shami", "36"));
        userList.add(new AttendanceModel(R.drawable.cse, "Umran Malik", "43"));
        userList.add(new AttendanceModel(R.drawable.c, "Mohammad Siraj", "66"));
        userList.add(new AttendanceModel(R.drawable.cs2, "Bumrah", "37"));
        userList.add(new AttendanceModel(R.drawable.cse, "Kumar", "38"));
        userList.add(new AttendanceModel(R.drawable.c, "Dhoni", "40"));
        userList.add(new AttendanceModel(R.drawable.cs2, "Sachin", "39"));
        userList.add(new AttendanceModel(R.drawable.cse, "Kamran", "31"));
        userList.add(new AttendanceModel(R.drawable.c, "James Anderson", "32"));
        userList.add(new AttendanceModel(R.drawable.cs2, "David Warner", "33"));
        userList.add(new AttendanceModel(R.drawable.cse, "Virat Kohli", "34"));
        userList.add(new AttendanceModel(R.drawable.c, "Rohit", "35"));
        userList.add(new AttendanceModel(R.drawable.cs2, "Mohammad Shami", "36"));
        userList.add(new AttendanceModel(R.drawable.cse, "Umran Malik", "43"));
        userList.add(new AttendanceModel(R.drawable.c, "Mohammad Siraj", "66"));
        userList.add(new AttendanceModel(R.drawable.cs2, "Bumrah", "37"));
        userList.add(new AttendanceModel(R.drawable.cse, "Kumar", "38"));
        userList.add(new AttendanceModel(R.drawable.c, "Dhoni", "40"));
        userList.add(new AttendanceModel(R.drawable.cs2, "Sachin", "39"));

    }

    /* *****************************************
                 set data to adapter
       ***************************************** */
    private void initRecyclerView() {

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerview.setLayoutManager(layoutManager);
        adapter = new RemarkAdapter(userList);
        binding.recyclerview.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        binding.recyclerview.addItemDecoration(dividerItemDecoration);
        adapter.notifyDataSetChanged();

    }
}