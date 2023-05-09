package com.example.collegeproject.profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.collegeproject.R;
import com.example.collegeproject.databinding.ActivityProfileBinding;
import com.example.collegeproject.databinding.ProfileEditBinding;
import com.example.collegeproject.studentData.StudentData;
import com.example.collegeproject.teacherData.TeacherData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {
    private ActivityProfileBinding binding;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    MaterialAlertDialogBuilder dailog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        // student
        db.collection("College_Project").document("student").collection("4th Year").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot stuRollNo : task.getResult().getDocuments()){
                                StudentData data = stuRollNo.toObject(StudentData.class);
                                if(data.getEmail().equals(mAuth.getCurrentUser().getEmail())){
                                    binding.constraintLayout.setVisibility(View.VISIBLE);
                                    binding.collapsingToolbar.setTitle(data.getFull_name());
                                    binding.email.setText(data.getEmail());
                                    binding.pPhone.setText(data.getPersonal_phone());
                                    binding.fPhone.setText(data.getFather_phone());
                                    binding.departmentName.setText(data.getDepartment());
                                    binding.aFee.setText(data.getAcademic_fee());
                                    if (data.getHostel_fee().isEmpty())
                                        binding.hFee.setText("N/A");
                                    else
                                        binding.hFee.setText(data.getHostel_fee());
                                }
                            }
                        }
                    }
                });

        db.collection("College_Project").document("student").collection("3rd Year").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot stuRollNo : task.getResult().getDocuments()){
                                StudentData data = stuRollNo.toObject(StudentData.class);
                                if(data.getEmail().equals(mAuth.getCurrentUser().getEmail())){
                                    binding.constraintLayout.setVisibility(View.VISIBLE);
                                    binding.collapsingToolbar.setTitle(data.getFull_name());
                                    binding.email.setText(data.getEmail());
                                    binding.pPhone.setText(data.getPersonal_phone());
                                    binding.fPhone.setText(data.getFather_phone());
                                    binding.departmentName.setText(data.getDepartment());
                                    binding.aFee.setText(data.getAcademic_fee());
                                    if (data.getHostel_fee().isEmpty())
                                        binding.hFee.setText("N/A");
                                    else
                                        binding.hFee.setText(data.getHostel_fee());
                                }
                            }
                        }
                    }
                });


        db.collection("College_Project").document("student").collection("2nd Year").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot stuRollNo : task.getResult().getDocuments()){
                                StudentData data = stuRollNo.toObject(StudentData.class);
                                if(data.getEmail().equals(mAuth.getCurrentUser().getEmail())){
                                    binding.constraintLayout.setVisibility(View.VISIBLE);
                                    binding.collapsingToolbar.setTitle(data.getFull_name());
                                    binding.email.setText(data.getEmail());
                                    binding.pPhone.setText(data.getPersonal_phone());
                                    binding.fPhone.setText(data.getFather_phone());
                                    binding.departmentName.setText(data.getDepartment());
                                    binding.aFee.setText(data.getAcademic_fee());
                                    if (data.getHostel_fee().isEmpty())
                                        binding.hFee.setText("N/A");
                                    else
                                        binding.hFee.setText(data.getHostel_fee());
                                }
                            }
                        }
                    }
                });


        db.collection("College_Project").document("student").collection("1st Year").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot stuRollNo : task.getResult().getDocuments()){
                                StudentData data = stuRollNo.toObject(StudentData.class);
                                if(data.getEmail().equals(mAuth.getCurrentUser().getEmail())){
                                    binding.constraintLayout.setVisibility(View.VISIBLE);
                                    binding.collapsingToolbar.setTitle(data.getFull_name());
                                    binding.email.setText(data.getEmail());
                                    binding.pPhone.setText(data.getPersonal_phone());
                                    binding.fPhone.setText(data.getFather_phone());
                                    binding.departmentName.setText(data.getDepartment());
                                    binding.aFee.setText(data.getAcademic_fee());
                                    if (data.getHostel_fee().isEmpty())
                                        binding.hFee.setText("N/A");
                                    else
                                        binding.hFee.setText(data.getHostel_fee());
                                }
                            }
                        }
                    }
                });

        //teacher

        db.collection("College_Project").document("teacher").collection("teacher_details")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot teacherEmail : task.getResult().getDocuments()){
                                TeacherData data = teacherEmail.toObject(TeacherData.class);
                                if(data.getEmail().equals(mAuth.getCurrentUser().getEmail())){
                                    binding.constraintLayout.setVisibility(View.VISIBLE);
                                    binding.collapsingToolbar.setTitle(data.getFull_name());
                                    binding.email.setText(data.getEmail());
                                    binding.pPhone.setText(data.getPhone_no());
                                    binding.departmentName.setText(data.getDepartment());
                                    binding.fnoText.setVisibility(View.GONE);
                                    binding.fnoLayout.setVisibility(View.GONE);
                                    binding.aFeeText.setVisibility(View.GONE);
                                    binding.aFeeLayout.setVisibility(View.GONE);
                                    binding.hFeeText.setVisibility(View.GONE);
                                    binding.hFeeLayout.setVisibility(View.GONE);

                                }
                            }
                        }
                    }
                });

        
        
        
        // Edit profile

        binding.emailEdit.setEnabled(false);

        
        binding.emailEdit.setOnClickListener(v -> {

            dailog = new MaterialAlertDialogBuilder(this);
            dailog.setTitle("Update Email Id");
            ProfileEditBinding profileEditBinding = ProfileEditBinding.inflate(LayoutInflater.from(this));
            dailog.setView(profileEditBinding.getRoot());
            dailog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if(binding.email.getText().toString().equals(profileEditBinding.email.getText().toString().trim()))
                        return;

                    HashMap<String,Object> hashMap = new HashMap<>();
                    hashMap.put("email", profileEditBinding.email.getText().toString().trim());

                    db.collection("College_Project").document("student").collection("4th Year").get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()){
                                        for(DocumentSnapshot stuRollNo : task.getResult().getDocuments()){
                                            StudentData data = stuRollNo.toObject(StudentData.class);
                                            if(data.getEmail().equals(mAuth.getCurrentUser().getEmail())){
                                                db.collection("College_Project").document("student").collection("4th Year")
                                                        .document(data.getRoll_number()).update(hashMap)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()){
                                                                    binding.email.setText(profileEditBinding.email.getText().toString().trim());
                                                                    Toast.makeText(ProfileActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    }
                                }
                            });

                    db.collection("College_Project").document("student").collection("3rd Year").get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()){
                                        for(DocumentSnapshot stuRollNo : task.getResult().getDocuments()){
                                            StudentData data = stuRollNo.toObject(StudentData.class);
                                            if(data.getEmail().equals(mAuth.getCurrentUser().getEmail())){
                                                db.collection("College_Project").document("student").collection("3rd Year")
                                                        .document(data.getRoll_number()).update(hashMap)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()){
                                                                    binding.email.setText(profileEditBinding.email.getText().toString().trim());
                                                                    Toast.makeText(ProfileActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    }
                                }
                            });


                    db.collection("College_Project").document("student").collection("2nd Year").get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()){
                                        for(DocumentSnapshot stuRollNo : task.getResult().getDocuments()){
                                            StudentData data = stuRollNo.toObject(StudentData.class);
                                            if(data.getEmail().equals(mAuth.getCurrentUser().getEmail())){
                                               db.collection("College_Project").document("student").collection("2nd Year")
                                                       .document(data.getRoll_number()).update(hashMap)
                                                       .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                           @Override
                                                           public void onComplete(@NonNull Task<Void> task) {
                                                               if(task.isSuccessful()){
                                                                   binding.email.setText(profileEditBinding.email.getText().toString().trim());
                                                                   Toast.makeText(ProfileActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                                                               }
                                                           }
                                                       });
                                            }
                                        }
                                    }
                                }
                            });


                    db.collection("College_Project").document("student").collection("1st Year").get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()){
                                        for(DocumentSnapshot stuRollNo : task.getResult().getDocuments()){
                                            StudentData data = stuRollNo.toObject(StudentData.class);
                                            if(data.getEmail().equals(mAuth.getCurrentUser().getEmail())){
                                                db.collection("College_Project").document("student").collection("1st Year")
                                                        .document(data.getRoll_number()).update(hashMap)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()){
                                                                    binding.email.setText(profileEditBinding.email.getText().toString().trim());
                                                                    Toast.makeText(ProfileActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    }
                                }
                            });

                    //teacher

                    db.collection("College_Project").document("teacher").collection("teacher_details")
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()){
                                        for(DocumentSnapshot teacherEmail : task.getResult().getDocuments()){
                                            TeacherData data = teacherEmail.toObject(TeacherData.class);
                                            if(data.getEmail().equals(mAuth.getCurrentUser().getEmail())){
                                                db.collection("College_Project").document("teacher").collection("teacher_details")
                                                        .document(mAuth.getCurrentUser().getEmail()).update(hashMap)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()){
                                                                    binding.email.setText(profileEditBinding.email.getText().toString().trim());
                                                                    Toast.makeText(ProfileActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });

                                            }
                                        }
                                    }
                                }
                            });

                    dialog.dismiss();
                }
            }).setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(ProfileActivity.this, "discarded", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }).create();
            profileEditBinding.emailLayout.setVisibility(View.VISIBLE);
            profileEditBinding.email.setText(binding.email.getText().toString());
            dailog.show();
        });
        
        binding.phoneEdit.setOnClickListener(v -> {
            dailog = new MaterialAlertDialogBuilder(this);
            dailog.setTitle("Update Phone Number");
            ProfileEditBinding profileEditBinding = ProfileEditBinding.inflate(LayoutInflater.from(this));
            dailog.setView(profileEditBinding.getRoot());
            dailog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if(binding.pPhone.getText().toString().equals(profileEditBinding.personalphone.getText().toString().trim()))
                        return;

                    HashMap<String,Object> hashMap = new HashMap<>();
                    hashMap.put("personal_phone", profileEditBinding.personalphone.getText().toString().trim());

                    // student

                    db.collection("College_Project").document("student").collection("4th Year").get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()){
                                        for(DocumentSnapshot stuRollNo : task.getResult().getDocuments()){
                                            StudentData data = stuRollNo.toObject(StudentData.class);
                                            if(data.getEmail().equals(mAuth.getCurrentUser().getEmail())){
                                                db.collection("College_Project").document("student").collection("4th Year")
                                                        .document(data.getRoll_number()).update(hashMap)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()){
                                                                    binding.pPhone.setText(profileEditBinding.personalphone.getText().toString().trim());
                                                                    Toast.makeText(ProfileActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    }
                                }
                            });

                    db.collection("College_Project").document("student").collection("3rd Year").get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()){
                                        for(DocumentSnapshot stuRollNo : task.getResult().getDocuments()){
                                            StudentData data = stuRollNo.toObject(StudentData.class);
                                            if(data.getEmail().equals(mAuth.getCurrentUser().getEmail())){
                                                db.collection("College_Project").document("student").collection("3rd Year")
                                                        .document(data.getRoll_number()).update(hashMap)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()){
                                                                    binding.pPhone.setText(profileEditBinding.personalphone.getText().toString().trim());
                                                                    Toast.makeText(ProfileActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    }
                                }
                            });


                    db.collection("College_Project").document("student").collection("2nd Year").get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()){
                                        for(DocumentSnapshot stuRollNo : task.getResult().getDocuments()){
                                            StudentData data = stuRollNo.toObject(StudentData.class);
                                            if(data.getEmail().equals(mAuth.getCurrentUser().getEmail())){
                                                db.collection("College_Project").document("student").collection("2nd Year")
                                                        .document(data.getRoll_number()).update(hashMap)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()){
                                                                    binding.pPhone.setText(profileEditBinding.personalphone.getText().toString().trim());
                                                                    Toast.makeText(ProfileActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    }
                                }
                            });


                    db.collection("College_Project").document("student").collection("1st Year").get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()){
                                        for(DocumentSnapshot stuRollNo : task.getResult().getDocuments()){
                                            StudentData data = stuRollNo.toObject(StudentData.class);
                                            if(data.getEmail().equals(mAuth.getCurrentUser().getEmail())){
                                                db.collection("College_Project").document("student").collection("1st Year")
                                                        .document(data.getRoll_number()).update(hashMap)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()){
                                                                    binding.pPhone.setText(profileEditBinding.personalphone.getText().toString().trim());
                                                                    Toast.makeText(ProfileActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    }
                                }
                            });




                    //teacher
                    if(binding.pPhone.getText().toString().equals(profileEditBinding.personalphone.getText().toString().trim()))
                        return;

                    HashMap<String, Object> hashMapTeacher = new HashMap<>();
                    hashMapTeacher.put("phone_no", profileEditBinding.personalphone.getText().toString().trim());

                    db.collection("College_Project").document("teacher").collection("teacher_details")
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()){
                                        for(DocumentSnapshot teacherEmail : task.getResult().getDocuments()){
                                            TeacherData data = teacherEmail.toObject(TeacherData.class);
                                            if(data.getEmail().equals(mAuth.getCurrentUser().getEmail())){
                                                db.collection("College_Project").document("teacher").collection("teacher_details")
                                                        .document(mAuth.getCurrentUser().getEmail()).update(hashMapTeacher)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()){
                                                                    binding.pPhone.setText(profileEditBinding.personalphone.getText().toString().trim());
                                                                    Toast.makeText(ProfileActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });

                                            }
                                        }
                                    }
                                }
                            });

                    dialog.dismiss();
                }
            }).setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(ProfileActivity.this, "discarded", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }).create();
            profileEditBinding.phoneLayout.setVisibility(View.VISIBLE);
            profileEditBinding.personalphone.setText(binding.pPhone.getText().toString());
            dailog.show();
        });
        
        binding.fatherPhoneEdit.setOnClickListener(v -> {
            dailog = new MaterialAlertDialogBuilder(this);
            dailog.setTitle("Update Father Phone");
            ProfileEditBinding profileEditBinding = ProfileEditBinding.inflate(LayoutInflater.from(this));
            dailog.setView(profileEditBinding.getRoot());
            dailog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if(binding.fPhone.getText().toString().equals(profileEditBinding.fatherphone.getText().toString().trim()))
                        return;

                    HashMap<String,Object> hashMap = new HashMap<>();
                    hashMap.put("father_phone", profileEditBinding.fatherphone.getText().toString().trim());

                    // student

                    db.collection("College_Project").document("student").collection("4th Year").get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()){
                                        for(DocumentSnapshot stuRollNo : task.getResult().getDocuments()){
                                            StudentData data = stuRollNo.toObject(StudentData.class);
                                            if(data.getEmail().equals(mAuth.getCurrentUser().getEmail())){
                                                db.collection("College_Project").document("student").collection("4th Year")
                                                        .document(data.getRoll_number()).update(hashMap)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()){
                                                                    binding.fPhone.setText(profileEditBinding.fatherphone.getText().toString().trim());
                                                                    Toast.makeText(ProfileActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    }
                                }
                            });

                    db.collection("College_Project").document("student").collection("3rd Year").get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()){
                                        for(DocumentSnapshot stuRollNo : task.getResult().getDocuments()){
                                            StudentData data = stuRollNo.toObject(StudentData.class);
                                            if(data.getEmail().equals(mAuth.getCurrentUser().getEmail())){
                                                db.collection("College_Project").document("student").collection("3rd Year")
                                                        .document(data.getRoll_number()).update(hashMap)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()){
                                                                    binding.fPhone.setText(profileEditBinding.fatherphone.getText().toString().trim());
                                                                    Toast.makeText(ProfileActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    }
                                }
                            });


                    db.collection("College_Project").document("student").collection("2nd Year").get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()){
                                        for(DocumentSnapshot stuRollNo : task.getResult().getDocuments()){
                                            StudentData data = stuRollNo.toObject(StudentData.class);
                                            if(data.getEmail().equals(mAuth.getCurrentUser().getEmail())){
                                                db.collection("College_Project").document("student").collection("2nd Year")
                                                        .document(data.getRoll_number()).update(hashMap)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()){
                                                                    binding.fPhone.setText(profileEditBinding.fatherphone.getText().toString().trim());
                                                                    Toast.makeText(ProfileActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    }
                                }
                            });


                    db.collection("College_Project").document("student").collection("1st Year").get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()){
                                        for(DocumentSnapshot stuRollNo : task.getResult().getDocuments()){
                                            StudentData data = stuRollNo.toObject(StudentData.class);
                                            if(data.getEmail().equals(mAuth.getCurrentUser().getEmail())){
                                                db.collection("College_Project").document("student").collection("1st Year")
                                                        .document(data.getRoll_number()).update(hashMap)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()){
                                                                    binding.fPhone.setText(profileEditBinding.fatherphone.getText().toString().trim());
                                                                    Toast.makeText(ProfileActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    }
                                }
                            });

                    dialog.dismiss();
                }
            }).setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(ProfileActivity.this, "discarded", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }).create();
            profileEditBinding.fphoneLayout.setVisibility(View.VISIBLE);
            profileEditBinding.fatherphone.setText(binding.fPhone.getText().toString());
            dailog.show();
        });
        
        binding.academicFeeEdit.setOnClickListener(v -> {
            dailog = new MaterialAlertDialogBuilder(this);
            dailog.setTitle("Update Academic Fee");
            ProfileEditBinding profileEditBinding = ProfileEditBinding.inflate(LayoutInflater.from(this));
            dailog.setView(profileEditBinding.getRoot());
            dailog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if(binding.aFee.getText().toString().equals(profileEditBinding.fee.getText().toString().trim()))
                        return;

                    HashMap<String,Object> hashMap = new HashMap<>();
                    hashMap.put("academic_fee", profileEditBinding.fee.getText().toString().trim());

                    // student

                    db.collection("College_Project").document("student").collection("4th Year").get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()){
                                        for(DocumentSnapshot stuRollNo : task.getResult().getDocuments()){
                                            StudentData data = stuRollNo.toObject(StudentData.class);
                                            if(data.getEmail().equals(mAuth.getCurrentUser().getEmail())){
                                                db.collection("College_Project").document("student").collection("4th Year")
                                                        .document(data.getRoll_number()).update(hashMap)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()){
                                                                    binding.aFee.setText(profileEditBinding.fee.getText().toString().trim());
                                                                    Toast.makeText(ProfileActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    }
                                }
                            });

                    db.collection("College_Project").document("student").collection("3rd Year").get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()){
                                        for(DocumentSnapshot stuRollNo : task.getResult().getDocuments()){
                                            StudentData data = stuRollNo.toObject(StudentData.class);
                                            if(data.getEmail().equals(mAuth.getCurrentUser().getEmail())){
                                                db.collection("College_Project").document("student").collection("3rd Year")
                                                        .document(data.getRoll_number()).update(hashMap)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()){
                                                                    binding.aFee.setText(profileEditBinding.fee.getText().toString().trim());
                                                                    Toast.makeText(ProfileActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    }
                                }
                            });


                    db.collection("College_Project").document("student").collection("2nd Year").get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()){
                                        for(DocumentSnapshot stuRollNo : task.getResult().getDocuments()){
                                            StudentData data = stuRollNo.toObject(StudentData.class);
                                            if(data.getEmail().equals(mAuth.getCurrentUser().getEmail())){
                                                db.collection("College_Project").document("student").collection("2nd Year")
                                                        .document(data.getRoll_number()).update(hashMap)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()){
                                                                    binding.aFee.setText(profileEditBinding.fee.getText().toString().trim());
                                                                    Toast.makeText(ProfileActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    }
                                }
                            });


                    db.collection("College_Project").document("student").collection("1st Year").get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()){
                                        for(DocumentSnapshot stuRollNo : task.getResult().getDocuments()){
                                            StudentData data = stuRollNo.toObject(StudentData.class);
                                            if(data.getEmail().equals(mAuth.getCurrentUser().getEmail())){
                                                db.collection("College_Project").document("student").collection("1st Year")
                                                        .document(data.getRoll_number()).update(hashMap)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()){
                                                                    binding.aFee.setText(profileEditBinding.fee.getText().toString().trim());
                                                                    Toast.makeText(ProfileActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    }
                                }
                            });

                    dialog.dismiss();
                }
            }).setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(ProfileActivity.this, "discarded", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }).create();
            profileEditBinding.academicFeeLayout.setVisibility(View.VISIBLE);
            profileEditBinding.fee.setText(binding.aFee.getText().toString());
            dailog.show();
        });
        
        binding.hostelFeeEdit.setOnClickListener(v -> {
            dailog = new MaterialAlertDialogBuilder(this);
            dailog.setTitle("Update Hostel fee");
            ProfileEditBinding profileEditBinding = ProfileEditBinding.inflate(LayoutInflater.from(this));
            dailog.setView(profileEditBinding.getRoot());
            dailog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if(binding.hFee.getText().toString().equals(profileEditBinding.hostelFee.getText().toString().trim()))
                        return;

                    HashMap<String,Object> hashMap = new HashMap<>();
                    hashMap.put("hostel_fee", profileEditBinding.hostelFee.getText().toString().trim());

                    // student

                    db.collection("College_Project").document("student").collection("4th Year").get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()){
                                        for(DocumentSnapshot stuRollNo : task.getResult().getDocuments()){
                                            StudentData data = stuRollNo.toObject(StudentData.class);
                                            if(data.getEmail().equals(mAuth.getCurrentUser().getEmail())){
                                                db.collection("College_Project").document("student").collection("4th Year")
                                                        .document(data.getRoll_number()).update(hashMap)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()){
                                                                    binding.hFee.setText(profileEditBinding.hostelFee.getText().toString().trim());
                                                                    Toast.makeText(ProfileActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    }
                                }
                            });

                    db.collection("College_Project").document("student").collection("3rd Year").get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()){
                                        for(DocumentSnapshot stuRollNo : task.getResult().getDocuments()){
                                            StudentData data = stuRollNo.toObject(StudentData.class);
                                            if(data.getEmail().equals(mAuth.getCurrentUser().getEmail())){
                                                db.collection("College_Project").document("student").collection("3rd Year")
                                                        .document(data.getRoll_number()).update(hashMap)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()){
                                                                    binding.hFee.setText(profileEditBinding.hostelFee.getText().toString().trim());
                                                                    Toast.makeText(ProfileActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    }
                                }
                            });


                    db.collection("College_Project").document("student").collection("2nd Year").get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()){
                                        for(DocumentSnapshot stuRollNo : task.getResult().getDocuments()){
                                            StudentData data = stuRollNo.toObject(StudentData.class);
                                            if(data.getEmail().equals(mAuth.getCurrentUser().getEmail())){
                                                db.collection("College_Project").document("student").collection("2nd Year")
                                                        .document(data.getRoll_number()).update(hashMap)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()){
                                                                    binding.hFee.setText(profileEditBinding.hostelFee.getText().toString().trim());
                                                                    Toast.makeText(ProfileActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    }
                                }
                            });


                    db.collection("College_Project").document("student").collection("1st Year").get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()){
                                        for(DocumentSnapshot stuRollNo : task.getResult().getDocuments()){
                                            StudentData data = stuRollNo.toObject(StudentData.class);
                                            if(data.getEmail().equals(mAuth.getCurrentUser().getEmail())){
                                                db.collection("College_Project").document("student").collection("1st Year")
                                                        .document(data.getRoll_number()).update(hashMap)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()){
                                                                    binding.hFee.setText(profileEditBinding.hostelFee.getText().toString().trim());
                                                                    Toast.makeText(ProfileActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    }
                                }
                            });

                    dialog.dismiss();
                }
            }).setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(ProfileActivity.this, "discarded", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }).create();
            profileEditBinding.hostelFeeLayout.setVisibility(View.VISIBLE);
            profileEditBinding.hostelFee.setText(binding.hFee.getText().toString());
            dailog.show();
        });

        binding.departmentEdit.setOnClickListener(v -> {
            Toast.makeText(this, "Since currently we have one department we can't edit it.", Toast.LENGTH_SHORT).show();
        });




       /* db.collection("College_Project").document("C.S.E/student/4th Year/"+mAuth.getCurrentUser().getUid()+"/"+mAuth.getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    StudentData data = task.getResult().toObject(StudentData.class);
                    if(data !=null){
                        binding.constraintLayout.setVisibility(View.VISIBLE);
                        binding.collapsingToolbar.setTitle(data.getFull_name());
                        binding.email.setText(data.getEmail());
                        binding.pPhone.setText(data.getPersonal_phone());
                        binding.fPhone.setText(data.getFather_phone());
                        binding.departmentName.setText(data.getDepartment());
                        binding.aFee.setText(data.getAcademic_fee());
                        if (data.getHostel_fee().isEmpty())
                            binding.hFee.setText("N/A");
                        else
                            binding.hFee.setText(data.getHostel_fee());
                    }
                }
            }
        });

        db.collection("College_Project").document("C.S.E/student/3rd Year/"+mAuth.getCurrentUser().getUid()+"/"+mAuth.getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    StudentData data = task.getResult().toObject(StudentData.class);
                    if(data !=null){
                        binding.constraintLayout.setVisibility(View.VISIBLE);
                        binding.collapsingToolbar.setTitle(data.getFull_name());
                        binding.email.setText(data.getEmail());
                        binding.pPhone.setText(data.getPersonal_phone());
                        binding.fPhone.setText(data.getFather_phone());
                        binding.departmentName.setText(data.getDepartment());
                        binding.aFee.setText(data.getAcademic_fee());
                        if (data.getHostel_fee().isEmpty())
                            binding.hFee.setText("N/A");
                        else
                            binding.hFee.setText(data.getHostel_fee());
                    }

                }
            }
        });

        db.collection("College_Project").document("C.S.E/student/2nd Year/"+mAuth.getCurrentUser().getUid()+"/"+mAuth.getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    StudentData data = task.getResult().toObject(StudentData.class);
                    if(data !=null){
                        binding.constraintLayout.setVisibility(View.VISIBLE);
                        binding.collapsingToolbar.setTitle(data.getFull_name());
                        binding.email.setText(data.getEmail());
                        binding.pPhone.setText(data.getPersonal_phone());
                        binding.fPhone.setText(data.getFather_phone());
                        binding.departmentName.setText(data.getDepartment());
                        binding.aFee.setText(data.getAcademic_fee());
                        if (data.getHostel_fee().isEmpty())
                            binding.hFee.setText("N/A");
                        else
                            binding.hFee.setText(data.getHostel_fee());
                    }
                }
            }
        });

        db.collection("College_Project").document("C.S.E/student/1st Year/"+mAuth.getCurrentUser().getUid()+"/"+mAuth.getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    StudentData data = task.getResult().toObject(StudentData.class);
                    if(data !=null){
                        binding.constraintLayout.setVisibility(View.VISIBLE);
                        binding.collapsingToolbar.setTitle(data.getFull_name());
                        binding.email.setText(data.getEmail());
                        binding.pPhone.setText(data.getPersonal_phone());
                        binding.fPhone.setText(data.getFather_phone());
                        binding.departmentName.setText(data.getDepartment());
                        binding.aFee.setText(data.getAcademic_fee());
                        if (data.getHostel_fee().isEmpty())
                            binding.hFee.setText("N/A");
                        else
                            binding.hFee.setText(data.getHostel_fee());
                    }

                }
            }
        });


        // teacher

        db.collection("College_Project").document("C.S.E/teacher/"+mAuth.getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    TeacherData data = task.getResult().toObject(TeacherData.class);
                    if(data !=null){
                        binding.constraintLayout.setVisibility(View.VISIBLE);
                        binding.collapsingToolbar.setTitle(data.getFull_name());
                        binding.email.setText(data.getEmail());
                        binding.pPhone.setText(data.getPhone_no());
                        binding.departmentName.setText(data.getDepartment());
                        binding.fnoText.setVisibility(View.GONE);
                        binding.fnoLayout.setVisibility(View.GONE);
                        binding.aFeeText.setVisibility(View.GONE);
                        binding.aFeeLayout.setVisibility(View.GONE);
                        binding.hFeeText.setVisibility(View.GONE);
                        binding.hFeeLayout.setVisibility(View.GONE);
                    }
                }
            }
        });


        db.collection("College_Project").document("student").collection("student_details")
            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult().getDocuments()) {
                            StudentData data = document.toObject(StudentData.class);
                            if (data != null) {
                                db.collection("College_Project").document("student").collection("student_details")
                                        .document(document.getId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful() && mAuth.getCurrentUser().getEmail().equals(task.getResult().getData().get("e_mail"))) {
                                                    // Toast.makeText(ProfileActivity.this, "data Found", Toast.LENGTH_SHORT).show();

                                                    binding.constraintLayout.setVisibility(View.VISIBLE);
                                                    binding.collapsingToolbar.setTitle(data.getFull_name());
                                                    binding.email.setText(data.getEmail());
                                                    binding.pPhone.setText(data.getPersonal_phone());
                                                    binding.fPhone.setText(data.getFather_phone());
                                                    binding.departmentName.setText(data.getDepartment());
                                                    binding.aFee.setText(data.getAcademic_fee());
                                                    if (data.getHostel_fee().isEmpty())
                                                        binding.hFee.setText("N/A");
                                                    else
                                                        binding.hFee.setText(data.getHostel_fee());

                                                }


                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(ProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                                            }
                                        });


                            }
                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ProfileActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        db.collection("College_Project").document("teacher").collection("teacher_details")
            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult().getDocuments()) {
                            TeacherData data = document.toObject(TeacherData.class);
                            if (data != null) {
                                db.collection("College_Project").document("teacher").collection("teacher_details")
                                        .document(document.getId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful() && mAuth.getCurrentUser().getEmail().equals(task.getResult().getData().get("email"))) {

                                                     binding.constraintLayout.setVisibility(View.VISIBLE);
                                                     binding.collapsingToolbar.setTitle(data.getFull_name());
                                                     binding.email.setText(data.getEmail());
                                                     binding.pPhone.setText(data.getPhone_no());
                                                     binding.departmentName.setText(data.getDepartment());
                                                     binding.fnoText.setVisibility(View.GONE);
                                                     binding.fnoLayout.setVisibility(View.GONE);
                                                     binding.aFeeText.setVisibility(View.GONE);
                                                     binding.aFeeLayout.setVisibility(View.GONE);
                                                     binding.hFeeText.setVisibility(View.GONE);
                                                     binding.hFeeLayout.setVisibility(View.GONE);

                                                }
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(ProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                                            }
                                        });


                            }
                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ProfileActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });*/

        binding.fab.setOnClickListener(v -> {
            onBackPressed();
        });
    }




}


