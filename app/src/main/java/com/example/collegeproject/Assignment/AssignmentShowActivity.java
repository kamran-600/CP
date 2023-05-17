package com.example.collegeproject.Assignment;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.collegeproject.R;
import com.example.collegeproject.databinding.ActivityAssignmentShowBinding;
import com.example.collegeproject.studentData.StudentData;
import com.example.collegeproject.teacherData.TeacherData;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.divider.MaterialDividerItemDecoration;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AssignmentShowActivity extends AppCompatActivity {

    ActivityAssignmentShowBinding binding;
    List<AssignmentSubmitModal> userList;
    LinearLayoutManager layoutManager;
    AssignmentSubmitAdapter adapter;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseStorage storage;
    StorageReference storageReference;
    UploadTask uploadTask;
    String studentName, rollNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAssignmentShowBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();

        setSupportActionBar(binding.postBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        binding.AttachAssignmentBtnStudent.setOnClickListener(v -> {
            docLauncher.launch("application/pdf");
        });

        binding.clear.setOnClickListener(v -> {
            binding.AssignmentCardStudent.setVisibility(View.GONE);
            binding.submit.setEnabled(false);
        });

        binding.postBar.setSubtitle(getIntent().getStringExtra("postedDate"));

        // Assignment url
        String url = getIntent().getStringExtra("url");

        storageReference = storage.getReferenceFromUrl(url);

        // get info from firestore Storage url
        storageReference.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
            @Override
            public void onSuccess(StorageMetadata storageMetadata) {
                binding.docTitle.setText(storageMetadata.getName());
                double i = (double) storageMetadata.getSizeBytes();
                if (i < 900000) {
                    i /= Math.pow(10, 3);
                    binding.docSize.setText(String.format("%.2f", i) + " KB");
                } else {
                    i /= Math.pow(10, 6);
                    binding.docSize.setText(String.format("%.2f", i) + " MB");
                }
                binding.docType.setText(storageMetadata.getContentType().split("/")[1]);

                binding.dueDate.setText(getIntent().getStringExtra("dueDate"));
                binding.desc.setText(getIntent().getStringExtra("desc"));
            }
        });

        // show the image or PDF file of assignment
        storageReference.getBytes(1024 * 1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                if (bitmap != null) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, new ByteArrayOutputStream());
                    binding.docImage.setImageBitmap(bitmap);
                    binding.AssignmentLayout.setOnClickListener(v -> {
                        Intent intent = new Intent(AssignmentShowActivity.this, AssignmentOpenActivity.class);
                        intent.putExtra("byte", bytes);
                        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(AssignmentShowActivity.this, Pair.create(binding.docImage, "ImageTransition"));
                        startActivity(intent, optionsCompat.toBundle());
                    });
                } else {
                    binding.AssignmentLayout.setOnClickListener(v -> {
                        Intent intent = new Intent(AssignmentShowActivity.this, AssignmentOpenActivity.class);
                        intent.putExtra("url", url);
                        intent.putExtra("name", binding.docTitle.getText().toString());
                        startActivity(intent);

                    });
                }

            }
        });


        // student

        // check whether assignment is submitted, checked  or not if submitted, checked then show (student perspective)

        db.collection("College_Project").document("student").collection("4th Year").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot stuRollNo : task.getResult().getDocuments()) {
                                StudentData data = stuRollNo.toObject(StudentData.class);
                                if (data.getEmail().equals(mAuth.getCurrentUser().getEmail())) {
                                    studentName = data.getFull_name();
                                    rollNo = data.getRoll_number();

                                    db.collection("College_Project").document("teacher").collection("assignments")
                                            .document(getIntent().getStringExtra("id")).collection("submittedAssignments")
                                            .document(studentName).get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        AssignmentSubmitModal modal = task.getResult().toObject(AssignmentSubmitModal.class);

                                                        if (modal != null && modal.getSubmittedAssignmentUrl() != null) {
                                                            storageReference = storage.getReferenceFromUrl(modal.getSubmittedAssignmentUrl());

                                                            storageReference.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
                                                                @Override
                                                                public void onSuccess(StorageMetadata storageMetadata) {
                                                                    binding.CompletedAssignmentName.setText(storageMetadata.getName());
                                                                    binding.AssignmentCardStudent.setVisibility(View.VISIBLE);
                                                                    binding.clear.setVisibility(View.INVISIBLE);
                                                                    binding.AttachAssignmentBtnStudent.setText("Submitted");
                                                                    binding.submit.setVisibility(View.GONE);
                                                                    binding.AttachAssignmentBtnStudent.setEnabled(false);
                                                                    binding.postBar.setTitle("Assignment Submitted");
                                                                    binding.AssignmentCardStudent.setOnClickListener(v -> {
                                                                        Intent intent = new Intent(AssignmentShowActivity.this, AssignmentOpenActivity.class);
                                                                        intent.putExtra("url", modal.getSubmittedAssignmentUrl());
                                                                        intent.putExtra("name", binding.CompletedAssignmentName.getText().toString());
                                                                        startActivity(intent);

                                                                    });

                                                                }
                                                            });


                                                        }
                                                        else {
                                                            binding.postBar.setTitle("Submit Assignment");
                                                            binding.AttachAssignmentBtnStudent.setVisibility(View.VISIBLE);
                                                            binding.submit.setVisibility(View.VISIBLE);
                                                            return;
                                                        }
                                                        if (modal.getCheckedAssignmentUrl() != null) {
                                                            storageReference = storage.getReferenceFromUrl(modal.getCheckedAssignmentUrl());

                                                            storageReference.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
                                                                @Override
                                                                public void onSuccess(StorageMetadata storageMetadata) {
                                                                    binding.CheckedAssignmentName.setText(storageMetadata.getName());
                                                                    binding.AssignmentCheckedCard.setVisibility(View.VISIBLE);
                                                                    binding.clear2.setVisibility(View.INVISIBLE);
                                                                    binding.AttachAssignmentBtnStudent.setText("Checked");
                                                                    binding.submit.setVisibility(View.GONE);
                                                                    binding.AttachAssignmentBtnStudent.setEnabled(false);
                                                                    getSupportActionBar().setTitle("Assignment Checked");
                                                                    binding.AssignmentCheckedCard.setOnClickListener(v -> {
                                                                        Intent intent = new Intent(AssignmentShowActivity.this, AssignmentOpenActivity.class);
                                                                        intent.putExtra("url", modal.getCheckedAssignmentUrl());
                                                                        intent.putExtra("name", binding.CheckedAssignmentName.getText().toString());
                                                                        startActivity(intent);

                                                                    });

                                                                }
                                                            });

                                                        }


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
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot stuRollNo : task.getResult().getDocuments()) {
                                StudentData data = stuRollNo.toObject(StudentData.class);
                                if (data.getEmail().equals(mAuth.getCurrentUser().getEmail())) {
                                    studentName = data.getFull_name();
                                    rollNo = data.getRoll_number();

                                    db.collection("College_Project").document("teacher").collection("assignments")
                                            .document(getIntent().getStringExtra("id")).collection("submittedAssignments")
                                            .document(studentName).get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        AssignmentSubmitModal modal = task.getResult().toObject(AssignmentSubmitModal.class);

                                                        if (modal != null && modal.getSubmittedAssignmentUrl() != null) {
                                                            storageReference = storage.getReferenceFromUrl(modal.getSubmittedAssignmentUrl());

                                                            storageReference.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
                                                                @Override
                                                                public void onSuccess(StorageMetadata storageMetadata) {
                                                                    binding.CompletedAssignmentName.setText(storageMetadata.getName());
                                                                    binding.AssignmentCardStudent.setVisibility(View.VISIBLE);
                                                                    binding.clear.setVisibility(View.INVISIBLE);
                                                                    binding.AttachAssignmentBtnStudent.setText("Submitted");
                                                                    binding.submit.setVisibility(View.GONE);
                                                                    binding.AttachAssignmentBtnStudent.setEnabled(false);
                                                                    binding.postBar.setTitle("Assignment Submitted");
                                                                    binding.AssignmentCardStudent.setOnClickListener(v -> {
                                                                        Intent intent = new Intent(AssignmentShowActivity.this, AssignmentOpenActivity.class);
                                                                        intent.putExtra("url", modal.getSubmittedAssignmentUrl());
                                                                        intent.putExtra("name", binding.CompletedAssignmentName.getText().toString());
                                                                        startActivity(intent);

                                                                    });

                                                                }
                                                            });


                                                        }
                                                        else {
                                                            binding.postBar.setTitle("Submit Assignment");
                                                            binding.AttachAssignmentBtnStudent.setVisibility(View.VISIBLE);
                                                            binding.submit.setVisibility(View.VISIBLE);
                                                            return;
                                                        }
                                                        if (modal.getCheckedAssignmentUrl() != null) {
                                                            storageReference = storage.getReferenceFromUrl(modal.getCheckedAssignmentUrl());

                                                            storageReference.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
                                                                @Override
                                                                public void onSuccess(StorageMetadata storageMetadata) {
                                                                    binding.CheckedAssignmentName.setText(storageMetadata.getName());
                                                                    binding.AssignmentCheckedCard.setVisibility(View.VISIBLE);
                                                                    binding.clear2.setVisibility(View.INVISIBLE);
                                                                    binding.AttachAssignmentBtnStudent.setText("Checked");
                                                                    binding.submit.setVisibility(View.GONE);
                                                                    binding.AttachAssignmentBtnStudent.setEnabled(false);
                                                                    getSupportActionBar().setTitle("Assignment Checked");
                                                                    binding.AssignmentCheckedCard.setOnClickListener(v -> {
                                                                        Intent intent = new Intent(AssignmentShowActivity.this, AssignmentOpenActivity.class);
                                                                        intent.putExtra("url", modal.getCheckedAssignmentUrl());
                                                                        intent.putExtra("name", binding.CheckedAssignmentName.getText().toString());
                                                                        startActivity(intent);

                                                                    });

                                                                }
                                                            });

                                                        }

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
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot stuRollNo : task.getResult().getDocuments()) {
                                StudentData data = stuRollNo.toObject(StudentData.class);
                                if (data.getEmail().equals(mAuth.getCurrentUser().getEmail())) {
                                    studentName = data.getFull_name();
                                    rollNo = data.getRoll_number();

                                    db.collection("College_Project").document("teacher").collection("assignments")
                                            .document(getIntent().getStringExtra("id")).collection("submittedAssignments")
                                            .document(studentName).get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        AssignmentSubmitModal modal = task.getResult().toObject(AssignmentSubmitModal.class);

                                                        if (modal != null && modal.getSubmittedAssignmentUrl() != null) {
                                                            storageReference = storage.getReferenceFromUrl(modal.getSubmittedAssignmentUrl());

                                                            storageReference.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
                                                                @Override
                                                                public void onSuccess(StorageMetadata storageMetadata) {
                                                                    binding.CompletedAssignmentName.setText(storageMetadata.getName());
                                                                    binding.AssignmentCardStudent.setVisibility(View.VISIBLE);
                                                                    binding.clear.setVisibility(View.INVISIBLE);
                                                                    binding.AttachAssignmentBtnStudent.setText("Submitted");
                                                                    binding.submit.setVisibility(View.GONE);
                                                                    binding.AttachAssignmentBtnStudent.setEnabled(false);
                                                                    binding.postBar.setTitle("Assignment Submitted");
                                                                    binding.AssignmentCardStudent.setOnClickListener(v -> {
                                                                        Intent intent = new Intent(AssignmentShowActivity.this, AssignmentOpenActivity.class);
                                                                        intent.putExtra("url", modal.getSubmittedAssignmentUrl());
                                                                        intent.putExtra("name", binding.CompletedAssignmentName.getText().toString());
                                                                        startActivity(intent);

                                                                    });

                                                                }
                                                            });


                                                        }
                                                        else {
                                                            binding.postBar.setTitle("Submit Assignment");
                                                            binding.AttachAssignmentBtnStudent.setVisibility(View.VISIBLE);
                                                            binding.submit.setVisibility(View.VISIBLE);
                                                            return;
                                                        }
                                                        if (modal.getCheckedAssignmentUrl() != null) {
                                                            storageReference = storage.getReferenceFromUrl(modal.getCheckedAssignmentUrl());

                                                            storageReference.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
                                                                @Override
                                                                public void onSuccess(StorageMetadata storageMetadata) {
                                                                    binding.CheckedAssignmentName.setText(storageMetadata.getName());
                                                                    binding.AssignmentCheckedCard.setVisibility(View.VISIBLE);
                                                                    binding.clear2.setVisibility(View.INVISIBLE);
                                                                    binding.AttachAssignmentBtnStudent.setText("Checked");
                                                                    binding.submit.setVisibility(View.GONE);
                                                                    binding.AttachAssignmentBtnStudent.setEnabled(false);
                                                                    getSupportActionBar().setTitle("Assignment Checked");
                                                                    binding.AssignmentCheckedCard.setOnClickListener(v -> {
                                                                        Intent intent = new Intent(AssignmentShowActivity.this, AssignmentOpenActivity.class);
                                                                        intent.putExtra("url", modal.getCheckedAssignmentUrl());
                                                                        intent.putExtra("name", binding.CheckedAssignmentName.getText().toString());
                                                                        startActivity(intent);

                                                                    });

                                                                }
                                                            });

                                                        }
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
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot stuRollNo : task.getResult().getDocuments()) {
                                StudentData data = stuRollNo.toObject(StudentData.class);
                                if (data.getEmail().equals(mAuth.getCurrentUser().getEmail())) {
                                    studentName = data.getFull_name();
                                    rollNo = data.getRoll_number();

                                    db.collection("College_Project").document("teacher").collection("assignments")
                                            .document(getIntent().getStringExtra("id")).collection("submittedAssignments")
                                            .document(studentName).get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        AssignmentSubmitModal modal = task.getResult().toObject(AssignmentSubmitModal.class);

                                                        if (modal != null && modal.getSubmittedAssignmentUrl() != null) {
                                                            storageReference = storage.getReferenceFromUrl(modal.getSubmittedAssignmentUrl());

                                                            storageReference.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
                                                                @Override
                                                                public void onSuccess(StorageMetadata storageMetadata) {
                                                                    binding.CompletedAssignmentName.setText(storageMetadata.getName());
                                                                    binding.AssignmentCardStudent.setVisibility(View.VISIBLE);
                                                                    binding.clear.setVisibility(View.INVISIBLE);
                                                                    binding.AttachAssignmentBtnStudent.setText("Submitted");
                                                                    binding.submit.setVisibility(View.GONE);
                                                                    binding.postBar.setTitle("Assignment Submitted");
                                                                    if(binding.AssignmentCheckedCard.getVisibility() == View.VISIBLE){
                                                                        binding.postBar.setTitle("Assignment Checked");
                                                                    }
                                                                    binding.AttachAssignmentBtnStudent.setEnabled(false);
                                                                    binding.AssignmentCardStudent.setOnClickListener(v -> {
                                                                        Intent intent = new Intent(AssignmentShowActivity.this, AssignmentOpenActivity.class);
                                                                        intent.putExtra("url", modal.getSubmittedAssignmentUrl());
                                                                        intent.putExtra("name", binding.CompletedAssignmentName.getText().toString());
                                                                        startActivity(intent);

                                                                    });

                                                                }
                                                            });


                                                        }
                                                        else {
                                                            binding.postBar.setTitle("Submit Assignment");
                                                            binding.AttachAssignmentBtnStudent.setVisibility(View.VISIBLE);
                                                            binding.submit.setVisibility(View.VISIBLE);
                                                            return;
                                                        }
                                                        if (modal.getCheckedAssignmentUrl() != null) {
                                                            storageReference = storage.getReferenceFromUrl(modal.getCheckedAssignmentUrl());

                                                            storageReference.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
                                                                @Override
                                                                public void onSuccess(StorageMetadata storageMetadata) {
                                                                    binding.CheckedAssignmentName.setText(storageMetadata.getName());
                                                                    binding.AssignmentCheckedCard.setVisibility(View.VISIBLE);
                                                                    binding.clear2.setVisibility(View.INVISIBLE);
                                                                    binding.AttachAssignmentBtnStudent.setText("Checked");
                                                                    binding.submit.setVisibility(View.GONE);
                                                                    binding.postBar.setTitle("Assignment Checked");
                                                                    binding.AttachAssignmentBtnStudent.setEnabled(false);
                                                                    binding.AssignmentCheckedCard.setOnClickListener(v -> {
                                                                        Intent intent = new Intent(AssignmentShowActivity.this, AssignmentOpenActivity.class);
                                                                        intent.putExtra("url", modal.getCheckedAssignmentUrl());
                                                                        intent.putExtra("name", binding.CheckedAssignmentName.getText().toString());
                                                                        startActivity(intent);

                                                                    });

                                                                }
                                                            });

                                                        }

                                                    }
                                                }
                                            });

                                }
                            }
                        }
                    }
                });



        // teacher


        // show the list of the students who have submitted the assignment (teacher perspective)

        db.collection("College_Project").document("teacher").collection("teacher_details")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot teacherEmail : task.getResult().getDocuments()) {
                                TeacherData data = teacherEmail.toObject(TeacherData.class);
                                if (data.getEmail().equals(mAuth.getCurrentUser().getEmail())) {
                                    binding.listOfStudentText.setVisibility(View.VISIBLE);
                                    binding.recyclerview.setVisibility(View.VISIBLE);

                                    db.collection("College_Project").document("teacher").collection("assignments")
                                            .document(getIntent().getStringExtra("id")).collection("submittedAssignments").get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        userList = new ArrayList<>();
                                                        for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                                            AssignmentSubmitModal submittedData = documentSnapshot.toObject(AssignmentSubmitModal.class);
                                                            if (submittedData != null) {
                                                                AssignmentSubmitModal modal = new AssignmentSubmitModal(submittedData.getStudentName(), submittedData.getSubmittedAssignmentUrl(), submittedData.getDate(), submittedData.getTime(), submittedData.getRoll_number());
                                                                userList.add(modal);
                                                                adapter = new AssignmentSubmitAdapter(userList, getIntent().getStringExtra("id"));
                                                                binding.recyclerview.setAdapter(adapter);
                                                                binding.recyclerview.addItemDecoration(new MaterialDividerItemDecoration(AssignmentShowActivity.this, MaterialDividerItemDecoration.VERTICAL));
                                                                adapter.notifyDataSetChanged();

                                                            }
                                                        }
                                                        if(userList.size() ==0){
                                                            Toast.makeText(AssignmentShowActivity.this, "No Student have submitted the assignment yet", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                }
                                            });


                                }
                            }

                        }
                    }
                });


    }

    // submit the assignment ans copy (student perspective)

    ActivityResultLauncher<String> docLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
            if(result != null){
                getTitleAndSize(result);

                StorageReference docReference = storage.getReference().child("Assignment/submittedDocs/"+binding.CompletedAssignmentName.getText().toString());
                uploadTask = docReference.putFile(result);
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //  Toast.makeText(CreateAssignmentActivity.this, "Success\n"+taskSnapshot.getUploadSessionUri().toString(), Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AssignmentShowActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(!task.isSuccessful())
                        {
                            Toast.makeText(AssignmentShowActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                        return docReference.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            Uri downloadUrl = task.getResult();
                            Toast.makeText(AssignmentShowActivity.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                            binding.submit.setText("SUBMIT");
                            binding.submit.setEnabled(true);
                            binding.CompletedAssignmentName.setOnClickListener(view -> {
                                Intent intent = new Intent(Intent.ACTION_VIEW, downloadUrl);
                                startActivity(intent);
                            });

                            binding.submit.setOnClickListener(v -> {
                                binding.submit.setEnabled(false);
                                Snackbar.make(v, "Submitting Assignment...", Snackbar.LENGTH_SHORT).setBackgroundTint(getResources().getColor(R.color.upperBlue)).setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).show();

                                String date = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(System.currentTimeMillis());

                                Calendar calendar = Calendar.getInstance();
                                int hour = calendar.get(Calendar.HOUR);
                                if(hour == 0)
                                    hour = 12;
                                final int minutes = calendar.get(Calendar.MINUTE);
                                final String am_pm = calendar.get(Calendar.AM_PM)==Calendar.AM ? " AM" : " PM";
                                final String currentTime = String.format(Locale.ENGLISH,"%02d:"+"%02d",hour,minutes)+am_pm;

                                Map<String,Object> map = new HashMap<>();
                                map.put("submittedAssignmentUrl", downloadUrl);
                                map.put("studentName", studentName);
                                map.put("roll_number", rollNo);
                                map.put("date", date);
                                map.put("time", currentTime);

                                db.collection("College_Project").document("teacher").collection("assignments")
                                        .document(getIntent().getStringExtra("id")).collection("submittedAssignments")
                                        .document(studentName).set(map, SetOptions.merge())
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Snackbar.make(v, "Assignment submitted successfully", Snackbar.LENGTH_SHORT).setBackgroundTint(getResources().getColor(R.color.upperBlue)).setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).show();
                                                    new Handler().postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            onBackPressed();
                                                        }
                                                    }, 1000);

                                                }
                                            }
                                        });


                            });


                        }
                    }
                });


            }
        }
    });

    private void getTitleAndSize(Uri result) {

        Cursor cursor = getContentResolver().query(result, null, null, null, null);
        int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        cursor.moveToFirst();
        binding.CompletedAssignmentName.setText(cursor.getString(nameIndex));
        cursor.close();

        binding.AssignmentCardStudent.setVisibility(View.VISIBLE);
        binding.submit.setText("WAIT");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}