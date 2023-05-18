package com.example.collegeproject.forgetPass;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.collegeproject.LoginActivity;
import com.example.collegeproject.databinding.ActivityForgetPasswordBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ForgetPasswordActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    ActivityForgetPasswordBinding binding;

    String email;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        // for hide keyboard
        binding.getRoot().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if(getCurrentFocus() !=null){
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        if(!Objects.equals(getIntent().getStringExtra("email"), "")){
            binding.email.setText(getIntent().getStringExtra("email"));
        }

        binding.forgetPassword.setOnClickListener(v -> {
            validateData();
        });

    }

    private void validateData() {
        email = binding.email.getText().toString();
        if (email.isEmpty()){
            binding.email.setError("Can not be empty");
            binding.email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.email.setError("Please enter email in correct pattern");
            binding.email.requestFocus();
            return;
        }
        forgetPass();

    }

    private void forgetPass() {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(this, "Password Reset link is sent to "+email, Toast.LENGTH_SHORT).show();
                       // startActivity(new Intent(ForgetPasswordActivity.this, LoginActivity.class));
                        finish();
                    }else {
                        Toast.makeText(this, "Error\n"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });
    }


}