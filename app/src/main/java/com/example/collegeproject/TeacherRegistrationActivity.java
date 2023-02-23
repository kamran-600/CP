package com.example.collegeproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.collegeproject.databinding.ActivityTeacherRegistrationBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class TeacherRegistrationActivity extends AppCompatActivity {

    private ActivityTeacherRegistrationBinding binding;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTeacherRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        db = FirebaseFirestore.getInstance();

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
                uploadTeacherData();
                Intent intent = new Intent(TeacherRegistrationActivity.this, UserPasswordActivity.class);
                intent.putExtra("id", 1);
                intent.putExtra("name", binding.Tname.getText().toString().trim().split(" ")[0]);
                intent.putExtra("phoneNo", binding.phoneNo.getText().toString().trim());
                intent.putExtra("email", binding.email.getText().toString().trim());
                startActivity(intent);
            }
        });
        /* binding.button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  //  if (getResources().getStringArray(R.array.Department_Array)[0] == binding.department.getText().toString() ) {
                        binding.ttc.getRoot().setVisibility(View.VISIBLE);
                    }
            });

            */
    }

    private void uploadTeacherData() {
        Map<String, Object> uTD = new HashMap<>();
        uTD.put("full_name", binding.Tname.getText().toString().trim());
        uTD.put("gender", binding.gender.getText().toString().trim());
        uTD.put("email", binding.email.getText().toString().trim());
        uTD.put("phone_no", binding.phoneNo.getText().toString().trim());
        uTD.put("department", binding.department.getText().toString().trim());
        uTD.put("role", binding.role.getText().toString().trim());

        db.collection("College_Project").document("teacher")
                .collection("teacher_details").document(binding.Tname.getText().toString().trim().split(" ")[0] + "_" + binding.phoneNo.getText().toString().trim()).set(uTD)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(TeacherRegistrationActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(TeacherRegistrationActivity.this, "Failed to Upload", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}