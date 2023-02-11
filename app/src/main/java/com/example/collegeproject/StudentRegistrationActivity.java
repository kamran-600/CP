package com.example.collegeproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.collegeproject.databinding.ActivityStudentRegistrationBinding;

import java.util.Calendar;

public class StudentRegistrationActivity extends AppCompatActivity {

    DatePickerDialog.OnDateSetListener setListener;
    private ActivityStudentRegistrationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
        binding.genderLayout.setEndIconTintList(ContextCompat.getColorStateList(StudentRegistrationActivity.this,R.color.calender_icon));
        binding.gender.setAdapter(gAdapter);

        // For department Dropdown Menu
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> dAdapter = ArrayAdapter.createFromResource(this,
                R.array.Department_Array, android.R.layout.simple_spinner_dropdown_item);
// Specify the layout to use when the list of choices appears
        dAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        binding.departmentLayout.setEndIconTintList(ContextCompat.getColorStateList(StudentRegistrationActivity.this,R.color.calender_icon));
        binding.department.setAdapter(dAdapter);

        // For Academic Year Dropdown Menu
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> yAdapter = ArrayAdapter.createFromResource(this,
                R.array.Year_Array, android.R.layout.simple_spinner_dropdown_item);
// Specify the layout to use when the list of choices appears
        yAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        binding.academicLayout.setEndIconTintList(ContextCompat.getColorStateList(StudentRegistrationActivity.this,R.color.calender_icon));
        binding.academicyear.setAdapter(yAdapter);

        binding.continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentRegistrationActivity.this,UserPasswordActivity.class));
            }
        });
    }
}