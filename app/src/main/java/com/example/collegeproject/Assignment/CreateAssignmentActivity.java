package com.example.collegeproject.Assignment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.MimeTypeMap;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.collegeproject.R;
import com.example.collegeproject.databinding.ActivityCreateAssignmentBinding;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
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
    View sheetView;
    
 
    
    // Request Camera Permissions
    ActivityResultLauncher<String> requestLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean result) {
            if (result) {
                cameraLauncher.launch(cameraIntent);
            } else
                Toast.makeText(CreateAssignmentActivity.this, "Camera Permission Denied\nTo Allow Permission go to\n Setting < App Manager / App Permission", Toast.LENGTH_SHORT).show();
        }
    });
    // Launch Gallery
    ActivityResultLauncher<String> galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {

        if (result != null) {

            binding.docImage.setImageURI(result);
            getTitleAndSize(result);

        }

    });
    // Open document Intent
    ActivityResultLauncher<String> docLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
            if (result != null) {

                getTitleAndSize(result);

            }

        }
    });

    @SuppressLint("QueryPermissionsNeeded")
=======
    FirebaseStorage storage;
    StorageReference storageReference;
    UploadTask uploadTask;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    Uri captureImageUri;
    File image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAssignmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        binding.clear.setOnClickListener(view -> {
            binding.assignmentCard.setVisibility(View.GONE);
        });

        binding.post.setOnClickListener(view -> {
            binding.post.setEnabled(false);
            Snackbar.make(view, "Assignment sent Successfully", Snackbar.LENGTH_SHORT).setBackgroundTint(getResources().getColor(R.color.upperBlue)).setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    onBackPressed();
                }
            }, 1000);

        });


        setSupportActionBar(binding.postBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.clear);

        bottomSheetDialog = new BottomSheetDialog(CreateAssignmentActivity.this, R.style.BottomSheetTheme);
        sheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.class_bottom_dialog, findViewById(R.id.bottom_sheet_dailog));
        bottomSheetDialog.setContentView(sheetView);


        binding.chooseClass.setOnClickListener(view -> {

            bottomSheetDialog.show();
        });
        RadioGroup csRadioGroup = sheetView.findViewById(R.id.csradiogroup);
        csRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (i == R.id.cs1) {
                    binding.chooseClass.setText("CS 1st Year");
                    bottomSheetDialog.dismiss();
                }
                if (i == R.id.cs2) {
                    binding.chooseClass.setText("CS 2nd Year");
                    bottomSheetDialog.dismiss();
                }
                if (i == R.id.cs3) {
                    binding.chooseClass.setText("CS 3rd Year");
                    bottomSheetDialog.dismiss();
                }
                if (i == R.id.cs4) {
                    binding.chooseClass.setText("CS 4th Year");
                    bottomSheetDialog.dismiss();
                }
            }
        });

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        binding.dobLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.dobLayout.setEndIconTintList(ContextCompat.getColorStateList(CreateAssignmentActivity.this, R.color.calender_icon));
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
                String date = day + "/" + month + "/" + year;
                binding.dob.setText(date);
            }
        };

        binding.cameraImg.setOnClickListener(view -> {
            binding.desc.onEditorAction(EditorInfo.IME_ACTION_DONE);   //for hide keyboard

            String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss",Locale.ENGLISH).format(new Date());

            ContentValues value = new ContentValues();
            value.put(MediaStore.Images.Media.TITLE,timeStamp);
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
            galleryLauncher.launch("image/*");
        });

        binding.documentImg.setOnClickListener(view -> {
            binding.desc.onEditorAction(EditorInfo.IME_ACTION_DONE);   //for hide keyboard
            docLauncher.launch("application/*");
        });

    }





    // Launch Camera
     /*
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                cameraLauncher.launch(cameraIntent);
            }
            else{
                Toast.makeText(CreateAssignmentActivity.this, "Camera Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }

    }

     */
