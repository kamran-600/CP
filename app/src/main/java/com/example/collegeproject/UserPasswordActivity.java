package com.example.collegeproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.collegeproject.databinding.ActivityUserPasswordBinding;
import com.example.collegeproject.profile.ProfileActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class UserPasswordActivity extends AppCompatActivity {

    Intent intent;
    private ActivityUserPasswordBinding binding;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        intent = getIntent();






        binding.continueBtnPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordData();
                signUp(intent.getStringExtra("email"), binding.confirmPassword.getText().toString().trim());

            }
        });

    }

    private void signUp(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                           Intent intent1 = new Intent(UserPasswordActivity.this, HomeActivity.class);
                           intent1.putExtra("id",intent.getIntExtra("id", 0));
                           startActivity(intent1);
                            Toast.makeText(UserPasswordActivity.this, "Login", Toast.LENGTH_SHORT).show();
                            finishAffinity();


                        } else {

                            Toast.makeText(UserPasswordActivity.this, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void passwordData() {
        Map<String, Object> pass = new HashMap<>();
        pass.put("password", binding.confirmPassword.getText().toString().trim());
        if (intent.getIntExtra("id", 0) == 0) {
            db.collection("College_Project").document("student")
                    .collection("student_details").document(intent.getStringExtra("rollNo")).set(pass, SetOptions.merge())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UserPasswordActivity.this, "fail data", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        if (intent.getIntExtra("id", 0) == 1) {
            db.collection("College_Project").document("teacher")
                    .collection("teacher_details")
                    .document(intent.getStringExtra("name") + "_" + intent.getStringExtra("phoneNo")).set(pass, SetOptions.merge())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UserPasswordActivity.this, "fail data", Toast.LENGTH_SHORT).show();
                        }
                    });
        }


    }
}