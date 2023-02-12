package com.example.collegeproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.collegeproject.databinding.ActivityStudentRegistrationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class StudentRegistrationActivity extends AppCompatActivity {

    DatePickerDialog.OnDateSetListener setListener;
    private ActivityStudentRegistrationBinding binding;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();


        /* *****************************************
                     For Showing Calender
           ***************************************** */

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        binding.dobLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.dobLayout.setEndIconTintList(ContextCompat.getColorStateList(StudentRegistrationActivity.this, R.color.calender_icon));
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        StudentRegistrationActivity.this
                        , setListener, year, month, day);
                datePickerDialog.show();
            }
        });
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                binding.dob.setText(date);
            }
        };

        // For Gender Dropdown Menu
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> gAdapter = ArrayAdapter.createFromResource(this, R.array.Gender_Array, android.R.layout.simple_spinner_dropdown_item);
// Specify the layout to use when the list of choices appears
        gAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        binding.genderLayout.setEndIconTintList(ContextCompat.getColorStateList(StudentRegistrationActivity.this, R.color.calender_icon));
        binding.gender.setAdapter(gAdapter);

        // For department Dropdown Menu
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> dAdapter = ArrayAdapter.createFromResource(this,
                R.array.Department_Array, android.R.layout.simple_spinner_dropdown_item);
// Specify the layout to use when the list of choices appears
        dAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        binding.departmentLayout.setEndIconTintList(ContextCompat.getColorStateList(StudentRegistrationActivity.this, R.color.calender_icon));
        binding.department.setAdapter(dAdapter);

        // For Academic Year Dropdown Menu
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> yAdapter = ArrayAdapter.createFromResource(this,
                R.array.Year_Array, android.R.layout.simple_spinner_dropdown_item);
// Specify the layout to use when the list of choices appears
        yAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        binding.academicLayout.setEndIconTintList(ContextCompat.getColorStateList(StudentRegistrationActivity.this, R.color.calender_icon));
        binding.academicyear.setAdapter(yAdapter);

        binding.continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentPersonalData();
                studentAcademicData();
                startActivity(new Intent(StudentRegistrationActivity.this, UserPasswordActivity.class));
            }
        });
    }

    private void studentAcademicData() {
        Map<String, Object> sAD = new HashMap<>();
        sAD.put("roll-number", binding.rollnumber.getText().toString().trim());
        sAD.put("department", binding.department.getText().toString().trim());
        sAD.put("academic-year", binding.academicyear.getText().toString().trim());
        sAD.put("batch", binding.batch.getText().toString().trim());
        sAD.put("academic-fee", binding.fee.getText().toString().trim());
        sAD.put("hostel-fee", binding.hostelFee.getText().toString().trim());

        db.collection("College-Project").document("student")
                .collection("academic-details").document(binding.rollnumber.getText().toString().trim()).set(sAD)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(StudentRegistrationActivity.this, "success", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(StudentRegistrationActivity.this, "fail", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    /* ===========================
            store in firestore
      ========================== */
        private void studentPersonalData() {
            Map<String, Object> sPD = new HashMap<>();
            sPD.put("full-name", binding.fullName.getText().toString().trim());
            sPD.put("gender", binding.gender.getText().toString().trim());
            sPD.put("dob", binding.dob.getText().toString().trim());
            sPD.put("e-mail", binding.email.getText().toString().trim());
            sPD.put("personal-phone", binding.personalphone.getText().toString().trim());
            sPD.put("father-name", binding.fathername.getText().toString().trim());
            sPD.put("father-phone", binding.fatherphone.getText().toString().trim());

            db.collection("College-Project").document("student")
                    .collection("personal-details").document(binding.rollnumber.getText().toString().trim()).set(sPD)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(StudentRegistrationActivity.this, "success", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(StudentRegistrationActivity.this, "fail", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
