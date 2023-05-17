package com.example.collegeproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.collegeproject.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;


public class LoginActivity extends AppCompatActivity {

    /*private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[!@#$%^&+=])" +      // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{6,}" +              // at least 6 characters
                    "$");*/
    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finishAffinity();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        binding.ll.setOnTouchListener(new View.OnTouchListener() {
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


        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
            }
        });

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateEnrollForm(binding.editTextEmailAddress.getText().toString().trim(), binding.editTextPassword.getText().toString().trim()))

                    signIn(binding.editTextEmailAddress.getText().toString().trim(), binding.editTextPassword.getText().toString().trim());
            }
        });


    }

    private boolean validateEnrollForm(String email, String password) {
        if (email.equalsIgnoreCase("") || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.editTextEmailAddress.setError("enter the valid email id!");
            binding.editTextEmailAddress.requestFocus();

        }
        if (password.equalsIgnoreCase("")) {
            binding.editTextPassword.setError("can not be empty");
            binding.editTextPassword.requestFocus();
            return false;
        }
        return true;
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finishAffinity();
                } else {
                    Toast.makeText(LoginActivity.this, "You have entered wrong Email / Password. OR \nYou are a new user, you will have to register first.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}