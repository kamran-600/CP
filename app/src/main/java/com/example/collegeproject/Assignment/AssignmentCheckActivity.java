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

import com.example.collegeproject.R;
import com.example.collegeproject.databinding.ActivityAssignmentCheckBinding;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AssignmentCheckActivity extends AppCompatActivity {

    ActivityAssignmentCheckBinding binding;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseStorage storage;
    StorageReference storageReference;
    UploadTask uploadTask;
    ActivityResultLauncher<String> docLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
            if (result != null) {
                getTitleOfDocument(result);

                StorageReference docReference = storage.getReference().child("Assignment/checkedDocs/" + binding.AssignmentNameChecked.getText().toString());
                uploadTask = docReference.putFile(result);
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //  Toast.makeText(CreateAssignmentActivity.this, "Success\n"+taskSnapshot.getUploadSessionUri().toString(), Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AssignmentCheckActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(AssignmentCheckActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                        return docReference.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUrl = task.getResult();
                            Toast.makeText(AssignmentCheckActivity.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                            binding.submit.setText("SUBMIT");
                            binding.submit.setEnabled(true);
                            binding.AssignmentNameChecked.setOnClickListener(view -> {
                                Intent intent = new Intent(Intent.ACTION_VIEW, downloadUrl);
                                startActivity(intent);
                            });

                            binding.submit.setOnClickListener(v -> {
                                binding.submit.setEnabled(false);
                                Snackbar.make(v, "Sending Checked Assignment...", Snackbar.LENGTH_SHORT).setBackgroundTint(getResources().getColor(R.color.upperBlue)).setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).show();

                                String date = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(System.currentTimeMillis());
                                Calendar calendar = Calendar.getInstance();
                                int hour = calendar.get(Calendar.HOUR);
                                if (hour == 0)
                                    hour = 12;
                                final int minutes = calendar.get(Calendar.MINUTE);
                                final String am_pm = calendar.get(Calendar.AM_PM) == Calendar.AM ? " AM" : " PM";
                                final String currentTime = String.format(Locale.ENGLISH, "%02d:" + "%02d", hour, minutes) + am_pm;

                                Map<String, Object> map = new HashMap<>();
                                map.put("checkedAssignmentUrl", downloadUrl);
                                map.put("checkedDate", date);
                                map.put("checkedTime", currentTime);

                                db.collection("College_Project").document("teacher").collection("assignments")
                                        .document(getIntent().getStringExtra("id")).collection("submittedAssignments")
                                        .document(binding.stuName.getText().toString())
                                        .set(map, SetOptions.merge())
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Snackbar.make(v, "Assignment sent successfully", Snackbar.LENGTH_SHORT).setBackgroundTint(getResources().getColor(R.color.upperBlue)).setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).show();
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

    // submit check assignment by teacher

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAssignmentCheckBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();

        setSupportActionBar(binding.topAppBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        binding.clear.setOnClickListener(v -> {
            binding.expendableLayout2.setVisibility(View.GONE);
            binding.submit.setEnabled(false);
            binding.submitCheckedCopy.setVisibility(View.VISIBLE);
        });

        Intent intent = getIntent();
        binding.stuName.setText(intent.getStringExtra("stuName"));

        if (getIntent().getByteArrayExtra("imageByte") != null) {
            Bitmap fullBitmap = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("imageByte"), 0, getIntent().getByteArrayExtra("imageByte").length);
            //  fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
            binding.image.setImageBitmap(fullBitmap);
        } else if (getIntent().getIntExtra("resource", R.drawable.cartoon) != 0) {
            binding.image.setImageResource(getIntent().getIntExtra("resource", R.drawable.cartoon));
        }


        binding.submissionDate.setText(intent.getStringExtra("date"));
        binding.submissionTime.setText(intent.getStringExtra("time"));

        // submitted assignment url

        String url = getIntent().getStringExtra("url");

        storageReference = storage.getReferenceFromUrl(url);

        storageReference.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
            @Override
            public void onSuccess(StorageMetadata storageMetadata) {
                binding.AssignmentName.setText(storageMetadata.getName());
                binding.expendableLayout1.setOnClickListener(v -> {
                    Intent intent = new Intent(AssignmentCheckActivity.this, AssignmentOpenActivity.class);
                    intent.putExtra("url", url);
                    intent.putExtra("name", binding.AssignmentName.getText().toString());
                    startActivity(intent);

                });
            }
        });


        binding.submitCheckedCopy.setOnClickListener(v -> docLauncher.launch("application/pdf"));


        // check whether assignment is checked or not if checked then show

        db.collection("College_Project").document("teacher").collection("assignments")
                .document(getIntent().getStringExtra("id")).collection("submittedAssignments")
                .document(binding.stuName.getText().toString()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            AssignmentSubmitModal modal = task.getResult().toObject(AssignmentSubmitModal.class);
                            if (modal != null && modal.getCheckedAssignmentUrl() != null) {
                                StorageReference storageReference2 = storage.getReferenceFromUrl(modal.getCheckedAssignmentUrl());

                                storageReference2.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
                                    @Override
                                    public void onSuccess(StorageMetadata storageMetadata) {
                                        binding.AssignmentNameChecked.setText(storageMetadata.getName());
                                        binding.expendableLayout2.setVisibility(View.VISIBLE);
                                        binding.clear.setVisibility(View.GONE);
                                        binding.submitCheckedCopy.setText("Checked");
                                        binding.submit.setVisibility(View.GONE);
                                        binding.submitCheckedCopy.setEnabled(false);
                                        binding.topAppBar.setTitle("Assignment Checked");
                                        binding.expendableLayout2.setOnClickListener(v -> {
                                            Intent intent = new Intent(AssignmentCheckActivity.this, AssignmentOpenActivity.class);
                                            intent.putExtra("url", modal.getCheckedAssignmentUrl());
                                            intent.putExtra("name", binding.AssignmentNameChecked.getText().toString());
                                            startActivity(intent);

                                        });

                                    }
                                });
                            }

                        }
                    }
                });


    }

    private void getTitleOfDocument(Uri result) {

        Cursor cursor = getContentResolver().query(result, null, null, null, null);
        int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        cursor.moveToFirst();
        binding.AssignmentNameChecked.setText(cursor.getString(nameIndex));
        binding.expendableLayout2.setVisibility(View.VISIBLE);
        binding.submit.setText("WAIT");
        binding.submitCheckedCopy.setVisibility(View.GONE);
        cursor.close();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}