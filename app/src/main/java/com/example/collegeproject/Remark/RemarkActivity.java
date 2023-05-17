package com.example.collegeproject.Remark;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.example.collegeproject.attendance.AttendanceModel;
import com.example.collegeproject.databinding.ActivityRemarkBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RemarkActivity extends AppCompatActivity {

    LinearLayoutManager layoutManager;
    List<AttendanceModel> userList;
    RemarkAdapter adapter;
    private ActivityRemarkBinding binding;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRemarkBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();

        setSupportActionBar(binding.topAppBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        userList = new ArrayList<>();


        db.collection("College_Project").document("student").collection(getIntent().getStringExtra("year")).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot stuRollNo : task.getResult().getDocuments()){
                                AttendanceModel data = stuRollNo.toObject(AttendanceModel.class);

                                if(data != null){

                                    userList.add(new AttendanceModel(data.getProfileImageBlob(), data.getFull_name(), data.getRoll_number()));
                                    adapter = new RemarkAdapter(userList);
                                    binding.recyclerview.setAdapter(adapter);
                                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(RemarkActivity.this, DividerItemDecoration.VERTICAL);
                                    binding.recyclerview.addItemDecoration(dividerItemDecoration);
                                    adapter.notifyDataSetChanged();

                                }


                            }
                            if(userList.size() == 0){
                                Toast.makeText(RemarkActivity.this, "No Student Enrolled in "+getIntent().getStringExtra("year"), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}