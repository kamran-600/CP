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
            }
        });
    }

    /*private void studentData() {
        Map<String, Object> sAD = new HashMap<>();
        sAD.put("roll_number", binding.rollnumber.getText().toString().trim());
        sAD.put("department", binding.department.getText().toString().trim());
        sAD.put("academic_year", binding.academicyear.getText().toString().trim());
        sAD.put("batch", binding.batch.getText().toString().trim());
        sAD.put("academic_fee", binding.fee.getText().toString().trim());
        sAD.put("hostel_fee", binding.hostelFee.getText().toString().trim());
        sAD.put("full_name", binding.fullName.getText().toString().trim());
        sAD.put("gender", binding.gender.getText().toString().trim());
        sAD.put("dob", binding.dob.getText().toString().trim());
        sAD.put("e_mail", binding.email.getText().toString().trim());
        sAD.put("personal_phone", binding.personalphone.getText().toString().trim());
        sAD.put("father_name", binding.fathername.getText().toString().trim());
        sAD.put("father_phone", binding.fatherphone.getText().toString().trim());

        db.collection("College_Project").document("student")
                .collection("student_details").document(binding.rollnumber.getText().toString().trim()).set(sAD)
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
*/
}
