package com.example.collegeproject.feed;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.collegeproject.R;
import com.example.collegeproject.databinding.ActivityFeedPostBinding;
import com.example.collegeproject.studentData.StudentData;
import com.example.collegeproject.teacherData.TeacherData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Blob;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class FeedPostActivity extends AppCompatActivity {

    ActivityFeedPostBinding binding;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseStorage storage;
    StorageReference storageReference;
    Uri captureImageUri;
    String role, roll_number, email;
    byte[] feedImageBytes;
    Blob feedImageByteBlob;
    ActivityResultLauncher<String> galLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
            if (result != null) {
                binding.imageCardImage.setImageURI(result);
                binding.imageCard.setVisibility(View.VISIBLE);
                binding.imageCard.setOnClickListener(v -> {
                    String[] help = result.toString().split("media");
                    Uri uri = Uri.parse(help[0] + "media/external/images/media" + help[help.length - 1]);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                });

                try {

                    Bitmap fullBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), result);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                    if (baos.toByteArray().length / 1024 < 400) {
                        binding.imageCardImage.setImageURI(result);
                        feedImageBytes = baos.toByteArray();
                    } else {
                        feedImageBytes = compressImage(fullBitmap);
                    }
                    binding.clear.setOnClickListener(v -> {
                        binding.imageCard.setVisibility(View.GONE);
                        binding.imageCardImage.setImageURI(null);
                        feedImageByteBlob = null;
                        feedImageBytes = null;
                    });

                } catch (IOException e) {
                    Toast.makeText(FeedPostActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        }
    });
    ActivityResultLauncher<Uri> cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicture(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean result) {
            if (result) {
                binding.imageCardImage.setImageURI(captureImageUri);
                binding.imageCard.setVisibility(View.VISIBLE);
                binding.imageCard.setOnClickListener(v -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW, captureImageUri);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                });
                binding.clear.setOnClickListener(v -> {
                    binding.imageCard.setVisibility(View.GONE);
                    binding.imageCardImage.setImageURI(null);
                    feedImageByteBlob = null;
                    feedImageBytes = null;
                });


                try {

                    Bitmap fullBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), captureImageUri);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                    if (baos.toByteArray().length / 1024 < 400) {
                        binding.imageCardImage.setImageURI(captureImageUri);
                        feedImageBytes = baos.toByteArray();
                    } else {
                        feedImageBytes = compressImage(fullBitmap);
                    }

                    binding.clear.setOnClickListener(v -> {
                        binding.imageCard.setVisibility(View.GONE);
                        binding.imageCardImage.setImageURI(null);
                        feedImageByteBlob = null;
                        feedImageBytes = null;
                    });

                } catch (IOException e) {
                    Toast.makeText(FeedPostActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }


            }
        }
    });
    ActivityResultLauncher<String> requestLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean result) {
            if (result) {
                cameraLauncher.launch(captureImageUri);
            } else
                Toast.makeText(FeedPostActivity.this, "Camera Permission Denied\nTo Allow Permission go to\n Setting < App Manager / App Permission", Toast.LENGTH_SHORT).show();
        }
    });

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        setSupportActionBar(binding.postBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

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


        db.collection("College_Project").document("student").collection("4th Year").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot rollNo : task.getResult().getDocuments()) {
                                StudentData data = rollNo.toObject(StudentData.class);
                                if (data.getEmail().equals(mAuth.getCurrentUser().getEmail())) {
                                    binding.userName.setText(data.getFull_name());
                                    role = data.getRole();
                                    roll_number = data.getRoll_number();

                                    if (data.getProfileImageBlob() != null) {
                                        Bitmap fullBitmap = BitmapFactory.decodeByteArray(data.getProfileImageBlob().toBytes(), 0, data.getProfileImageBlob().toBytes().length);
                                        fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                                        binding.userProfile.setImageBitmap(fullBitmap);

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
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot rollNo : task.getResult().getDocuments()) {
                                StudentData data = rollNo.toObject(StudentData.class);
                                if (data.getEmail().equals(mAuth.getCurrentUser().getEmail())) {
                                    binding.userName.setText(data.getFull_name());
                                    role = data.getRole();
                                    roll_number = data.getRoll_number();

                                    if (data.getProfileImageBlob() != null) {
                                        Bitmap fullBitmap = BitmapFactory.decodeByteArray(data.getProfileImageBlob().toBytes(), 0, data.getProfileImageBlob().toBytes().length);
                                        fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                                        binding.userProfile.setImageBitmap(fullBitmap);

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
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot rollNo : task.getResult().getDocuments()) {
                                StudentData data = rollNo.toObject(StudentData.class);
                                if (data.getEmail().equals(mAuth.getCurrentUser().getEmail())) {
                                    binding.userName.setText(data.getFull_name());

                                    role = data.getRole();
                                    roll_number = data.getRoll_number();

                                    if (data.getProfileImageBlob() != null) {
                                        Bitmap fullBitmap = BitmapFactory.decodeByteArray(data.getProfileImageBlob().toBytes(), 0, data.getProfileImageBlob().toBytes().length);
                                        fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                                        binding.userProfile.setImageBitmap(fullBitmap);

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
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot stuRollNo : task.getResult().getDocuments()) {
                                StudentData data = stuRollNo.toObject(StudentData.class);
                                if (data.getEmail().equals(mAuth.getCurrentUser().getEmail())) {
                                    binding.userName.setText(data.getFull_name());

                                    role = data.getRole();
                                    roll_number = data.getRoll_number();

                                    if (data.getProfileImageBlob() != null) {
                                        Bitmap fullBitmap = BitmapFactory.decodeByteArray(data.getProfileImageBlob().toBytes(), 0, data.getProfileImageBlob().toBytes().length);
                                        fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                                        binding.userProfile.setImageBitmap(fullBitmap);

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
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot teacherEmail : task.getResult().getDocuments()) {
                                TeacherData data = teacherEmail.toObject(TeacherData.class);
                                if (data.getEmail().equals(mAuth.getCurrentUser().getEmail())) {
                                    binding.userName.setText(data.getFull_name());
                                    role = data.getRole();
                                    email = data.getEmail();

                                    if (data.getProfileImageBlob() != null) {
                                        Bitmap fullBitmap = BitmapFactory.decodeByteArray(data.getProfileImageBlob().toBytes(), 0, data.getProfileImageBlob().toBytes().length);
                                        fullBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                                        binding.userProfile.setImageBitmap(fullBitmap);

                                    }
                                }
                            }
                        }
                    }
                });


        // post the feed
        binding.feedPost.setOnClickListener(v -> {

            if (binding.feedMsg.getText().toString().isEmpty() && feedImageBytes == null)
                return;

            binding.feedMsg.onEditorAction(EditorInfo.IME_ACTION_DONE);   //for hide keyboard
            binding.feedPost.setEnabled(false);

            Snackbar.make(v, "Feed Sending", Snackbar.LENGTH_SHORT).setBackgroundTint(getResources().getColor(R.color.upperBlue)).setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).show();
            String dateFormat = new SimpleDateFormat("ddMMyyyy_HHmmss", Locale.ENGLISH).format(System.currentTimeMillis());
            String date = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(System.currentTimeMillis());
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR);
            if (hour == 0)
                hour = 12;
            final int minutes = calendar.get(Calendar.MINUTE);
            final String am_pm = calendar.get(Calendar.AM_PM) == Calendar.AM ? " AM" : " PM";
            final String currentTime = String.format(Locale.ENGLISH, "%02d:" + "%02d", hour, minutes) + am_pm;

            if (feedImageBytes != null) {
                feedImageByteBlob = Blob.fromBytes(feedImageBytes);
            }

            Map<String, Object> map = new HashMap<>();

            if (email != null) {
                map.put("email", email);
            }
            if (roll_number != null) {
                map.put("roll_number", roll_number);
            }
            map.put("feedImageByteBlob", feedImageByteBlob);
            map.put("senderName", binding.userName.getText().toString());
            map.put("role", role);
            map.put("feedMsg", binding.feedMsg.getText().toString());
            map.put("date", date);
            map.put("time", currentTime);

            db.collection("College_Project").document("feed").collection("feed_details").document("feed_" + dateFormat)
                    .set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Snackbar.make(v, "Feed sent Successfully", Snackbar.LENGTH_SHORT).setBackgroundTint(getResources().getColor(R.color.upperBlue)).setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        FeedPostActivity.super.onBackPressed();
                                    }
                                }, 2000);
                            } else
                                Toast.makeText(FeedPostActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        binding.gallery.setOnClickListener(v -> {
            galLauncher.launch("image/*");
        });


        binding.camera.setOnClickListener(view -> {

            binding.feedMsg.onEditorAction(EditorInfo.IME_ACTION_DONE);   //for hide keyboard

            String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss", Locale.ENGLISH).format(new Date());
            ContentValues value = new ContentValues();
            value.put(MediaStore.Images.Media.DISPLAY_NAME, timeStamp);
            value.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            captureImageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, value);

            if (ActivityCompat.checkSelfPermission(FeedPostActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestLauncher.launch(Manifest.permission.CAMERA);
            } else {
                cameraLauncher.launch(captureImageUri);
            }
        });

    }

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
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
        binding.imageCardImage.setImageBitmap(bitmap);
        return baos.toByteArray();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (!Objects.requireNonNull(binding.feedMsg.getText()).toString().equals("") || binding.imageCard.getVisibility() == View.VISIBLE) {

            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);

            builder.setTitle("Are You Sure ?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    FeedPostActivity.super.onBackPressed();
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
        } else {
            FeedPostActivity.super.onBackPressed();
        }

    }
}