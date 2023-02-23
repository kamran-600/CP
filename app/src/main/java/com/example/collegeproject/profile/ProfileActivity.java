package com.example.collegeproject.profile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.collegeproject.databinding.ActivityProfileBinding;
import com.example.collegeproject.studentData.StudentData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class ProfileActivity extends AppCompatActivity {
    private ActivityProfileBinding binding;
    FirebaseFirestore db;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();




        setSupportActionBar(binding.toolbar);
       db.collection("College_Project").document("student").collection("student_details")
               .document(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                       if (task.isSuccessful()) {
                           StudentData data = task.getResult().toObject(StudentData.class);
                           getSupportActionBar().setTitle(data.getFull_name());
                           binding.roll.setText("Student");
                           binding.email.setText(data.getE_mail());
                           binding.pPhone.setText(data.getPersonal_phone());
                           binding.fPhone.setText(data.getFather_phone());
                           binding.departmentName.setText(data.getDepartment());
                           binding.aFee.setText(data.getAcademic_fee());
                           if (data.getHostel_fee().isEmpty())
                               binding.hFee.setText("N/A");
                           else
                               binding.hFee.setText(data.getHostel_fee());
                           binding.pass.setText(data.getPassword());
                       }else
                           Toast.makeText(ProfileActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                   }
               }).addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       Toast.makeText(ProfileActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();

                   }
               });

    }
}

