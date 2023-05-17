package com.example.collegeproject.Assignment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

import com.example.collegeproject.R;
import com.example.collegeproject.databinding.ActivityCreateAssignmentBinding;
import com.example.collegeproject.databinding.ClassBottomDialogBinding;
import com.example.collegeproject.feed.FeedPostActivity;
import com.example.collegeproject.teacherData.TeacherData;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CreateAssignmentActivity extends AppCompatActivity {

    ActivityCreateAssignmentBinding binding;
    DatePickerDialog.OnDateSetListener setListener;
    BottomSheetDialog bottomSheetDialog;
    FirebaseStorage storage;
    StorageReference storageReference;
    UploadTask uploadTask;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    Uri captureImageUri;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAssignmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        // for hide keyboard
            binding.getRoot().setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    if(getCurrentFocus() !=null){
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                        return true;
                    }
                    return false;
                }
            });




        binding.clear.setOnClickListener(view -> {
            binding.assignmentCard.setVisibility(View.GONE);
            binding.post.setEnabled(false);
        });

        setSupportActionBar(binding.postBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.clear);


        // teacher

        db.collection("College_Project").document("teacher").collection("teacher_details").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot teacherEmail : task.getResult().getDocuments()){
                                TeacherData data = teacherEmail.toObject(TeacherData.class);
                                if(data.getEmail().equals(mAuth.getCurrentUser().getEmail())) {
                                    binding.UserName.setText(data.getFull_name());

                                    if (data.getProfileImageBlob() != null) {
                                        Bitmap fullBitmap = BitmapFactory.decodeByteArray(data.getProfileImageBlob().toBytes(), 0, data.getProfileImageBlob().toBytes().length);
                                        fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                                        binding.UserImage.setImageBitmap(fullBitmap);
                                    }
                                }
                            }
                        }
                    }
                });

        bottomSheetDialog = new BottomSheetDialog(CreateAssignmentActivity.this, R.style.BottomSheetTheme);
        ClassBottomDialogBinding bottomDialogBinding = ClassBottomDialogBinding.inflate(LayoutInflater.from(this));
        bottomSheetDialog.setContentView(bottomDialogBinding.getRoot());


        binding.chooseClass.setOnClickListener(view -> {

            bottomSheetDialog.show();
        });
        bottomDialogBinding.csradiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (i == R.id.cs1) {
                    binding.chooseClass.setText("CSE 1st Year");
                    bottomSheetDialog.dismiss();
                }
                if (i == R.id.cs2) {
                    binding.chooseClass.setText("CSE 2nd Year");
                    bottomSheetDialog.dismiss();
                }
                if (i == R.id.cs3) {
                    binding.chooseClass.setText("CSE 3rd Year");
                    bottomSheetDialog.dismiss();
                }
                if (i == R.id.cs4) {
                    binding.chooseClass.setText("CSE 4th Year");
                    bottomSheetDialog.dismiss();
                }
            }
        });

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        binding.dueDateLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        CreateAssignmentActivity.this
                        , setListener, year, month, day);
                datePickerDialog.show();
            }
        });
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                final String currentDate = String.format(Locale.ENGLISH,"%02d/"+"%02d/",day,month)+year;

                binding.dueDate.setText(currentDate);
            }
        };

        binding.cameraImg.setOnClickListener(view -> {
            binding.desc.onEditorAction(EditorInfo.IME_ACTION_DONE);   //for hide keyboard
            binding.dueDate.onEditorAction(EditorInfo.IME_ACTION_DONE);

            String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss",Locale.ENGLISH).format(new Date());

            ContentValues value = new ContentValues();
            value.put(MediaStore.Images.Media.DISPLAY_NAME,timeStamp);
            value.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            captureImageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, value);
           /* image = new File(getApplicationContext().getCacheDir(), timeStamp+".jpg");
           // image = File.createTempFile(time)
            captureImageUri = FileProvider.getUriForFile(getApplicationContext(), "com.example.collegeproject.fileprovider", image);*/
            Toast.makeText(this, captureImageUri.toString(), Toast.LENGTH_SHORT).show();

//            cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, captureImageUri);
           /* File image;
            try {
                image = File.createTempFile(timeStamp,".jpg", getExternalFilesDir(Environment.DIRECTORY_PICTURES));
            } catch (IOException e) {
                Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                throw new RuntimeException(e);
            }
           captureImageUri = FileProvider.getUriForFile(this, "com.example.collegeproject.fileprovider", image);
*/
           // if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                if (ActivityCompat.checkSelfPermission(CreateAssignmentActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // ActivityCompat.requestPermissions(CreateAssignmentActivity.this, new String[]{Manifest.permission.CAMERA},1);
                    requestLauncher.launch(Manifest.permission.CAMERA);
                } else {

                    cameraLauncher.launch(captureImageUri);
                }
           /* } else {
                Toast.makeText(CreateAssignmentActivity.this, "There is no app that support this action", Toast.LENGTH_SHORT).show();
            }
*/
         });


        binding.galleryImg.setOnClickListener(view -> {
            binding.desc.onEditorAction(EditorInfo.IME_ACTION_DONE);   //for hide keyboard
            binding.dueDate.onEditorAction(EditorInfo.IME_ACTION_DONE);
            galleryLauncher.launch("image/*");
        });

        binding.documentImg.setOnClickListener(view -> {
            binding.desc.onEditorAction(EditorInfo.IME_ACTION_DONE);   //for hide keyboard
            binding.dueDate.onEditorAction(EditorInfo.IME_ACTION_DONE);
            docLauncher.launch("application/pdf");
        });

    }

    ActivityResultLauncher<Uri> cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicture(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean result) {
            Toast.makeText(CreateAssignmentActivity.this, "return\n"+ result+ captureImageUri , Toast.LENGTH_SHORT).show();
            if(result){
              //  binding.docImage.setImageURI(captureImageUri);
                getTitleAndSize(captureImageUri);
                binding.docImage.setOnClickListener(v -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW, captureImageUri);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                });



                try {

                    Bitmap fullBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), captureImageUri);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                    StorageReference galReference = storageReference.child("images/"+binding.docTitle.getText().toString());

                    if(baos.toByteArray().length/1024 < 400){
                        binding.docImage.setImageURI(captureImageUri);
                        uploadTask = galReference.putFile(captureImageUri);
                    }else {
                        byte[] downSizeBytes = compressImage(fullBitmap);
                        double i = Double.parseDouble(String.valueOf(downSizeBytes.length));
                        if (i < 900000) {
                            i /= Math.pow(10, 3);
                            binding.docSize.setText("Size : " + String.format("%.2f", i) + " KB");
                        } else {
                            i /= Math.pow(10, 6);
                            binding.docSize.setText("Size : " + String.format("%.2f", i) + " MB");

                        }
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
                            Toast.makeText(CreateAssignmentActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(!task.isSuccessful())
                            {
                                Toast.makeText(CreateAssignmentActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                            return galReference.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if(task.isSuccessful()){
                                Uri downloadUrl = task.getResult();
                                Toast.makeText(CreateAssignmentActivity.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                                binding.post.setText("POST");
                                binding.post.setEnabled(true);
                                binding.docTitle.setOnClickListener(view -> {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, downloadUrl);
                                    startActivity(intent);
                                });

                                db.collection("College_Project").document("teacher").collection("teacher_details")
                                        .document(mAuth.getCurrentUser().getEmail()).get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if(task.isSuccessful()){
                                                    String teacherName = task.getResult().get("full_name").toString();

                                                    binding.post.setOnClickListener(v -> {

                                                        binding.desc.onEditorAction(EditorInfo.IME_ACTION_DONE);   //for hide keyboard
                                                        binding.dueDate.onEditorAction(EditorInfo.IME_ACTION_DONE);

                                                        if(binding.desc.getText().toString().trim().isEmpty() || binding.dueDate.getText().toString().trim().isEmpty()){
                                                            if(binding.desc.getText().toString().trim().isEmpty()){
                                                                binding.descLayout.setHintTextColor(ColorStateList.valueOf(Color.RED));
                                                                binding.descLayout.setHint("Can not be empty");

                                                            }
                                                            if(binding.dueDate.getText().toString().trim().isEmpty()){
                                                                binding.dueDateLayout.setHintTextColor(ColorStateList.valueOf(Color.RED));
                                                                binding.dueDateLayout.setHint("Can not be empty");
                                                            }
                                                            return;
                                                        }
                                                        binding.descLayout.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.upperBlue)));
                                                        binding.descLayout.setHint("Description");

                                                        binding.dueDateLayout.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.upperBlue)));
                                                        binding.dueDateLayout.setHint("Due Date");

                                                        binding.post.setEnabled(false);

                                                        Snackbar.make(v, "Assignment Sending", Snackbar.LENGTH_SHORT).setBackgroundTint(getResources().getColor(R.color.upperBlue)).setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).show();


                                                        String dateFormat = new SimpleDateFormat("ddMMyyyy_HHmmss", Locale.ENGLISH).format(System.currentTimeMillis());
                                                        String date = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(System.currentTimeMillis());
                                                        Calendar calendar = Calendar.getInstance();
                                                        int hour = calendar.get(Calendar.HOUR);
                                                        if(hour == 0)
                                                            hour = 12;
                                                        final int minutes = calendar.get(Calendar.MINUTE);
                                                        final String am_pm = calendar.get(Calendar.AM_PM)==Calendar.AM ? " AM" : " PM";
                                                        final String currentTime = String.format(Locale.ENGLISH,"%02d:"+"%02d",hour,minutes)+am_pm;

                                                        Map<String,Object> map = new HashMap<>();
                                                        map.put("assignmentUrl", downloadUrl);
                                                        map.put("teacherName", teacherName);
                                                        map.put("email", mAuth.getCurrentUser().getEmail());
                                                        map.put("className", binding.chooseClass.getText().toString());
                                                        map.put("desc", binding.desc.getText().toString());
                                                        map.put("dueDate", binding.dueDate.getText().toString());
                                                        map.put("date", date);
                                                        map.put("time", currentTime);

                                                        db.collection("College_Project").document("teacher").collection("assignments")
                                                                .document("ASGNT_"+dateFormat).set(map)
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if(task.isSuccessful()){
                                                                            Snackbar.make(v, "Assignment sent Successfully", Snackbar.LENGTH_SHORT).setBackgroundTint(getResources().getColor(R.color.upperBlue)).setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).show();

                                                                            new Handler().postDelayed(new Runnable() {
                                                                                @Override
                                                                                public void run() {
                                                                                    CreateAssignmentActivity.super.onBackPressed();
                                                                                }
                                                                            }, 2000);

                                                                        }
                                                                        else
                                                                            Toast.makeText(CreateAssignmentActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });

                                                    });

                                                }
                                            }
                                        });
                            }
                        }
                    });



                } catch (IOException e) {
                    Toast.makeText(CreateAssignmentActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }



            }
        }
    });
    // Request Camera Permissions
    ActivityResultLauncher<String> requestLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean result) {
            if (result) {
               cameraLauncher.launch(captureImageUri);
            } else
                Toast.makeText(CreateAssignmentActivity.this, "Camera Permission Denied\nTo Allow Permission go to\n Setting < App Manager / App Permission", Toast.LENGTH_SHORT).show();
        }
    });

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
        binding.docImage.setImageBitmap(bitmap);
        return baos.toByteArray();
    }

    // Launch Gallery
    ActivityResultLauncher<String> galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {

        if (result != null) {
            //binding.docImage.setImageURI(result);
            getTitleAndSize(result);
            binding.docType.setText("JPEG");

            try {
                Bitmap fullBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), result);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                StorageReference galReference = storageReference.child("Assignment/images/"+binding.docTitle.getText().toString());


                if(baos.toByteArray().length/1024 < 400){
                    binding.docImage.setImageURI(result);
                    uploadTask = galReference.putFile(result);
                }else {
                    byte[] downSizeBytes = compressImage(fullBitmap);
                    double i = Double.parseDouble(String.valueOf(downSizeBytes.length));
                    if (i < 900000) {
                        i /= Math.pow(10, 3);
                        binding.docSize.setText("Size : " + String.format("%.2f", i) + " KB");
                    } else {
                        i /= Math.pow(10, 6);
                        binding.docSize.setText("Size : " + String.format("%.2f", i) + " MB");

                    }
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
                        Toast.makeText(CreateAssignmentActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(!task.isSuccessful())
                        {
                            Toast.makeText(CreateAssignmentActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                        return galReference.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            Uri downloadUrl = task.getResult();
                            Toast.makeText(CreateAssignmentActivity.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                            binding.post.setText("POST");
                            binding.post.setEnabled(true);
                            binding.docTitle.setOnClickListener(view -> {
                                Intent intent = new Intent(Intent.ACTION_VIEW, downloadUrl);
                                startActivity(intent);
                            });

                            db.collection("College_Project").document("teacher").collection("teacher_details")
                                    .document(mAuth.getCurrentUser().getEmail()).get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if(task.isSuccessful()){
                                                String teacherName = task.getResult().get("full_name").toString();

                                                binding.post.setOnClickListener(v -> {

                                                    binding.desc.onEditorAction(EditorInfo.IME_ACTION_DONE);   //for hide keyboard
                                                    binding.dueDate.onEditorAction(EditorInfo.IME_ACTION_DONE);

                                                    if(binding.desc.getText().toString().trim().isEmpty() || binding.dueDate.getText().toString().trim().isEmpty()){
                                                        if(binding.desc.getText().toString().trim().isEmpty()){
                                                            binding.descLayout.setHintTextColor(ColorStateList.valueOf(Color.RED));
                                                            binding.descLayout.setHint("Can not be empty");

                                                        }
                                                        if(binding.dueDate.getText().toString().trim().isEmpty()){
                                                            binding.dueDateLayout.setHintTextColor(ColorStateList.valueOf(Color.RED));
                                                            binding.dueDateLayout.setHint("Can not be empty");
                                                        }
                                                        return;
                                                    }
                                                    binding.descLayout.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.upperBlue)));
                                                    binding.descLayout.setHint("Description");

                                                    binding.dueDateLayout.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.upperBlue)));
                                                    binding.dueDateLayout.setHint("Due Date");

                                                    binding.post.setEnabled(false);

                                                    Snackbar.make(v, "Assignment Sending", Snackbar.LENGTH_SHORT).setBackgroundTint(getResources().getColor(R.color.upperBlue)).setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).show();


                                                    String dateFormat = new SimpleDateFormat("ddMMyyyy_HHmmss", Locale.ENGLISH).format(System.currentTimeMillis());
                                                    String date = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(System.currentTimeMillis());
                                                    Calendar calendar = Calendar.getInstance();
                                                    int hour = calendar.get(Calendar.HOUR);
                                                    if(hour == 0)
                                                        hour = 12;
                                                    final int minutes = calendar.get(Calendar.MINUTE);
                                                    final String am_pm = calendar.get(Calendar.AM_PM)==Calendar.AM ? " AM" : " PM";
                                                    final String currentTime = String.format(Locale.ENGLISH,"%02d:"+"%02d",hour,minutes)+am_pm;

                                                    Map<String,Object> map = new HashMap<>();
                                                    map.put("assignmentUrl", downloadUrl);
                                                    map.put("teacherName", teacherName);
                                                    map.put("email", mAuth.getCurrentUser().getEmail());
                                                    map.put("className", binding.chooseClass.getText().toString());
                                                    map.put("desc", binding.desc.getText().toString());
                                                    map.put("dueDate", binding.dueDate.getText().toString());
                                                    map.put("date", date);
                                                    map.put("time", currentTime);

                                                    db.collection("College_Project").document("teacher").collection("assignments")
                                                            .document("ASGNT_"+dateFormat).set(map)
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if(task.isSuccessful()){
                                                                        Snackbar.make(v, "Assignment sent Successfully", Snackbar.LENGTH_SHORT).setBackgroundTint(getResources().getColor(R.color.upperBlue)).setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).show();

                                                                        new Handler().postDelayed(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                CreateAssignmentActivity.super.onBackPressed();
                                                                            }
                                                                        }, 2000);

                                                                    }
                                                                    else
                                                                        Toast.makeText(CreateAssignmentActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                                                }
                                                            });

                                                });

                                            }
                                        }
                                    });
                        }
                    }
                });



            } catch (IOException e) {
                Toast.makeText(CreateAssignmentActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }






        }

    });
    // Open document Intent
    ActivityResultLauncher<String> docLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
            if (result != null) {
               
                getTitleAndSize(result);

                StorageReference docReference = storageReference.child("Assignment/docs/"+binding.docTitle.getText().toString());
                uploadTask = docReference.putFile(result);
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //  Toast.makeText(CreateAssignmentActivity.this, "Success\n"+taskSnapshot.getUploadSessionUri().toString(), Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CreateAssignmentActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(!task.isSuccessful())
                        {
                            Toast.makeText(CreateAssignmentActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                        return docReference.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            Uri downloadUrl = task.getResult();
                            Toast.makeText(CreateAssignmentActivity.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                            binding.post.setText("POST");
                            binding.post.setEnabled(true);
                            binding.docTitle.setOnClickListener(view -> {
                                Intent intent = new Intent(Intent.ACTION_VIEW, downloadUrl);
                                startActivity(intent);
                            });

                            db.collection("College_Project").document("teacher").collection("teacher_details")
                                    .document(mAuth.getCurrentUser().getEmail()).get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if(task.isSuccessful()){
                                                String teacherName = task.getResult().get("full_name").toString();

                                                binding.post.setOnClickListener(v -> {

                                                    binding.desc.onEditorAction(EditorInfo.IME_ACTION_DONE);   //for hide keyboard
                                                    binding.dueDate.onEditorAction(EditorInfo.IME_ACTION_DONE);

                                                    if(binding.desc.getText().toString().trim().isEmpty() || binding.dueDate.getText().toString().trim().isEmpty()){
                                                        if(binding.desc.getText().toString().trim().isEmpty()){
                                                            binding.descLayout.setHintTextColor(ColorStateList.valueOf(Color.RED));
                                                            binding.descLayout.setHint("Can not be empty");

                                                        }
                                                        if(binding.dueDate.getText().toString().trim().isEmpty()){
                                                            binding.dueDateLayout.setHintTextColor(ColorStateList.valueOf(Color.RED));
                                                            binding.dueDateLayout.setHint("Can not be empty");
                                                        }
                                                        return;
                                                    }
                                                    binding.descLayout.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.upperBlue)));
                                                    binding.descLayout.setHint("Description");

                                                    binding.dueDateLayout.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.upperBlue)));
                                                    binding.dueDateLayout.setHint("Due Date");

                                                    binding.post.setEnabled(false);

                                                    Snackbar.make(v, "Sending Assignment...", Snackbar.LENGTH_SHORT).setBackgroundTint(getResources().getColor(R.color.upperBlue)).setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).show();


                                                    String dateFormat = new SimpleDateFormat("ddMMyyyy_HHmmss", Locale.ENGLISH).format(System.currentTimeMillis());
                                                    String date = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(System.currentTimeMillis());
                                                    Calendar calendar = Calendar.getInstance();
                                                    int hour = calendar.get(Calendar.HOUR);
                                                    if(hour == 0)
                                                        hour = 12;
                                                    final int minutes = calendar.get(Calendar.MINUTE);
                                                    final String am_pm = calendar.get(Calendar.AM_PM)==Calendar.AM ? " AM" : " PM";
                                                    final String currentTime = String.format(Locale.ENGLISH,"%02d:"+"%02d",hour,minutes)+am_pm;

                                                    Map<String,Object> map = new HashMap<>();
                                                    map.put("assignmentUrl", downloadUrl);
                                                    map.put("teacherName", teacherName);
                                                    map.put("email", mAuth.getCurrentUser().getEmail());
                                                    map.put("className", binding.chooseClass.getText().toString());
                                                    map.put("desc", binding.desc.getText().toString());
                                                    map.put("dueDate", binding.dueDate.getText().toString());
                                                    map.put("date", date);
                                                    map.put("time", currentTime);

                                                    db.collection("College_Project").document("teacher").collection("assignments")
                                                            .document("ASGNT_"+dateFormat).set(map)
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if(task.isSuccessful()){
                                                                        Snackbar.make(v, "Assignment sent Successfully", Snackbar.LENGTH_SHORT).setBackgroundTint(getResources().getColor(R.color.upperBlue)).setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).show();

                                                                        new Handler().postDelayed(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                CreateAssignmentActivity.super.onBackPressed();
                                                                            }
                                                                        }, 2000);

                                                                    }
                                                                    else
                                                                        Toast.makeText(CreateAssignmentActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                                                }
                                                            });

                                                });

                                            }
                                        }
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
        int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
        cursor.moveToFirst();
        double i = Double.parseDouble(cursor.getString(sizeIndex));
        if (i < 900000) {
            i /= Math.pow(10, 3);
            binding.docSize.setText("Size : " + String.format("%.2f", i) + " KB");
        } else {
            i /= Math.pow(10, 6);
            binding.docSize.setText("Size : " + String.format("%.2f", i) + " MB");

        }
        binding.docTitle.setText(cursor.getString(nameIndex));
        cursor.close();
        binding.assignmentCard.setVisibility(View.VISIBLE);
        binding.post.setText("WAIT");
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);

        builder.setTitle("Are You Sure ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                CreateAssignmentActivity.super.onBackPressed();
                dialogInterface.dismiss();
            }
        });


        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }
}