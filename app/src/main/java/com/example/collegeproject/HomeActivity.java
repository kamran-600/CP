package com.example.collegeproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.collegeproject.Assignment.AssignmentOpenActivity;
import com.example.collegeproject.BottomFragments.AssignmentFragment;
import com.example.collegeproject.BottomFragments.ChatsFragment;
import com.example.collegeproject.BottomFragments.ContactsFragment;
import com.example.collegeproject.BottomFragments.HomeFragment;
import com.example.collegeproject.Progress.ProgressFragment;

import com.example.collegeproject.attendance.ClassFragment;
import com.example.collegeproject.databinding.ActivityHomeBinding;
import com.example.collegeproject.databinding.DrawerHeaderBinding;
import com.example.collegeproject.fee.FeeFragment;
import com.example.collegeproject.profile.ProfileActivity;
import com.example.collegeproject.studentData.Remark.RemarkFragment;
import com.example.collegeproject.studentData.StudentData;
import com.example.collegeproject.teacherData.TeacherData;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class HomeActivity extends AppCompatActivity {

    static final float END_SCALE = 0.7f;
    DrawerHeaderBinding headerBinding;
    StorageReference storageReference;
    UploadTask uploadTask;

    ActivityResultLauncher<String> requestLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean result) {
            if (result) {
                getSupportFragmentManager().beginTransaction().replace(R.id.bReplace, new ContactsFragment()).commit();
            } else {
                Toast.makeText(HomeActivity.this, "Call Permission Denied\nTo Allow Permission go to\n Setting < App Manager / App Permission", Toast.LENGTH_SHORT).show();
            }
        }
    });
    private ActivityHomeBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseStorage storage;


    /* *****************************************
                   Drawer Animation
       ***************************************** */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        animateNavDrawer(); //calling for animation

        Menu menu = binding.navigationView.getMenu(); //taking reference of logout menu option
        MenuItem menuItem = menu.findItem(R.id.logout);
        menu.findItem(R.id.progress).setVisible(false);

        /* *****************************************
                 for Coloring the logout option
           ***************************************** */

        SpannableString logout = new SpannableString(menuItem.getTitle());
        ForegroundColorSpan fcs = new ForegroundColorSpan(getResources().getColor(R.color.bloodRed));
        logout.setSpan(fcs, 0, logout.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        menuItem.setTitle(logout);
        getSupportFragmentManager().beginTransaction().replace(R.id.bReplace, new HomeFragment()).commit();


        /* *****************************************
                     Drawer OnCLick Perform
           ***************************************** */


        binding.navigationView.setCheckedItem(R.id.home);

        binding.navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.bReplace, new HomeFragment()).commit();
                    break;
                case R.id.attendence:
                    getSupportFragmentManager().beginTransaction().replace(R.id.bReplace, new ClassFragment()).commit();
                    break;
                case R.id.fee:
                    getSupportFragmentManager().beginTransaction().replace(R.id.bReplace, new FeeFragment()).commit();
                    break;
                case R.id.remark:
                    getSupportFragmentManager().beginTransaction().replace(R.id.bReplace, new RemarkFragment()).commit();
                    break;
                case R.id.progress:
                    getSupportFragmentManager().beginTransaction().replace(R.id.bReplace, new ProgressFragment()).commit();
                    break;
                case R.id.logout:
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.signOut();
                    Toast.makeText(HomeActivity.this, "Sign Out", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                    finishAffinity();
                    break;
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START,true);
            return true;
        });

         /* *****************************************
                 goto to profile activity
           ***************************************** */
      //  View headerView = binding.navigationView.getHeaderView(0);
        headerBinding = DrawerHeaderBinding.inflate(LayoutInflater.from(this));
        binding.navigationView.addHeaderView(headerBinding.getRoot());


        headerBinding.editImage.setOnClickListener(v ->
                galLauncher.launch("image/*"));


        // student

        db.collection("College_Project").document("student").collection("4th Year").get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for(DocumentSnapshot rollNo : task.getResult().getDocuments()){
                                        StudentData data = rollNo.toObject(StudentData.class);
                                        if(data.getEmail().equals(mAuth.getCurrentUser().getEmail())){
                                            headerBinding.name.setText(data.getFull_name());
                                            headerBinding.role.setText(data.getRole());
                                            menu.findItem(R.id.fee).setVisible(false);


                                            if(data.getProfileImageUrl() != null){
                                                storageReference = storage.getReferenceFromUrl(data.getProfileImageUrl());
                                                storageReference.getBytes(1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                                    @Override
                                                    public void onSuccess(byte[] bytes) {
                                                        if(bytes != null){
                                                            Bitmap fullBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                            fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                                                            headerBinding.profileImage.setImageBitmap(fullBitmap);
                                                            headerBinding.profileImage.setOnClickListener(v -> {
                                                                Intent intent = new Intent(HomeActivity.this, AssignmentOpenActivity.class);
                                                                intent.putExtra("byte", bytes);
                                                                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(HomeActivity.this, Pair.create(headerBinding.profileImage, "ImageTransition"));
                                                                startActivity(intent, optionsCompat.toBundle());
                                                            });
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
                                    headerBinding.name.setText(data.getFull_name());
                                    headerBinding.role.setText(data.getRole());
                                    menu.findItem(R.id.fee).setVisible(false);

                                    if(data.getProfileImageUrl() != null){
                                        storageReference = storage.getReferenceFromUrl(data.getProfileImageUrl());
                                        storageReference.getBytes(1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                            @Override
                                            public void onSuccess(byte[] bytes) {
                                                if(bytes != null){
                                                    Bitmap fullBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                    fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                                                    headerBinding.profileImage.setImageBitmap(fullBitmap);
                                                    headerBinding.profileImage.setOnClickListener(v -> {
                                                        Intent intent = new Intent(HomeActivity.this, AssignmentOpenActivity.class);
                                                        intent.putExtra("byte", bytes);
                                                        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(HomeActivity.this, Pair.create(headerBinding.profileImage, "ImageTransition"));
                                                        startActivity(intent, optionsCompat.toBundle());
                                                    });
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
                                    headerBinding.name.setText(data.getFull_name());
                                    headerBinding.role.setText(data.getRole());
                                    menu.findItem(R.id.fee).setVisible(false);

                                    if(data.getProfileImageUrl() != null){
                                        storageReference = storage.getReferenceFromUrl(data.getProfileImageUrl());
                                        storageReference.getBytes(1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                            @Override
                                            public void onSuccess(byte[] bytes) {
                                                if(bytes != null){
                                                    Bitmap fullBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                    fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                                                    headerBinding.profileImage.setImageBitmap(fullBitmap);
                                                    headerBinding.profileImage.setOnClickListener(v -> {
                                                        Intent intent = new Intent(HomeActivity.this, AssignmentOpenActivity.class);
                                                        intent.putExtra("byte", bytes);
                                                        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(HomeActivity.this, Pair.create(headerBinding.profileImage, "ImageTransition"));
                                                        startActivity(intent, optionsCompat.toBundle());
                                                    });
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
                                    headerBinding.name.setText(data.getFull_name());
                                    headerBinding.role.setText(data.getRole());
                                    menu.findItem(R.id.fee).setVisible(false);

                                    if(data.getProfileImageUrl() != null){
                                        storageReference = storage.getReferenceFromUrl(data.getProfileImageUrl());
                                        storageReference.getBytes(1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                            @Override
                                            public void onSuccess(byte[] bytes) {
                                                if(bytes != null){
                                                    Bitmap fullBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                    fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                                                    headerBinding.profileImage.setImageBitmap(fullBitmap);
                                                    headerBinding.profileImage.setOnClickListener(v -> {
                                                        Intent intent = new Intent(HomeActivity.this, AssignmentOpenActivity.class);
                                                        intent.putExtra("byte", bytes);
                                                        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(HomeActivity.this, Pair.create(headerBinding.profileImage, "ImageTransition"));
                                                        startActivity(intent, optionsCompat.toBundle());
                                                    });
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
                                    headerBinding.name.setText(data.getFull_name());
                                    headerBinding.role.setText(data.getRole());

                                    if(data.getProfileImageUrl() != null){
                                        storageReference = storage.getReferenceFromUrl(data.getProfileImageUrl());
                                        storageReference.getBytes(1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                            @Override
                                            public void onSuccess(byte[] bytes) {
                                                if(bytes != null){
                                                    Bitmap fullBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                    fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                                                    headerBinding.profileImage.setImageBitmap(fullBitmap);
                                                    headerBinding.profileImage.setOnClickListener(v -> {
                                                        Intent intent = new Intent(HomeActivity.this, AssignmentOpenActivity.class);
                                                        intent.putExtra("byte", bytes);
                                                        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(HomeActivity.this, Pair.create(headerBinding.profileImage, "ImageTransition"));
                                                        startActivity(intent, optionsCompat.toBundle());
                                                    });
                                                }
                                            }
                                        });
                                    }
                                }
                            }
                        }
                    }
                });







        headerBinding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                intent.putExtra("name", headerBinding.name.getText().toString().trim());
                startActivity(intent);
            }
        });



        /* *****************************************
               Bottom Navigation OnCLick Perform
           ***************************************** */

        binding.bottom.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.bReplace, new HomeFragment()).commit();
                    break;
                case R.id.assignment:
                    getSupportFragmentManager().beginTransaction().replace(R.id.bReplace, new AssignmentFragment()).commit();
                    break;
                case R.id.chats:
                    getSupportFragmentManager().beginTransaction().replace(R.id.bReplace, new ChatsFragment()).commit();
                    break;
                case R.id.contacts:
                    if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // ActivityCompat.requestPermissions(CreateAssignmentActivity.this, new String[]{Manifest.permission.CAMERA},1);  //line 136-151
                        requestLauncher.launch(Manifest.permission.CALL_PHONE);
                    } else {
                        getSupportFragmentManager().beginTransaction().replace(R.id.bReplace, new ContactsFragment()).commit();
                    }
                    break;
            }

            return true;
        });

    }

    private byte[] compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//Compression quality, here 100 means no compression, the storage of compressed data to baos
        int options = 90;
        while (baos.toByteArray().length / 1024 > 400) {  //Loop if compressed picture is greater than 400kb, than to compression
            baos.reset();//Reset baos is empty baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//The compression options%, storing the compressed data to the baos
            options -= 10;//Every time reduced by 10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//The storage of compressed data in the baos to ByteArrayInputStream
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//The ByteArrayInputStream data generation
        headerBinding.profileImage.setImageBitmap(bitmap);
        return baos.toByteArray();
    }

    ActivityResultLauncher<String> galLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
            if(result != null){
               // headerBinding.profileImage.setImageURI(result);

                try {
                    Bitmap fullBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), result);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                    StorageReference galReference = storageReference.child("ProfileImage/"+"image.jpg");


                    if(baos.toByteArray().length/1024 < 400){
                        headerBinding.profileImage.setImageURI(result);
                        uploadTask = galReference.putFile(result);
                    }else {
                        byte[] downSizeBytes = compressImage(fullBitmap);
                        uploadTask = galReference.putBytes(downSizeBytes);

                    }
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //  Toast.makeText(CreateAssignmentActivity.this, "Success\n"+taskSnapshot.getUploadSessionUri().toString(), Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(HomeActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(!task.isSuccessful())
                            {
                                Toast.makeText(HomeActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                            return galReference.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if(task.isSuccessful()){
                                Uri downloadUrl = task.getResult();
                                Toast.makeText(HomeActivity.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                                headerBinding.profileImage.setOnClickListener(v -> {
                                    storageReference = storage.getReferenceFromUrl(downloadUrl.toString());
                                    storageReference.getBytes(1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                        @Override
                                        public void onSuccess(byte[] bytes) {
                                            if(bytes != null){
                                                Bitmap fullBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                                                headerBinding.profileImage.setImageBitmap(fullBitmap);
                                                headerBinding.profileImage.setOnClickListener(v -> {
                                                    Intent intent = new Intent(HomeActivity.this, AssignmentOpenActivity.class);
                                                    intent.putExtra("byte", bytes);
                                                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(HomeActivity.this, Pair.create(headerBinding.profileImage, "ImageTransition"));
                                                    startActivity(intent, optionsCompat.toBundle());
                                                });
                                            }
                                        }
                                    });

                                });

                                Map<String,Object> map = new HashMap<>();
                                map.put("profileImageUrl", downloadUrl);

                                // student

                                db.collection("College_Project").document("student").collection("4th Year").get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if(task.isSuccessful()){
                                                    for(DocumentSnapshot rollNo : task.getResult().getDocuments()){
                                                        StudentData data = rollNo.toObject(StudentData.class);
                                                        if(data.getEmail().equals(mAuth.getCurrentUser().getEmail())){
                                                            db.collection("College_Project").document("student").collection("4th Year")
                                                                    .document(data.getRoll_number()).set(map, SetOptions.merge())
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            Toast.makeText(HomeActivity.this, "Profile Image URL Added", Toast.LENGTH_SHORT).show();
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
                                                    for(DocumentSnapshot rollNo : task.getResult().getDocuments()){
                                                        StudentData data = rollNo.toObject(StudentData.class);
                                                        if(data.getEmail().equals(mAuth.getCurrentUser().getEmail())) {
                                                            db.collection("College_Project").document("student").collection("3rd Year")
                                                                    .document(data.getRoll_number()).set(map, SetOptions.merge())
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            Toast.makeText(HomeActivity.this, "Profile Image URL Added", Toast.LENGTH_SHORT).show();
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
                                                    for(DocumentSnapshot rollNo : task.getResult().getDocuments()){
                                                        StudentData data = rollNo.toObject(StudentData.class);
                                                        if(data.getEmail().equals(mAuth.getCurrentUser().getEmail())){
                                                            db.collection("College_Project").document("student").collection("2nd Year")
                                                                    .document(data.getRoll_number()).set(map, SetOptions.merge())
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            Toast.makeText(HomeActivity.this, "Profile Image URL Added", Toast.LENGTH_SHORT).show();
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
                                                                    .document(data.getRoll_number()).set(map, SetOptions.merge())
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            Toast.makeText(HomeActivity.this, "Profile Image URL Added", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });
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
                                                            db.collection("College_Project").document("teacher").collection("teacher_details")
                                                                    .document(mAuth.getCurrentUser().getEmail()).set(map, SetOptions.merge())
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            Toast.makeText(HomeActivity.this, "Profile Image URl Added", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });
                                                        }
                                                    }
                                                }
                                            }
                                        });


                            }
                        }
                    });



                } catch (IOException e) {
                    Toast.makeText(HomeActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }
    });

    private void animateNavDrawer() {
        binding.drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                //Scale the view based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                binding.bReplace.setScaleX(offsetScale);
                binding.bReplace.setScaleY(offsetScale);
                // translate the view accounting of the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = binding.bReplace.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                binding.bReplace.setTranslationX(xTranslation);
                binding.bottom.setVisibility(View.GONE);
            }


            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                binding.bottom.setVisibility(View.GONE);
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                    binding.bottom.setVisibility(View.VISIBLE);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                    binding.drawerLayout.openDrawer(GravityCompat.START,true);

        }
        return super.onOptionsItemSelected(item);
    }
}