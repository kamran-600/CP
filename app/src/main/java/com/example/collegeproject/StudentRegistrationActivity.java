package com.example.collegeproject;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.collegeproject.databinding.ActivityStudentRegistrationBinding;

import java.util.Calendar;
import java.util.Locale;

public class StudentRegistrationActivity extends AppCompatActivity {

    DatePickerDialog.OnDateSetListener setListener;
    private ActivityStudentRegistrationBinding binding;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /* *****************************************
                     For Showing Calender
           ***************************************** */

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

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        binding.dobLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                final String date = String.format(Locale.ENGLISH, "%02d/" + "%02d/", day, month) + year;

                binding.dob.setText(date);
            }
        };

        // For Gender Dropdown Menu
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> gAdapter = ArrayAdapter.createFromResource(this, R.array.Gender_Array, android.R.layout.simple_spinner_dropdown_item);
// Specify the layout to use when the list of choices appears
        gAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        binding.gender.setAdapter(gAdapter);

        // For department Dropdown Menu
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> dAdapter = ArrayAdapter.createFromResource(this,
                R.array.Department_Array, android.R.layout.simple_spinner_dropdown_item);
// Specify the layout to use when the list of choices appears
        dAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        binding.department.setAdapter(dAdapter);

        // For Academic Year Dropdown Menu
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> yAdapter = ArrayAdapter.createFromResource(this,
                R.array.Year_Array, android.R.layout.simple_spinner_dropdown_item);
// Specify the layout to use when the list of choices appears
        yAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        binding.academicyear.setAdapter(yAdapter);

        binding.continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!binding.rollnumber.getText().toString().trim().equals("") &&
                        !binding.email.getText().toString().trim().equals("") &&
                        !binding.department.getText().toString().trim().equals("") &&
                        !binding.academicyear.getText().toString().trim().equals("") &&
                        !binding.batch.getText().toString().trim().equals("") &&
                        !binding.fee.getText().toString().trim().equals("") &&
                        !binding.fullName.getText().toString().trim().equals("") &&
                        !binding.gender.getText().toString().trim().equals("") &&
                        !binding.dob.getText().toString().trim().equals("") &&
                        !binding.personalphone.getText().toString().trim().equals("") &&
                        !binding.fathername.getText().toString().trim().equals("") &&
                        !binding.fatherphone.getText().toString().trim().equals("")) {

                    if (!Patterns.EMAIL_ADDRESS.matcher(binding.email.getText().toString().trim()).matches()) {
                        Toast.makeText(StudentRegistrationActivity.this, "Please fill email in correct Pattern", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Intent intent = new Intent(StudentRegistrationActivity.this, UserPasswordActivity.class);
                    intent.putExtra("id", 0);
                    intent.putExtra("roll_number", binding.rollnumber.getText().toString().trim());
                    intent.putExtra("email", binding.email.getText().toString().trim());
                    intent.putExtra("department", binding.department.getText().toString().trim());
                    intent.putExtra("academic_year", binding.academicyear.getText().toString().trim());
                    intent.putExtra("batch", binding.batch.getText().toString().trim());
                    intent.putExtra("academic_fee", binding.fee.getText().toString().trim());
                    intent.putExtra("hostel_fee", binding.hostelFee.getText().toString().trim());
                    intent.putExtra("full_name", binding.fullName.getText().toString().trim());
                    intent.putExtra("gender", binding.gender.getText().toString().trim());
                    intent.putExtra("dob", binding.dob.getText().toString().trim());
                    intent.putExtra("personal_phone", binding.personalphone.getText().toString().trim());
                    intent.putExtra("father_name", binding.fathername.getText().toString().trim());
                    intent.putExtra("father_phone", binding.fatherphone.getText().toString().trim());

                    startActivity(intent);
                } else {
                    Toast.makeText(StudentRegistrationActivity.this, "Please fill * fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
