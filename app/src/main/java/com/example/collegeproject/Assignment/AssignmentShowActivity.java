package com.example.collegeproject.Assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.collegeproject.R;
import com.example.collegeproject.databinding.ActivityAssignmentShowBinding;

import java.util.ArrayList;
import java.util.List;

public class AssignmentShowActivity extends AppCompatActivity {

    ActivityAssignmentShowBinding binding;
    List<AssignmentSubmitModal> userList;
    LinearLayoutManager layoutManager;
    AssignmentSubmitAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAssignmentShowBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initData();
        initRecyclerView();


    }

    private void initData() {
        userList = new ArrayList<>();

        userList.add(new AssignmentSubmitModal(R.drawable.cartoon,"Kamran","12/01/2023","12:10AM"));
        userList.add(new AssignmentSubmitModal(R.drawable.cartoon,"Aftab","13/10/2023","12:10AM"));
        userList.add(new AssignmentSubmitModal(R.drawable.cartoon,"Alam","14/10/2023","12:10AM"));
        userList.add(new AssignmentSubmitModal(R.drawable.cartoon,"Arif","15/10/2023","12:10AM"));
        userList.add(new AssignmentSubmitModal(R.drawable.cartoon,"Arshad","16/10/2023","12:10AM"));
        userList.add(new AssignmentSubmitModal(R.drawable.cartoon,"Rehan","24/12/2023","12:10AM"));
        userList.add(new AssignmentSubmitModal(R.drawable.cartoon,"Kamran","12/01/2023","12:10AM"));
        userList.add(new AssignmentSubmitModal(R.drawable.cartoon,"Aftab","13/10/2023","12:10AM"));
        userList.add(new AssignmentSubmitModal(R.drawable.cartoon,"Alam","14/10/2023","12:10AM"));
        userList.add(new AssignmentSubmitModal(R.drawable.cartoon,"Arif","15/10/2023","12:10AM"));
        userList.add(new AssignmentSubmitModal(R.drawable.cartoon,"Arshad","16/10/2023","12:10AM"));
        userList.add(new AssignmentSubmitModal(R.drawable.cartoon,"Rehan","24/12/2023","12:10AM"));
        userList.add(new AssignmentSubmitModal(R.drawable.cartoon,"Kamran","12/01/2023","12:10AM"));
        userList.add(new AssignmentSubmitModal(R.drawable.cartoon,"Aftab","13/10/2023","12:10AM"));
        userList.add(new AssignmentSubmitModal(R.drawable.cartoon,"Alam","14/10/2023","12:10AM"));
        userList.add(new AssignmentSubmitModal(R.drawable.cartoon,"Arif","15/10/2023","12:10AM"));
        userList.add(new AssignmentSubmitModal(R.drawable.cartoon,"Arshad","16/10/2023","12:10AM"));
        userList.add(new AssignmentSubmitModal(R.drawable.cartoon,"Rehan","24/12/2023","12:10AM"));
        userList.add(new AssignmentSubmitModal(R.drawable.cartoon,"Kamran","12/01/2023","12:10AM"));
        userList.add(new AssignmentSubmitModal(R.drawable.cartoon,"Aftab","13/10/2023","12:10AM"));
        userList.add(new AssignmentSubmitModal(R.drawable.cartoon,"Alam","14/10/2023","12:10AM"));
        userList.add(new AssignmentSubmitModal(R.drawable.cartoon,"Arif","15/10/2023","12:10AM"));
        userList.add(new AssignmentSubmitModal(R.drawable.cartoon,"Arshad","16/10/2023","12:10AM"));
        userList.add(new AssignmentSubmitModal(R.drawable.cartoon,"Rehan","24/12/2023","12:10AM"));

    }
    private void initRecyclerView() {
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerview.setLayoutManager(layoutManager);
        adapter = new AssignmentSubmitAdapter(userList);
        binding.recyclerview.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,layoutManager.getOrientation());
        binding.recyclerview.addItemDecoration(dividerItemDecoration);
        adapter.notifyDataSetChanged();

    }
}