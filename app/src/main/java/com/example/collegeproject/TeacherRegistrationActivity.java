package com.example.collegeproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.collegeproject.databinding.ActivityTeacherRegistrationBinding;

public class TeacherRegistrationActivity extends AppCompatActivity {

    private ActivityTeacherRegistrationBinding binding;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTeacherRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

        // For Gender Dropdown Menu
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> gAdapter = ArrayAdapter.createFromResource(this, R.array.Gender_Array, android.R.layout.simple_spinner_dropdown_item);
// Specify the layout to use when the list of choices appears
        gAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        binding.gender.setAdapter(gAdapter);

        // For department Dropdown Menu
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> tRAdapter = ArrayAdapter.createFromResource(this,
                R.array.Department_Array, android.R.layout.simple_spinner_dropdown_item);
// Specify the layout to use when the list of choices appears
        tRAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        binding.department.setAdapter(tRAdapter);


        ArrayAdapter<CharSequence> roleAdapter = ArrayAdapter.createFromResource(this,
                R.array.Teacher_Role_Array, android.R.layout.simple_spinner_dropdown_item);
// Specify the layout to use when the list of choices appears
        roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        binding.role.setAdapter(roleAdapter);


        binding.continueBtnTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!binding.Tname.getText().toString().trim().equals("") &&
                        !binding.gender.getText().toString().trim().equals("") &&
                        !binding.email.getText().toString().trim().equals("") &&
                        !binding.phoneNo.getText().toString().trim().equals("") &&
                        !binding.department.getText().toString().trim().equals("") &&
                        !binding.role.getText().toString().trim().equals("")) {

                    if (!Patterns.EMAIL_ADDRESS.matcher(binding.email.getText().toString().trim()).matches()) {
                        Toast.makeText(TeacherRegistrationActivity.this, "Please fill email in correct pattern", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Intent intent = new Intent(TeacherRegistrationActivity.this, UserPasswordActivity.class);
                    intent.putExtra("id", 1);
                    intent.putExtra("full_name", binding.Tname.getText().toString().trim());
                    intent.putExtra("gender", binding.gender.getText().toString().trim());
                    intent.putExtra("email", binding.email.getText().toString().trim());
                    intent.putExtra("phone_no", binding.phoneNo.getText().toString().trim());
                    intent.putExtra("department", binding.department.getText().toString().trim());
                    intent.putExtra("role", binding.role.getText().toString().trim());
                    startActivity(intent);
                } else {
                    Toast.makeText(TeacherRegistrationActivity.this, "Please fill * fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}