package com.example.collegeproject.attendance;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeproject.R;
import com.example.collegeproject.databinding.ActivityAttendanceBinding;
import com.example.collegeproject.fee.FeeSummaryActivity;
import com.example.collegeproject.fee.FeeSummaryAdapter;
import com.example.collegeproject.fee.FeeSummaryModel;
import com.example.collegeproject.studentData.StudentData;
import com.faltenreich.skeletonlayout.Skeleton;
import com.faltenreich.skeletonlayout.SkeletonLayoutUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class AttendanceActivity extends AppCompatActivity {

    ActivityAttendanceBinding binding;
    LinearLayoutManager layoutManager;
    List<AttendanceModel> userList;
    AttendanceAdapter adapter;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAttendanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();


        setSupportActionBar(binding.topAppBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        userList = new ArrayList<>();

        Skeleton skeleton = SkeletonLayoutUtils.applySkeleton(binding.recyclerview, R.layout.single_row_take_attendance,10);
        skeleton.showSkeleton();

        db.collection("College_Project").document("student").collection(getIntent().getStringExtra("year")).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            skeleton.showOriginal();
                            for(DocumentSnapshot stuRollNo : task.getResult().getDocuments()){
                                AttendanceModel data = stuRollNo.toObject(AttendanceModel.class);

                                if(data != null){

                                    userList.add(new AttendanceModel(data.getProfileImageBlob(), data.getFull_name(), data.getRoll_number()));
                                    adapter = new AttendanceAdapter(userList);
                                    binding.recyclerview.setAdapter(adapter);
                                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(AttendanceActivity.this, DividerItemDecoration.VERTICAL);
                                    binding.recyclerview.addItemDecoration(dividerItemDecoration);
                                    adapter.notifyDataSetChanged();

                                }


                            }
                            if(userList.size() == 0){
                                Toast.makeText(AttendanceActivity.this, "No Student Enrolled in "+getIntent().getStringExtra("year"), Toast.LENGTH_SHORT).show();
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