/*
    ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {

            if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                Toast.makeText(CreateAssignmentActivity.this, "return ", Toast.LENGTH_SHORT).show();
                binding.docImage.setImageURI(captureImageUri);
             //   binding.assignmentCard.setVisibility(View.VISIBLE);
              //  binding.docImage.setImageBitmap(bitmap);
              *//*  Bundle bundle = result.getData().getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");
                binding.UserImage.setImageBitmap(bitmap);*//*


                //BitMap to URI
                *//* ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,bytes);
                bytes.toByteArray();
                *//*

                getTitleAndSize(captureImageUri);

               *//* String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "AssignmentImg", null);
                binding.docImage.setImageURI(Uri.parse(path));
                getTitleAndSize(Uri.parse(path));
                /*binding.docTitle.setOnClickListener(view -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(path));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                });*//*
            }
        }
    });*/

    ActivityResultLauncher<Uri> cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicture(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean result) {
            Toast.makeText(CreateAssignmentActivity.this, "return\n"+ result+ captureImageUri , Toast.LENGTH_SHORT).show();
            if(result){
                binding.docImage.setImageURI(captureImageUri);
                getTitleAndSize(captureImageUri);
                binding.docImage.setOnClickListener(v -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW, captureImageUri);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                });

                StorageReference galReference = storageReference.child("images/"+binding.docTitle.getText().toString());
                uploadTask = galReference.putFile(captureImageUri);
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(CreateAssignmentActivity.this, "Success \n"+taskSnapshot.getUploadSessionUri().toString(), Toast.LENGTH_LONG).show();
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
                            binding.docTitle.setOnClickListener(view -> {
                                Intent intent = new Intent(Intent.ACTION_VIEW, downloadUrl);
                                startActivity(intent);
                            });
                            String dateFormat = new SimpleDateFormat("ddMMyyyy_HHmmss", Locale.ENGLISH).format(System.currentTimeMillis());
                            Map<String,Object> map = new HashMap<>();
                            map.put("assignmentUrl_"+ dateFormat , downloadUrl);
                            db.collection("College_Project").document("teacher").collection("teacher_details")
                                    .document(mAuth.getCurrentUser().getEmail()).set(map, SetOptions.merge())
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(CreateAssignmentActivity.this, "Successfully saved url", Toast.LENGTH_SHORT).show();
                                            }
                                            else
                                                Toast.makeText(CreateAssignmentActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
                });


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
    // Launch Gallery
    ActivityResultLauncher<String> galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {

        if (result != null) {
            binding.docImage.setImageURI(result);
            getTitleAndSize(result);

            StorageReference galReference = storageReference.child("images/"+binding.docTitle.getText().toString());
            uploadTask = galReference.putFile(result);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(CreateAssignmentActivity.this, "Success \n"+taskSnapshot.getUploadSessionUri().toString(), Toast.LENGTH_LONG).show();
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
                        binding.docTitle.setOnClickListener(view -> {
                            Intent intent = new Intent(Intent.ACTION_VIEW, downloadUrl);
                            startActivity(intent);
                        });
                        String dateFormat = new SimpleDateFormat("ddMMyyyy_HHmmss", Locale.ENGLISH).format(System.currentTimeMillis());
                        Map<String,Object> map = new HashMap<>();
                        map.put("assignmentUrl_"+ dateFormat , downloadUrl);
                        db.collection("College_Project").document("teacher").collection("teacher_details")
                                .document(mAuth.getCurrentUser().getEmail()).set(map, SetOptions.merge())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(CreateAssignmentActivity.this, "Successfully saved url", Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                            Toast.makeText(CreateAssignmentActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }
            });







        }

    });
    // Open document Intent
    ActivityResultLauncher<String> docLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
            if (result != null) {
               
                getTitleAndSize(result);

                StorageReference docsReference = storageReference.child("docs/"+binding.docTitle.getText().toString());
                uploadTask = docsReference.putFile(result);
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(CreateAssignmentActivity.this, "Success \n"+taskSnapshot.getUploadSessionUri().toString(), Toast.LENGTH_LONG).show();
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
                        return docsReference.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            Uri downloadUrl = task.getResult();
                            binding.docTitle.setOnClickListener(view -> {
                                Intent intent = new Intent(Intent.ACTION_VIEW, downloadUrl);
                                startActivity(intent);
                            });
                            String dateFormat = new SimpleDateFormat("ddMMyyyy_hhmmss", Locale.ENGLISH).format(System.currentTimeMillis());
                            Map<String,Object> map = new HashMap<>();
                            map.put("assignmentUrl_"+ dateFormat , downloadUrl);
                            db.collection("College_Project").document("teacher").collection("teacher_details")
                                    .document(mAuth.getCurrentUser().getEmail()).set(map, SetOptions.merge())
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(CreateAssignmentActivity.this, "Successfully saved url", Toast.LENGTH_SHORT).show();
                                            }
                                            else
                                                Toast.makeText(CreateAssignmentActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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