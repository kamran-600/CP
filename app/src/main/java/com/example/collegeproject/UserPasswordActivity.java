package com.example.collegeproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.collegeproject.databinding.ActivityUserPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserPasswordActivity extends AppCompatActivity {

    Intent intent;
    private ActivityUserPasswordBinding binding;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        intent = getIntent();

        // for hide keyboard
        binding.getRoot().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (getCurrentFocus() != null) {
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });


        binding.continueBtnPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.enterPassword.getText().toString().trim().equals("") ||
                        binding.confirmPassword.getText().toString().trim().equals("")) {
                    Toast.makeText(UserPasswordActivity.this, "Please fill * fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!binding.enterPassword.getText().toString().trim().equals(binding.confirmPassword.getText().toString().trim())) {
                    Toast.makeText(UserPasswordActivity.this, "Both passwords are not same", Toast.LENGTH_SHORT).show();
                    return;
                }

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
                            passwordData(mAuth.getCurrentUser().getEmail());
                            Intent intent1 = new Intent(UserPasswordActivity.this, HomeActivity.class);
                            startActivity(intent1);
                            Toast.makeText(UserPasswordActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            finishAffinity();


                        } else {

                            Toast.makeText(UserPasswordActivity.this, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void passwordData(String currentUserEmail) {
        Map<String, Object> details = new HashMap<>();
        details.put("password", binding.confirmPassword.getText().toString().trim());

        if (intent.getIntExtra("id", 0) == 0) {
            details.put("roll_number", intent.getStringExtra("roll_number"));
            details.put("department", intent.getStringExtra("department"));
            details.put("academic_year", intent.getStringExtra("academic_year"));
            details.put("batch", intent.getStringExtra("batch"));
            details.put("academic_fee", intent.getStringExtra("academic_fee"));
            details.put("hostel_fee", intent.getStringExtra("hostel_fee"));
            details.put("full_name", intent.getStringExtra("full_name"));
            details.put("gender", intent.getStringExtra("gender"));
            details.put("dob", intent.getStringExtra("dob"));
            details.put("email", intent.getStringExtra("email"));
            details.put("personal_phone", intent.getStringExtra("personal_phone"));
            details.put("father_name", intent.getStringExtra("father_name"));
            details.put("father_phone", intent.getStringExtra("father_phone"));

            details.put("role", "Student");

            db.collection("College_Project").document("student").collection(intent.getStringExtra("academic_year"))
                    .document(intent.getStringExtra("roll_number")).set(details)
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

            details.put("full_name", intent.getStringExtra("full_name"));
            details.put("gender", intent.getStringExtra("gender"));
            details.put("email", intent.getStringExtra("email"));
            details.put("phone_no", intent.getStringExtra("phone_no"));
            details.put("department", intent.getStringExtra("department"));
            details.put("role", intent.getStringExtra("role"));

            db.collection("College_Project").document("teacher").collection("teacher_details")
                    .document(currentUserEmail).set(details)
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