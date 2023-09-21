package com.example.collegeproject.fee;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.collegeproject.R;
import com.example.collegeproject.databinding.ActivityFeeSummaryBinding;
import com.example.collegeproject.studentData.StudentData;
import com.faltenreich.skeletonlayout.Skeleton;
import com.faltenreich.skeletonlayout.SkeletonLayoutUtils;
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

        setSupportActionBar(binding.topAppBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        userList = new ArrayList<>();

        Skeleton skeleton = SkeletonLayoutUtils.applySkeleton(binding.recyclerview, R.layout.fee_summary_single_row, 10);
        skeleton.showSkeleton();


        db.collection("College_Project").document("student").collection(getIntent().getStringExtra("year")).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            skeleton.showOriginal();
                            for (DocumentSnapshot stuRollNo : task.getResult().getDocuments()) {
                                StudentData data = stuRollNo.toObject(StudentData.class);

                                if (data != null) {

                                    userList.add(new FeeSummaryModel(data.getProfileImageBlob(), data.getFull_name(), data.getRoll_number(), data.getAcademic_fee(), data.getSubmittedAcademicFee()));
                                    adapter = new FeeSummaryAdapter(userList);
                                    binding.recyclerview.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();

                                }


                            }
                            if (userList.size() == 0) {
                                Toast.makeText(FeeSummaryActivity.this, "No Student Enrolled in " + getIntent().getStringExtra("year"), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });


        // pull to refresh
        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                skeleton.showSkeleton();

                db.collection("College_Project").document("student").collection(getIntent().getStringExtra("year")).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    int size = userList.size();
                                    userList.clear();
                                    if (size != 0) {
                                        adapter.notifyItemRangeRemoved(0, size);
                                    }
                                    skeleton.showOriginal();
                                    for (DocumentSnapshot stuRollNo : task.getResult().getDocuments()) {
                                        StudentData data = stuRollNo.toObject(StudentData.class);

                                        if (data != null) {

                                            userList.add(new FeeSummaryModel(data.getProfileImageBlob(), data.getFull_name(), data.getRoll_number(), data.getAcademic_fee(), data.getSubmittedAcademicFee()));
                                            adapter = new FeeSummaryAdapter(userList);
                                            binding.recyclerview.setAdapter(adapter);
                                            adapter.notifyDataSetChanged();

                                        }


                                    }
                                    if (userList.size() == 0) {
                                        Toast.makeText(FeeSummaryActivity.this, "No Student Enrolled in " + getIntent().getStringExtra("year"), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });

                binding.swipeRefresh.setRefreshing(false);

            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}