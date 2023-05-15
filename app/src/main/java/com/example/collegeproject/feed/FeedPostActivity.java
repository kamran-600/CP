package com.example.collegeproject.feed;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.collegeproject.databinding.ActivityFeedPostBinding;
import com.example.collegeproject.studentData.StudentData;
import com.example.collegeproject.teacherData.TeacherData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;

public class FeedPostActivity extends AppCompatActivity {

    ActivityFeedPostBinding binding;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseStorage storage;
    StorageReference storageReference;
    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        db.collection("College_Project").document("student").collection("4th Year").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot rollNo : task.getResult().getDocuments()){
                                StudentData data = rollNo.toObject(StudentData.class);
                                if(data.getEmail().equals(mAuth.getCurrentUser().getEmail())){
                                    binding.userName.setText(data.getFull_name());


                                    if(data.getProfileImageUrl() != null){
                                        storageReference = storage.getReferenceFromUrl(data.getProfileImageUrl());
                                        storageReference.getBytes(1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                            @Override
                                            public void onSuccess(byte[] bytes) {
                                                if(bytes != null){
                                                    Bitmap fullBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                    fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                                                    binding.userProfile.setImageBitmap(fullBitmap);
                                                }
                                            }
                                        });
                                    }
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
                            for(DocumentSnapshot rollNo : task.getResult().getDocuments()){
                                StudentData data = rollNo.toObject(StudentData.class);
                                if(data.getEmail().equals(mAuth.getCurrentUser().getEmail())) {
                                    binding.userName.setText(data.getFull_name());

                                    if(data.getProfileImageUrl() != null){
                                        storageReference = storage.getReferenceFromUrl(data.getProfileImageUrl());
                                        storageReference.getBytes(1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                            @Override
                                            public void onSuccess(byte[] bytes) {
                                                if(bytes != null){
                                                    Bitmap fullBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                    fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                                                    binding.userProfile.setImageBitmap(fullBitmap);
                                                }
                                            }
                                        });
                                    }
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
                            for(DocumentSnapshot rollNo : task.getResult().getDocuments()){
                                StudentData data = rollNo.toObject(StudentData.class);
                                if(data.getEmail().equals(mAuth.getCurrentUser().getEmail())){
                                    binding.userName.setText(data.getFull_name());

                                    if(data.getProfileImageUrl() != null){
                                        storageReference = storage.getReferenceFromUrl(data.getProfileImageUrl());
                                        storageReference.getBytes(1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                            @Override
                                            public void onSuccess(byte[] bytes) {
                                                if(bytes != null){
                                                    Bitmap fullBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                    fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                                                    binding.userProfile.setImageBitmap(fullBitmap);
                                                }
                                            }
                                        });
                                    }
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
                                    binding.userName.setText(data.getFull_name());

                                    if(data.getProfileImageUrl() != null){
                                        storageReference = storage.getReferenceFromUrl(data.getProfileImageUrl());
                                        storageReference.getBytes(1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                            @Override
                                            public void onSuccess(byte[] bytes) {
                                                if(bytes != null){
                                                    Bitmap fullBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                    fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                                                    binding.userProfile.setImageBitmap(fullBitmap);
                                                }
                                            }
                                        });
                                    }
                                }
                            }
                        }
                    }
                });


        // teacher

        db.collection("College_Project").document("teacher").collection("teacher_details").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot teacherEmail : task.getResult().getDocuments()){
                                TeacherData data = teacherEmail.toObject(TeacherData.class);
                                if(data.getEmail().equals(mAuth.getCurrentUser().getEmail())){
                                    binding.userName.setText(data.getFull_name());

                                    if(data.getProfileImageUrl() != null){
                                        storageReference = storage.getReferenceFromUrl(data.getProfileImageUrl());
                                        storageReference.getBytes(1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                            @Override
                                            public void onSuccess(byte[] bytes) {
                                                if(bytes != null){
                                                    Bitmap fullBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                    fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                                                    binding.userProfile.setImageBitmap(fullBitmap);
                                                }
                                            }
                                        });
                                    }
                                }
                            }
                        }
                    }
                });


        binding.gallery.setOnClickListener(v -> {
            galLauncher.launch("image/*");
        });

        binding.feedPost.setOnClickListener(v -> {
            onBackPressed();

        });


    }

    ActivityResultLauncher<String> galLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
               if(result != null){
                   binding.imageCardImage.setImageURI(result);
                   binding.imageCard.setVisibility(View.VISIBLE);
                   imageUri = result;
                   binding.clear.setOnClickListener(v -> {
                       binding.imageCard.setVisibility(View.GONE);
                       binding.imageCardImage.setImageURI(null);
                   });
               }

        }
    });
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("feedText", binding.feedMsg.getText().toString());
       // intent.putExtra("feedImage", imageUri);
        setResult(RESULT_OK, intent);
        finish();
    }


}