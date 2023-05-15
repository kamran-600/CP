package com.example.collegeproject.fee;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeproject.R;
import com.example.collegeproject.databinding.ActivityFeeSummaryBinding;
import com.example.collegeproject.studentData.StudentData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FeeSummaryActivity extends AppCompatActivity {


    List<FeeSummaryModel> userList;
    FeeSummaryAdapter adapter;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    private ActivityFeeSummaryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeeSummaryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        userList = new ArrayList<>();


        db.collection("College_Project").document("student").collection(getIntent().getStringExtra("year")).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot stuRollNo : task.getResult().getDocuments()){
                                StudentData data = stuRollNo.toObject(StudentData.class);

                                if(data != null){

                                    userList.add(new FeeSummaryModel(R.drawable.cartoon, data.getFull_name(), data.getRoll_number(), data.getAcademic_fee(),"30000"));
                                    adapter = new FeeSummaryAdapter(userList);
                                    binding.recyclerview.setAdapter(adapter);
                                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(FeeSummaryActivity.this, DividerItemDecoration.VERTICAL);
                                    binding.recyclerview.addItemDecoration(dividerItemDecoration);
                                    adapter.notifyDataSetChanged();

                                }


                            }
                            if(userList.size() == 0){
                                Toast.makeText(FeeSummaryActivity.this, "No Student Enrolled in "+getIntent().getStringExtra("year"), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });


    }




   /* *//* *****************************************
               set data to adapter
     ***************************************** *//*
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

    *//* *****************************************
           initialize the data for adapter
     ***************************************** *//*
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
    }*/
}